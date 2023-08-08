package com.cineme.cinemeapp.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.cineme.cinemeapp.dao.UsersRepository;
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
import com.cineme.cinemeapp.service.UsersService;
import com.cineme.cinemeapp.utility.Gender;
import com.cineme.cinemeapp.utility.UsersUtility;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Qualifier("userService")
@Slf4j
public class UsersServiceImpl implements UsersService{
	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	UsersUtility userUtility;

	//create ----->
	
	@Transactional
	public Users addUser(UsersInputModel userModel) throws UserAlreadyExistsException, InvalidInputException{
		
		log.info("Adding new user with: {}", userModel);
		
		if( userUtility.validateInputModel(userModel) == false) {
			return null;
		}
		
		Users existingUser = userRepository.getUserByEmail(userModel.getEmail());
		
		if(existingUser != null) {
			log.info("New user not added");
			throw new UserAlreadyExistsException("Another user exists with same email, may edit user details");
		}
		
		Users user = userUtility.parseEntityFromInput(userModel);
		user = userRepository.save(user);
		
		log.info("New user added successfully with id = {}", user.getUserId());
		return user;
	}
	
	//read ----->
	
	@Transactional
	public Users getUserById(Integer userId) throws UserNotFoundException {
		
		log.info("Fethching user with id = {}", userId);
		Users user = userRepository.findById(userId).orElse(null);
		if(user==null) {
			throw new UserNotFoundException("User not found");
		}
		log.info("User with id = {}, name = {} fetched successfully", user.getUserId(), user.getUserName());
		return user;
	}
	
	
	@Transactional
	public Users getUserByEmail(String emailId) throws UserNotFoundException{
		
		log.info("Fethching user with email = {}", emailId);
		
		Users user = userRepository.getUserByEmail(emailId);
		if(user==null) {
			throw new UserNotFoundException("User not found");
		}
		log.info("User with id = {}, email = {} fetched successfully", user.getUserId(), user.getEmail());
		return user;
	}
	
	
	@Transactional
	public List<Reviews> getAllReviewsByUser(Integer userId) throws UserNotFoundException, ReviewNotFoundException{
		
		log.info("Fetching all reviews by user with id = {}", userId);
		List<Reviews> reviewsList = this.getUserById(userId).getReviewsList();
		if(reviewsList.size() == 0) {
			throw new ReviewNotFoundException("User has not reviewed any movie yet");
		}
		log.info("Fetched all reviews by user with id = {}, {} reviews found", userId, reviewsList.size());
		return reviewsList;
	}
	
	
	@Transactional
	public List<Ratings> getAllRatingsByUser(Integer userId) throws UserNotFoundException, RatingNotFoundException{
		
		log.info("Fetching all ratings by user with id = {}", userId);
		List<Ratings> ratingsList = this.getUserById(userId).getRatingsList();
		if(ratingsList.size() == 0) {
			throw new RatingNotFoundException("User has not rated any movie yet");
		}
		log.info("Fetched all ratings by user with id = {}, {} ratings found", userId, ratingsList.size());
		return ratingsList;
	}
	
	
	@Transactional
	public List<WatchListEntries> getAllWatchListEntriesByUser(Integer userId) throws UserNotFoundException, WatchListEntryNotFoundException{
		
		log.info("Fetching all watchlist entries by user with id = {}", userId);
		List<WatchListEntries> entryList = this.getUserById(userId).getWatchListEntriesList();
		if(entryList.size() == 0) {
			throw new WatchListEntryNotFoundException("User's watchlist is empty");
		}
		log.info("Fetched all watchlist entries by user with id = {}, {} entries found", userId, entryList.size());
		return entryList;
	}
	
	
	//update --------->
	
	@Transactional
	public Users updateUserDetails(String emailId, String password, UsersInputModel userInput) throws UserNotFoundException, InvalidInputException {
		
		log.info("Updating user, auth: email={}, pass={}, with update= {}", emailId, password, userInput);
		
		Users user = this.userAuthentication(emailId, password);
		
		if(user == null) {
			log.info("User not updated");
			throw new UserNotFoundException("Invalid Credentials");
		}
		
		if( userUtility.validateInputModel(userInput) == false) {
			return null;
		}
		
		user.setDateOfBirth(LocalDate.parse(userInput.getDateOfBirth(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		user.setEmail(userInput.getEmail());
		user.setGender(Gender.valueOf(userInput.getGender().toUpperCase()));
		user.setPassword(userInput.getPassword());
		user.setUserName(userInput.getUserName());
		
		log.info("Auth success, user updated");
		return userRepository.save(user);
	}

	
	//delete ----->
	
	@Transactional
	public Users deleteUser(String emailId, String password) throws UserNotFoundException {
		
		log.info("Deleting user requested, auth: email={}", emailId);
		Users user = this.userAuthentication(emailId, password);
		
		if(user == null) {
			log.info("User not deleted");
			throw new UserNotFoundException("Invalid Credentials");
		}
		
		userRepository.deleteById(user.getUserId());
		
		log.info("User with id = {}, name = {} deleted successfully", user.getUserId(), user.getUserName());
		return user;
	}
	
	
	//authentication ----->
	@Transactional
	public Users userAuthentication(String emailId, String password) {
		/*
		 * Returns user instance if valid else null
		 */
		log.info("Authentication requested for email={} with pass={}", emailId, password);
		Users user = userRepository.userLogin(emailId,password);
		if(user == null) {
			log.info("Auth Failed");
			return user;
		}
		log.info("Auth Successful for user with id={}",  user.getUserId());
		return user;
	}
}
