package com.fidelity.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;

import com.fidelity.business.Book;
import com.fidelity.business.BookList;
import com.fidelity.integration.LibraryDao;

@RestController
@RequestMapping("/library")
public class LibraryController {
	@Autowired
	private Logger logger;
	
	@Autowired
	private LibraryDao dao; // DAO replaced by business service

	// TODO: 
	//    1. After implementing the LibraryBusinessService, refactor the controller
	//       by replacing the LibraryDao field above with a LibraryBusinessService instance.
	//       Make changes in the controller methods as required.
	//    2. Add an appropriate annotation to your LibraryBusinessService
	//       class to mark it as a Spring-managed service
	//    3. Test the RESTful service with Insomnia and confirm that it works as before.

	// GET http://localhost:8080/library/books
	@GetMapping(value = "/books", 
			produces = { MediaType.APPLICATION_JSON_VALUE, 
						 MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<BookList> getBooks() {
		
		BookList booksList = null;
		
		try {
			List<Book> books = dao.getBooks();
			
			if (books == null || books.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			
			booksList = new BookList(books);
			return ResponseEntity.ok(booksList);
		} 
		catch (RuntimeException e) {
			logger.error("getBooks: exception: " + e);
			throw new ServerErrorException("Unable to communicate with the library", e);
		}
	}

	// GET http://localhost:8080/library/book/978-0060512804
	@GetMapping(value = "/book/{isbn}", 
			    produces = { MediaType.APPLICATION_JSON_VALUE, 
						     MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Book> getBookById(@PathVariable String isbn) {
		try {
			// BONUS TODO: validating input data is business logic, so move the following
			//       method caLL to the appropriate method in LibraryBusinessService.
			validateIsbn(isbn);
			
			Book book = dao.getBookByIsbn(isbn);
		
			if (book == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok().body(book);
		} 
		catch (IllegalArgumentException e) {
		 	throw new ServerWebInputException(e.getMessage());
		}
		catch (RuntimeException e) {
			logger.error("getBookById: exception: " + e);
			throw new ServerErrorException("Unable to communicate with the library", e);
		}
	}
	
	// BONUS TODO: Move the following method to the LibraryBusinessService.
	private void validateIsbn(String isbn) {
		if (isbn == null || isbn.length() < 10) {  // ISBN numbers contain at least 10 digits
			throw new IllegalArgumentException("invalid ISBN number " + isbn);
		}
	}

	// POST http://localhost:8080/library/book
	// {"title":"The Overstory", "author": "Richard Powers", "isbn": "978-0393356687"}
	@PostMapping(value = "/book", 
				 produces = { MediaType.APPLICATION_JSON_VALUE, 
						      MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(HttpStatus.CREATED)  // if no error, response status is 201
	public void addBook(@RequestBody Book book) {
		try {
			// BONUS TODO: move the following method caLL to the appropriate method
			// 		 in LibraryBusinessService.
			validateBook(book);
			
			dao.addBook(book); // DAO replaced by business service
		} 
		catch (IllegalArgumentException e) {
		 	throw new ServerWebInputException(e.getMessage());
		}
		catch (RuntimeException e) {
			logger.error("addBook: exception: " + e);
			throw new ServerErrorException("Unable to communicate with the library", e);
		}
	}
	
	// BONUS TODO: Move the following method to the LibraryBusinessService.
	private void validateBook(Book book) {
		if (book == null || 
			book.getAuthor() == null || book.getAuthor().isBlank() ||
			book.getTitle() == null || book.getTitle().isBlank()) {
				throw new IllegalArgumentException("book is not fully populated: " + book);
		}

		// don't try to validate the book's ISBN until you're sure the book isn't null
		validateIsbn(book.getIsbn());
	}
	
}
