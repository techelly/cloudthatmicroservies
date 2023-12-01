package com.fidelity.restcontroller;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fidelity.business.Book;
import com.fidelity.businessservice.LibraryBusinessService;

/**
 * LibraryControllerWebLayerTest defines the web layer integration tests
 * for the LibraryController REST endpoint.
 */
@WebMvcTest(LibraryController.class)
public class LibraryControllerWebLayerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private LibraryBusinessService mockBusinessService;
	
	private static final List<Book> expectedBooks = List.of(
		new Book("Cryptonomicon", "Neal Stephenson", "978-0060512804"),
		new Book("The Overstory", "Richard Powers", "978-0393356687")
	);
	
	@Test
	public void testGetBooks_DaoReturnsMultipleBooks() throws Exception {
		when(mockBusinessService.getAllBooks())
			.thenReturn(expectedBooks);
		
		mockMvc.perform(get("/library/books"))
		       //.andDo(print())
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.items.length()").value(2))
		       .andExpect(jsonPath("$.items[0].title").value("Cryptonomicon"))
		       .andExpect(jsonPath("$.items[1].title").value("The Overstory"));
	}
	
	@Test
	public void testGetBooks_DaoReturnsEmptyList() throws Exception {
		when(mockBusinessService.getAllBooks())
			.thenReturn(new ArrayList<>());
		
		mockMvc.perform(get("/library/books"))
			    //.andDo(print())
		       .andExpect(status().isNoContent())
		       .andExpect(content().string(is(emptyOrNullString())));
	}
	
	@Test
	public void testGetBooks_DaoReturnsNullList() throws Exception {
		when(mockBusinessService.getAllBooks())
			.thenReturn(null);
		
		mockMvc.perform(get("/library/books"))
		       //.andDo(print())
		       .andExpect(status().isNoContent())
		       .andExpect(content().string(is(emptyOrNullString())));
	}
	
	@Test
	public void testGetBooks_DaoThrowsException() throws Exception {
		when(mockBusinessService.getAllBooks())
			.thenThrow(new RuntimeException("mock exception"));
		
		mockMvc.perform(get("/library/books"))
		       //.andDo(print())
		       .andExpect(status().isInternalServerError())
		       .andExpect(content().string(is(emptyOrNullString())));
	}

	@Test
	public void testGetBookById_BookIsPresent() throws Exception {
		String isbn = "123-4567890";
		Book expectedBook = expectedBooks.get(1);

		when(mockBusinessService.getBookById(isbn))
			.thenReturn(expectedBook);
		
		mockMvc.perform(get("/library/book/" + isbn))
		       //.andDo(print())
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.title").value(expectedBook.getTitle()))
		       .andExpect(jsonPath("$.author").value(expectedBook.getAuthor()))
		       .andExpect(jsonPath("$.isbn").value(expectedBook.getIsbn()));
	}
	
	@Test
	public void testGetBookById_InvalidId() throws Exception {
		String isbn = "123-456";
		when(mockBusinessService.getBookById(isbn))
			.thenThrow(new IllegalArgumentException("mock exception"));
		
		mockMvc.perform(get("/library/book/" + isbn))
		       //.andDo(print())
		       .andExpect(status().isBadRequest())
		       .andExpect(content().string(is(emptyOrNullString())));
	}
	
	@Test
	public void testGetBookById_NonExistantId() throws Exception {
		String isbn = "123-4567890";
		when(mockBusinessService.getBookById(isbn))
			.thenReturn(null);
		
		mockMvc.perform(get("/library/book/" + isbn))
		       //.andDo(print())
		       .andExpect(status().isNoContent())
		       .andExpect(content().string(is(emptyOrNullString())));
	}
	
	@Test
	public void testGetBookById_DaoThrowsException() throws Exception {
		String isbn = "123-4567890";
		when(mockBusinessService.getBookById(isbn))
			.thenThrow(new RuntimeException("mock exception"));
		
		mockMvc.perform(get("/library/book/" + isbn))
		       //.andDo(print())
		       .andExpect(status().isInternalServerError())
		       .andExpect(content().string(is(emptyOrNullString())));
	}
}
