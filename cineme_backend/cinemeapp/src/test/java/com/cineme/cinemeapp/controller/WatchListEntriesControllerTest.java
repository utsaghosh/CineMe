
package com.cineme.cinemeapp.controller;

import com.cineme.cinemeapp.entity.WatchListEntries;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.UserNotFoundException;
import com.cineme.cinemeapp.exception.WatchListEntryAlreadyExistsException;
import com.cineme.cinemeapp.exception.WatchListEntryNotFoundException;
import com.cineme.cinemeapp.model.WatchListEntryInputModel;
import com.cineme.cinemeapp.model.WatchListEntryOutputModel;
import com.cineme.cinemeapp.service.WatchListEntriesService;
import com.cineme.cinemeapp.utility.MoviesUtility;
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
import static org.mockito.Mockito.*;

@SpringBootTest
public class WatchListEntriesControllerTest {

    @Mock
    private WatchListEntriesService watchListEntriesService;

    @Mock
    private MoviesUtility moviesUtility;
    
    @Mock
    private WatchListUtility watchlistUtility;

    @InjectMocks
    private WatchListEntriesController watchListEntriesController;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllWatchListEntriesByUser() throws UserNotFoundException, WatchListEntryNotFoundException {
        int userId = 1;
        List<WatchListEntries> entriesEntityList = new ArrayList<>();
        List<WatchListEntryOutputModel> userWatchList = new ArrayList<>();

        when(watchListEntriesService.getAllWatchListEntriesByUser(userId)).thenReturn(entriesEntityList);
        when(watchlistUtility.parseOutputFromEntityList(entriesEntityList)).thenReturn(userWatchList);

        ResponseEntity<List<WatchListEntryOutputModel>> response = watchListEntriesController.getAllWatchListEntriesByUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userWatchList, response.getBody());

        verify(watchListEntriesService, times(1)).getAllWatchListEntriesByUser(userId);
        verify(moviesUtility, times(entriesEntityList.size())).parseOutputFromEntity(any());
    }
    
    @Test
    public void testGetWatchListEntryId() throws WatchListEntryNotFoundException {
    	Integer movieId = 1;
    	Integer userId = 1;
    	Integer entryId = 1;
    	
    	when(watchListEntriesService.getWatchListEntryId(movieId, userId)).thenReturn(entryId);
    	
    	ResponseEntity<Integer> response = watchListEntriesController.getWatchListEntryId(movieId, userId);
    	
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    	assertEquals(entryId, response.getBody());
    	
    	verify(watchListEntriesService, times(1)).getWatchListEntryId(movieId, userId);
    }

    @Test
    public void testCheckMovieInUserWatchList() throws MovieNotFoundException, UserNotFoundException {
        int movieId = 1;
        int userId = 1;
        boolean isInWatchList = true;

        when(watchListEntriesService.checkMovieInUserWatchList(movieId, userId)).thenReturn(isInWatchList);

        ResponseEntity<Boolean> response = watchListEntriesController.checkMovieInUserWatchList(movieId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(isInWatchList, response.getBody());

        verify(watchListEntriesService, times(1)).checkMovieInUserWatchList(movieId, userId);
    }

    @Test
    public void testCreateWatchListEntry() throws MovieNotFoundException, UserNotFoundException, WatchListEntryAlreadyExistsException {
        int movieId = 1;
        int userId = 1;
        WatchListEntries entry = new WatchListEntries();
        WatchListEntryOutputModel entryOutput = new WatchListEntryOutputModel();
        WatchListEntryInputModel entryInput = new WatchListEntryInputModel(movieId, userId);

        when(watchListEntriesService.createWatchListEntry(movieId, userId)).thenReturn(entry);
        when(watchlistUtility.parseOutputFromEntity(entry)).thenReturn(entryOutput);

        ResponseEntity<WatchListEntryOutputModel> response = watchListEntriesController.createWatchListEntry(entryInput);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entryOutput, response.getBody());

        verify(watchListEntriesService, times(1)).createWatchListEntry(movieId, userId);
        verify(watchlistUtility, times(1)).parseOutputFromEntity(entry);
    }

    @Test
    public void testDeleteWatchListEntry() throws WatchListEntryNotFoundException, MovieNotFoundException {
        int entryId = 1;
        WatchListEntries entry = new WatchListEntries();
        WatchListEntryOutputModel entryOutput = new WatchListEntryOutputModel();

        when(watchListEntriesService.deleteWatchListEntry(entryId)).thenReturn(entry);
        when(watchlistUtility.parseOutputFromEntity(entry)).thenReturn(entryOutput);

        ResponseEntity<WatchListEntryOutputModel> response = watchListEntriesController.deleteWatchListEntry(entryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entryOutput, response.getBody());

        verify(watchListEntriesService, times(1)).deleteWatchListEntry(entryId);
        verify(watchlistUtility, times(1)).parseOutputFromEntity(entry);
    }
}
