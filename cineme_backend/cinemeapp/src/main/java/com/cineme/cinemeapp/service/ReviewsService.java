package com.cineme.cinemeapp.service;

import java.util.List;

import com.cineme.cinemeapp.entity.Reviews;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.ReviewAlreadyExistsException;
import com.cineme.cinemeapp.exception.ReviewNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.model.ReviewsInputModel;

public interface ReviewsService {
	
	//create methods
	Reviews addReview(ReviewsInputModel reviewInput) throws UserNotFoundException, MovieNotFoundException, InvalidInputException, ReviewAlreadyExistsException;
	
	
	//read methods
	Reviews getReviewById(Integer reviewId) throws ReviewNotFoundException;
	
	List<Reviews> getAllReviewsOfMovie(Integer movieId) throws MovieNotFoundException, ReviewNotFoundException;
	
	List<Reviews> getAllReviewsByUser(Integer userId) throws UserNotFoundException, ReviewNotFoundException;
	
	Reviews getReviewOfMovieByUser(Integer movieId, Integer userId) throws UserNotFoundException, MovieNotFoundException, ReviewNotFoundException;
	
	
	//update methods
	Reviews updateReview(Integer reviewId, ReviewsInputModel reviewInput) throws UserNotFoundException, MovieNotFoundException, InvalidInputException, ReviewNotFoundException;
	
	
	//delete methods
	Reviews deleteReview(Integer reviewId) throws ReviewNotFoundException;
}
