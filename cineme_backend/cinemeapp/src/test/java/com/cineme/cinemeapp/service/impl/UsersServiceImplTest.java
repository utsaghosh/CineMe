package com.cineme.cinemeapp.service.impl;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cineme.cinemeapp.dao.UsersRepository;
import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.entity.Reviews;
import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.entity.WatchListEntries;
import com.cineme.cinemeapp.exception.*;
import com.cineme.cinemeapp.model.UsersInputModel;
import com.cineme.cinemeapp.utility.Gender;
import com.cineme.cinemeapp.utility.UsersUtility;


@SpringBootTest
public class UsersServiceImplTest {

    @Mock
    private UsersRepository userRepository;

    @Mock
    private UsersUtility userUtility;

    @InjectMocks
    private UsersServiceImpl usersService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUser() throws UserAlreadyExistsException, InvalidInputException {
        UsersInputModel userModel = new UsersInputModel();
        // Set up the userModel with necessary data
        userModel.setUserName("Test User");
        userModel.setEmail("testemail@gmail.com");
        userModel.setPassword("testpassword");
        userModel.setGender("MALE");
        userModel.setDateOfBirth("29/10/2000");

        Users user = new Users();
        // Set up the user with necessary data
        user.setUserName("Test User");
        user.setEmail("testemail@gmail.com");
        user.setPassword("testpassword");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        when(userUtility.validateInputModel(userModel)).thenReturn(true);
        when(userRepository.getUserByEmail(userModel.getEmail())).thenReturn(null);
        when(userUtility.parseEntityFromInput(userModel)).thenReturn(user);
        when(userRepository.save(any(Users.class))).thenReturn(user);
        
        Users addedUser = usersService.addUser(userModel);
        
        verify(userUtility).validateInputModel(userModel);
        verify(userRepository).getUserByEmail(userModel.getEmail());
        verify(userUtility).parseEntityFromInput(userModel);
        verify(userRepository).save(any(Users.class));
        
        Assertions.assertEquals(user, addedUser);
    }
    
    @Test
    public void testAddUser_InvalidInput() {
        UsersInputModel userModel = new UsersInputModel();
        // Set up the userModel with necessary data
        userModel.setUserName("Test User");
        userModel.setEmail(null);
        userModel.setPassword("testpassword");
        userModel.setGender("MALE");
        userModel.setDateOfBirth("29/10/2000");

        Users user = new Users();
        // Set up the user with necessary data
        user.setUserName("Test User");
        user.setEmail("testemail@gmail.com");
        user.setPassword("testpassword");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        InvalidInputException thrown = Assertions.assertThrows(InvalidInputException.class, () -> userUtility.validateInputModel(userModel));
        
        Assertions.assertEquals("UserName cannot be null", thrown.getMessage());
    }
    
    @Test
    public void testAddUser_ExistingUser() {
        UsersInputModel userModel = new UsersInputModel();
        // Set up the userModel with necessary data
        userModel.setUserName("Test User");
        userModel.setEmail("testemail@gmail.com");
        userModel.setPassword("testpassword");
        userModel.setGender("MALE");
        userModel.setDateOfBirth("29/10/2000");

        Users user = new Users();
        // Set up the user with necessary data
        user.setUserId(1);
        user.setUserName("Test User");
        user.setEmail("testemail@gmail.com");
        user.setPassword("testpassword");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        when(userRepository.getUserByEmail(userModel.getEmail())).thenReturn(user);
        
        UserAlreadyExistsException thrown = Assertions.assertThrows(UserAlreadyExistsException.class, () -> usersService.addUser(userModel));
        
        Assertions.assertEquals("Another user exists with same email, may edit user details", thrown.getMessage());
    }

    @Test
    public void testGetUserById() throws UserNotFoundException {
        int userId = 1;
        Users user = new Users();
        // Set up the user with necessary data
        user.setUserId(1);
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        Users retrievedUser = usersService.getUserById(userId);
        
        verify(userRepository).findById(userId);
        
        Assertions.assertEquals(user, retrievedUser);
    }
    
    @Test
    public void testGetUserById_NotFound(){
        int userId = 1;
        
        Users user = new Users();
        // Set up the user with necessary data
        user.setUserId(2);
        user.setUserName("Test User");
        user.setEmail("testemail@gmail.com");
        user.setPassword("testpassword");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        when(userRepository.findById(userId)).thenReturn(null);
        
        UserNotFoundException thrown = Assertions.assertThrows(UserNotFoundException.class, () -> usersService.getUserById(userId));
        
        verify(userRepository).findById(userId);
        
        Assertions.assertEquals("User not found", thrown.getMessage());
    }

    @Test
    public void testGetUserByEmail() throws UserNotFoundException {
        String emailId = "test@example.com";
        Users user = new Users();
        // Set up the user with necessary data
        user.setEmail("test@example.com");
        
        when(userRepository.getUserByEmail(emailId)).thenReturn(user);
        
        Users retrievedUser = usersService.getUserByEmail(emailId);
        
        verify(userRepository).getUserByEmail(emailId);
        
        Assertions.assertEquals(user, retrievedUser);
    }
    
