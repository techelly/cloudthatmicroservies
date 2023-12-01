package com.fidelity.businessservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fidelity.business.Book;
import com.fidelity.integration.LibraryDao;

class LibraryBusinessServicePojoUnitTest {
	@Mock
	private LibraryDao mockDao;
	
	@InjectMocks
	private LibraryBusinessService service;

	private List<Book> bookList = List.of(
		new Book("Cryptonomicon", "Neal Stephenson", "978-0060512804"),
		new Book("The Overstory", "Richard Powers", "978-0393356687")
	);
		
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllBooks_Success() {
		when(mockDao.getBooks())
			.thenReturn(bookList);
		
		List<Book> actualBooks = service.getAllBooks();
		
		assertEquals(bookList, actualBooks);
	}

	@Test
	void testGetBookByIsbn_Success() {
		String isbn = "978-0060512804";
		Book book = new Book("Cryptonomicon", "Neal Stephenson", isbn);
		
		when(mockDao.getBookByIsbn(isbn))
			.thenReturn(book);
		
		Book actualBook = service.getBookById(isbn);
		
		assertEquals(book, actualBook);
	}

	@Test
	void testAddBook_Success() {
		Book book = new Book("Cryptonomicon", "Neal Stephenson", "978-0060512804");
		doNothing().when(mockDao).addBook(book);  // LibraryDao.addBook() returns void

		service.addBook(book);
		
		// Because LibraryDao.addBook() returns void, we must resort to whitebox testing
		// to verify that the service method did something reasonable. Try to avoid this; 
		// it makes your tests very brittle
		verify(mockDao).addBook(book);
	}

	@Test
	void testAddBook_InvalidIsbnThrowsException() {
		Book book = new Book("Cryptonomicon", "Neal Stephenson", "978-006");

		assertThrows(IllegalArgumentException.class, () -> {
			service.addBook(book);
		});

		verify(mockDao, never()).addBook(book);
	}

	@Test
	void testAddBook_BookHasNoAuthorThrowsException() {
		Book book = new Book("Cryptonomicon", "", "978-0060s512804");

		assertThrows(IllegalArgumentException.class, () -> {
			service.addBook(book);
		});

		verify(mockDao, never()).addBook(book);
	}

	@Test
	void testAddBook_BookHasNoTitleThrowsException() {
		Book book = new Book("", "Neal Stephenson", "978-0060s512804");

		assertThrows(IllegalArgumentException.class, () -> {
			service.addBook(book);
		});

		verify(mockDao, never()).addBook(book);
	}

	@Test
	void testAddBook_AlreadyPresentThrowsException() {
		when(mockDao.getBooks())
			.thenReturn(bookList);
		
		Book book = new Book("Cryptonomicon", "Neal Stephenson", "978-0060512804");

		assertThrows(IllegalArgumentException.class, () -> {
			service.addBook(book);
		});

		verify(mockDao, never()).addBook(book);
	}

}
