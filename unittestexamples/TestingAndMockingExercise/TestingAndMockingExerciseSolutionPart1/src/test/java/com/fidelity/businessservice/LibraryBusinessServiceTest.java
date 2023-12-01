package com.fidelity.businessservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.business.Book;
import com.fidelity.integration.LibraryDao;
import com.fidelity.integration.StubLibraryDao;

class LibraryBusinessServiceTest {
	private LibraryBusinessService service;
	private LibraryDao dao;

	// Initialize a few Library for expected values in test cases
	private Book book1 = new Book("Cryptonomicon", "Neal Stephenson", "978-0060512804");
	private Book book2 = new Book("The Overstory", "Richard Powers", "978-0393356687");

	@BeforeEach
	public void setUp() {
		dao = new StubLibraryDao();
		service = new LibraryBusinessService(dao);
	}
	
	@Test
	void testGetAllBooks_Success() {
		List<Book> allBooks = service.getAllBooks();
		
		assertEquals(5, allBooks.size());
		// spot-check a few books
		assertTrue(allBooks.contains(book1));
		assertTrue(allBooks.contains(book2));
	}
	
	@Test
	void testGetBookById_Success() {
		Book book = service.getBookById("978-0393356687");
		
		assertEquals(book2, book);
	}
	
	@Test
	void testAddBook_Success() {
		String isbn = "979-8373322577";
		Book newBook = new Book("Moby Dick", "Herman Melville", isbn);

		service.addBook(newBook);
		
		Book book = dao.getBookByIsbn(isbn);
		assertEquals(newBook, book);
	}

}
