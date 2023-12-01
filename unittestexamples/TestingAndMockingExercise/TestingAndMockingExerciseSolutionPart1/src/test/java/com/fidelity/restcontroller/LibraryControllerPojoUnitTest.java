package com.fidelity.restcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;

import com.fidelity.business.Book;
import com.fidelity.business.BookList;
import com.fidelity.businessservice.LibraryBusinessService;

/**
 * LibraryControllerPojoUnitTest defines unit tests for LibraryController.
 */
public class LibraryControllerPojoUnitTest {
	@Mock
	private LibraryBusinessService mockBusinessService;

	@Mock
	private Logger mockLogger;
	
	@InjectMocks
	private LibraryController controller;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	private static final List<Book> expectedBooks = Arrays.asList(
		new Book("Cryptonomicon", "Neal Stephenson", "978-0060512804"),
		new Book("Black & White: The Rise and Fall of Bobby Fischer", "Julian Voloj", "978-1419759864")
	);
	
	@Test
	public void testGetBooks_DaoReturnsMultipleBooks() throws Exception {
		when(mockBusinessService.getAllBooks())
			.thenReturn(expectedBooks);
		
		ResponseEntity<BookList> responseStatus = controller.getBooks();
		
		HttpStatus statusCode = responseStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
		List<Book> actualBooks = responseStatus.getBody().getItems();
		assertEquals(expectedBooks, actualBooks);
	}

	@Test
	public void testGetBooks_DaoReturnsEmptyList() throws Exception {
		when(mockBusinessService.getAllBooks())
			.thenReturn(new ArrayList<Book>());
		
		ResponseEntity<BookList> responseStatus = controller.getBooks();
		
		HttpStatus statusCode = responseStatus.getStatusCode();
		assertEquals(HttpStatus.NO_CONTENT, statusCode);
		BookList actualBooks = responseStatus.getBody();
		assertNull(actualBooks);
	}

	@Test
	public void testGetBooks_DaoReturnsNull() throws Exception {
		when(mockBusinessService.getAllBooks())
			.thenReturn(null);
		
		ResponseEntity<BookList> responseStatus = controller.getBooks();
		
		HttpStatus statusCode = responseStatus.getStatusCode();
		assertEquals(HttpStatus.NO_CONTENT, statusCode);
		BookList actualBooks = responseStatus.getBody();
		assertNull(actualBooks);
	}

	@Test
	public void testGetBooks_DaoThrowsException() throws Exception {
		when(mockBusinessService.getAllBooks())
			.thenThrow(new RuntimeException("mock exception"));
		
		ServerErrorException ex = assertThrows(ServerErrorException.class, 
			() -> controller.getBooks()
		);
			
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
	}

	@Test
	public void testGetBookById_Success() throws Exception {
		Book expectedBook = expectedBooks.get(0);
		when(mockBusinessService.getBookById(anyString()))
			.thenReturn(expectedBook);
				
		ResponseEntity<Book> response = controller.getBookById("978-0060512804");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedBook, response.getBody());
	}
	
	@Test
	public void testGetBookById_NonExistantId() throws Exception {
		when(mockBusinessService.getBookById(anyString()))
			.thenReturn(null);
				
		ResponseEntity<Book> response = controller.getBookById("999-9999999999");
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
	}
	
	@Test
	public void testGetBookById_InvalidId() throws Exception {
		String isbn = "999-999";
		when(mockBusinessService.getBookById(isbn))
			.thenThrow(new IllegalArgumentException("mock exception"));

		assertThrows(ServerWebInputException.class, 
			() -> controller.getBookById(isbn)
		);
	}
	
	@Test
	public void testGetBookById_NullId() throws Exception {
		when(mockBusinessService.getBookById(null))
			.thenThrow(new IllegalArgumentException("mock exception"));

		assertThrows(ServerWebInputException.class, 
			() -> controller.getBookById(null)
		);
	}
	
	@Test
	public void testGetBookById_DaoThrowsException() throws Exception {
		when(mockBusinessService.getBookById(anyString()))
			.thenThrow(new RuntimeException("mock exception"));
		
		ServerErrorException ex = assertThrows(ServerErrorException.class, 
			() -> controller.getBookById("123-4567890")
		);
			
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ex.getStatus());
	}
}
