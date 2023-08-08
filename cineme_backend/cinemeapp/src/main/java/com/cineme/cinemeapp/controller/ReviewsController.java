package com.cineme.cinemeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.ReviewAlreadyExistsException;
import com.cineme.cinemeapp.exception.ReviewNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.model.ReviewsInputModel;
import com.cineme.cinemeapp.model.ReviewsOutputModel;
import com.cineme.cinemeapp.service.ReviewsService;
import com.cineme.cinemeapp.utility.ReviewsUtility;

import lombok.extern.slf4j.Slf4j;



@RestController
@CrossOrigin
@RequestMapping("/reviews")
@Slf4j
public class ReviewsController {
	
	@Autowired
	ReviewsService reviewsService;
	
	@Autowired
	ReviewsUtility reviewsUtility;
	
	
	//get ------->
	
	@GetMapping("/getreviewofmoviebyuser/{movieid}/{userid}")
	public ResponseEntity<ReviewsOutputModel> getReviewOfMovieByUser(@PathVariable("movieid") Integer movieId, @PathVariable("userid") Integer userId) 
			throws MovieNotFoundException, UserNotFoundException, ReviewNotFoundException {
		
		log.info("Request: review of movie[id={}] by user[id={}]", movieId,userId);
		ReviewsOutputModel review = reviewsUtility.parseOutputFromEntity(reviewsService.getReviewOfMovieByUser(movieId, userId));
		log.info("Response: {}", review);
		return new ResponseEntity<ReviewsOutputModel>(review, HttpStatus.OK);
	}
	
	
	@GetMapping("/getallreviewsofmovie/{movieid}")
	public ResponseEntity<List<ReviewsOutputModel>> getAllReviewsOfMovie(@PathVariable("movieid") Integer movieId) throws MovieNotFoundException, ReviewNotFoundException{
		
		log.info("Request: all reviews of movie[id={}]", movieId);
		List<ReviewsOutputModel> movieReviews = reviewsUtility.parseOutputFromEntityList(reviewsService.getAllReviewsOfMovie(movieId));
		log.info("Response: {}", movieReviews);
		return new ResponseEntity<List<ReviewsOutputModel>>(movieReviews, HttpStatus.OK);
	}
	
	
	@GetMapping("/getallreviewsbyuser/{userid}")
	public ResponseEntity<List<ReviewsOutputModel>> getAllReviewsByUser(@PathVariable("userid") Integer userId) throws UserNotFoundException, ReviewNotFoundException{
		
		log.info("Request: all reviews by user[id={}]",userId);
		List<ReviewsOutputModel> userReviews = reviewsUtility.parseOutputFromEntityList(reviewsService.getAllReviewsByUser(userId));
		log.info("Response: {}", userReviews);
		return new ResponseEntity<List<ReviewsOutputModel>>(userReviews, HttpStatus.OK);
	}
	
	
	//post ----->
	
	@PostMapping("/addreview")
	public ResponseEntity<ReviewsOutputModel> addReview(@RequestBody ReviewsInputModel reviewInput) throws UserNotFoundException, MovieNotFoundException, ReviewAlreadyExistsException, InvalidInputException{
		
		log.info("Request: add new review {}", reviewInput);
		ReviewsOutputModel review = reviewsUtility.parseOutputFromEntity(reviewsService.addReview(reviewInput));
		log.info("Response: new review added {}", review);
		return new ResponseEntity<ReviewsOutputModel>(review, HttpStatus.OK);
	}
	
	
	//put -------->
	
	@PutMapping("/updatereview/{reviewid}")
	public ResponseEntity<ReviewsOutputModel> updateReview(@PathVariable("reviewid") Integer reviewId, @RequestBody ReviewsInputModel reviewsInput) throws UserNotFoundException, MovieNotFoundException, ReviewNotFoundException, InvalidInputException{
			
		log.info("Request: update review with id={} with {}", reviewId, reviewsInput);
		ReviewsOutputModel review = reviewsUtility.parseOutputFromEntity(reviewsService.updateReview(reviewId, reviewsInput));
		log.info("Response: updated rating with id={} to {}", review);
		return new ResponseEntity<ReviewsOutputModel>(review, HttpStatus.OK);
	}
		
	
	//delete ------->
		
	@DeleteMapping("/deletereview/{reviewid}")
	public ResponseEntity<ReviewsOutputModel> deleteReview(@PathVariable("reviewid") Integer reviewId) throws ReviewNotFoundException {
				
		log.info("Request: delete review with id={}", reviewId);
		ReviewsOutputModel review = reviewsUtility.parseOutputFromEntity(reviewsService.deleteReview(reviewId));
		log.info("Response: deleted review {}", review);
		return new ResponseEntity<ReviewsOutputModel>(review, HttpStatus.OK);
	}
}
