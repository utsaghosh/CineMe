package com.cineme.cinemeapp.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cineme.cinemeapp.entity.Reviews;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.model.ReviewsInputModel;
import com.cineme.cinemeapp.model.ReviewsOutputModel;

@Component
public class ReviewsUtility {
	
	public ReviewsOutputModel parseOutputFromEntity(Reviews review) {
		
		ReviewsOutputModel reviewOutput = new ReviewsOutputModel();
		reviewOutput.setReviewId(review.getReviewId());
		reviewOutput.setMovieId(review.getMovie().getMovieId());
		reviewOutput.setMovieName(review.getMovie().getMovieName());
		reviewOutput.setUserId(review.getUser().getUserId());
		reviewOutput.setUserName(review.getUser().getUserName());
		reviewOutput.setCreationDate(review.getCreationDate());
		reviewOutput.setBody(review.getBody());
		return reviewOutput;
	}
	
	
	public List<ReviewsOutputModel> parseOutputFromEntityList(List<Reviews> reviewList){
		
		List<ReviewsOutputModel> reviewOutputList = new ArrayList<ReviewsOutputModel>();
		for(Reviews r : reviewList) {
			reviewOutputList.add(this.parseOutputFromEntity(r));
		}
		return reviewOutputList;
	}
	
	public boolean validateInputModel(ReviewsInputModel input) throws InvalidInputException{
		if( input.getBody() == null) {
			throw new InvalidInputException("Review body absent");
		}
		if( input.getBody().isBlank() ) {
			throw new InvalidInputException("Review body cannot be whitespace");
		}
		if( input.getBody().length() > 16777215 ) {
			throw new InvalidInputException("Review body cannot exceed max character limit of 16777215");
		}
		
		return true;
	}
}
