package com.cineme.cinemeapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cineme.cinemeapp.entity.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer>{
	@Query(value="select r from Reviews r where r.movie.movieId=?1 order by r.creationDate desc")
	List<Reviews> getAllReviewsOfMovie(Integer movieId);
	
	@Query(value="select r from Reviews r where r.user.userId=?1 order by r.creationDate desc")
	List<Reviews> getAllReviewsByUser(Integer userId);
	
	@Query(value="select r from Reviews r where r.movie.movieId=?1 and r.user.userId=?2 order by r.creationDate desc")
	Reviews getReviewOfMovieByUser(Integer movieId, Integer userId);
}
