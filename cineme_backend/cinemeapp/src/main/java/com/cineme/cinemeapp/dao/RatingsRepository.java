package com.cineme.cinemeapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cineme.cinemeapp.entity.Ratings;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, Integer> {

	@Query(value="select r from Ratings r where r.movie.movieId=?1")
	List<Ratings> getAllRatingsOfMovie(Integer movieId);
	
	@Query(value="select r from Ratings r where r.user.userId=?1")
	List<Ratings> getAllRatingsByUser(Integer userId);
	
	@Query(value="select r from Ratings r where r.movie.movieId=?1 and r.user.userId=?2")
	Ratings getRatingOfMovieByUser(Integer movieId, Integer userId);

}
