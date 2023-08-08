package com.cineme.cinemeapp.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.model.RatingsOutputModel;

@Component
public class RatingsUtility {
	
	public RatingsOutputModel parseOutputFromEntity(Ratings rating) {
		
		RatingsOutputModel ratingOutput = new RatingsOutputModel();
		ratingOutput.setRatingId(rating.getRatingId());
		ratingOutput.setMovieId(rating.getMovie().getMovieId());
		ratingOutput.setMovieName(rating.getMovie().getMovieName());
		ratingOutput.setUserId(rating.getUser().getUserId());
		ratingOutput.setUserName(rating.getUser().getUserName());
		ratingOutput.setScore(rating.getScore());
		return ratingOutput;
	}
	
	
	public List<RatingsOutputModel> parseOutputFromEntityList(List<Ratings> ratingList){
		List<RatingsOutputModel> ratingOutputList = new ArrayList<RatingsOutputModel>();
		for(Ratings r : ratingList) {
			ratingOutputList.add(this.parseOutputFromEntity(r));
		}
		return ratingOutputList;
	}
}
