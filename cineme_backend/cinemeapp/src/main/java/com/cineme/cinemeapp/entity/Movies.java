package com.cineme.cinemeapp.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Movies {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer movieId;
	
	@Column (length = 50, nullable = false)
	private String movieName;
	
	@Column (length = 30)
	private String movieLang;
	
	@Column (length = 50)
	private String director;
	
	private Integer duration;
	
	@Lob
	@Column (length = 16777215)
	private String synopsis;
	
	@Lob
	@Column (length = 16777215)
	private String poster;
	
	@Column(nullable = false)
	private LocalDate releaseDate;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
	private List<Ratings> ratingsList = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,  mappedBy = "movie")
	private List<Reviews> reviewsList = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,  mappedBy = "movie")
	private List<WatchListEntries> watchListEntriesList = new ArrayList<>();
	
}
