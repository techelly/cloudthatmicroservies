package com.roifmr.presidents.restcontroller;

import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.deleteFromTables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.roifmr.presidents.business.President;

/**
 * These test cases assert the behavior of our deployed service. To do that we will
 * start the application up and listen for a connection like it would do in
 * production, and then send an HTTP request and assert the response.
 * 
 * Note that Spring Boot has provided a TestRestTemplate for you automatically.
 * All you have to do is @Autowired it.
 * 
 * Instead of using a specific port number when constructing the server URL, simply use a 
 * relative URL. For example:
 * 	   String request = "/warehouse/presidents";
 * 
 * By using webEnvironment = WebEnvironment.RANDOM_PORT in SpringBootTest, a random port 
 * will be selected and added to the URL automatically.
 * The port that is being used appears in the Console window when the application is run.
 *
 * Also note the use of @Sql on the class to execute the database setup scripts before
 * each test case. Because @SpringBootTest runs Tomcat in a different thread than the
 * test cases themselves, @Transactional has no effect here. So we need to re-initialize 
 * the database before each test case.
 * Just another reason not to use the production database in testing :)
 * 
 * The database scripts referenced in @Sql are in the folder src/test/resources
 * 
 * For some test cases we'll need to query or modify the database directly, so we'll
 * use Spring's JdbcTestUtils class, which has methods like countRowsInTable() and
 * deleteFromTables().
 *  
 * Note that Spring Boot needs to find an application class in order to scan
 * for components. The trivial class com.fidelity.TestApplication in src/test/java 
 * contains the @SpringBootApplication annotation, which triggers the component scan.
 * 
 * @author ROI Instructor
 */

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"},
	 executionPhase = ExecutionPhase.AFTER_TEST_METHOD) 
public class PresidentsControllerE2eTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;  // for executing SQL queries
	
	// Initialize a few Presidents for expected values in test cases
	private President president1 = new President(1,"George","Washington",1789,1797,"georgewashington.jpg",
			"On April 30, 1789, George Washington, standing on the balcony of Federal Hall on Wall Street "
			+ "in New York, took his oath of office as the first President of the United States. "
			+ "\"As the first of every thing, in our situation will serve to establish a Precedent,\" "
			+ "he wrote James Madison, \"it is devoutly wished on my part, that these precedents may be "
			+ "fixed on true principles.\" Born in 1732 into a Virginia planter family, he learned the "
			+ "morals, manners, and body of knowledge requisite for an 18th century Virginia gentleman.");
	
	private President president10 = new President(10,"John","Tyler",1841,1845,"johntyler.jpg",
			"Dubbed \"His Accidency\" by his detractors, John Tyler was the first Vice President to be "
			+ "elevated to the office of President by the death of his predecessor. Born in Virginia in "
			+ "1790, he was raised believing that the Constitution must be strictly construed. He never "
			+ "wavered from this conviction. He attended the College of William and Mary and studied law.");

	/**
	 * This test verifies the PresidentsRestController can query successfully for all the
	 * Presidents in the database
	 */
	@Test
	public void testQueryForAllPresidents() {
		// get the row count from the Presidents table
		int presidentCount = countRowsInTable(jdbcTemplate, "presidents");
		
		String request = "/presidents";

		ResponseEntity<President[]> response = 
				restTemplate.getForEntity(request, President[].class);
		
		// verify the response HTTP status is OK
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// verify that the service returned all Presidents in the database
		President[] responsePresidents = response.getBody();
		assertEquals(presidentCount, responsePresidents.length); 
		
		// spot-check a few Presidents
		assertEquals(president1, responsePresidents[0]);
		assertEquals(president10, responsePresidents[9]);
	}

	/**
	 * This test verifies the PresidentsRestController successfully handles the case
	 * where there are no Presidents in the Presidents.
	 */
	@Test
	public void testQueryForAllPresidents_NoPresidentsInDb() {
		// delete all rows from the Presidents table
		deleteFromTables(jdbcTemplate, "presidents");
		
		String request = "/presidents";

		ResponseEntity<String> response = restTemplate.getForEntity(request, String.class);
		
		// verify the response HTTP status is 204 (NO_CONTENT)
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
		// verify that the response body is empty
		assertTrue(response.getBody() == null || response.getBody().isBlank());
	}

	/**
	 * This test verifies the PresidentsRestController can query successfully for one President.
	 */
	@Test
	public void testQueryForPresidentById() {
		String request = "/presidents/10";

		ResponseEntity<BiographyDto> response = 
				restTemplate.getForEntity(request, BiographyDto.class);
		
		// verify the response HTTP status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// verify that the service returned the correct President
		assertEquals(president10.getBiography(), response.getBody().getBio());
	}

	/**
	 * This test verifies the PresidentsRestController handles a query for a non-existent President.
	 */
	@Test
	public void testQueryForPresidentById_NotPresent() {
		String request = "/presidents/99";

		ResponseEntity<BiographyDto> response = 
				restTemplate.getForEntity(request, BiographyDto.class);
		
		// verify the response HTTP status
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
