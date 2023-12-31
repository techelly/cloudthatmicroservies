package com.techelly.springsecurityexampleusingtoyandbooklibapp.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/*
	 * @ExceptionHandler(ToyNotFoundException.class) public ResponseEntity<String>
	 * handleToyNotFoundException(Exception e) { ResponseEntity<String>
	 * responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	 * return responseEntity; }
	 */	
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<String> handleBookNotFoundException(Exception e) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		return responseEntity;
	}
	
	/*
	 * @ExceptionHandler(PaymentNotFoundException.class) public
	 * ResponseEntity<String> handlePaymentNotFoundException(Exception e) {
	 * ResponseEntity<String> responseEntity = new
	 * ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); return responseEntity;
	 * }
	 * 
	 * @ExceptionHandler(ParentNotFoundException.class) public
	 * ResponseEntity<String> handleParentNotFoundException(Exception e) {
	 * ResponseEntity<String> responseEntity = new
	 * ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); return responseEntity;
	 * }
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(Exception e) {
		ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		return responseEntity;
	}
	/*
	 * @ExceptionHandler(MembershipTypeNotFoundException.class) public
	 * ResponseEntity<String> handleMembershipTypeNotFoundException(Exception e) {
	 * ResponseEntity<String> responseEntity = new
	 * ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); return responseEntity;
	 * }
	 * 
	 * @ExceptionHandler(LendItemNotFoundException.class) public
	 * ResponseEntity<String> handleLendItemNotFoundException(Exception e) {
	 * ResponseEntity<String> responseEntity = new
	 * ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND); return responseEntity;
	 * }
	 */
}
