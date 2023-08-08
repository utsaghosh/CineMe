package com.cineme.cinemeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingsInputModel {
	private Integer movieId;
	private String userEmail;
	private Integer score;
}
