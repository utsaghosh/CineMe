
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

import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.RatingAlreadyExistsException;
import com.cineme.cinemeapp.exception.RatingNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.service.RatingsService;
import com.cineme.cinemeapp.utility.RatingsUtility;
import com.cineme.cinemeapp.model.RatingsInputModel;
import com.cineme.cinemeapp.model.RatingsOutputModel;

@SpringBootTest
public class RatingsControllerTest {

    @Mock
    private RatingsService ratingsService;

    @Mock
    private RatingsUtility ratingsUtility;

    @InjectMocks
    private RatingsController ratingsController;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRatingOfMovieByUser() throws MovieNotFoundException, UserNotFoundException, RatingNotFoundException {
        int movieId = 1;
        int userId = 1;
        Ratings rating = new Ratings();
        RatingsOutputModel outputModel = new RatingsOutputModel();

        when(ratingsService.getRatingOfMovieByUser(movieId, userId)).thenReturn(rating);
        when(ratingsUtility.parseOutputFromEntity(rating)).thenReturn(outputModel);

        ResponseEntity<RatingsOutputModel> response = ratingsController.getRatingOfMovieByUser(movieId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(outputModel, response.getBody());

        verify(ratingsService, times(1)).getRatingOfMovieByUser(movieId, userId);
        verify(ratingsUtility, times(1)).parseOutputFromEntity(rating);
    }

    @Test
    public void testGetAllRatingsOfMovie() throws MovieNotFoundException, RatingNotFoundException {
        int movieId = 1;
        List<Ratings> ratingsEntity = new ArrayList<>();
        List<RatingsOutputModel> ratings = new ArrayList<>();

        when(ratingsService.getAllRatingsOfMovie(movieId)).thenReturn(ratingsEntity);
        when(ratingsUtility.parseOutputFromEntityList(ratingsEntity)).thenReturn(ratings);

        ResponseEntity<List<RatingsOutputModel>> response = ratingsController.getAllRatingsOfMovie(movieId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratings, response.getBody());

        verify(ratingsService, times(1)).getAllRatingsOfMovie(movieId);
        verify(ratingsUtility, times(1)).parseOutputFromEntityList(ratingsEntity);
    }

    @Test
    public void testGetAllRatingsByUser() throws UserNotFoundException, RatingNotFoundException {
        int userId = 1;
        List<Ratings> ratingsEntity = new ArrayList<>();
        List<RatingsOutputModel> ratings = new ArrayList<>();

        when(ratingsService.getAllRatingsByUser(userId)).thenReturn(ratingsEntity);
        when(ratingsUtility.parseOutputFromEntityList(ratingsEntity)).thenReturn(ratings);

        ResponseEntity<List<RatingsOutputModel>> response = ratingsController.getAllRatingsByUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratings, response.getBody());

        verify(ratingsService, times(1)).getAllRatingsByUser(userId);
        verify(ratingsUtility, times(1)).parseOutputFromEntityList(ratingsEntity);
    }

    @Test
    public void testAddRating() throws UserNotFoundException, MovieNotFoundException, RatingNotFoundException, RatingAlreadyExistsException, InvalidInputException {
        RatingsInputModel ratingInput = new RatingsInputModel();
        RatingsOutputModel ratingOutput = new RatingsOutputModel();
        Ratings ratingEntity = new Ratings();

        when(ratingsService.addRating(ratingInput)).thenReturn(ratingEntity);
        when(ratingsUtility.parseOutputFromEntity(ratingEntity)).thenReturn(ratingOutput);

        ResponseEntity<RatingsOutputModel> response = ratingsController.addRating(ratingInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratingOutput, response.getBody());

        verify(ratingsService, times(1)).addRating(ratingInput);
        verify(ratingsUtility, times(1)).parseOutputFromEntity(ratingEntity);
    }

    @Test
    public void testUpdateRating() throws UserNotFoundException, MovieNotFoundException, RatingNotFoundException, InvalidInputException {
        int ratingId = 1;
        RatingsInputModel ratingInput = new RatingsInputModel();
        RatingsOutputModel ratingOutput = new RatingsOutputModel();
        Ratings ratingEntity = new Ratings();

        when(ratingsService.updateRating(ratingId, ratingInput)).thenReturn(ratingEntity);
        when(ratingsUtility.parseOutputFromEntity(ratingEntity)).thenReturn(ratingOutput);

        ResponseEntity<RatingsOutputModel> response = ratingsController.updateRating(ratingId, ratingInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratingOutput, response.getBody());

        verify(ratingsService, times(1)).updateRating(ratingId, ratingInput);
        verify(ratingsUtility, times(1)).parseOutputFromEntity(ratingEntity);
    }

    @Test
    public void testDeleteRating() throws RatingNotFoundException, MovieNotFoundException {
        int ratingId = 1;
        RatingsOutputModel ratingOutput = new RatingsOutputModel();
        Ratings ratingEntity = new Ratings();
        
        when(ratingsService.deleteRating(ratingId)).thenReturn(ratingEntity);
        when(ratingsUtility.parseOutputFromEntity(ratingEntity)).thenReturn(ratingOutput);

        ResponseEntity<RatingsOutputModel> response = ratingsController.deleteRating(ratingId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratingOutput, response.getBody());

        verify(ratingsService, times(1)).deleteRating(ratingId);
        verify(ratingsUtility, times(1)).parseOutputFromEntity(ratingEntity);
    }
}
