package com.cineme.cinemeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingsOutputModel {
	
	private Integer ratingId;
	private Integer movieId;
	private String movieName;
	private Integer userId;
	private String userName;
	private Integer score;
}
