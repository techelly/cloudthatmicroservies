package com.fidelity.integration;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import org.springframework.transaction.annotation.Transactional;
import com.fidelity.business.Gadget;
import com.fidelity.business.Widget;

/**
 * This is an integration test for WarehouseDaoMyBatisImpl.
 * 
 * Notice that the database schema and data scripts are run after setting the DataSource. 
 * This insures the database is in a known state prior to running the tests.
 * The database scripts are in the folder: src/test/resources/
 *  
 * To verify that the DAO is actually working, we'll need to query the database 
 * directly, so we'll use Spring's JdbcTestUtils class, which has methods like 
 * countRowsInTable() and deleteFromTables().
 *
 * Notice the use of @Transactional to automatically rollback 
 * any changes to the database that may be made in a test.
 * 
 * Note that Spring Boot needs to find an application class in order to scan
 * for components. The trivial class com.fidelity.TestApplication in src/test/java 
 * contains the @SpringBootApplication annotation, which triggers the component scan.
 * 
 * @author ROI Instructor
 *
 */
@SpringBootTest
@Transactional
public class WarehouseDaoMyBatisImplUnitTest {

	@Autowired
	private WarehouseDao dao;

	@Autowired
	private JdbcTemplate jdbcTemplate;  // for executing SQL queries
	
	// Because the test database is tiny, we can check all products.
	// If the database was larger, we could just spot-check a few products.
	// These are the values defined in the data-dev.sql script.
	
	private static List<Widget> allWidgets = Arrays.asList(
		new Widget(1, "Low Impact Widget", 12.99, 2, 3),
		new Widget(2, "Medium Impact Widget", 42.99, 5, 5),
		new Widget(3, "High Impact Widget", 89.99, 10, 8)
	);

	private static List<Gadget> allGadgets = Arrays.asList(
		new Gadget(1, "Two Cylinder Gadget", 19.99, 2), 
		new Gadget(2, "Four Cylinder Gadget", 29.99, 4), 
		new Gadget(3, "Eight Cylinder Gadget", 49.99, 8) 
	);

	// ***** Widget Tests *****
	@Test
	void testGetAllWidgets() {
		List<Widget> widgets = dao.getAllWidgets();
		
		// verify the Widgets from the database 
		assertEquals(allWidgets, widgets);
	}

	@Test
	void testGetWidget() {
		Widget widget = dao.getWidget(1);
		
		// verify the Widget from the database
		assertEquals(allWidgets.get(0), widget);
	}

	@Test
	void testDeleteWidget() {
		int id = 1;
		// verify that Widget 1 is in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id), 1);

		int rows = dao.deleteWidget(id);
		
		// verify that 1 row was deleted
		assertEquals(1, rows);
		
		// verify that Widget 1 is no longer in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id), 0);
	}

	@Test
	void testInsertWidget() {
		int id = 42;
		
		// verify that Widget with id = 42 is NOT in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id), 0);

		Widget w = new Widget(id, "Test widget", 4.52, 20, 10);

		int rows = dao.insertWidget(w);
		
		// verify that 1 row was inserted
		assertEquals(1, rows);
		
		// verify that Widget with id = 42 IS in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id), 1);
	}

	@Test
	void testUpdateWidget() {
		int id = 1;
		
		// load the original Widget from the database
		Widget originalWidget = loadWidgetFromDb(id);
		
		// modify the local Widget
		originalWidget.setPrice(originalWidget.getPrice() + 1.0);

		int rows = dao.updateWidget(originalWidget);
		
		// verify that 1 row was updated
		assertEquals(1, rows);
		
		// reload widget from database 
		Widget updatedWidget = loadWidgetFromDb(id);
		
		// verify that only the price was updated in the database
		assertEquals(updatedWidget, originalWidget);

	}

	// ***** Gadget Tests *****
	@Test
	void testGetAllGadgets() {
		List<Gadget> gadgets = dao.getAllGadgets();
		
		// verify the Gadgets from the database
		assertEquals(allGadgets, gadgets);
	}

	@Test
	void testGetGadget() {
		Gadget gadget = dao.getGadget(1);
		
		// verify the Gadget from the database
		assertEquals(allGadgets.get(0), gadget);
	}

	@Test
	void testDeleteGadget() {
		int id = 1;
		
		// verify that Gadget with id = 1 is in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "gadgets", "id = " + id), 1);

		int rows = dao.deleteGadget(id);
		
		// verify that 1 row was deleted
		assertEquals(1, rows);
		
		// verify that Gadget 1 is no longer in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "gadgets", "id = " + id), 0);
	}

	@Test
	void testInsertGadget() {
		int id = 42;
		
		// verify the Gadget with id = 42 is NOT in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "gadgets", "id = " + id), 0);

		Gadget g = new Gadget(id, "Test Gadget", 99.99, 2);

		int rows = dao.insertGadget(g);
		
		// verify that 1 row was inserted
		assertEquals(1, rows);
		
		// verify that the Gadget with id = 42 IS in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "gadgets", "id = " + id), 1);
	}

	@Test
	void testUpdateGadget() {
		int id = 1;
		
		// load the Gadget from the database
		Gadget originalGadget = loadGadgetFromDb(id);
		
		// modify the local Gadget
		originalGadget.setCylinders(originalGadget.getCylinders() * 2);

		int rows = dao.updateGadget(originalGadget);
		
		// verify that 1 row was updated
		assertEquals(1, rows);
				
		// reload gadget from database 
		Gadget updatedGadget = loadGadgetFromDb(id);
		
		// verify that only the cylinder count was updated in the database
		assertEquals(updatedGadget, originalGadget);

	}

	// ***** Utility Methods Used in the Tests *****
	
	// Load the Widget with the specified id from the database
	private Widget loadWidgetFromDb(int id) {
		String sql = "select * from widgets where id = " + id;
		
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
			new Widget(rs.getInt("id"), rs.getString("description"), rs.getDouble("price"), 
					   rs.getInt("gears"), rs.getInt("sprockets")));
	}
	
	// Load the Gadget with the specified id from the database
	private Gadget loadGadgetFromDb(int id) {
		String sql = "select * from gadgets where id = " + id;
		
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
			new Gadget(rs.getInt("id"), rs.getString("description"), 
					   rs.getDouble("price"), rs.getInt("cylinders")));
	}
	
}
