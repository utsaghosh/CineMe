package com.cineme.cinemeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.cineme.cinemeapp.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class MyControllerAdvice {
	
	//404 NOT_FOUND ----->
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(404, e.getMessage());
		log.error("Could not find User in database raised UserNotFoundException with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MovieNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleMovieNotFoundException(MovieNotFoundException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(404, e.getMessage());
		log.error("Could not find Movie in database raised MovieNotFoundException with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RatingNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleRatingNotFoundException(RatingNotFoundException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(404, e.getMessage());
		log.error("Could not find Rating in database raised RatingNotFoundException with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ReviewNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleReviewNotFoundException(ReviewNotFoundException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(404, e.getMessage());
		log.error("Could not find Review in database raised ReviewNotFoundException with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(WatchListEntryNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleWatchListEntryNotFoundException(WatchListEntryNotFoundException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(404, e.getMessage());
		log.error("Could not find WatchList entry in database raised WatchListEntryNotFoundException with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}
	
	
	
	//409 CONFLICT ------->
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(409, e.getMessage());
		log.error("User with same email already exists in database raised UserAlreadyExistsException with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(RatingAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ResponseEntity<ErrorResponse> handleRatingAlreadyExistsException(RatingAlreadyExistsException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(409, e.getMessage());
		log.error("Rating for Movie by User already exists in database raised RatingAlreadyExistsException with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(ReviewAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ResponseEntity<ErrorResponse> handleReviewAlreadyExistsException(ReviewAlreadyExistsException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(409, e.getMessage());
		log.error("Review for Movie by User already exists in database raised ReviewAlreadyExistsException with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(WatchListEntryAlreadyExistsException.class)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ResponseEntity<ErrorResponse> handleWatchListEntryAlreadyExistsException(WatchListEntryAlreadyExistsException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(409, e.getMessage());
		log.error("WatchList Entry for Movie by User already exists in database raised WatchListEntryAlreadyExistsExceptio with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.CONFLICT);
	}
	
	
	
	
	//400 BAD_REQUEST ------->
	
	@ExceptionHandler(InvalidInputException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException e, WebRequest we){
		ErrorResponse response = new ErrorResponse(400, e.getMessage());
		log.error("Given input is invalid raised InvalidInputException with message: "+e.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}
}
