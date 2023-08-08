package com.cineme.cinemeapp.service;

import java.util.List;

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
import com.cineme.cinemeapp.model.UsersInputModel;

public interface UsersService {
	
	//create methods
	Users addUser(UsersInputModel userModel) throws UserAlreadyExistsException, InvalidInputException;
	
	
	//read methods
	Users getUserById(Integer userId) throws UserNotFoundException;
	
	Users getUserByEmail(String emailId) throws UserNotFoundException;
	
	List<Ratings> getAllRatingsByUser(Integer userId) throws UserNotFoundException, RatingNotFoundException;
	
	List<Reviews> getAllReviewsByUser(Integer userId) throws UserNotFoundException, ReviewNotFoundException;
	
	List<WatchListEntries> getAllWatchListEntriesByUser(Integer userId) throws UserNotFoundException, WatchListEntryNotFoundException;
	
	
	//update methods
	Users updateUserDetails(String emailId, String password, UsersInputModel userModel) throws UserNotFoundException, InvalidInputException;
	
	
	//delete methods
	Users deleteUser(String emailId, String password) throws UserNotFoundException;
	
	
	//Authentication: returns null or User entity
	Users userAuthentication(String emailId, String password);
}
