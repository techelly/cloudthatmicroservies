package com.fidelity.businessservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fidelity.business.Book;
import com.fidelity.integration.LibraryDao;

/**
 * LibraryBusinessService implements business logic for requests to the Warehouse
 * REST controller. For example, it defines business rules for validating the inputs
 * to library methods.
 */
@Service
public class LibraryBusinessService {
	private final LibraryDao dao;

	// @Autowired isn't required here: Spring will automatically pass a LibraryDao instance
	public LibraryBusinessService(LibraryDao dao) {
		this.dao = dao;
	}

	public List<Book> getAllBooks() {
		List<Book> books = dao.getBooks();
		return books;
	}

	public Book getBookById(String isbn) {
		// the following method call was originally in LibraryController
		validateIsbn(isbn);

		Book book = dao.getBookByIsbn(isbn);
		return book;
	}

	public void addBook(Book book) {
		// the following method call was originally in LibraryController
		validateBook(book);

		dao.addBook(book);
	}

	private void validateIsbn(String isbn) {
		if (isbn == null || isbn.length() < 10) {  // ISBN numbers contain at least 10 digits
			throw new IllegalArgumentException("invalid ISBN number " + isbn);
		}
	}

	private void validateBook(Book inputBook) {
		if (inputBook == null || 
			inputBook.getAuthor() == null || inputBook.getAuthor().isBlank() ||
			inputBook.getTitle() == null || inputBook.getTitle().isBlank()) {
				throw new IllegalArgumentException("book is not fully populated: " + inputBook);
		}

		// don't try to validate the book's ISBN until you're sure the book isn't null
		validateIsbn(inputBook.getIsbn());

		// additional bonus task: don't add the book if it's already present
		if (dao.getBooks().stream()
				          .anyMatch(book -> inputBook.getIsbn().equals(book.getIsbn())) ) {
			throw new IllegalArgumentException("book is already present in the library: " + inputBook);
		}
	}
}
