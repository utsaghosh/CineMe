package com.cineme.cinemeapp.service;

import java.util.List;

import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.RatingAlreadyExistsException;
import com.cineme.cinemeapp.exception.RatingNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.model.RatingsInputModel;

public interface RatingsService {
	
	//create methods
	Ratings addRating(RatingsInputModel ratingInput) throws UserNotFoundException, MovieNotFoundException, InvalidInputException, RatingAlreadyExistsException;
	
	
	//read methods
	Ratings getRatingById(Integer ratingId) throws RatingNotFoundException;
	
	List<Ratings> getAllRatingsOfMovie(Integer movieId) throws MovieNotFoundException, RatingNotFoundException;
	
	List<Ratings> getAllRatingsByUser(Integer userId) throws UserNotFoundException, RatingNotFoundException;
	
	Ratings getRatingOfMovieByUser(Integer movieId, Integer userId) throws UserNotFoundException, MovieNotFoundException, RatingNotFoundException;
	
	
	//update methods
	Ratings updateRating(Integer ratingId, RatingsInputModel ratingInput) throws UserNotFoundException, MovieNotFoundException, InvalidInputException, RatingNotFoundException;
	
	
	//delete methods
	Ratings deleteRating(Integer ratingId) throws RatingNotFoundException;
}
