package com.cineme.cinemeapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cineme.cinemeapp.dao.MoviesRepository;
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
import com.cineme.cinemeapp.service.MoviesService;
import com.cineme.cinemeapp.utility.MoviesUtility;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Qualifier("movieService")
@Slf4j
public class MoviesServiceImpl implements MoviesService{
	@Autowired
	MoviesRepository movieRepository;
	
	@Autowired
	MoviesUtility moviesUtility;

	
	//add methods
	
	//will not keep this public
	@Transactional
	public Movies addMovie(MoviesInputModel moviesModel) throws InvalidInputException{
		
		log.info("Adding new movie with: {}", moviesModel);
			
		Movies movie = moviesUtility.parseEntityFromInput(moviesModel);
		movie = movieRepository.save(movie);
		
		log.info("New movie added successfully with id = {}", movie.getMovieId());
		
		return movie;
	}
	
	//get methods
	@Transactional
	public Movies getMovieById(Integer movieId) throws MovieNotFoundException {
    	
		log.info("Fethching movie with id = {}", movieId);
		
		Movies movie = movieRepository.findById(movieId).orElse(null);
		
    	if( movie==null ) {
    		throw new MovieNotFoundException("Movie Not Found");
    	}
    	
    	log.info("Movie with id = {}, name = {} fetched successfully", movie.getMovieId(), movie.getMovieName());
    	
    	return movie;
    	
    }
	
	@Transactional
	public List<Movies> getAllMovies() throws MovieNotFoundException{
		
		log.info("Fetching all movies from database");
		
		List<Movies> moviesList = movieRepository.findAll(Sort.by(Sort.Direction.DESC, "releaseDate"));
		
		if(moviesList.size() == 0) {
			throw new MovieNotFoundException("No Movies Found In Database");
		}
		
		log.info("All movies fetched from database: {} movies found", +moviesList.size());
		
		return moviesList;
	}
	
	@Transactional
	public List<Movies> getMoviesMatchingName(String nameSearch) throws MovieNotFoundException{
		
		log.info("Fetching movies with name like "+nameSearch);		
		
		List<Movies> moviesList = movieRepository.getMoviesMatchingName(nameSearch.toLowerCase());
		
		if(moviesList.size() == 0) {
			throw new MovieNotFoundException("No Movies Found with name matching "+nameSearch);
		}
		
		log.info("Movies with name like {} fetched: {} matches found", nameSearch, moviesList.size());		
		
		return moviesList;
	}
	
	@Transactional
	public List<Movies> getMoviesOfLanguage(String language) throws MovieNotFoundException{
		
		log.info("Fetching movies with language as "+language);
		
		List<Movies> moviesList = movieRepository.getMoviesOfLanguage(language.toLowerCase());
		
		if(moviesList.size() == 0) {
			throw new MovieNotFoundException("No Movies Found of language " + language);
		}
		
		log.info("Movies with language as {} fetched: {} matches found", language, moviesList.size());
		
		return moviesList;
		
	}
	
	@Transactional
	public List<Movies> getMoviesByYearOfRelease(Integer minYear, Integer maxYear) throws MovieNotFoundException{
		
		log.info("Fetching movies with release year in ["+minYear+", "+maxYear+"]");
		
		List<Movies> moviesList = movieRepository.getMoviesByYearOfRelease(minYear,maxYear);
		
		if(moviesList.size() == 0) {
			throw new MovieNotFoundException("No Movies Found with release date in range [" +minYear +", "+maxYear+"]");
		}
		
		log.info("Movies with release year in [{}, {}] fetched: {} matches found", minYear, maxYear, moviesList.size());
		
		return moviesList;
		
	}
	
