package com.cineme.cinemeapp.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.model.MoviesInputModel;
import com.cineme.cinemeapp.model.MoviesOutputModel;

@Component
public class MoviesUtility {
	
	public MoviesOutputModel parseOutputFromEntity(Movies movie) {
		
		MoviesOutputModel moviesOutput = new MoviesOutputModel();
		moviesOutput.setMovieId(movie.getMovieId());
		moviesOutput.setMovieName(movie.getMovieName());
		moviesOutput.setMovieLang(movie.getMovieLang());
		moviesOutput.setReleaseDate(movie.getReleaseDate());
		moviesOutput.setPoster(movie.getPoster());
		moviesOutput.setSynopsis(movie.getSynopsis());
		moviesOutput.setDuration(movie.getDuration());
		moviesOutput.setDirector(movie.getDirector());
		
		//setting average rating
		Float avgRating = (float) movie.getRatingsList()
										.stream()
										.mapToDouble(r -> r.getScore())
										.average()
										.orElse(0.0);
		moviesOutput.setAvgRating(avgRating);
		
		//setting total watchlist additions count
		moviesOutput.setTotalWatchlistAdd(movie.getWatchListEntriesList().size());
		return moviesOutput;
	}
	
	public List<MoviesOutputModel> parseOutputFromEntityList(List<Movies> moviesList){
		
		List<MoviesOutputModel> moviesOutputList = new ArrayList<MoviesOutputModel>();
		for(Movies m : moviesList) {
			moviesOutputList.add(this.parseOutputFromEntity(m));
		}
		return moviesOutputList;
	}
	
	
	public Movies parseEntityFromInput(MoviesInputModel movieInput) throws InvalidInputException {
		
		if( this.validateInputModel(movieInput) == false) {
			return null;
		}
		Movies movie = new Movies();
		movie.setMovieName(movieInput.getMovieName());
		movie.setMovieLang(movieInput.getMovieLang());
		movie.setSynopsis(movieInput.getSynopsis());
		movie.setDirector(movieInput.getDirector());
		movie.setDuration(movieInput.getDuration());
		movie.setPoster(movieInput.getPoster());
		movie.setReleaseDate(LocalDate.parse(movieInput.getReleaseDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		return movie;
	}
	
	public Boolean validateInputModel(MoviesInputModel moviesModel) throws InvalidInputException{
		
		if( ! this.validateMovieName( moviesModel.getMovieName() ) ) {
			throw new InvalidInputException("Movie name must be non null, non whitespace and have <= 50 characters");
		}
		if( ! this.validateReleaseDate( moviesModel.getReleaseDate() ) ) {
			throw new InvalidInputException("Release Date must be non null and in pattern dd/MM/yyyy");
		}
		if( (moviesModel.getMovieLang() != null) && (! this.validateMovieLanguage( moviesModel.getMovieLang() )) ) {
			throw new InvalidInputException("Language string must only contain [A-Za-z] and have <= 30 characters");
		}
		if( (moviesModel.getDirector() != null) && ( ! this.validateDirector( moviesModel.getDirector() ) ) ) {
			throw new InvalidInputException("Director string must only contain [A-Za-z] and have <= 50 characters");
		}
		if( ( moviesModel.getDuration() != null ) && ( ! this.validateDuration( moviesModel.getDuration() ) ) ) {
			throw new InvalidInputException("Duration must be positive integer denoting minutes");
		}
		
		return true;
	}
	
	public boolean validateDuration(Integer duration) {
		if(duration <= 0) {
			return false;
		}
		return true;
	}

	public Boolean validateReleaseDate(String releaseDate) {
		
		if(releaseDate == null) {
			return false;
		}
		DateTimeFormatter myDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		try {
			LocalDate.parse(releaseDate, myDateFormat);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	
	public Boolean validateMovieName(String movieName) {
		
		if(movieName == null || movieName.isBlank() || movieName.length() > 50) {
			return false;
		}
		return true;
	}
	
	public Boolean validateMovieLanguage(String movieLang) {
		
		String languageRegex = "^[A-Za-z]{1,30}$";
		return movieLang.matches(languageRegex);
	}
	
	public Boolean validateDirector(String director) {
		
		String directorRegex = "^[A-Za-z ]{1,50}$";
		return director.matches(directorRegex);
	}
}
