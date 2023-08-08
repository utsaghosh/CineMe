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
import com.cineme.cinemeapp.exception.RatingAlreadyExistsException;
import com.cineme.cinemeapp.exception.RatingNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.model.RatingsInputModel;
import com.cineme.cinemeapp.model.RatingsOutputModel;
import com.cineme.cinemeapp.service.RatingsService;
import com.cineme.cinemeapp.utility.RatingsUtility;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/ratings")
@Slf4j
public class RatingsController {
	
	@Autowired
	RatingsService ratingsService;
	
	@Autowired
	RatingsUtility ratingsUtility;
	
	//get ------>
	
	@GetMapping("/getratingofmoviebyuser/{movieid}/{userid}")
	public ResponseEntity<RatingsOutputModel> getRatingOfMovieByUser(@PathVariable("movieid") Integer movieId, @PathVariable("userid") Integer userId)
			throws MovieNotFoundException, UserNotFoundException, RatingNotFoundException {
		
		log.info("Request: rating of movie[id={}] by user[id={}]", movieId,userId);
		RatingsOutputModel rating = ratingsUtility.parseOutputFromEntity(ratingsService.getRatingOfMovieByUser(movieId, userId));
		log.info("Response: {}", rating);
		return new ResponseEntity<RatingsOutputModel>(rating, HttpStatus.OK);
	}
	
	
	@GetMapping("/getallratingsofmovie/{movieid}")
	public ResponseEntity<List<RatingsOutputModel>> getAllRatingsOfMovie(@PathVariable("movieid") Integer movieId) throws MovieNotFoundException, RatingNotFoundException{
		
		log.info("Request: all ratings of movie[id={}]", movieId);
		List<RatingsOutputModel> movieRatings = ratingsUtility.parseOutputFromEntityList(ratingsService.getAllRatingsOfMovie(movieId));
		log.info("Response: {}", movieRatings);
		return new ResponseEntity<List<RatingsOutputModel>>(movieRatings, HttpStatus.OK);
	}
	
	
	@GetMapping("/getallratingsbyuser/{userid}")
	public ResponseEntity<List<RatingsOutputModel>> getAllRatingsByUser(@PathVariable("userid") Integer userId) throws UserNotFoundException, RatingNotFoundException{
		
		log.info("Request: all ratings by user[id={}]",userId);
		List<RatingsOutputModel> userRatings = ratingsUtility.parseOutputFromEntityList(ratingsService.getAllRatingsByUser(userId));
		log.info("Response: {}", userRatings);
		return new ResponseEntity<List<RatingsOutputModel>>(userRatings, HttpStatus.OK);
	}
	
	
	//post -------->
	
	@PostMapping("/addrating")
	public ResponseEntity<RatingsOutputModel> addRating(@RequestBody RatingsInputModel ratingInput) throws UserNotFoundException, MovieNotFoundException, RatingNotFoundException, RatingAlreadyExistsException, InvalidInputException{
		
		log.info("Request: add new rating {}", ratingInput);
		RatingsOutputModel rating = ratingsUtility.parseOutputFromEntity(ratingsService.addRating(ratingInput));
		log.info("Response: new rating added {}", rating);
		return new ResponseEntity<RatingsOutputModel>(rating, HttpStatus.OK);
	}
	
	
	//put -------->
	
	@PutMapping("/updaterating/{ratingid}")
	public ResponseEntity<RatingsOutputModel> updateRating(@PathVariable("ratingid") Integer ratingId, @RequestBody RatingsInputModel ratingInput) 
			throws UserNotFoundException, MovieNotFoundException, InvalidInputException, RatingNotFoundException{
		
		log.info("Request: update rating with id={} with {}", ratingId, ratingInput);
		RatingsOutputModel rating = ratingsUtility.parseOutputFromEntity(ratingsService.updateRating(ratingId, ratingInput));
		log.info("Response: updated rating with id={} to {}", rating);
		return new ResponseEntity<RatingsOutputModel>(rating, HttpStatus.OK);
	}
	
	//delete ------->
	
	@DeleteMapping("/deleterating/{ratingid}")
	public ResponseEntity<RatingsOutputModel> deleteRating(@PathVariable("ratingid") Integer ratingId) throws RatingNotFoundException {
			
		log.info("Request: delete rating with id={}", ratingId);
		RatingsOutputModel rating = ratingsUtility.parseOutputFromEntity(ratingsService.deleteRating(ratingId));
		log.info("Response: deleted rating {}", rating);
		return new ResponseEntity<RatingsOutputModel>(rating, HttpStatus.OK);
	}
}
