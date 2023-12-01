package com.fidelity.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fidelity.business.Book;
import com.fidelity.business.BookList;

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
 * 	   String request = "/library/books";
 * 
 * By using webEnvironment = WebEnvironment.RANDOM_PORT in SpringBootTest, a random port 
 * will be selected and added to the URL automatically.
 * The port that is being used appears in the Console window when the application is run.
 * 
 * @author ROI Instructor
 */

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class LibraryControllerE2eTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	// Initialize a few Library for expected values in test cases
	private Book book1 = new Book("Cryptonomicon", "Neal Stephenson", "978-0060512804");
	private Book book2 = new Book("The Overstory", "Richard Powers", "978-0393356687");

	/**
	 * Verify the LibraryController can query successfully for all the
	 * Books in the database
	 */
	@Test
	public void testQueryForAllBooks_Success() {
		// get the row count from the Library table
		int bookCount = 5;
		
		String request = "/library/books";

		ResponseEntity<BookList> response = 
				restTemplate.getForEntity(request, BookList.class);
		
		// verify the response HTTP status is OK
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		// verify that the service returned all Library in the database
		List<Book> responseLibrary = response.getBody().getItems();
		assertEquals(bookCount, responseLibrary.size()); 
		
		// spot-check a few books
		assertTrue(responseLibrary.contains(book1));
		assertTrue(responseLibrary.contains(book2));
	}

	/**
	 * Verify the LibraryController can query successfully for one Book.
	 */
	@Test
	public void testQueryForBookById_Success() {
		String request = "/library/book/978-0393356687";

		ResponseEntity<Book> response = 
				restTemplate.getForEntity(request, Book.class);
		
		// verify the response HTTP status
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		// verify that the service returned the correct Book
		assertEquals(book2, response.getBody());
	}

	/**
	 * Verify the LibraryController handles a query for a non-existent Book.
	 */
	@Test
	public void testQueryForBookById_NotPresent() {
		String request = "/library/book/000-0000000000";

		ResponseEntity<Book> response = 
				restTemplate.getForEntity(request, Book.class);
		
		// verify the response HTTP status
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	/**
	 * Verify the LibraryController handles a query with an invalid ISBN number.
	 */
	@Test
	public void testQueryForBookById_InvalidIsbnNumber() {
		String request = "/library/book/978-000";

		ResponseEntity<Book> response = 
				restTemplate.getForEntity(request, Book.class);
		
		// verify the response HTTP status
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	/**
	 * Verify the LibraryController can successfully add a Book.
	 */
	@Test
	public void testAddBook_Success() {
		Book newBook = new Book("Moby Dick", "Herman Melville", "979-8373322577");
		String request = "/library/book";

		ResponseEntity<Void> postResponse = 
				restTemplate.postForEntity(request, newBook, Void.class);
		
		// verify the response HTTP status
		assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);

		// because we're using a stub DAO, we'll have to call another 
		// DAO method to verify that the book was added
		request = "/library/book/979-8373322577";
		ResponseEntity<Book> response = 
				restTemplate.getForEntity(request, Book.class);
		assertEquals(newBook, response.getBody());
	}
}
