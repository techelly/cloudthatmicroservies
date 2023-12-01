package com.fidelity.business;

import java.util.ArrayList;
import java.util.List;

/**
 * BookList is a Data Transfer Object (DTO) that is a wrapper for the "real" data. 
 * You create DTOs when you want to control the JSON returned by a RESTful controller method. 
 * For example, if you controller method is defined like this:
 *		public List<Book> getBooks() ...
 * the JSON response is a raw array of objects:
 * 		[ {"title":"Cryptonomicon", ... }, {"title":"Flash Boys", ...}, ... ]
 * But if the method returns a DTO instead:
 *		public BookList getBooks() ...
 * The array is the value of an element in a wrapper object:
 *      { "items": 
 * 		     {"title":"Cryptonomicon", ... }, {"title":"Flash Boys", ...}, ... ]
 *      }
 * Note that the name of the "items" element comes from the JavaBean property defined
 * by the getter method getItems(), not from the name of the bookList field.
 */
public class BookList {

	private List<Book> bookList;

	public BookList() {
		bookList = new ArrayList<>();
	}

	public BookList(List<Book> list) {
		bookList = list;
	}

	public List<Book> getItems() {
		return bookList;
	}
}
