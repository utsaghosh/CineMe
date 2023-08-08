package com.cineme.cinemeapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cineme.cinemeapp.dao.ReviewsRepository;
import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.entity.Reviews;
import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.ReviewAlreadyExistsException;
import com.cineme.cinemeapp.exception.ReviewNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.model.ReviewsInputModel;
import com.cineme.cinemeapp.service.ReviewsService;
import com.cineme.cinemeapp.utility.ReviewsUtility;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Qualifier("reviewsService")
@Slf4j
public class ReviewsServiceImpl implements ReviewsService{
	@Autowired
	UsersServiceImpl userService;
	
	@Autowired
	MoviesServiceImpl movieService;
	
	@Autowired
	ReviewsUtility reviewUtility;
	
	@Autowired
	ReviewsRepository reviewRepository;
	
	
	//create ----->
	
	@Transactional
	public Reviews addReview(ReviewsInputModel reviewInput) 
			throws UserNotFoundException, MovieNotFoundException, InvalidInputException, ReviewAlreadyExistsException{
		
		log.info("Adding new review with: {}", reviewInput);
		
		if( ! reviewUtility.validateInputModel(reviewInput) ) {
			return null;
		}
		
		Users user = userService.getUserByEmail(reviewInput.getUserEmail());
		log.info("User Found");
		
		Movies movie = movieService.getMovieById(reviewInput.getMovieId());
		log.info("Movie Found");
		
		Reviews existingReview = reviewRepository.getReviewOfMovieByUser(movie.getMovieId(), user.getUserId());
		
		if(existingReview != null) {
			log.info("New review not added");
			throw new ReviewAlreadyExistsException("User has already reviewed the movie, may edit review");
		}
		
		String body = reviewInput.getBody();
		
		Reviews review = new Reviews();
		review.setUser(user);
		review.setMovie(movie);
		review.setBody(body);
		review.setCreationDate(LocalDateTime.now());	
		review = reviewRepository.save(review);
		
		log.info("New review added successfully with id = {}", review.getReviewId());
		
		return review;
	}
	
	
	//read ------->
	
	@Transactional
	public Reviews getReviewById(Integer reviewId) throws ReviewNotFoundException {
		
		log.info("Fethching review with id = {}", reviewId);
		Reviews review = reviewRepository.findById(reviewId).orElse(null);
		if(review == null) {
			throw new ReviewNotFoundException("Review Not Found");
		}
		log.info("Review fetched successfully");
		return review;
	}
	
	
	@Transactional
	public List<Reviews> getAllReviewsOfMovie(Integer movieId) throws MovieNotFoundException, ReviewNotFoundException{
		
		log.info("Fetching all reviews of movie with id = {}", movieId);
		Movies movie = movieService.getMovieById(movieId);
		log.info("Movie Found");
		List<Reviews> reviewsList = reviewRepository.getAllReviewsOfMovie(movie.getMovieId());
		if(reviewsList.size() == 0) {
			throw new ReviewNotFoundException("No reviews found for movie "+movie.getMovieName());
		}
		log.info("Fetched all reviews of movie with id = {}, {} reviews found", movieId, reviewsList.size());
		return reviewsList;
	}
	
	
	@Transactional
	public List<Reviews> getAllReviewsByUser(Integer userId) throws UserNotFoundException, ReviewNotFoundException{
		
		log.info("Fetching all reviews by user with id = {}", userId);
		Users user = userService.getUserById(userId);
		log.info("User Found");
		List<Reviews> reviewsList = reviewRepository.getAllReviewsByUser(user.getUserId());
		if(reviewsList.size() == 0) {
			throw new ReviewNotFoundException("No reviews found by user "+user.getUserName());
		}
		log.info("Fetched all ratings by user with id = {}, {} reviews found", userId, reviewsList.size());
		return reviewsList;
	}
	
	
	@Transactional
	public Reviews getReviewOfMovieByUser(Integer movieId, Integer userId) throws MovieNotFoundException, UserNotFoundException, ReviewNotFoundException {
		
		log.info("Fetching review of movie with id= {}, by user with id= {}", movieId, userId);
		Movies movie = movieService.getMovieById(movieId);
		log.info("Movie Found");
		Users user = userService.getUserById(userId);
		log.info("User Found");
		Reviews review = reviewRepository.getReviewOfMovieByUser(movie.getMovieId(), user.getUserId());
		if(review == null) {
			throw new ReviewNotFoundException("User"+user.getUserName()+" has not reviewed movie "+movie.getMovieName());
		}
		log.info("Review of {} by {} is fetched successfully", movie.getMovieName(), user.getUserName());
		return review;
	}

	
	//update ------->
	
	@Transactional
	public Reviews updateReview(Integer reviewId, ReviewsInputModel reviewInput)
			throws UserNotFoundException, MovieNotFoundException, InvalidInputException, ReviewNotFoundException {
		
		log.info("Updating review with id={} with {}", reviewId, reviewInput);
		
		Reviews review = this.getReviewById(reviewId);
		
		if (! review.getUser().getEmail().equals(reviewInput.getUserEmail())) {
			log.info("Review not updated");
			throw new UserNotFoundException("User mismatch");
		}
		
		if(review.getMovie().getMovieId() != reviewInput.getMovieId()) {
			log.info("Review not updated");
			throw new MovieNotFoundException("Movie mismatch");
		}
		
		if(reviewInput.getBody().isBlank() || reviewInput.getBody() == null) {
			log.info("Review not updated");
			throw new InvalidInputException("Review cannot be empty or contain only whitespaces");
		}
		
		if( ! reviewUtility.validateInputModel(reviewInput) ) {
			return null;
		}
		
		review.setBody(reviewInput.getBody());;
		reviewRepository.save(review);
		log.info("Review updated successfully");
		return review;
	}
	
	
	//delete -------->
	
	@Transactional
	public Reviews deleteReview(Integer reviewId) throws ReviewNotFoundException {
		
		log.info("Deleting review with id={}", reviewId);
		Reviews review = this.getReviewById(reviewId);
		reviewRepository.deleteById(reviewId);
		log.info("Review deleted successfully");
		return review;
	}
}
