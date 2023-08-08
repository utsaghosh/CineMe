package com.cineme.cinemeapp.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cineme.cinemeapp.entity.WatchListEntries;
import com.cineme.cinemeapp.model.WatchListEntryOutputModel;

@Component
public class WatchListUtility {
	
	public WatchListEntryOutputModel parseOutputFromEntity(WatchListEntries entry) {
		
		WatchListEntryOutputModel watchlistEntry = new WatchListEntryOutputModel();
		watchlistEntry.setEntryId(entry.getEntryId());
		watchlistEntry.setCreationDate(entry.getCreationDate());
		watchlistEntry.setMovieId(entry.getMovie().getMovieId());
		watchlistEntry.setMovieName(entry.getMovie().getMovieName());
		watchlistEntry.setUserId(entry.getUser().getUserId());
		watchlistEntry.setUserName(entry.getUser().getUserName());
		return watchlistEntry;
	}
	
	public List<WatchListEntryOutputModel> parseOutputFromEntityList(List<WatchListEntries> entryList){
		
		List<WatchListEntryOutputModel> entryOutputList = new ArrayList<WatchListEntryOutputModel>();
		for(WatchListEntries w : entryList) {
			entryOutputList.add(this.parseOutputFromEntity(w));
		}
		return entryOutputList;
	}
}
