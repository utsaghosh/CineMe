package com.cineme.cinemeapp.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviesOutputModel {
	
	private Integer movieId;
	private String movieName;
	private String movieLang;
	private String synopsis;
	private String poster;
	private String director;
	private Integer duration;
	private LocalDate releaseDate;
	private float avgRating;
	private Integer totalWatchlistAdd;
}
