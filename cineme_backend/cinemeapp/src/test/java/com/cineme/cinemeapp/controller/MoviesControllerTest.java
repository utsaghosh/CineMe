
package com.cineme.cinemeapp.controller;

import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.entity.Reviews;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MoviesControllerTest {

    @Mock
    private MoviesService  movieService;

    @Mock
    private MoviesUtility moviesUtility;

    @Mock
    private ReviewsUtility reviewsUtility;

    @Mock
    private RatingsUtility ratingsUtility;

    @InjectMocks
    private MoviesController moviesController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddMovie() throws InvalidInputException {
        MoviesInputModel inputModel = new MoviesInputModel();
        MoviesOutputModel outputModel = new MoviesOutputModel();
        Movies movieEntity = new Movies();

        when(movieService.addMovie(inputModel)).thenReturn(movieEntity);
        when(moviesUtility.parseOutputFromEntity(movieEntity)).thenReturn(outputModel);

        ResponseEntity<MoviesOutputModel> response = moviesController.addMovie(inputModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModel, response.getBody());

        verify(movieService, times(1)).addMovie(inputModel);
        verify(moviesUtility, times(1)).parseOutputFromEntity(movieEntity);
    }

    // Write similar test methods for other controller methods

    @Test
    public void testGetAllMovies() throws MovieNotFoundException {
        List<Movies> movies = new ArrayList<>();
        List<MoviesOutputModel> outputModels = new ArrayList<>();

        when(movieService.getAllMovies()).thenReturn(movies);
        when(moviesUtility.parseOutputFromEntityList(movies)).thenReturn(outputModels);

        ResponseEntity<List<MoviesOutputModel>> response = moviesController.getAllMovies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModels, response.getBody());

        verify(movieService, times(1)).getAllMovies();
        verify(moviesUtility, times(1)).parseOutputFromEntityList(movies);
    }
    
    @Test
    public void testGetMovieDetails() throws MovieNotFoundException {
        int movieId = 1;
        MoviesOutputModel outputModel = new MoviesOutputModel();
        Movies movieEntity = new Movies();

        when(movieService.getMovieById(movieId)).thenReturn(movieEntity);
        when(moviesUtility.parseOutputFromEntity(movieEntity)).thenReturn(outputModel);

        ResponseEntity<MoviesOutputModel> response = moviesController.getMovieDetails(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModel, response.getBody());

        verify(movieService, times(1)).getMovieById(movieId);
        verify(moviesUtility, times(1)).parseOutputFromEntity(movieEntity);
    }
    
    @Test
    public void testGetMoviesMatchingName() throws MovieNotFoundException {
        String nameSearch = "Avengers";
        List<MoviesOutputModel> outputModels = new ArrayList<>();
        List<Movies> movies = new ArrayList<>();

        when(movieService.getMoviesMatchingName(nameSearch)).thenReturn(movies);
        when(moviesUtility.parseOutputFromEntityList(movies)).thenReturn(outputModels);

        ResponseEntity<List<MoviesOutputModel>> response = moviesController.getMoviesMatchingName(nameSearch);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModels, response.getBody());

        verify(movieService, times(1)).getMoviesMatchingName(nameSearch);
        verify(moviesUtility, times(1)).parseOutputFromEntityList(movies);
    }
    
    @Test
    public void testGetMoviesOfLanguage() throws MovieNotFoundException {
        String language = "English";
        List<MoviesOutputModel> outputModels = new ArrayList<>();
        List<Movies> movies = new ArrayList<>();

        when(movieService.getMoviesOfLanguage(language)).thenReturn(movies);
        when(moviesUtility.parseOutputFromEntityList(movies)).thenReturn(outputModels);

        ResponseEntity<List<MoviesOutputModel>> response = moviesController.getMoviesOfLanguage(language);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModels, response.getBody());

        verify(movieService, times(1)).getMoviesOfLanguage(language);
        verify(moviesUtility, times(1)).parseOutputFromEntityList(movies);
    }
    
    @Test
    public void testGetMoviesByYearOfRelease() throws MovieNotFoundException {
        int minYear = 2000;
        int maxYear = 2020;
        List<MoviesOutputModel> outputModels = new ArrayList<>();
        List<Movies> movies = new ArrayList<>();

        when(movieService.getMoviesByYearOfRelease(minYear, maxYear)).thenReturn(movies);
        when(moviesUtility.parseOutputFromEntityList(movies)).thenReturn(outputModels);

        ResponseEntity<List<MoviesOutputModel>> response = moviesController.getMoviesByYearOfRelease(minYear, maxYear);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModels, response.getBody());

        verify(movieService, times(1)).getMoviesByYearOfRelease(minYear, maxYear);
        verify(moviesUtility, times(1)).parseOutputFromEntityList(movies);
    }
    
    @Test
    public void testGetMoviesWithMinAvgRating() throws MovieNotFoundException {
        int minAvgRating = 4;
        List<MoviesOutputModel> outputModels = new ArrayList<>();

        when(movieService.getMoviesWithMinAvgRating(minAvgRating)).thenReturn(outputModels);

        ResponseEntity<List<MoviesOutputModel>> response = moviesController.getMoviesWithMinAvgRating(minAvgRating);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModels, response.getBody());

        verify(movieService, times(1)).getMoviesWithMinAvgRating(minAvgRating);
    }
    
    @Test
    public void testGetMoviesWithMinWatchListAdditions() throws MovieNotFoundException {
        int minAdditions = 10;
        List<MoviesOutputModel> outputModels = new ArrayList<>();

        when(movieService.getMoviesWithMinWatchListAdditions(minAdditions)).thenReturn(outputModels);

        ResponseEntity<List<MoviesOutputModel>> response = moviesController.getMoviesWithMinWatchListAdditions(minAdditions);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModels, response.getBody());

        verify(movieService, times(1)).getMoviesWithMinWatchListAdditions(minAdditions);
    }
    
    @Test
    public void testGetAllReviewsOfMovie() throws MovieNotFoundException, ReviewNotFoundException {
        int movieId = 1;
        List<Reviews> reviews = new ArrayList<>();
        List<ReviewsOutputModel> outputModels = new ArrayList<>();

        when(movieService.getAllReviewsOfMovie(movieId)).thenReturn(reviews);
        when(reviewsUtility.parseOutputFromEntityList(reviews)).thenReturn(outputModels);
        
        ResponseEntity<List<ReviewsOutputModel>> response = moviesController.getAllReviewsOfMovie(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModels, response.getBody());

        verify(movieService, times(1)).getAllReviewsOfMovie(movieId);
        verify(reviewsUtility, times(1)).parseOutputFromEntityList(reviews);
    }

    @Test
    public void testGetAllRatingsOfMovie() throws MovieNotFoundException, RatingNotFoundException {
        int movieId = 1;
        List<Ratings> ratings = new ArrayList<>();
        List<RatingsOutputModel> outputModels = new ArrayList<>();

        when(movieService.getAllRatingsOfMovie(movieId)).thenReturn(ratings);
        when(ratingsUtility.parseOutputFromEntityList(ratings)).thenReturn(outputModels);

        ResponseEntity<List<RatingsOutputModel>> response = moviesController.getAllRatingsOfMovie(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratings, response.getBody());

        verify(movieService, times(1)).getAllRatingsOfMovie(movieId);
        verify(ratingsUtility, times(1)).parseOutputFromEntityList(ratings);
    }

}
