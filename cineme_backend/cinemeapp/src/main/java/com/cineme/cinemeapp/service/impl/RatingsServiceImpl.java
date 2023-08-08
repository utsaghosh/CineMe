package com.cineme.cinemeapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cineme.cinemeapp.dao.RatingsRepository;
import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.RatingAlreadyExistsException;
import com.cineme.cinemeapp.exception.RatingNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.model.RatingsInputModel;
import com.cineme.cinemeapp.service.RatingsService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Qualifier("ratingsService")
@Slf4j
public class RatingsServiceImpl implements RatingsService{
	@Autowired
	UsersServiceImpl userService;
	
	@Autowired
	MoviesServiceImpl movieService;
	
	@Autowired
	RatingsRepository ratingRepository;
	
	
	//create ------->
	
	@Transactional
	public Ratings addRating(RatingsInputModel ratingInput) 
			throws UserNotFoundException, MovieNotFoundException, InvalidInputException, RatingAlreadyExistsException{
		
		log.info("Adding new rating with: {}", ratingInput);
		
		Users user = userService.getUserByEmail(ratingInput.getUserEmail());
		log.info("User Found");
		
		Movies movie = movieService.getMovieById(ratingInput.getMovieId());
		log.info("Movie Found");
		
		Ratings existingRating = ratingRepository.getRatingOfMovieByUser(movie.getMovieId(), user.getUserId());
		
		if(existingRating != null) {
			log.info("New rating not added");
			throw new RatingAlreadyExistsException("User has already rated the movie, may update rating");
		}
		
		Integer score = ratingInput.getScore();
		
		if(score < 1 || score > 5 || score == null) {
			log.info("New rating not added");
			throw new InvalidInputException("Score must be between 1 and 5 (both inclusive)");
		}
		
		Ratings rating = new Ratings();
		rating.setUser(user);
		rating.setMovie(movie);
		rating.setScore(score);
		rating = ratingRepository.save(rating);
		
		log.info("New rating added successfully with id = {}", rating.getRatingId());	
		return rating;
	}
	
	
	//read --------->
	
	@Transactional
	public Ratings getRatingById(Integer ratingId) throws RatingNotFoundException {
		
		log.info("Fethching rating with id = {}", ratingId);
		Ratings rating = ratingRepository.findById(ratingId).orElse(null);
		if(rating == null) {
			throw new RatingNotFoundException("Rating Not Found");
		}
		log.info("Rating fetched successfully");
		return rating;
	}
	
	@Transactional
	public List<Ratings> getAllRatingsOfMovie(Integer movieId) throws MovieNotFoundException, RatingNotFoundException{
		
		log.info("Fetching all ratings of movie with id = {}", movieId);
		Movies movie = movieService.getMovieById(movieId);
		log.info("Movie Found");
		List<Ratings> ratingsList = ratingRepository.getAllRatingsOfMovie(movie.getMovieId());
		if(ratingsList.size() == 0) {
			throw new RatingNotFoundException("No rating found for movie "+movie.getMovieName());
		}
		log.info("Fetched all ratings of movie with id = {}, {} ratings found", movieId, ratingsList.size());
		return ratingsList;
	}
	
	@Transactional
	public List<Ratings> getAllRatingsByUser(Integer userId) throws UserNotFoundException, RatingNotFoundException{
		
		log.info("Fetching all ratings by user with id = {}", userId);
		Users user = userService.getUserById(userId);
		log.info("User Found");
		List<Ratings> ratingsList = ratingRepository.getAllRatingsByUser(user.getUserId());
		if(ratingsList.size() == 0) {
			throw new RatingNotFoundException("No ratings found by user "+user.getUserName());
		}
		log.info("Fetched all ratings by user with id = {}, {} ratings found", userId, ratingsList.size());
		return ratingsList;
	}
	
	@Transactional
	public Ratings getRatingOfMovieByUser(Integer movieId, Integer userId) throws MovieNotFoundException, UserNotFoundException, RatingNotFoundException {
		
		log.info("Fetching rating of movie with id= {}, by user with id= {}", movieId, userId);
		Movies movie = movieService.getMovieById(movieId);
		log.info("Movie Found");
		Users user = userService.getUserById(userId);
		log.info("User Found");
		Ratings rating = ratingRepository.getRatingOfMovieByUser(movie.getMovieId(), user.getUserId());
		if(rating == null) {
			throw new RatingNotFoundException("User"+user.getUserName()+" has not reviewed movie "+movie.getMovieName());
		}
		log.info("Rating of {} by {} is {} fetched successfully", movie.getMovieName(), user.getUserName(), rating.getScore());
		return rating;
	}

	
	//update -------->

	@Transactional
	public Ratings updateRating(Integer ratingId, RatingsInputModel ratingInput)
			throws UserNotFoundException, MovieNotFoundException, InvalidInputException, RatingNotFoundException {
		
		log.info("Updating rating with id={} with {}", ratingId, ratingInput);
		
		Ratings rating = this.getRatingById(ratingId);
		
		if (! rating.getUser().getEmail().equals(ratingInput.getUserEmail())) {
			log.info("Rating not updated");
			throw new UserNotFoundException("User mismatch");
		}
		
		if(rating.getMovie().getMovieId() != ratingInput.getMovieId()) {
			log.info("Rating not updated");
			throw new MovieNotFoundException("Movie mismatch");
		}
		
		if(ratingInput.getScore() < 1 || ratingInput.getScore() > 5 || ratingInput.getScore() == null) {
			log.info("Rating not updated");
			throw new InvalidInputException("Score must be between 1 and 5 (both inclusive)");
		}
		rating.setScore(ratingInput.getScore());
		rating = ratingRepository.save(rating);
		log.info("Rating updated successfully");
		return rating;
	}
	
	
	//delete ------>
	
	@Transactional
	public Ratings deleteRating(Integer ratingId) throws RatingNotFoundException {
		
		log.info("Deleting rating with id={}", ratingId);
		Ratings rating = this.getRatingById(ratingId);
		ratingRepository.deleteById(ratingId);
		log.info("Rating deleted successfully");
		return rating;
	}
}
