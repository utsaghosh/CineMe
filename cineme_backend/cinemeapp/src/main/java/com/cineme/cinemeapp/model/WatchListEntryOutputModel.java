package com.cineme.cinemeapp.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchListEntryOutputModel {
	
	private Integer entryId;
	private LocalDateTime creationDate;
	private Integer movieId;
	private String movieName;
	private Integer userId;
	private String userName;
}
