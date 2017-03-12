package com.springboot.file.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.file.domain.ErrorResponse;

/**
 * Controller advice class to handle errors
 * @author Prasad Paravatha
 */
@ControllerAdvice
public class GlobalControllerAdvice {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerAdvice.class);
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleBadRequests(IllegalArgumentException illegalArgumentException) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), 
				HttpStatus.BAD_REQUEST.getReasonPhrase(), illegalArgumentException.getMessage());
		LOGGER.error(illegalArgumentException.getMessage());
	    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnknownExceptions(Exception exception) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Unexpected error, please try again later");
		LOGGER.error(exception.getMessage());
		LOGGER.error(exception.getCause().toString());
	    return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);		
	}
}
