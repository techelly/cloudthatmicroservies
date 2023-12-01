package com.fidelity.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ProductTest {

	@Test
	void testGetPriceWithSalesTax_NH() {
		Widget widget = new Widget(1, "Test widget", 100.0, 3, 4);
		
		double priceWithSalesTax = widget.getPriceWithSalesTax("NH");
		
		assertEquals(100.0, priceWithSalesTax);
	}

	@Test
	void testGetPriceWithSalesTax_MA() {
		Widget widget = new Widget(1, "Test widget", 100.0, 3, 4);
		
		double priceWithSalesTax = widget.getPriceWithSalesTax("MA");
		
		assertEquals(106.25, priceWithSalesTax);
	}

	@Test
	void testGetPriceWithSalesTax_NJ() {
		Widget widget = new Widget(1, "Test widget", 100.0, 3, 4);
		
		double priceWithSalesTax = widget.getPriceWithSalesTax("NJ");
		
		assertEquals(106.63, priceWithSalesTax);
	}

	@Test
	void testGetPriceWithSalesTax_nj() {
		Widget widget = new Widget(1, "Test widget", 100.0, 3, 4);
		
		double priceWithSalesTax = widget.getPriceWithSalesTax("nj");
		
		assertEquals(106.63, priceWithSalesTax);
	}

	@Test
	void testGetPriceWithSalesTax_UnknownState() {
		Widget widget = new Widget(1, "Test widget", 100.0, 3, 4);
		
		assertThrows(IllegalArgumentException.class, 
			() -> widget.getPriceWithSalesTax("ZZ")
		);
	}

	@Test
	void testGetPriceWithSalesTax_NullState() {
		Widget widget = new Widget(1, "Test widget", 100.0, 3, 4);
		
		assertThrows(IllegalArgumentException.class, 
			() -> widget.getPriceWithSalesTax(null)
		);
		
	}

	@Test
	void testGetPriceWithSalesTax_EmptyState() {
		Widget widget = new Widget(1, "Test widget", 100.0, 3, 4);
		
		Exception exc = assertThrows(IllegalArgumentException.class, 
			() -> widget.getPriceWithSalesTax("")
		);
		assertEquals("State is null or empty", exc.getMessage());
	}

	@Test
	void testGetPriceWithSalesTax_StateWithLeadingAndTrailingBlanks() {
		Widget widget = new Widget(1, "Test widget", 100.0, 3, 4);
		
		double priceWithSalesTax = widget.getPriceWithSalesTax("  MA  ");
		
		assertEquals(106.25, priceWithSalesTax);
	}


}
