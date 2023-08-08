package com.cineme.cinemeapp.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cineme.cinemeapp.dao.RatingsRepository;
import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.exception.*;
import com.cineme.cinemeapp.model.RatingsInputModel;

@SpringBootTest
public class RatingsServiceImplTest {

    @Mock
    private UsersServiceImpl userService;

    @Mock
    private MoviesServiceImpl movieService;

    @Mock
    private RatingsRepository ratingRepository;

    @InjectMocks
    private RatingsServiceImpl ratingsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRating() throws UserNotFoundException, MovieNotFoundException, InvalidInputException, RatingAlreadyExistsException {
        RatingsInputModel ratingInput = new RatingsInputModel();
        ratingInput.setScore(5);
        
        Ratings newrating = new Ratings();
        newrating.setScore(5);
        // Set up the ratingInput with necessary data

        Users user = new Users();
        // Set up the user with necessary data

        Movies movie = new Movies();
        // Set up the movie with necessary data

        Ratings existingRating = null;
        // Set up existingRating to be null indicating no existing rating
        
        when(userService.getUserByEmail(ratingInput.getUserEmail())).thenReturn(user);
        when(movieService.getMovieById(ratingInput.getMovieId())).thenReturn(movie);
        when(ratingRepository.getRatingOfMovieByUser(movie.getMovieId(), user.getUserId())).thenReturn(existingRating);
        when(ratingRepository.save(any(Ratings.class))).thenReturn(newrating);
        
        Ratings addedRating = ratingsService.addRating(ratingInput);
        
        verify(userService).getUserByEmail(ratingInput.getUserEmail());
        verify(movieService).getMovieById(ratingInput.getMovieId());
        verify(ratingRepository).getRatingOfMovieByUser(movie.getMovieId(), user.getUserId());
        verify(ratingRepository).save(any(Ratings.class));
        
        Assertions.assertEquals(newrating, addedRating);
    }

    @Test
    public void testGetRatingById() throws RatingNotFoundException {
        int ratingId = 1;
        Ratings rating = new Ratings();
        // Set up the rating with necessary data
        
        when(ratingRepository.findById(ratingId)).thenReturn(java.util.Optional.of(rating));
        
        Ratings retrievedRating = ratingsService.getRatingById(ratingId);
        
        verify(ratingRepository).findById(ratingId);
        
        Assertions.assertEquals(rating, retrievedRating);
    }

    @Test
    public void testGetAllRatingsOfMovie() throws MovieNotFoundException, RatingNotFoundException {
        int movieId = 1;
        Movies movie = new Movies();
        List<Ratings> ratingsList = new ArrayList<>();
        
        Ratings rating = new Ratings(); rating.setRatingId(1);
        ratingsList.add(rating);
        movie.setRatingsList(ratingsList);
        // Set up the movie and ratingsList with necessary data
        
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(ratingRepository.getAllRatingsOfMovie(movie.getMovieId())).thenReturn(ratingsList);
        
        List<Ratings> retrievedRatingsList = ratingsService.getAllRatingsOfMovie(movieId);
        
        verify(movieService).getMovieById(movieId);
        verify(ratingRepository).getAllRatingsOfMovie(movie.getMovieId());
        
        Assertions.assertEquals(ratingsList, retrievedRatingsList);
    }

    @Test
    public void testGetAllRatingsByUser() throws UserNotFoundException, RatingNotFoundException {
        int userId = 1;
        Users user = new Users();
        List<Ratings> ratingsList = new ArrayList<>();
        // Set up the user and ratingsList with necessary data
        
        Ratings rating = new Ratings(); rating.setRatingId(1);
        ratingsList.add(rating);
        user.setRatingsList(ratingsList);
        
        when(userService.getUserById(userId)).thenReturn(user);
        when(ratingRepository.getAllRatingsByUser(user.getUserId())).thenReturn(ratingsList);
        
        List<Ratings> retrievedRatingsList = ratingsService.getAllRatingsByUser(userId);
        
        verify(userService).getUserById(userId);
        verify(ratingRepository).getAllRatingsByUser(user.getUserId());
        
        Assertions.assertEquals(ratingsList, retrievedRatingsList);
    }

    @Test
    public void testGetRatingOfMovieByUser() throws MovieNotFoundException, UserNotFoundException, RatingNotFoundException {
        int movieId = 1;
        int userId = 1;
        Movies movie = new Movies();
        Users user = new Users();
        Ratings rating = new Ratings();
        // Set up the movie, user, and rating with necessary data
        
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(userService.getUserById(userId)).thenReturn(user);
        when(ratingRepository.getRatingOfMovieByUser(movie.getMovieId(), user.getUserId())).thenReturn(rating);
        
        Ratings retrievedRating = ratingsService.getRatingOfMovieByUser(movieId, userId);
        
        verify(movieService).getMovieById(movieId);
        verify(userService).getUserById(userId);
        verify(ratingRepository).getRatingOfMovieByUser(movie.getMovieId(), user.getUserId());
        
        Assertions.assertEquals(rating, retrievedRating);
    }

    @Test
    public void testUpdateRating() throws UserNotFoundException, MovieNotFoundException, InvalidInputException, RatingNotFoundException {
        int ratingId = 1;
        Movies movie = new Movies();
        movie.setMovieId(1);
        
        Users user = new Users();
        user.setEmail("test@email.com");
        
        RatingsInputModel ratingInput = new RatingsInputModel();
        // Set up the ratingId and ratingInput with necessary data
        ratingInput.setMovieId(1);
        ratingInput.setScore(5);
        ratingInput.setUserEmail("test@email.com");

        Ratings rating = new Ratings();
        rating.setRatingId(1);
        rating.setMovie(movie);
        rating.setScore(3);
        rating.setUser(user);
        
        Ratings newRating = new Ratings();
        newRating.setRatingId(1);
        newRating.setMovie(movie);
        newRating.setScore(5);
        rating.setUser(user);
        // Set up the rating with necessary data
        
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        when(ratingRepository.save(any(Ratings.class))).thenReturn(newRating);
        
        Ratings updatedRating = ratingsService.updateRating(ratingId, ratingInput);
        
        verify(ratingRepository).findById(ratingId);
        verify(ratingRepository).save(newRating);
        
        Assertions.assertEquals(newRating, updatedRating);
    }

    @Test
    public void testDeleteRating() throws RatingNotFoundException {
        int ratingId = 1;
        Ratings rating = new Ratings();
        // Set up the rating with necessary data
        
        when(ratingRepository.findById(ratingId)).thenReturn(java.util.Optional.of(rating));
        
        Ratings deletedRating = ratingsService.deleteRating(ratingId);
        
        verify(ratingRepository).findById(ratingId);
        verify(ratingRepository).deleteById(ratingId);
        
        Assertions.assertEquals(rating, deletedRating);
    }

    // ...
}

