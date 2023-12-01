package com.fidelity.business;

import java.util.Objects;

import com.fidelity.utils.Generated;

public class Book implements Comparable<Book> {
	private String title;
	private String author;
	private String isbn;
	
	public Book() {}
	
	public Book(String title, String author, String isbn) {
		this.title = title;
		this.author = author;
		this.isbn = isbn;
	}

	@Override
	public int compareTo(Book otherBook) {
		return this.getTitle().compareToIgnoreCase(otherBook.getTitle());
	}

	@Generated  // exclude this method from the EclEmma code coverage report
	public String getTitle() {
		return title;
	}

	@Generated
	public void setTitle(String title) {
		this.title = title;
	}

	@Generated
	public String getAuthor() {
		return author;
	}

	@Generated
	public void setAuthor(String author) {
		this.author = author;
	}

	@Generated
	public String getIsbn() {
		return isbn;
	}

	@Generated
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	@Generated
	public int hashCode() {
		return Objects.hash(author, isbn, title);
	}

	@Override
	@Generated
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && Objects.equals(isbn, other.isbn)
				&& Objects.equals(title, other.title);
	}

	@Override
	@Generated
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", isbn=" + isbn + "]";
	}
	
}
