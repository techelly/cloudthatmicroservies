package com.fidelity.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fidelity.business.Book;
import com.fidelity.utils.Generated;

@Repository
@Generated  // exclude from EclEmma code coverage report
public class StubLibraryDao implements LibraryDao {

	private Map<String, Book> booksMap = new HashMap<>(Map.of(
		"978-0060512804", new Book("Cryptonomicon", "Neal Stephenson", "978-0060512804"),
		"978-0393356687", new Book("The Overstory", "Richard Powers", "978-0393356687"),
		"978-0393351590", new Book("Flash Boys", "Michael Lewis", "978-0393351590"),
		"978-1419745508", new Book("The Last Count of Monte Cristo", "Ayize Jama-Everett", "978-1419745508"),
		"978-1419759864", new Book("Black & White: The Rise and Fall of Bobby Fischer", "Julian Voloj", "978-1419759864")
	));
	
	/** 
	 * Return the collection of Books.
	 */
	@Override
	public List<Book> getBooks() {
		List<Book> books = null;
		
		// Create a list of all the Books in booksMap
		Collection<Book> allBooks = booksMap.values();
		books = new ArrayList<>(allBooks);
		
		return books;
	}
	
	/** 
	 * Return the Book specified by its isbn number.
	 */
	@Override
	public Book getBookByIsbn(String isbn) {
		Book book = null;
		
		book = booksMap.get(isbn);
		
		return book;
	}

	/** 
	 * Add a book.
	 */
	@Override
	public void addBook(Book book) {
		booksMap.put(book.getIsbn(), book);
	}

	/** 
	 * Update a book.
	 */
	@Override
	public void updateBook(Book book) {
		booksMap.put(book.getIsbn(), book);
	}

	/** 
	 * Delete a book
	 */
	@Override
	public void deleteBook(Book book) {
		booksMap.remove(book.getIsbn());
	}
}
