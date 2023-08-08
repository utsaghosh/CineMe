package com.cineme.cinemeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviesInputModel {
	
	private String movieName;
	private String movieLang;
	private String synopsis;
	private String poster;
	private String releaseDate;
	private String director;
	private Integer duration;
	
}
