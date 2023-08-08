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

import com.cineme.cinemeapp.dao.ReviewsRepository;
import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.entity.Reviews;
import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.exception.*;
import com.cineme.cinemeapp.model.ReviewsInputModel;
import com.cineme.cinemeapp.utility.ReviewsUtility;

@SpringBootTest
public class ReviewsServiceImplTest {

    @Mock
    private UsersServiceImpl userService;

    @Mock
    private MoviesServiceImpl movieService;

    @Mock
    private ReviewsUtility reviewUtility;

    @Mock
    private ReviewsRepository reviewRepository;

    @InjectMocks
    private ReviewsServiceImpl reviewsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddReview() throws UserNotFoundException, MovieNotFoundException, InvalidInputException, ReviewAlreadyExistsException {
        ReviewsInputModel reviewInput = new ReviewsInputModel();
        reviewInput.setBody("test");
        // Set up the reviewInput with necessary data
        
        Reviews newReview = new Reviews();

        Users user = new Users();
        // Set up the user with necessary data

        Movies movie = new Movies();
        // Set up the movie with necessary data

        Reviews existingReview = null;
        // Set up existingReview to be null indicating no existing review
        
        when(reviewUtility.validateInputModel(reviewInput)).thenReturn(true);
        when(userService.getUserByEmail(reviewInput.getUserEmail())).thenReturn(user);
        when(movieService.getMovieById(reviewInput.getMovieId())).thenReturn(movie);
        when(reviewRepository.getReviewOfMovieByUser(movie.getMovieId(), user.getUserId())).thenReturn(existingReview);
        when(reviewRepository.save(any(Reviews.class))).thenReturn(newReview);
        
        Reviews addedReview = reviewsService.addReview(reviewInput);
        
        verify(reviewUtility).validateInputModel(reviewInput);
        verify(userService).getUserByEmail(reviewInput.getUserEmail());
        verify(movieService).getMovieById(reviewInput.getMovieId());
        verify(reviewRepository).getReviewOfMovieByUser(movie.getMovieId(), user.getUserId());
        verify(reviewRepository).save(any(Reviews.class));
        
        Assertions.assertEquals(newReview ,addedReview);
    }

    @Test
    public void testGetReviewById() throws ReviewNotFoundException {
        int reviewId = 1;
        Reviews review = new Reviews();
        // Set up the review with necessary data
        
        when(reviewRepository.findById(reviewId)).thenReturn(java.util.Optional.of(review));
        
        Reviews retrievedReview = reviewsService.getReviewById(reviewId);
        
        verify(reviewRepository).findById(reviewId);
        
        Assertions.assertEquals(review, retrievedReview);
    }

    @Test
    public void testGetAllReviewsOfMovie() throws MovieNotFoundException, ReviewNotFoundException {
        int movieId = 1;
        Movies movie = new Movies();
        List<Reviews> reviewsList = new ArrayList<>();
        // Set up the movie and reviewsList with necessary data
        
        Reviews review = new Reviews(); review.setReviewId(1);
        reviewsList.add(review);
        movie.setReviewsList(reviewsList);
        
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(reviewRepository.getAllReviewsOfMovie(movie.getMovieId())).thenReturn(reviewsList);
        
        List<Reviews> retrievedReviewsList = reviewsService.getAllReviewsOfMovie(movieId);
        
        verify(movieService).getMovieById(movieId);
        verify(reviewRepository).getAllReviewsOfMovie(movie.getMovieId());
        
        Assertions.assertEquals(reviewsList, retrievedReviewsList);
    }

    @Test
    public void testGetAllReviewsByUser() throws UserNotFoundException, ReviewNotFoundException {
        int userId = 1;
        Users user = new Users();
        List<Reviews> reviewsList = new ArrayList<>();
        // Set up the user and reviewsList with necessary data
        
        Reviews review = new Reviews(); review.setReviewId(1);
        reviewsList.add(review);
        user.setReviewsList(reviewsList);
        
        when(userService.getUserById(userId)).thenReturn(user);
        when(reviewRepository.getAllReviewsByUser(user.getUserId())).thenReturn(reviewsList);
        
        List<Reviews> retrievedReviewsList = reviewsService.getAllReviewsByUser(userId);
        
        verify(userService).getUserById(userId);
        verify(reviewRepository).getAllReviewsByUser(user.getUserId());
        
        Assertions.assertEquals(reviewsList, retrievedReviewsList);
    }

    @Test
    public void testGetReviewOfMovieByUser() throws MovieNotFoundException, UserNotFoundException, ReviewNotFoundException {
        int movieId = 1;
        int userId = 1;
        Movies movie = new Movies();
        Users user = new Users();
        Reviews review = new Reviews();
        // Set up the movie, user, and review with necessary data
        
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(userService.getUserById(userId)).thenReturn(user);
        when(reviewRepository.getReviewOfMovieByUser(movie.getMovieId(), user.getUserId())).thenReturn(review);
        
        Reviews retrievedReview = reviewsService.getReviewOfMovieByUser(movieId, userId);
        
        verify(movieService).getMovieById(movieId);
        verify(userService).getUserById(userId);
        verify(reviewRepository).getReviewOfMovieByUser(movie.getMovieId(), user.getUserId());
        
        Assertions.assertEquals(review, retrievedReview);
    }

    @Test
    public void testUpdateReview() throws UserNotFoundException, MovieNotFoundException, InvalidInputException, ReviewNotFoundException {
        int reviewId = 1;
        
        Movies movie = new Movies();
        movie.setMovieId(1);
        
        Users user = new Users();
        user.setEmail("test@email.com");
        
        ReviewsInputModel reviewInput = new ReviewsInputModel();
        // Set up the reviewId and reviewInput with necessary data
        reviewInput.setMovieId(1);
        reviewInput.setBody("test");
        reviewInput.setUserEmail("test@email.com");

        Reviews review = new Reviews();
        // Set up the review with necessary data
        review.setReviewId(1);
        review.setMovie(movie);
        review.setBody("old");
        review.setUser(user);
        
        Reviews newReview = new Reviews();
        newReview.setReviewId(1);
        newReview.setMovie(movie);
        newReview.setBody("test");
        newReview.setUser(user);
        
        when(reviewRepository.findById(reviewId)).thenReturn(java.util.Optional.of(review));
        when(reviewRepository.save(any(Reviews.class))).thenReturn(newReview);
        
        Reviews updatedReview = reviewsService.updateReview(reviewId, reviewInput);
        
        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository).save(newReview);
        
        Assertions.assertEquals(review, updatedReview);
    }

    @Test
    public void testDeleteReview() throws ReviewNotFoundException {
        int reviewId = 1;
        Reviews review = new Reviews();
        // Set up the review with necessary data
        
        when(reviewRepository.findById(reviewId)).thenReturn(java.util.Optional.of(review));
        
        Reviews deletedReview = reviewsService.deleteReview(reviewId);
        
        verify(reviewRepository).findById(reviewId);
        verify(reviewRepository).deleteById(reviewId);
        
        Assertions.assertEquals(review, deletedReview);
    }

    // ...
}

