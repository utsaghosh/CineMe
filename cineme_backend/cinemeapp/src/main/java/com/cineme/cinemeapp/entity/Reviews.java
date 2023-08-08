package com.cineme.cinemeapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reviewId;
	
	@Lob
	@Column(nullable = false, length = 16777215)
	private String body;
	
	@Column(nullable = false)
	private LocalDateTime creationDate;
	
	@ManyToOne
	@JoinColumn(name="movie_id", nullable = false)
	private Movies movie;
	
	@ManyToOne
	@JoinColumn(name="user_id", nullable = false)
	private Users user;
}
