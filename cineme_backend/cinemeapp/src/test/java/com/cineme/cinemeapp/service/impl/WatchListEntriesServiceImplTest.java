package com.cineme.cinemeapp.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.cineme.cinemeapp.dao.WatchListEntriesRepository;
import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.entity.WatchListEntries;
import com.cineme.cinemeapp.exception.*;

@SpringBootTest
public class WatchListEntriesServiceImplTest {

    @Mock
    private MoviesServiceImpl movieService;

    @Mock
    private UsersServiceImpl userService;

    @Mock
    private WatchListEntriesRepository watchListEntryRepository;

    @InjectMocks
    private WatchListEntriesServiceImpl watchListEntriesService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateWatchListEntry() throws MovieNotFoundException, UserNotFoundException, WatchListEntryAlreadyExistsException {
        int movieId = 1;
        int userId = 1;
        Movies movie = new Movies();
        movie.setMovieId(1);
        Users user = new Users();
        user.setUserId(1);
        WatchListEntries newEntry = new WatchListEntries();
        newEntry.setEntryId(1);
        // Set up the movie, user, and existingEntry with necessary data
        
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(userService.getUserById(userId)).thenReturn(user);
        when(watchListEntryRepository.getWatchListEntryId(movie.getMovieId(), user.getUserId())).thenReturn(null);
        when(watchListEntryRepository.save(any(WatchListEntries.class))).thenReturn(newEntry);
        
        WatchListEntries createdEntry = watchListEntriesService.createWatchListEntry(movieId, userId);
        
        verify(movieService).getMovieById(movieId);
        verify(userService).getUserById(userId);
        verify(watchListEntryRepository).getWatchListEntryId(movie.getMovieId(), user.getUserId());
        verify(watchListEntryRepository).save(any(WatchListEntries.class));
        
        Assertions.assertEquals(newEntry ,createdEntry);
    }

    @Test
    public void testGetWatchListEntryId() throws WatchListEntryNotFoundException {
        int movieId = 1;
        int userId = 1;
        int entryId = 1;
        // Set up the movieId, userId, and entryId with necessary data
        
        when(watchListEntryRepository.getWatchListEntryId(movieId, userId)).thenReturn(entryId);
        
        Integer retrievedEntryId = watchListEntriesService.getWatchListEntryId(movieId, userId);
        
        verify(watchListEntryRepository).getWatchListEntryId(movieId, userId);
        
        Assertions.assertEquals(entryId, retrievedEntryId);
    }

    @Test
    public void testGetWatchListEntryById() throws WatchListEntryNotFoundException {
        int entryId = 1;
        WatchListEntries entry = new WatchListEntries();
        // Set up the entryId and entry with necessary data
        
        when(watchListEntryRepository.findById(entryId)).thenReturn(java.util.Optional.of(entry));
        
        WatchListEntries retrievedEntry = watchListEntriesService.getWatchListEntryById(entryId);
        
        verify(watchListEntryRepository).findById(entryId);
        
        Assertions.assertEquals(entry, retrievedEntry);
    }

    @Test
    public void testGetAllWatchListEntriesOfMovie() throws MovieNotFoundException, WatchListEntryNotFoundException {
        int movieId = 1;
        Movies movie = new Movies();
        movie.setMovieId(1);
        
        WatchListEntries entry = new WatchListEntries();
        entry.setEntryId(1);
        List<WatchListEntries> entryList = new ArrayList<>();
        entryList.add(entry);
        
        movie.setWatchListEntriesList(entryList);
        
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(watchListEntryRepository.getAllWatchListEntriesOfMovie(movie.getMovieId())).thenReturn(entryList);
        
        List<WatchListEntries> retrievedEntryList = watchListEntriesService.getAllWatchListEntriesOfMovie(movieId);
        
        verify(movieService).getMovieById(movieId);
        verify(watchListEntryRepository).getAllWatchListEntriesOfMovie(movie.getMovieId());
        
        Assertions.assertEquals(entryList, retrievedEntryList);
    }

    @Test
    public void testGetAllWatchListEntriesByUser() throws UserNotFoundException, WatchListEntryNotFoundException {
        int userId = 1;
        Users user = new Users();
        user.setUserId(1);
        
        WatchListEntries entry = new WatchListEntries();
        entry.setEntryId(1);
        List<WatchListEntries> entryList = new ArrayList<>();
        entryList.add(entry);
        
        user.setWatchListEntriesList(entryList);
        // Set up the user and entryList with necessary data
        
        when(userService.getUserById(userId)).thenReturn(user);
        when(watchListEntryRepository.getAllWatchListEntriesByUser(user.getUserId())).thenReturn(entryList);
        
        List<WatchListEntries> retrievedEntryList = watchListEntriesService.getAllWatchListEntriesByUser(userId);
        
        verify(userService).getUserById(userId);
        verify(watchListEntryRepository).getAllWatchListEntriesByUser(user.getUserId());
        
        Assertions.assertEquals(entryList, retrievedEntryList);
    }

    @Test
    public void testCheckMovieInUserWatchList() throws MovieNotFoundException, UserNotFoundException {
        int movieId = 1;
        int userId = 1;
        Movies movie = new Movies();
        Users user = new Users();
        Integer entryId = null;
        // Set up the movie, user, and entryId with necessary data
        
        when(movieService.getMovieById(movieId)).thenReturn(movie);
        when(userService.getUserById(userId)).thenReturn(user);
        when(watchListEntryRepository.getWatchListEntryId(movie.getMovieId(), user.getUserId())).thenReturn(entryId);
        
        boolean result = watchListEntriesService.checkMovieInUserWatchList(movieId, userId);
        
        verify(movieService).getMovieById(movieId);
        verify(userService).getUserById(userId);
        verify(watchListEntryRepository).getWatchListEntryId(movie.getMovieId(), user.getUserId());
        
        Assertions.assertFalse(result);
    }

    @Test
    public void testDeleteWatchListEntry() throws WatchListEntryNotFoundException {
        int entryId = 1;
        WatchListEntries entry = new WatchListEntries();
        // Set up the entryId and entry with necessary data
        
        when(watchListEntryRepository.findById(entryId)).thenReturn(java.util.Optional.of(entry));
        
        WatchListEntries deletedEntry = watchListEntriesService.deleteWatchListEntry(entryId);
        
        verify(watchListEntryRepository).findById(entryId);
        verify(watchListEntryRepository).deleteById(entryId);
        
        Assertions.assertEquals(entry, deletedEntry);
    }

    // ...
}