    @Test
    public void testGetUserByEmail_NotFound(){
        String emailId = "test@example.com";
        
        when(userRepository.getUserByEmail(emailId)).thenReturn(null);
        
        UserNotFoundException thrown = Assertions.assertThrows(UserNotFoundException.class, () -> usersService.getUserByEmail(emailId));
        
        verify(userRepository).getUserByEmail(emailId);
        
        Assertions.assertEquals("User not found", thrown.getMessage());
    }

    @Test
    public void testGetAllReviewsByUser() throws UserNotFoundException, ReviewNotFoundException {
        int userId = 1;
        
        List<Reviews> reviewsList = new ArrayList<>();
        // Set up the user and reviewsList with necessary data
        Reviews review1 = new Reviews();
        review1.setReviewId(1);
        
        Reviews review2 = new Reviews();
        review2.setReviewId(2);
        
        Reviews review3 = new Reviews();
        review3.setReviewId(3);
        
        reviewsList.add(review1); reviewsList.add(review2);
        
        
        Users user = new Users();
        user.setReviewsList(reviewsList);
        
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        List<Reviews> retrievedReviewsList = usersService.getAllReviewsByUser(userId);
        
        verify(userRepository).findById(userId);
        
        Assertions.assertEquals(reviewsList, retrievedReviewsList);
    }
    
    @Test
    public void testGetAllReviewsByUser_NoReviewsFound() {
        int userId = 1;
        
        Users user = new Users();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        ReviewNotFoundException thrown = Assertions.assertThrows(ReviewNotFoundException.class, () -> usersService.getAllReviewsByUser(userId));
        
        verify(userRepository).findById(userId);
        
        Assertions.assertEquals("User has not reviewed any movie yet", thrown.getMessage());
    }

    @Test
    public void testGetAllRatingsByUser() throws UserNotFoundException, RatingNotFoundException {
        int userId = 1;
        Users user = new Users();
        List<Ratings> ratingsList = new ArrayList<>();
        // Set up the user and ratingsList with necessary data
        
        Ratings rating1 = new Ratings(); rating1.setRatingId(1);
        Ratings rating2 = new Ratings(); rating2.setRatingId(2);
        Ratings rating3 = new Ratings(); rating3.setRatingId(3);
        
        ratingsList.add(rating1); ratingsList.add(rating2); ratingsList.add(rating3);
        
        user.setRatingsList(ratingsList);
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        List<Ratings> retrievedRatingsList = usersService.getAllRatingsByUser(userId);
        
        verify(userRepository).findById(userId);
        
        Assertions.assertEquals(ratingsList, retrievedRatingsList);
    }
    
    @Test
    public void testGetAllRatingsByUser_NoRatingsFound() {
        int userId = 1;
        
        Users user = new Users();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        RatingNotFoundException thrown = Assertions.assertThrows(RatingNotFoundException.class, () -> usersService.getAllRatingsByUser(userId));
        
        verify(userRepository).findById(userId);
        
        Assertions.assertEquals("User has not rated any movie yet", thrown.getMessage());
    }

    @Test
    public void testGetAllWatchListEntriesByUser() throws UserNotFoundException, WatchListEntryNotFoundException {
        int userId = 1;
        Users user = new Users();
        List<WatchListEntries> entryList = new ArrayList<>();
        // Set up the user and entryList with necessary data
        
        WatchListEntries entry1 = new WatchListEntries(); entry1.setEntryId(1);
        WatchListEntries entry2 = new WatchListEntries(); entry2.setEntryId(2);
        WatchListEntries entry3 = new WatchListEntries(); entry3.setEntryId(3);
        
        entryList.add(entry1); entryList.add(entry2); entryList.add(entry3);
        
        user.setWatchListEntriesList(entryList);
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        List<WatchListEntries> retrievedEntryList = usersService.getAllWatchListEntriesByUser(userId);
        
        verify(userRepository).findById(userId);
        
        Assertions.assertEquals(entryList, retrievedEntryList);
    }
    
    @Test
    public void testGetAllWatchListEntriesByUser_NoEntryFound() {
        int userId = 1;
        
        Users user = new Users();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        
        WatchListEntryNotFoundException thrown = Assertions.assertThrows(WatchListEntryNotFoundException.class, () -> usersService.getAllWatchListEntriesByUser(userId));
        
        verify(userRepository).findById(userId);
        
        Assertions.assertEquals("User's watchlist is empty", thrown.getMessage());
    }

