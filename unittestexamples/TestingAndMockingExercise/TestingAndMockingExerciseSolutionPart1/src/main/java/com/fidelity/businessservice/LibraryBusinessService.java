package com.fidelity.businessservice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fidelity.business.Book;
import com.fidelity.integration.LibraryDao;

@Service
public class LibraryBusinessService {
	private final LibraryDao dao;

	// Autowired isn't required here: Spring will automatically pass a LibraryDao instance
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

	private void validateBook(Book book) {
		if (book == null || 
			book.getAuthor() == null || book.getAuthor().isBlank() ||
			book.getTitle() == null || book.getTitle().isBlank()) {
				throw new IllegalArgumentException("book is not fully populated: " + book);
		}

		validateIsbn(book.getIsbn());

		// additional bonus task: don't add the book if it's already present
		if (dao.getBooks().stream()
				          .anyMatch(b -> book.getIsbn().equals(b.getIsbn())) ) {
			throw new IllegalArgumentException("book is already present in the library: " + book);
		}
	}
}
