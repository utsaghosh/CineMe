package com.cineme.cinemeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineme.cinemeapp.entity.Users;
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

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/users")
@Slf4j
public class UsersController {
	
	@Autowired
	UsersService userService;
	
	@Autowired
	UsersUtility userUtility;
	
	@Autowired
	MoviesUtility moviesUtility;
	
	@Autowired
	ReviewsUtility reviewsUtility;
	
	@Autowired
	RatingsUtility ratingsUtility;
	
	@Autowired
	WatchListUtility watchListUtility;
	
	//get ------>
	@GetMapping("/getuserbyemail/{email}")
	public ResponseEntity<UsersOutputModel> getUserByEmail(@PathVariable("email") String email) throws UserNotFoundException {
		
		log.info("Request: user with email = {}", email);
		Users userEntity = userService.getUserByEmail(email);
		UsersOutputModel user = userUtility.parseOutputFromEntity(userEntity);
		log.info("Response: user: {}", user);
		return new ResponseEntity<UsersOutputModel>(user, HttpStatus.OK);
	}
	
	
	@GetMapping("/getallreviewsbyuser/{userid}")
	public ResponseEntity<List<ReviewsOutputModel>> getAllReviewsByUser(@PathVariable("userid") Integer userId) throws UserNotFoundException, ReviewNotFoundException{
		
		log.info("Request: all reviews by user[id={}]", userId);
		List<ReviewsOutputModel> userReviews = reviewsUtility.parseOutputFromEntityList(userService.getAllReviewsByUser(userId));
		log.info("Response: {}", userReviews);
		return new ResponseEntity<List<ReviewsOutputModel>>(userReviews, HttpStatus.OK);
	}
	
	
	@GetMapping("/getallratingsbyuser/{userid}")
	public ResponseEntity<List<RatingsOutputModel>> getAllRatingsByUser(@PathVariable("userid") Integer userId) throws UserNotFoundException, RatingNotFoundException{
		
		log.info("Request: all ratings by user[id={}]",userId);
		List<RatingsOutputModel> userRatings = ratingsUtility.parseOutputFromEntityList(userService.getAllRatingsByUser(userId));
		log.info("Response: {}", userRatings);
		return new ResponseEntity<List<RatingsOutputModel>>(userRatings, HttpStatus.OK);
	}
	
	
	@GetMapping("/getuserwatchlist/{userid}")
	public ResponseEntity<List<WatchListEntryOutputModel>> getUserWatchList(@PathVariable("userid") Integer userId) throws UserNotFoundException, WatchListEntryNotFoundException{
		
		log.info("Request: watchlist of user[id={}]",userId);
		List<WatchListEntryOutputModel> userWatchList =  watchListUtility.parseOutputFromEntityList(userService.getAllWatchListEntriesByUser(userId));
		log.info("Response: {}", userWatchList);
		return new ResponseEntity<List<WatchListEntryOutputModel>>(userWatchList, HttpStatus.OK);
	}
	
	
	@GetMapping("/userlogin/{email}/{pass}")
	public ResponseEntity<UsersOutputModel> userLogin(@PathVariable("email") String email, @PathVariable("pass") String pass) throws UserNotFoundException{
		
		log.info("Request: login requested by email = {}", email);
		Users userEntity = userService.userAuthentication(email, pass);
		if(userEntity == null) {
			throw new UserNotFoundException("Invalid Credentials");
		}
		UsersOutputModel user = userUtility.parseOutputFromEntity(userEntity);
		log.info("Response: login successful for user {}", user);
		return new ResponseEntity<UsersOutputModel>(user, HttpStatus.OK);
	}	
	
	//post ----->
	
	@PostMapping("/adduser")
	public ResponseEntity<UsersOutputModel> addUser(@RequestBody UsersInputModel userModel) throws UserAlreadyExistsException, InvalidInputException {
		
		log.info("Request: add new user with {}", userModel);
		Users userEntity = userService.addUser(userModel);
		UsersOutputModel user = userUtility.parseOutputFromEntity(userEntity);
		log.info("Response: new user created {}", user);
		return new ResponseEntity<UsersOutputModel>(user, HttpStatus.OK);
	}
	
	
	@PutMapping("/updateuserdetails/{email}/{pass}")
	public ResponseEntity<UsersOutputModel> updateUserDetails(@PathVariable("email") String email, @PathVariable("pass") String pass, @RequestBody UsersInputModel userModel) throws UserNotFoundException, InvalidInputException{
		
		log.info("Request: update user, auth: email={}, pass={}, update= {}", email, pass, userModel);
		UsersOutputModel user = userUtility.parseOutputFromEntity(userService.updateUserDetails(email,pass,userModel));
		log.info("Response: auth success, updated {}", user);
		return new ResponseEntity<UsersOutputModel>(user, HttpStatus.OK);
	}
	
	
	//delete ------->
	
	@DeleteMapping("/deleteuser/{email}/{pass}")
	public ResponseEntity<UsersOutputModel> deleteUser(@PathVariable("email") String email, @PathVariable("pass") String pass) throws UserNotFoundException{
		
		log.info("Request: delete user with email={}", email);
		UsersOutputModel user = userUtility.parseOutputFromEntity(userService.deleteUser(email, pass));
		log.info("Response: deleted user {}", user);		
		return new ResponseEntity<UsersOutputModel>(user, HttpStatus.OK);
	}
}
