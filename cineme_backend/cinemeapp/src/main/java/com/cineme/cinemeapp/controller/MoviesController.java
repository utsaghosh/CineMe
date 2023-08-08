package com.cineme.cinemeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.RatingNotFoundException;
import com.cineme.cinemeapp.exception.ReviewNotFoundException;
import com.cineme.cinemeapp.model.MoviesInputModel;
import com.cineme.cinemeapp.model.MoviesOutputModel;
import com.cineme.cinemeapp.model.RatingsOutputModel;
import com.cineme.cinemeapp.model.ReviewsOutputModel;
import com.cineme.cinemeapp.service.MoviesService;
import com.cineme.cinemeapp.utility.MoviesUtility;
import com.cineme.cinemeapp.utility.RatingsUtility;
import com.cineme.cinemeapp.utility.ReviewsUtility;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/movies") //The class-level annotation maps a specific request path or pattern onto a controller. 
@Slf4j
public class MoviesController {
	
	@Autowired
	MoviesService movieService;
	
	@Autowired
	MoviesUtility moviesUtility;
	
	@Autowired
	ReviewsUtility reviewsUtility;
	
	@Autowired
	RatingsUtility ratingsUtility;
	
	@PostMapping("/addmovie")
	public ResponseEntity<MoviesOutputModel> addMovie(@RequestBody MoviesInputModel moviesModel) throws InvalidInputException{
		
		log.info("Request: add new movie with: {}", moviesModel);
		Movies movieEntity = movieService.addMovie(moviesModel);
		MoviesOutputModel movie = moviesUtility.parseOutputFromEntity(movieEntity);
		log.info("Response: new movie created with id={} : {}", movieEntity.getMovieId(), movie);
		return new ResponseEntity<MoviesOutputModel>(movie, HttpStatus.OK);
	}
	
	
	//get -------->
	
	@GetMapping("/getallmovies")
	public ResponseEntity<List<MoviesOutputModel>> getAllMovies() throws MovieNotFoundException{
		
		log.info("Request: all movies from database");
		List<MoviesOutputModel> movieList = moviesUtility.parseOutputFromEntityList(movieService.getAllMovies());
		log.info("Response: {}", movieList);
		return new ResponseEntity<List<MoviesOutputModel>>(movieList, HttpStatus.OK);
	}
	
	@GetMapping("/getmoviedetails/{movieid}")
	public ResponseEntity<MoviesOutputModel> getMovieDetails(@PathVariable("movieid") Integer movieId) throws MovieNotFoundException{
		
		log.info("Request: details of movie with id = {}",movieId);
		MoviesOutputModel movie = moviesUtility.parseOutputFromEntity(movieService.getMovieById(movieId));
		log.info("Response: {}", movie);
		return new ResponseEntity<MoviesOutputModel>(movie, HttpStatus.OK);
	}
	
	@GetMapping("/getmoviesmatchingname/{name}")
	public ResponseEntity<List<MoviesOutputModel>> getMoviesMatchingName(@PathVariable("name") String nameSearch) throws MovieNotFoundException{
		
		log.info("Request: all movies with name like \'{}\'",nameSearch);
		List<MoviesOutputModel> moviesList = moviesUtility.parseOutputFromEntityList(movieService.getMoviesMatchingName(nameSearch));
		log.info("Response: {}", moviesList);
		return new ResponseEntity<List<MoviesOutputModel>>(moviesList, HttpStatus.OK);
	}
	
	@GetMapping("/getmoviesoflanguage/{language}")
	public ResponseEntity<List<MoviesOutputModel>> getMoviesOfLanguage(@PathVariable("language") String language) throws MovieNotFoundException{
		
		log.info("Request: movies of language \'{}\'", language);
		List<MoviesOutputModel> movieList = moviesUtility.parseOutputFromEntityList(movieService.getMoviesOfLanguage(language));
		log.info("Response: {}", movieList);
		return new ResponseEntity<List<MoviesOutputModel>>(movieList, HttpStatus.OK);		
	}
	
	@GetMapping("/getmoviesbyyearofrelease/{minyear}/{maxyear}")
	public ResponseEntity<List<MoviesOutputModel>> getMoviesByYearOfRelease(@PathVariable("minyear") Integer minYear, 
																			@PathVariable("maxyear") Integer maxYear) 
															throws MovieNotFoundException{
		
		log.info("Request: movies with release year in [{}, {}]", minYear, maxYear);
		List<MoviesOutputModel> movieList = moviesUtility.parseOutputFromEntityList(movieService.getMoviesByYearOfRelease(minYear, maxYear));
		log.info("Response: {}", movieList);
		return new ResponseEntity<List<MoviesOutputModel>>(movieList, HttpStatus.OK);
	}
	
	@GetMapping("/getmovieswithminavgrating/{minavgrating}")
	public ResponseEntity<List<MoviesOutputModel>> getMoviesWithMinAvgRating(@PathVariable("minavgrating") Integer minAvgRating) throws MovieNotFoundException{
		
		log.info("Request: movies with minimum average rating >= {}",minAvgRating);
		List<MoviesOutputModel> movieList = movieService.getMoviesWithMinAvgRating(minAvgRating);
		log.info("Response: {}", movieList);
		return new ResponseEntity<List<MoviesOutputModel>>(movieList, HttpStatus.OK);
	}
	
	@GetMapping("/getmovieswithminwatchlistadditions/{minadd}")
	public ResponseEntity<List<MoviesOutputModel>> getMoviesWithMinWatchListAdditions(@PathVariable("minadd") Integer minAdditions) throws MovieNotFoundException{
		
		log.info("Request: movies with minimum watchlist additions >= {}",minAdditions);
		List<MoviesOutputModel> movieList = movieService.getMoviesWithMinWatchListAdditions(minAdditions);
		log.info("Response: {}", movieList);
		return new ResponseEntity<List<MoviesOutputModel>>(movieList, HttpStatus.OK);
	}
	
	@GetMapping("/getallreviewsofmovie/{movieid}")
	public ResponseEntity<List<ReviewsOutputModel>> getAllReviewsOfMovie(@PathVariable("movieid") Integer movieId) throws MovieNotFoundException, ReviewNotFoundException{
		
		log.info("Request: all reviews of movie with id = {}",movieId);
		List<ReviewsOutputModel> reviewsList = reviewsUtility.parseOutputFromEntityList(movieService.getAllReviewsOfMovie(movieId));
		log.info("Response: {}", reviewsList);
		return new ResponseEntity<List<ReviewsOutputModel>>(reviewsList, HttpStatus.OK);
	}
	
	@GetMapping("/getallratingsofmovie/{movieid}")
	public ResponseEntity<List<RatingsOutputModel>> getAllRatingsOfMovie(@PathVariable("movieid") Integer movieId) throws MovieNotFoundException, RatingNotFoundException{
		
		log.info("Request: all ratings of movie with id = {}",movieId);
		List<RatingsOutputModel> ratingsList = ratingsUtility.parseOutputFromEntityList(movieService.getAllRatingsOfMovie(movieId));
		log.info("Response: {}", ratingsList);
		return new ResponseEntity<List<RatingsOutputModel>>(ratingsList, HttpStatus.OK);
	}
}
