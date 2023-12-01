package com.fidelity.business.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import org.springframework.transaction.annotation.Transactional;

import com.fidelity.business.Gadget;
import com.fidelity.business.Widget;

/**
 * WarehouseBusinessServiceIntegrationTest is an integration test for WarehouseBusinessServiceImpl.
 * 
 * Notice that the database schema and data scripts are run
 * after setting the DataSource. 
 * The database scripts are in the folder: src/test/resources/
 * This guarantees that the database is in a known state before the tests are run.
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
class WarehouseBusinessServiceIntegrationTest {
	@Autowired
	WarehouseBusinessService service;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;  // for executing SQL queries
	
	// Because the test database is tiny, we can check all products.
	// If the database was larger, we could just spot-check a few products.
	
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
	
	@Test
	void basicSanityTest() {
		assertNotNull(service);
	}

	// ***** Widget Test *****
	@Test
	void testGetAllWidgets() {
		List<Widget> widgets = service.findAllWidgets();
		
		// verify the collection of Widgets
		assertEquals(allWidgets, widgets);
	}

	@Test
	void testFindWidgetById() {
		int id = 1;
		Widget firstWidget = new Widget(id, "Low Impact Widget", 12.99, 2, 3);
		
		Widget w = service.findWidgetById(id);
		
		// verify the Widget
		assertEquals(firstWidget, w);
	}

	@Test
	void testDeleteWidget() {
		int id = 1;
		
		// verify that Widget 1 IS in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id), 1);

		int rows = service.removeWidget(id);
		
		// verify that 1 row was deleted
		assertEquals(1, rows);
		
		// verify that Widget 1 is NOT in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id), 0);
	}
	
	@Test
	void testInsertWidget() {
		int id = 42;
		
		// verify Widget 42 is NOT in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id), 0);

		Widget w = new Widget(id, "Test widget", 4.52, 20, 10);

		int rows = service.addWidget(w);
		
		// verify that 1 row was inserted		
		assertEquals(1, rows);
		
		// verify that Widget 42 iIS in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "widgets", "id = " + id), 1);
	}
	
	@Test
	void testUpdateWidget() {
		int id = 1;
		
		// load Widget 1 from teh database
		Widget localWidget = loadWidgetFromDb(id);
		
		// modify the local Widget 1
		localWidget.setPrice(localWidget.getPrice() + 1.0);

		int rows = service.modifyWidget(localWidget);
		
		// verify that 1 row was updated
		assertEquals(1, rows);
		
		// reload widget from database 
		Widget updatedWidget = loadWidgetFromDb(id);
		
		// verify that only the price was updated
		assertEquals(updatedWidget, localWidget);

	}
	// ***** Gadget Test *****
	
	@Test
	void testGetAllGadgets() {
		List<Gadget> gadgets = service.findAllGadgets();
		
		// verify teh collection of Gadgets
		assertEquals(allGadgets, gadgets);
	}

	@Test
	void testFindGadgetById() {
		int id = 1;
		Gadget firstGadget = new Gadget(1, "Two Cylinder Gadget", 19.99, 2);
		
		Gadget w = service.findGadgetById(id);
		
		// verify Gadget 1
		assertEquals(firstGadget, w);
	}

	@Test
	void testDeleteGadget() {
		int id = 1;
		// verify that Gadget 1 IS in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "gadgets", "id = " + id), 1);

		int rows = service.removeGadget(id);
		
		// verify that 1 row was deleted
		assertEquals(1, rows);
		
		// verify that Gadget 1 is NOT in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "gadgets", "id = " + id), 0);
	}
	
	@Test
	void testInsertGadget() {
		int id = 42;
		
		// verify that Gadget 42 is NOT in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "gadgets", "id = " + id), 0);

		Gadget g = new Gadget(id, "Two Cylinder Gadget", 19.99, 2);

		int rows = service.addGadget(g);
		
		// verify that 1 row was inserted
		assertEquals(1, rows);
		
		// verify that Gadget 42 IS in the database
		assertEquals(countRowsInTableWhere(jdbcTemplate, "gadgets", "id = " + id), 1);
	}
	
	@Test
	void testUpdateGadget() {
		int id = 1;
		// load Gadget 1 from database
		Gadget g = loadGadgetFromDb(id);
		
		// modify the local Gadget
		g.setPrice(g.getPrice() + 1.0);

		int rows = service.modifyGadget(g);
		
		// verify that 1 row was updated
		assertEquals(1, rows);
		
		// reload widget from database 
		Gadget updatedGadget = loadGadgetFromDb(id);
		
		// verify that only the price was updated
		assertEquals(updatedGadget, g);

	}

	// ***** Utility Methods to Load a Widget or Gadget from the Database *****
	private Widget loadWidgetFromDb(int id) {
		String sql = "select * from widgets where id = " + id;
		
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
			new Widget(rs.getInt("id"), rs.getString("description"), rs.getDouble("price"), 
					   rs.getInt("gears"), rs.getInt("sprockets")));
	}
	
	private Gadget loadGadgetFromDb(int id) {
		String sql = "select * from gadgets where id = " + id;
		
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
			new Gadget(rs.getInt("id"), rs.getString("description"), 
					   rs.getDouble("price"), rs.getInt("cylinders")));
	}
	

}