	@Transactional
	public List<MoviesOutputModel> getMoviesWithMinAvgRating(Integer minAvgRating) throws MovieNotFoundException{
		
		log.info("Fetching movies with minimum average rating >= "+minAvgRating);
		
		List<MoviesOutputModel> moviesList = moviesUtility.parseOutputFromEntityList(this.getAllMovies())
															.stream()
															.filter(m -> (m.getAvgRating() >= minAvgRating))
															.toList();
		if(moviesList.size() == 0) {
			throw new MovieNotFoundException("No Movies Found with minimum average rating >= " +minAvgRating);
		}
		
		log.info("Movies with minimum average rating >= {} fetched: {} matches found", minAvgRating, moviesList.size());
		
		return moviesList;
	}
	
	@Transactional
	public List<MoviesOutputModel> getMoviesWithMinWatchListAdditions(Integer minAdditions) throws MovieNotFoundException{
		
		log.info("Fetching movies with minimum watchlist additions >= "+minAdditions);
		
		List<MoviesOutputModel> moviesList = moviesUtility.parseOutputFromEntityList(this.getAllMovies())
															.stream()
															.filter(m -> (m.getTotalWatchlistAdd() >= minAdditions))
															.toList();
		if(moviesList.size() == 0) {
			throw new MovieNotFoundException("No Movies Found with minimum watchlist additions >= " +minAdditions);
		}
		
		log.info("Movies with minimum watchlist additions >= {} fetched: {} matches found", minAdditions, moviesList.size());
		
		return moviesList;
	}
	
	@Transactional
	public List<Reviews> getAllReviewsOfMovie(Integer movieId) throws MovieNotFoundException, ReviewNotFoundException{
		
		log.info("Fetching all reviews of movie with id = {}", movieId);
		
		List<Reviews> reviewsList = this.getMovieById(movieId).getReviewsList();
		
		if(reviewsList.size() == 0) {
			throw new ReviewNotFoundException("No user review found for the movie");
		}
		
		log.info("Fetched all reviews of movie with id = {}, {} reviews found", movieId, reviewsList.size());
		
		return reviewsList;
	}
	
	@Transactional
	public List<Ratings> getAllRatingsOfMovie(Integer movieId) throws MovieNotFoundException, RatingNotFoundException{
		
		log.info("Fetching all ratings of movie with id = {}", movieId);
		
		List<Ratings> ratingsList = this.getMovieById(movieId).getRatingsList();
		
		if(ratingsList.size() == 0) {
			throw new RatingNotFoundException("No user ratings found for the movie");
		}
		
		log.info("Fetched all ratings of movie with id = {}, {} ratings found", movieId, ratingsList.size());
		
		return ratingsList;
	}
	
	@Transactional
	public List<WatchListEntries> getAllWatchListEntriesOfMovie(Integer movieId) throws MovieNotFoundException, WatchListEntryNotFoundException{
		
		log.info("Fetching all watchlist entries of movie with id = {}", movieId);
		
		List<WatchListEntries> entryList = this.getMovieById(movieId).getWatchListEntriesList();
		
		if(entryList.size() == 0) {
			throw new WatchListEntryNotFoundException("Movie has not been added to any user's watchlist");
		}
		
		log.info("Fetched all watchlist entries of movie with id = {}, {} entries found", movieId, entryList.size());
		
		return entryList;
	}
	
	/* will implement with SpringSecurity different user roles - admin
	@Transactional
	public Movies updateMovieDetails(String adminEmail, String adminPass, MoviesInputModel moviesModel) throws UserNotFoundException {
		//Enter data for field you want to change
		//and leave rest as null/blank
	}	
	*/
	
	//currently unexposed as api endpoint
	// will implement with SpringSecurity different user roles - admin
	//delete methods
	
	@Transactional
	public Movies deleteMovie(Integer movieId) throws MovieNotFoundException {
		
		log.info("Deleting movie with id = {}", movieId);
		Movies movie = this.getMovieById(movieId);
		movieRepository.deleteById(movieId);
		log.info("Movie with id = {}, name = {} deleted successfully", movie.getMovieId(), movie.getMovieName());
		return movie;
	}
}
