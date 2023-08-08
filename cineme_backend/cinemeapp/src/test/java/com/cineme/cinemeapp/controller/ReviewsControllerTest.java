
package com.cineme.cinemeapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.ReviewAlreadyExistsException;
import com.cineme.cinemeapp.exception.ReviewNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.entity.Reviews;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.model.ReviewsInputModel;
import com.cineme.cinemeapp.model.ReviewsOutputModel;
import com.cineme.cinemeapp.service.ReviewsService;
import com.cineme.cinemeapp.utility.ReviewsUtility;

@SpringBootTest
public class ReviewsControllerTest {

    @Mock
    private ReviewsService reviewsService;

    @Mock
    private ReviewsUtility reviewsUtility;

    @InjectMocks
    private ReviewsController reviewsController;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReviewOfMovieByUser() throws MovieNotFoundException, UserNotFoundException, ReviewNotFoundException {
        int movieId = 1;
        int userId = 1;
        Reviews review = new Reviews();
        ReviewsOutputModel output = new ReviewsOutputModel();

        when(reviewsService.getReviewOfMovieByUser(movieId, userId)).thenReturn(review);
        when(reviewsUtility.parseOutputFromEntity(review)).thenReturn(output);

        ResponseEntity<ReviewsOutputModel> response = reviewsController.getReviewOfMovieByUser(movieId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(output, response.getBody());

        verify(reviewsService, times(1)).getReviewOfMovieByUser(movieId, userId);
        verify(reviewsUtility, times(1)).parseOutputFromEntity(review);
    }

    @Test
    public void testGetAllReviewsOfMovie() throws MovieNotFoundException, ReviewNotFoundException {
        int movieId = 1;
        List<Reviews> reviews = new ArrayList<>();
        List<ReviewsOutputModel> output = new ArrayList<>();

        when(reviewsService.getAllReviewsOfMovie(movieId)).thenReturn(reviews);
        when(reviewsUtility.parseOutputFromEntityList(reviews)).thenReturn(output);

        ResponseEntity<List<ReviewsOutputModel>> response = reviewsController.getAllReviewsOfMovie(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(output, response.getBody());

        verify(reviewsService, times(1)).getAllReviewsOfMovie(movieId);
        verify(reviewsUtility, times(1)).parseOutputFromEntityList(reviews);
    }

    @Test
    public void testGetAllReviewsByUser() throws UserNotFoundException, ReviewNotFoundException {
        int userId = 1;
        List<Reviews> reviews = new ArrayList<>();
        List<ReviewsOutputModel> output = new ArrayList<>();

        when(reviewsService.getAllReviewsByUser(userId)).thenReturn(reviews);
        when(reviewsUtility.parseOutputFromEntityList(reviews)).thenReturn(output);

        ResponseEntity<List<ReviewsOutputModel>> response = reviewsController.getAllReviewsByUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(output, response.getBody());

        verify(reviewsService, times(1)).getAllReviewsByUser(userId);
        verify(reviewsUtility, times(1)).parseOutputFromEntityList(reviews);
    }

    @Test
    public void testAddReview() throws UserNotFoundException, MovieNotFoundException, InvalidInputException, ReviewAlreadyExistsException {
        ReviewsInputModel reviewInput = new ReviewsInputModel();
        ReviewsOutputModel reviewOutput = new ReviewsOutputModel();
        Reviews review = new Reviews();

        when(reviewsService.addReview(reviewInput)).thenReturn(review);
        when(reviewsUtility.parseOutputFromEntity(review)).thenReturn(reviewOutput);

        ResponseEntity<ReviewsOutputModel> response = reviewsController.addReview(reviewInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewOutput, response.getBody());

        verify(reviewsService, times(1)).addReview(reviewInput);
        verify(reviewsUtility, times(1)).parseOutputFromEntity(review);
    }

    @Test
    public void testUpdateReview() throws UserNotFoundException, MovieNotFoundException, InvalidInputException, ReviewNotFoundException {
        int reviewId = 1;
        ReviewsInputModel reviewInput = new ReviewsInputModel();
        ReviewsOutputModel reviewOutput = new ReviewsOutputModel();
        Reviews review = new Reviews();

        when(reviewsService.updateReview(reviewId, reviewInput)).thenReturn(review);
        when(reviewsUtility.parseOutputFromEntity(review)).thenReturn(reviewOutput);

        ResponseEntity<ReviewsOutputModel> response = reviewsController.updateReview(reviewId, reviewInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewOutput, response.getBody());

        verify(reviewsService, times(1)).updateReview(reviewId, reviewInput);
        verify(reviewsUtility, times(1)).parseOutputFromEntity(review);
    }

    @Test
    public void testDeleteReview() throws ReviewNotFoundException {
        int reviewId = 1;
        Reviews review = new Reviews();
        ReviewsOutputModel output = new ReviewsOutputModel();

        when(reviewsService.deleteReview(reviewId)).thenReturn(review);
        when(reviewsUtility.parseOutputFromEntity(review)).thenReturn(output);

        ResponseEntity<ReviewsOutputModel> response = reviewsController.deleteReview(reviewId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(output, response.getBody());

        verify(reviewsService, times(1)).deleteReview(reviewId);
        verify(reviewsUtility, times(1)).parseOutputFromEntity(review);
    }
}