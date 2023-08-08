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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.exception.WatchListEntryAlreadyExistsException;
import com.cineme.cinemeapp.exception.WatchListEntryNotFoundException;
import com.cineme.cinemeapp.model.WatchListEntryInputModel;
import com.cineme.cinemeapp.model.WatchListEntryOutputModel;
import com.cineme.cinemeapp.service.WatchListEntriesService;
import com.cineme.cinemeapp.utility.MoviesUtility;
import com.cineme.cinemeapp.utility.WatchListUtility;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@RequestMapping("/watchlist")
@Slf4j
public class WatchListEntriesController {
	
	@Autowired
	WatchListEntriesService watchListEntriesService;
	
	@Autowired
	MoviesUtility moviesUtility;
	
	@Autowired
	WatchListUtility watchlistUtility;
	
	
	//get ----->
	
	
	@GetMapping("/getuserwatchlist/{userid}")
	public ResponseEntity<List<WatchListEntryOutputModel>> getAllWatchListEntriesByUser(@PathVariable("userid") Integer userId) throws UserNotFoundException, WatchListEntryNotFoundException{
		
		log.info("Request: watchlist of user[id={}]",userId);
		List<WatchListEntryOutputModel> userWatchList =  watchlistUtility.parseOutputFromEntityList(watchListEntriesService.getAllWatchListEntriesByUser(userId));
		log.info("Response: {}", userWatchList);
		return new ResponseEntity<List<WatchListEntryOutputModel>>(userWatchList, HttpStatus.OK);
	}
	
	
	@GetMapping("/checkmovieinuserwatchlist/{movieid}/{userid}")
	public ResponseEntity<Boolean> checkMovieInUserWatchList(@PathVariable("movieid") Integer movieId,@PathVariable("userid") Integer userId) 
			throws MovieNotFoundException, UserNotFoundException{
		
		log.info("Request: check if movie(id={}) exists in watchlist of user(id={})", movieId, userId);
		Boolean isInWatchList = watchListEntriesService.checkMovieInUserWatchList(movieId, userId);
		log.info("Response: {}", isInWatchList);
		return new ResponseEntity<Boolean>(isInWatchList, HttpStatus.OK);
	}
	
	@GetMapping("/getwatchlistentryid/{movieid}/{userid}")
	public ResponseEntity<Integer> getWatchListEntryId(@PathVariable("movieid") Integer movieId,@PathVariable("userid") Integer userId) throws WatchListEntryNotFoundException{
		
		log.info("Request: get id of watchlist entry with movie(id={}), user(id={})", movieId, userId);
		Integer entryId = watchListEntriesService.getWatchListEntryId(movieId, userId);
		log.info("Response: {}", entryId);
		return new ResponseEntity<Integer>(entryId, HttpStatus.OK);
	}
	
	
	
	//post ----->
	
	@PostMapping("/addtowatchlist")
	public ResponseEntity<WatchListEntryOutputModel> createWatchListEntry(@RequestBody WatchListEntryInputModel entryInput) throws MovieNotFoundException, UserNotFoundException, WatchListEntryAlreadyExistsException{
		
		log.info("Request: add to watchlist movie[id={}] of user[id={}]", entryInput.getMovieId(), entryInput.getUserId());
		WatchListEntryOutputModel entryOutput = watchlistUtility.parseOutputFromEntity(watchListEntriesService.createWatchListEntry(entryInput.getMovieId(), entryInput.getUserId()));
		log.info("Response: {}", entryOutput);
		return new ResponseEntity<WatchListEntryOutputModel>(entryOutput, HttpStatus.OK);
	}
	
	
	//delete ----->
	
	@DeleteMapping("/deletewatchlistentry/{entryid}")
	public ResponseEntity<WatchListEntryOutputModel> deleteWatchListEntry(@PathVariable("entryid") Integer entryId) throws WatchListEntryNotFoundException{
		
		log.info("Request: delete watchlist entry with id={}", entryId);
		WatchListEntryOutputModel entryOutput = watchlistUtility.parseOutputFromEntity(watchListEntriesService.deleteWatchListEntry(entryId));
		log.info("Response: {}", entryOutput);
		return new ResponseEntity<WatchListEntryOutputModel>(entryOutput, HttpStatus.OK);
	}
	
	
}
