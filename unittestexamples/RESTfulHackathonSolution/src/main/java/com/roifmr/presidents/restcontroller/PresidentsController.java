package com.roifmr.presidents.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebInputException;

import com.roifmr.presidents.business.President;
import com.roifmr.presidents.integration.PresidentsDao;

/**
 * PresidentsController is a RESTful endpoint that handles requests for 
 * data about US presidents.
 */
@RestController
@RequestMapping("/presidents")
public class PresidentsController {
	@Autowired
	private Logger logger;

	@Autowired
	private PresidentsDao dao;
	
	/**
	 * Get all details of all presidents.
	 * @return a collection of all president representations from the database.
	 */
	@GetMapping
	public ResponseEntity<List<President>> queryForAllPresidents() {
		logger.debug("enter");
		try {
			List<President> presidents = dao.queryForAllPresidents();
			
			ResponseEntity<List<President>> responseEntity;
			// If there is at least one president in the database, return a JSON array of objects
			// and HTTP status 200
			if (presidents != null && presidents.size() > 0) {
				responseEntity = ResponseEntity.ok(presidents);
			}
			else {
				// If there are no Presidents in the database, the response should have an empty
				// body and HTTP status 204
				responseEntity = ResponseEntity.noContent().build();
			}
			return responseEntity;
		} 
		catch (Exception e) {
			// If there are any other errors during the lookup operation, the response should
			// have HTTP status 500
			logger.error("Exception while getting all presidents: " +  e);
			throw new ServerErrorException("Backend issue", e);
		}
	}

	/**
	 * Get the biography for a specific president.
	 * @param id the id of a president.
	 * @return the biography of the requested president.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<BiographyDto> queryForPresidentBiography(@PathVariable int id) {
		logger.debug("getting bio for president " + id);
		// If the id in the request is less than or equal to zero, the response should have
		// HTTP status 400
		if (id <= 0) {
			// don't nest this block inside the "try" block because the exception
			// would be caught in the catch for Exception below instead of the caller
			throw new ServerWebInputException("id must be greater than 0");
		}
		try {
			String bioString = dao.queryForPresidentBiography(id);
			
			ResponseEntity<BiographyDto> responseEntity;
			// If the requested president is found, return their biography string and HTTP status 200
			if (bioString != null) {
				// return a DTO instead of a plain String to ensure that the response body is valid JSON
				BiographyDto biographyDto = new BiographyDto(bioString);
				responseEntity = ResponseEntity.ok(biographyDto); 
			}
			else {
				// If there is no president with the given id, the response should have an empty
				// body and HTTP status 204
				responseEntity = ResponseEntity.noContent().build();
			}
			return responseEntity;
		} 
		catch (Exception e) {
			// If any other error occurs during the lookup operation, the response should have
			// HTTP status 500
			logger.error("Exception while getting bio for president " + id + ": " + e);
			throw new ServerErrorException("Backend issue", e);
		}
	
	}
}
