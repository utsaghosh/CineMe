package com.cineme.cinemeapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cineme.cinemeapp.entity.WatchListEntries;

@Repository
public interface WatchListEntriesRepository extends JpaRepository<WatchListEntries, Integer>{
	
	@Query(value="select w.entryId from WatchListEntries w where w.movie.movieId = ?1 and w.user.userId = ?2")
	Integer getWatchListEntryId(Integer movieId, Integer userId);
	
	@Query(value="select w from WatchListEntries w where w.movie.movieId=?1 order by w.creationDate desc")
	List<WatchListEntries> getAllWatchListEntriesOfMovie(Integer movieId);
	
	@Query(value="select w from WatchListEntries w where w.user.userId=?1 order by w.creationDate desc")
	List<WatchListEntries> getAllWatchListEntriesByUser(Integer userId);
}