package com.cineme.cinemeapp.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.cineme.cinemeapp.utility.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column (length = 50, nullable = false)
	private String userName;
	
	@Column (length = 255, unique = true, nullable = false)
	private String email;
	
	@Column (length = 255, nullable = false)
	private String password;
	
	private LocalDate dateOfBirth;
	
	@Transient
	private Long age;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Ratings> ratingsList = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Reviews> reviewsList = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<WatchListEntries> watchListEntriesList = new ArrayList<>();
	
	
	public Long getAge() {
		
		return ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
	}
}
