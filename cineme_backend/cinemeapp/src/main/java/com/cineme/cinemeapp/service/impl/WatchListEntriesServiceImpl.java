package com.cineme.cinemeapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cineme.cinemeapp.dao.WatchListEntriesRepository;
import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.entity.WatchListEntries;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.exception.WatchListEntryAlreadyExistsException;
import com.cineme.cinemeapp.exception.WatchListEntryNotFoundException;
import com.cineme.cinemeapp.service.WatchListEntriesService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Qualifier("watchListEntriesService")
@Slf4j
public class WatchListEntriesServiceImpl implements WatchListEntriesService{
	@Autowired
	MoviesServiceImpl movieService;
	
	@Autowired
	UsersServiceImpl userService;
	
	@Autowired
	WatchListEntriesRepository watchListEntryRepository;
	
	
	//create ------>
	
	@Transactional
	public WatchListEntries createWatchListEntry(Integer movieId, Integer userId)
			throws MovieNotFoundException, UserNotFoundException, WatchListEntryAlreadyExistsException {
		
		log.info("Creating Watchlist entry of movie[id={}] by user[id={}]", movieId, userId);
		
		Movies movie = movieService.getMovieById(movieId);
		log.info("Movie Found");
		
		Users user = userService.getUserById(userId);
		log.info("User Found");
		
		Boolean existingEntry = this.checkMovieInUserWatchList(movie.getMovieId(), user.getUserId());
		
		if(existingEntry == true) {
			log.info("New entry not added");
			throw new WatchListEntryAlreadyExistsException("Movie is already added to user watchlist");
		}
		
		WatchListEntries entry = new WatchListEntries();
		entry.setMovie(movie);
		entry.setUser(user);
		entry.setCreationDate(LocalDateTime.now());
		entry = watchListEntryRepository.save(entry);
		
		log.info("New watchlist entry added successfully with id = {}", entry.getEntryId());
		return entry;
	}
	
	
	//read ----->
	
	@Transactional
	public Integer getWatchListEntryId(Integer movieId, Integer userId) throws WatchListEntryNotFoundException{
		
		log.info("Fethching watchlist entry id with movieid = {}, userid = {}", movieId, userId);
		Integer entryId = watchListEntryRepository.getWatchListEntryId(movieId, userId);
		if(entryId == null) {
			throw new WatchListEntryNotFoundException("No Such Entry Exists");
		}
		log.info("watchlist entry fetched successfully");
		return entryId;
		
	}
	
	@Transactional
	public WatchListEntries getWatchListEntryById(Integer entryId) throws WatchListEntryNotFoundException {
		
		log.info("Fethching watchlist entry with id = {}", entryId);
		WatchListEntries entry = watchListEntryRepository.findById(entryId).orElse(null);
		if(entry == null) {
			throw new WatchListEntryNotFoundException("No Such Entry Exists");
		}
		log.info("watchlist entry fetched successfully");
		return entry;
	}
	
	@Transactional
	public List<WatchListEntries> getAllWatchListEntriesOfMovie(Integer movieId) throws MovieNotFoundException, WatchListEntryNotFoundException{
		
		log.info("Fetching all watchlist entries of movie with id = {}", movieId);
		Movies movie = movieService.getMovieById(movieId);
		log.info("Movie Found");
		List<WatchListEntries> entryList = watchListEntryRepository.getAllWatchListEntriesOfMovie(movie.getMovieId());
		if(entryList.size() == 0) {
			throw new WatchListEntryNotFoundException("No watchlist entry found for movie "+movie.getMovieName());
		}
		log.info("Fetched all watchlist entries of movie with id = {}, {} entries found", movieId, entryList.size());
		return entryList;
	}
	
	
	@Transactional
	public List<WatchListEntries> getAllWatchListEntriesByUser(Integer userId) throws UserNotFoundException, WatchListEntryNotFoundException{

		log.info("Fetching all watchlist entries by user with id = {}", userId);
		Users user = userService.getUserById(userId);
		log.info("User Found");
		List<WatchListEntries> entryList =  watchListEntryRepository.getAllWatchListEntriesByUser(user.getUserId());
		if(entryList.size() == 0) {
			throw new WatchListEntryNotFoundException("No watchlist entry found by user "+user.getUserName());
		}
		log.info("Fetched all watchlist entries by user with id = {}, {} entries found", userId, entryList.size());
		return entryList;
	}

	@Transactional
	public Boolean checkMovieInUserWatchList(Integer movieId, Integer userId)
			throws MovieNotFoundException, UserNotFoundException {
		
		log.info("Checking if movie[id={}] exists in watchlist of user[id={}]");
		
		Movies movie = movieService.getMovieById(movieId);
		log.info("Movie Found");
		
		Users user = userService.getUserById(userId);
		log.info("User Found");
		
		Integer entryId = watchListEntryRepository.getWatchListEntryId(movie.getMovieId(), user.getUserId());
		if(entryId == null) {
			log.info("Watchlist entry not found, returning false");
			return false;
		}
		log.info("Watchlist entry found, returning true");
		return true;
	}

	
	
	//delete ---->
	
	@Transactional
	public WatchListEntries deleteWatchListEntry(Integer entryId) throws WatchListEntryNotFoundException {
		
		log.info("Deleting watchlist entry with id={}", entryId);
		
		WatchListEntries entry = this.getWatchListEntryById(entryId);
		watchListEntryRepository.deleteById(entryId);
		
		log.info("Watchlist entry deleted successfully");
		return entry;
	}
	
}
