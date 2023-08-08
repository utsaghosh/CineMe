package com.cineme.cinemeapp.service;

import java.util.List;

import com.cineme.cinemeapp.entity.WatchListEntries;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.exception.WatchListEntryAlreadyExistsException;
import com.cineme.cinemeapp.exception.WatchListEntryNotFoundException;

public interface WatchListEntriesService {
	
	//create methods
	WatchListEntries createWatchListEntry(Integer movieId, Integer userId) throws MovieNotFoundException, UserNotFoundException, WatchListEntryAlreadyExistsException;
	
	
	//read methods
	Integer getWatchListEntryId(Integer movieId, Integer userId) throws WatchListEntryNotFoundException;
	
	WatchListEntries getWatchListEntryById(Integer entryId) throws WatchListEntryNotFoundException;
	
	List<WatchListEntries> getAllWatchListEntriesOfMovie(Integer movieId) throws MovieNotFoundException, WatchListEntryNotFoundException;
	
	List<WatchListEntries> getAllWatchListEntriesByUser(Integer userId) throws UserNotFoundException, WatchListEntryNotFoundException;
	
	Boolean checkMovieInUserWatchList(Integer movieId, Integer userId) throws MovieNotFoundException, UserNotFoundException;
	
	
	//delete method
	WatchListEntries deleteWatchListEntry(Integer entryId) throws WatchListEntryNotFoundException;
}
