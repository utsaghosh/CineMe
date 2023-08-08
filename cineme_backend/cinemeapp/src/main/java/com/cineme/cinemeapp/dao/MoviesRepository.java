package com.cineme.cinemeapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cineme.cinemeapp.entity.Movies;

@Repository
public interface MoviesRepository extends JpaRepository<Movies, Integer> {
	
	@Query(value="select m from Movies m where m.movieName like concat('%',?1,'%') order by m.releaseDate desc")
	List<Movies> getMoviesMatchingName(String nameSearch);
	
	@Query(value="select m from Movies m where m.movieLang like concat('%',?1,'%') order by m.releaseDate desc")
	List<Movies> getMoviesOfLanguage(String language);
	
	@Query(value="select m from Movies m where year(m.releaseDate) between ?1 and ?2 order by m.releaseDate desc")
	List<Movies> getMoviesByYearOfRelease(Integer minYear, Integer maxYear);
}