package com.fidelity.business;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookTest {
	private Book book;

	@BeforeEach
	void setUp() throws Exception {
		book = new Book("Moby Dick", "Herman Melville", "979-8373322577");
	}

	@Test
	void testCompareTo_ThisTitleIsLessThanOtherTitle() {
		Book otherBook = new Book("Noby Dick", "Herman Melville", "979-8373322577");
		
		int result = book.compareTo(otherBook);
		
		assertTrue(result < 0);
	}


	@Test
	void testCompareTo_ThisTitleIsEqualToOtherTitle() {
		Book otherBook = new Book("Moby Dick", "Herman Melville", "979-8373322577");
		
		int result = book.compareTo(otherBook);
		
		assertEquals(0, result);

	}

	@Test
	void testCompareTo_ThisTitleIsGreaterThanOtherTitle() {
		Book otherBook = new Book("Loby Dick", "Herman Melville", "979-8373322577");
		
		int result = book.compareTo(otherBook);
		
		assertTrue(result > 0);
	}

	@Test
	void testCompareTo_ThisTitleIsEqualToOtherTitleMixedCase() {
		Book otherBook = new Book("mOBY dICK", "Herman Melville", "979-8373322577");
		
		int result = book.compareTo(otherBook);
		
		assertEquals(0, result);
	}

}
