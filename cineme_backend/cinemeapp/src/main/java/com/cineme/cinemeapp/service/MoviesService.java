package com.cineme.cinemeapp.service;

import java.util.List;

import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.entity.Reviews;
import com.cineme.cinemeapp.entity.WatchListEntries;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.RatingNotFoundException;
import com.cineme.cinemeapp.exception.ReviewNotFoundException;
import com.cineme.cinemeapp.exception.WatchListEntryNotFoundException;
import com.cineme.cinemeapp.model.MoviesInputModel;
import com.cineme.cinemeapp.model.MoviesOutputModel;


public interface MoviesService {
	
	//create methods
	
	Movies addMovie(MoviesInputModel moviesModel) throws InvalidInputException;
	
	
	//get methods
	
	List<Movies> getAllMovies() throws MovieNotFoundException;
	
	List<Movies> getMoviesMatchingName(String nameSearch) throws MovieNotFoundException;
	
	List<Movies> getMoviesOfLanguage(String language) throws MovieNotFoundException;
	
	List<Movies> getMoviesByYearOfRelease(Integer minYear, Integer maxYear) throws MovieNotFoundException;
	
	List<MoviesOutputModel> getMoviesWithMinAvgRating(Integer minAvgRating) throws MovieNotFoundException;
	
	List<MoviesOutputModel> getMoviesWithMinWatchListAdditions(Integer minAdditions) throws MovieNotFoundException;
	
	Movies getMovieById(Integer movieId) throws MovieNotFoundException;
	
	List<Reviews> getAllReviewsOfMovie(Integer movieId) throws MovieNotFoundException, ReviewNotFoundException;
	
	List<Ratings> getAllRatingsOfMovie(Integer movieId) throws MovieNotFoundException, RatingNotFoundException;
	
	List<WatchListEntries> getAllWatchListEntriesOfMovie(Integer movieId) throws MovieNotFoundException, WatchListEntryNotFoundException;
	
	
	//delete methods
	
	Movies deleteMovie(Integer movieId) throws MovieNotFoundException;
}