    @Test
    public void testUpdateUserDetails() throws UserNotFoundException, InvalidInputException {
        String emailId = "test@example.com";
        String password = "password";
        
        Users oldUser = new Users();
        oldUser.setUserName("Test User");
        oldUser.setEmail(emailId);
        oldUser.setPassword(password);
        oldUser.setGender(Gender.MALE);
        oldUser.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        UsersInputModel userInput = new UsersInputModel();
        // Set up the emailId, password, and userInput with necessary data
        userInput.setUserName("Test User");
        userInput.setEmail("testemail@gmail.com");
        userInput.setPassword("testpassword");
        userInput.setGender("MALE");
        userInput.setDateOfBirth("29/10/2000");
        
        Users user = new Users();
        // Set up the user with necessary data
        user.setUserName("Test User");
        user.setEmail("testemail@gmail.com");
        user.setPassword("testpassword");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        when(userRepository.userLogin(emailId, password)).thenReturn(oldUser);
        when(userUtility.validateInputModel(userInput)).thenReturn(true);
        when(userRepository.save(any(Users.class))).thenReturn(user);
        
        Users updatedUser = usersService.updateUserDetails(emailId, password, userInput);
        
        verify(userRepository).userLogin(emailId, password);
        verify(userUtility).validateInputModel(userInput);
        verify(userRepository).save(any(Users.class));
        
        Assertions.assertEquals(user, updatedUser);
    }
    
    @Test
    public void testUpdateUserDetails_AuthFailed() {
        String emailId = "test@example.com";
        String password = "password";
        
        UsersInputModel userInput = new UsersInputModel();
        // Set up the emailId, password, and userInput with necessary data
        userInput.setUserName("Test User");
        userInput.setEmail("testemail@gmail.com");
        userInput.setPassword("testpassword");
        userInput.setGender("MALE");
        userInput.setDateOfBirth("29/10/2000");
        
        when(userRepository.userLogin(emailId, password)).thenReturn(null);
        
        UserNotFoundException thrown = Assertions.assertThrows(UserNotFoundException.class, () -> usersService.updateUserDetails(emailId, password, userInput));
        
        Assertions.assertEquals("Invalid Credentials", thrown.getMessage());
    }
    
    @Test
    public void testUpdateUserDetails_InvalidInput() {
        String emailId = "test@example.com";
        String password = "password";
        
        Users oldUser = new Users();
        oldUser.setUserName("Test User");
        oldUser.setEmail(emailId);
        oldUser.setPassword(password);
        oldUser.setGender(Gender.MALE);
        oldUser.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        UsersInputModel userInput = new UsersInputModel();
        // Set up the emailId, password, and userInput with necessary data
        userInput.setUserName("Test User");
        userInput.setEmail(null);
        userInput.setPassword("testpassword");
        userInput.setGender("MALE");
        userInput.setDateOfBirth("29/10/2000");
        
        when(userRepository.userLogin(emailId, password)).thenReturn(oldUser);
        
        InvalidInputException thrown = Assertions.assertThrows(InvalidInputException.class, () -> userUtility.validateInputModel(userInput));
        
        Assertions.assertEquals("UserName cannot be null", thrown.getMessage());
    }

    @Test
    public void testDeleteUser() throws UserNotFoundException {
        String emailId = "test@example.com";
        String password = "password";
        Users user = new Users();
        // Set up the user with necessary data
        user.setUserName("Test User");
        user.setEmail(emailId);
        user.setPassword(password);
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        when(userRepository.userLogin(emailId, password)).thenReturn(user);
        
        Users deletedUser = usersService.deleteUser(emailId, password);
        
        verify(userRepository).userLogin(emailId, password);
        verify(userRepository).deleteById(user.getUserId());
        
        Assertions.assertEquals(user, deletedUser);
    }
    
    @Test
    public void testDeleteUser_UserNotFound() {
        String emailId = "test@example.com";
        String password = "password";
        
        when(userRepository.userLogin(emailId, password)).thenReturn(null);
        
        UserNotFoundException thrown = Assertions.assertThrows(UserNotFoundException.class, () -> usersService.deleteUser(emailId, password));
        
        Assertions.assertEquals("Invalid Credentials", thrown.getMessage());
    }

    @Test
    public void testUserAuthentication() {
        String emailId = "test@example.com";
        String password = "password";
        Users user = new Users();
        // Set up the user with necessary data
        user.setUserName("Test User");
        user.setEmail(emailId);
        user.setPassword(password);
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("29/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        when(userRepository.userLogin(emailId, password)).thenReturn(user);
        
        Users authenticatedUser = usersService.userAuthentication(emailId, password);
        
        verify(userRepository).userLogin(emailId, password);
        
        Assertions.assertEquals(user, authenticatedUser);
    }
    
    @Test
    public void testUserAuthentication_AuthFail() {
        String emailId = "test@example.com";
        String password = "password";
        Users user = null;
        // Set up the user with necessary data
        
        when(userRepository.userLogin(emailId, password)).thenReturn(null);
        
        Users authenticatedUser = usersService.userAuthentication(emailId, password);
        
        verify(userRepository).userLogin(emailId, password);
        
        Assertions.assertEquals(user, authenticatedUser);
    }
}
