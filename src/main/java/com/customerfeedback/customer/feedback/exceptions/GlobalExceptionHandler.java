package com.customerfeedback.customer.feedback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	 @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex) {
	        ApiError error = new ApiError(ex.getMessage(), "BAD_REQUEST");
	        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	    }

}
