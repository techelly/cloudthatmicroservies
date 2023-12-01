package com.roifmr.presidents.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.roifmr.presidents.business.President;

/**
 * Integration tests for PresidentsMyBatisDao.
 */
@SpringBootTest
@Transactional
public class PresidentsMyBatisDaoIntegrationTest {

	@Autowired
	private PresidentsDao dao;
	
	private String washingtonExpectedBio;
	private String tylerExpectedBio;
	
	@BeforeEach
	void setUp() {
		washingtonExpectedBio = "On April 30, 1789, George Washington, standing on the balcony of Federal Hall on Wall Street in New York, "
				+ "took his oath of office as the first President of the United States. \"As the first of every thing, in our "
				+ "situation will serve to establish a Precedent,\" he wrote James Madison, \"it is devoutly wished on my part, "
				+ "that these precedents may be fixed on true principles.\" Born in 1732 into a Virginia planter family, he "
				+ "learned the morals, manners, and body of knowledge requisite for an 18th century Virginia gentleman.";
		
		tylerExpectedBio = "Dubbed \"His Accidency\" by his detractors, John Tyler was the "
				+ "first Vice President to be elevated to the office of President by the "
				+ "death of his predecessor. Born in Virginia in 1790, he was raised "
				+ "believing that the Constitution must be strictly construed. He never "
				+ "wavered from this conviction. He attended the College of William and "
				+ "Mary and studied law.";
	}
	
	@Test
	void testQueryForAllPresidents() {
		President washington = new President(1, "George","Washington", 1789, 1797, "georgewashington.jpg", washingtonExpectedBio);
		President tyler = new President(10, "John", "Tyler", 1841, 1845, "johntyler.jpg", tylerExpectedBio);

		List<President> presidentList = dao.queryForAllPresidents();

		assertEquals(10, presidentList.size());
		assertEquals(washington, presidentList.get(0));
		assertEquals(tyler, presidentList.get(9));
	}
	
	@Test
	void testQueryForPresidentBiography() {
		String actualBio = dao.queryForPresidentBiography(1);

		assertEquals(washingtonExpectedBio, actualBio);
	}
	
	@Test
	void testQueryForPresidentBiographyFailure() {
		assertNull(dao.queryForPresidentBiography(99));
	}
}
