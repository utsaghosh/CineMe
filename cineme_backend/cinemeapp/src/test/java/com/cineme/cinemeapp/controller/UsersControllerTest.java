
package com.cineme.cinemeapp.controller;

import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.entity.Reviews;
import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.entity.WatchListEntries;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.RatingNotFoundException;
import com.cineme.cinemeapp.exception.ReviewNotFoundException;
import com.cineme.cinemeapp.exception.UserAlreadyExistsException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.exception.WatchListEntryNotFoundException;
import com.cineme.cinemeapp.model.RatingsOutputModel;
import com.cineme.cinemeapp.model.ReviewsOutputModel;
import com.cineme.cinemeapp.model.UsersInputModel;
import com.cineme.cinemeapp.model.UsersOutputModel;
import com.cineme.cinemeapp.model.WatchListEntryOutputModel;
import com.cineme.cinemeapp.service.UsersService;
import com.cineme.cinemeapp.utility.MoviesUtility;
import com.cineme.cinemeapp.utility.RatingsUtility;
import com.cineme.cinemeapp.utility.ReviewsUtility;
import com.cineme.cinemeapp.utility.UsersUtility;
import com.cineme.cinemeapp.utility.WatchListUtility;

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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsersControllerTest {

    @Mock
    private UsersService userService;

    @Mock
    private UsersUtility userUtility;

    @Mock
    private MoviesUtility moviesUtility;

    @Mock
    private ReviewsUtility reviewsUtility;

    @Mock
    private RatingsUtility ratingsUtility;
    
    @Mock
    private WatchListUtility watchListUtility;

    @InjectMocks
    private UsersController usersController;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByEmail() throws UserNotFoundException {
        String email = "test@example.com";
        UsersOutputModel userModel = new UsersOutputModel();
        Users userEntity = new Users();

        when(userService.getUserByEmail(email)).thenReturn(userEntity);
        when(userUtility.parseOutputFromEntity(userEntity)).thenReturn(userModel);

        ResponseEntity<UsersOutputModel> response = usersController.getUserByEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userModel, response.getBody());

        verify(userService, times(1)).getUserByEmail(email);
        verify(userUtility, times(1)).parseOutputFromEntity(userEntity);
    }

    @Test
    public void testGetAllReviewsByUser() throws UserNotFoundException, ReviewNotFoundException {
        int userId = 1;
        List<Reviews> reviewEntityList = new ArrayList<>();
        List<ReviewsOutputModel> reviews = new ArrayList<>();

        when(userService.getAllReviewsByUser(userId)).thenReturn(reviewEntityList);
        when(reviewsUtility.parseOutputFromEntityList(reviewEntityList)).thenReturn(reviews);

        ResponseEntity<List<ReviewsOutputModel>> response = usersController.getAllReviewsByUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());

        verify(userService, times(1)).getAllReviewsByUser(userId);
        verify(reviewsUtility, times(1)).parseOutputFromEntityList(reviewEntityList);
    }

    @Test
    public void testGetAllRatingsByUser() throws UserNotFoundException, RatingNotFoundException {
        int userId = 1;
        List<Ratings> ratingsEntityList = new ArrayList<>();
        List<RatingsOutputModel> ratings = new ArrayList<>();

        when(userService.getAllRatingsByUser(userId)).thenReturn(ratingsEntityList);
        when(ratingsUtility.parseOutputFromEntityList(ratingsEntityList)).thenReturn(ratings);

        ResponseEntity<List<RatingsOutputModel>> response = usersController.getAllRatingsByUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ratings, response.getBody());

        verify(userService, times(1)).getAllRatingsByUser(userId);
        verify(ratingsUtility, times(1)).parseOutputFromEntityList(ratingsEntityList);
    }

    @Test
    public void testGetUserWatchList() throws UserNotFoundException, WatchListEntryNotFoundException {
        int userId = 1;
        List<WatchListEntries> entriesEntityList = new ArrayList<>();
        List<WatchListEntryOutputModel> userWatchList = new ArrayList<>();

        when(userService.getAllWatchListEntriesByUser(userId)).thenReturn(entriesEntityList);
        when(watchListUtility.parseOutputFromEntityList(entriesEntityList)).thenReturn(userWatchList);

        ResponseEntity<List<WatchListEntryOutputModel>> response = usersController.getUserWatchList(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userWatchList, response.getBody());

        verify(userService, times(1)).getAllWatchListEntriesByUser(userId);
        verify(watchListUtility, times(1)).parseOutputFromEntityList(entriesEntityList);
    }
    
    
    @Test
    public void testUserLoginWithValidCredentials() throws UserNotFoundException {
        String email = "test@example.com";
        String password = "password";
        Users userEntity = new Users();
        UsersOutputModel userModel = new UsersOutputModel();

        when(userService.userAuthentication(email, password)).thenReturn(userEntity);
        when(userUtility.parseOutputFromEntity(userEntity)).thenReturn(userModel);

        ResponseEntity<UsersOutputModel> response = usersController.userLogin(email, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userModel, response.getBody());

        verify(userService, times(1)).userAuthentication(email, password);
        verify(userUtility, times(1)).parseOutputFromEntity(userEntity);
    }
    
    
    @Test
    public void testUserLoginWithInvalidCredentials() {
        String email = "test@example.com";
        String password = "password";

        when(userService.userAuthentication(email, password)).thenReturn(null);
        
        assertThrows(UserNotFoundException.class, () -> usersController.userLogin(email, password));
    }
    

    @Test
    public void testAddUser() throws UserAlreadyExistsException, InvalidInputException {
        UsersInputModel userModel = new UsersInputModel();
        Users userEntity = new Users();
        UsersOutputModel userOutput = new UsersOutputModel();

        when(userService.addUser(userModel)).thenReturn(userEntity);
        when(userUtility.parseOutputFromEntity(userEntity)).thenReturn(userOutput);

        ResponseEntity<UsersOutputModel> response = usersController.addUser(userModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userOutput, response.getBody());

        verify(userService, times(1)).addUser(userModel);
        verify(userUtility, times(1)).parseOutputFromEntity(userEntity);
    }

    @Test
    public void testUpdateUserDetails() throws UserNotFoundException, InvalidInputException {
        String email = "test@example.com";
        String password = "password";
        
        Users userEntity = new Users();
        
        UsersInputModel userModel = new UsersInputModel();
        
        UsersOutputModel userOutput = new UsersOutputModel();

        when(userService.updateUserDetails(email, password, userModel)).thenReturn(userEntity);
        when(userUtility.parseOutputFromEntity(userEntity)).thenReturn(userOutput);

        ResponseEntity<UsersOutputModel> response = usersController.updateUserDetails(email, password, userModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userOutput, response.getBody());

        verify(userService, times(1)).updateUserDetails(email, password, userModel);
        verify(userUtility, times(1)).parseOutputFromEntity(userEntity);
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {
        String email = "test@example.com";
        String password = "password";
        Users userEntity = new Users();
        UsersOutputModel userOutput = new UsersOutputModel();

        when(userService.deleteUser(email, password)).thenReturn(userEntity);
        when(userUtility.parseOutputFromEntity(userEntity)).thenReturn(userOutput);

        ResponseEntity<UsersOutputModel> response = usersController.deleteUser(email, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userOutput, response.getBody());

        verify(userService, times(1)).deleteUser(email, password);
        verify(userUtility, times(1)).parseOutputFromEntity(userEntity);
    }
}
