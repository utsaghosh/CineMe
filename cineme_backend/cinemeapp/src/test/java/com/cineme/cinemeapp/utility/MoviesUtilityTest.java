package com.cineme.cinemeapp.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.model.MoviesInputModel;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.model.MoviesOutputModel;

@SpringBootTest
public class MoviesUtilityTest {

    @InjectMocks
    private MoviesUtility moviesUtility;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testParseOutputFromEntity() {
        Movies movie = new Movies();
        // Set up the movie with necessary data
        movie.setMovieId(1);
        movie.setMovieName("Movie Name");
        movie.setMovieLang("English");
        movie.setSynopsis("Synopsis");
        movie.setPoster("Poster");
        movie.setReleaseDate(LocalDate.of(2023, 06, 27));
        movie.setDirector("director");
        movie.setDuration(120);
        movie.setRatingsList(new ArrayList<>());
        movie.setReviewsList(new ArrayList<>());
        movie.setWatchListEntriesList(new ArrayList<>());
        
        MoviesOutputModel moviesModel = new MoviesOutputModel();
        // Set up the movieInput with necessary data
        moviesModel.setMovieId(1);
        moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate(LocalDate.of(2023, 06, 27));
        moviesModel.setDirector("director");
        moviesModel.setDuration(120);
        moviesModel.setAvgRating(0);
        moviesModel.setTotalWatchlistAdd(0);
        
        MoviesOutputModel moviesOutput = moviesUtility.parseOutputFromEntity(movie);
        
        Assertions.assertEquals(moviesModel,moviesOutput);
        // Assert other properties based on your expectations
    }

    @Test
    public void testParseOutputFromEntityList() {
    	Movies movie = new Movies();
        // Set up the movie with necessary data
        movie.setMovieId(1);
        movie.setMovieName("Movie Name");
        movie.setMovieLang("English");
        movie.setSynopsis("Synopsis");
        movie.setPoster("Poster");
        movie.setReleaseDate(LocalDate.of(2023, 06, 27));
        movie.setDirector("director");
        movie.setDuration(120);
        movie.setRatingsList(new ArrayList<>());
        movie.setReviewsList(new ArrayList<>());
        movie.setWatchListEntriesList(new ArrayList<>());
        
        MoviesOutputModel moviesModel = new MoviesOutputModel();
        // Set up the movieInput with necessary data
        moviesModel.setMovieId(1);
        moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate(LocalDate.of(2023, 06, 27));
        moviesModel.setDirector("director");
        moviesModel.setDuration(120);
        moviesModel.setAvgRating(0);
        moviesModel.setTotalWatchlistAdd(0);
    	
    	List<Movies> moviesList = List.of(movie);
    	List<MoviesOutputModel> outList = List.of(moviesModel);
        // Set up the moviesList with necessary data
        
        List<MoviesOutputModel> moviesOutputList = moviesUtility.parseOutputFromEntityList(moviesList);
        
        Assertions.assertEquals(outList, moviesOutputList);
        // Assert other properties of the output models based on your expectations
    }

    @Test
    public void testParseEntityFromInput() throws InvalidInputException {
        MoviesInputModel moviesModel = new MoviesInputModel();
        // Set up the movieInput with necessary data
        moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate("27/06/2023");
        moviesModel.setDirector("director");
        moviesModel.setDuration(120);
        
        Movies movie = new Movies();
        movie.setMovieName("Movie Name");
        movie.setMovieLang("English");
        movie.setSynopsis("Synopsis");
        movie.setPoster("Poster");
        movie.setReleaseDate(LocalDate.of(2023, 06, 27));
        movie.setDirector("director");
        movie.setDuration(120);
        
        Movies movieOut = moviesUtility.parseEntityFromInput(moviesModel);
        
        Assertions.assertEquals(movie.getMovieName(), movieOut.getMovieName());
        Assertions.assertEquals(movie.getMovieLang(), movieOut.getMovieLang());
        Assertions.assertEquals(movie.getSynopsis(), movieOut.getSynopsis());
        Assertions.assertEquals(movie.getPoster(), movieOut.getPoster());
        Assertions.assertEquals(movie.getReleaseDate(), movieOut.getReleaseDate());
        Assertions.assertEquals(movie.getDirector(), movieOut.getDirector());
        Assertions.assertEquals(movie.getDuration(), movieOut.getDuration());
        // Assert other properties of the movie based on your expectations
    }

    @Test
    public void testValidateInputModel_ValidInput() throws InvalidInputException {
        MoviesInputModel moviesModel = new MoviesInputModel();
        // Set up the moviesModel with valid input
        moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate("27/06/2023");
        moviesModel.setDirector("director");
        moviesModel.setDuration(120);
        
        boolean result = moviesUtility.validateInputModel(moviesModel);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateInputModel_InvalidMovieName() {
        MoviesInputModel moviesModel = new MoviesInputModel();
        moviesModel.setMovieName(null); // Set invalid movie name
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            moviesUtility.validateInputModel(moviesModel);
        });
    }

    @Test
    public void testValidateInputModel_InvalidReleaseDate() {
        MoviesInputModel moviesModel = new MoviesInputModel();
        moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setDirector("director");
        moviesModel.setDuration(120);
        moviesModel.setReleaseDate("2023-07-17"); // Set invalid release date format
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            moviesUtility.validateInputModel(moviesModel);
        });
    }

    @Test
    public void testValidateInputModel_InvalidMovieLanguage() {
        MoviesInputModel moviesModel = new MoviesInputModel();
        moviesModel.setMovieName("Movie Name");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate("27/06/2023");
        moviesModel.setDirector("director");
        moviesModel.setDuration(120);
        moviesModel.setMovieLang("English123"); // Set invalid movie language
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            moviesUtility.validateInputModel(moviesModel);
        });
    }

    @Test
    public void testValidateInputModel_InvalidDirector() {
        MoviesInputModel moviesModel = new MoviesInputModel();
        moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate("27/06/2023");
        moviesModel.setDuration(120);
        moviesModel.setDirector("John123"); // Set invalid director name
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            moviesUtility.validateInputModel(moviesModel);
        });
    }

    @Test
    public void testValidateInputModel_InvalidDuration() {
        MoviesInputModel moviesModel = new MoviesInputModel();
        moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate("27/06/2023");
        moviesModel.setDirector("director");
        moviesModel.setDuration(-1); // Set invalid duration
        
        Assertions.assertThrows(InvalidInputException.class, () -> {
            moviesUtility.validateInputModel(moviesModel);
        });
    }

    @Test
    public void testValidateReleaseDate_ValidReleaseDate() {
    	
        String releaseDate = "01/01/2023"; // Set a valid release date
        
        boolean result = moviesUtility.validateReleaseDate(releaseDate);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateReleaseDate_InvalidReleaseDate() {
        String releaseDate = "2023-07-17"; // Set an invalid release date format
        
        boolean result = moviesUtility.validateReleaseDate(releaseDate);
        
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateMovieName_ValidMovieName() {
        String movieName = "Sample Movie"; // Set a valid movie name
        
        boolean result = moviesUtility.validateMovieName(movieName);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateMovieName_InvalidMovieName_Null() {
        String movieName = null; // Set an invalid null movie name
        
        boolean result = moviesUtility.validateMovieName(movieName);
        
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateMovieName_InvalidMovieName_Blank() {
        String movieName = "  "; // Set an invalid blank movie name
        
        boolean result = moviesUtility.validateMovieName(movieName);
        
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateMovieLanguage_ValidMovieLanguage() {
        String movieLang = "English"; // Set a valid movie language
        
        boolean result = moviesUtility.validateMovieLanguage(movieLang);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateMovieLanguage_InvalidMovieLanguage() {
        String movieLang = "English123"; // Set an invalid movie language
        
        boolean result = moviesUtility.validateMovieLanguage(movieLang);
        
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateDirector_ValidDirector() {
        String director = "John Doe"; // Set a valid director name
        
        boolean result = moviesUtility.validateDirector(director);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateDirector_InvalidDirector() {
        String director = "John123"; // Set an invalid director name
        
        boolean result = moviesUtility.validateDirector(director);
        
        Assertions.assertFalse(result);
    }

    @Test
    public void testValidateDuration_ValidDuration() {
        int duration = 120; // Set a valid duration
        
        boolean result = moviesUtility.validateDuration(duration);
        
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidateDuration_InvalidDuration() {
        int duration = -1; // Set an invalid duration
        
        boolean result = moviesUtility.validateDuration(duration);
        
        Assertions.assertFalse(result);
    }
}


/*
public class MoviesUtilityTest {
	
	
	/*
	 * @Test
    public void testAddMovieNullMovieName_ThrowsInvalidInput() {
    	MoviesInputModel moviesModel = new MoviesInputModel();
    	moviesModel.setMovieName(null);
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate(LocalDate.of(2023, 06, 27));
        
        InvalidInputException thrown = Assertions.assertThrows(InvalidInputException.class, () -> moviesService.addMovie(moviesModel));
        
        Assertions.assertEquals("Movie name cannot be blank", thrown.getMessage());
    }
    
    @Test
    public void testAddMovieBlankMovieName_ThrowsInvalidInput() {
    	MoviesInputModel moviesModel = new MoviesInputModel();
    	moviesModel.setMovieName("       	");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate(LocalDate.of(2023, 06, 27));
        
        InvalidInputException thrown = Assertions.assertThrows(InvalidInputException.class, () -> moviesService.addMovie(moviesModel));
        
        Assertions.assertEquals("Movie name cannot be blank", thrown.getMessage());
    }
    
    @Test
    public void testAddMovieNullReleaseDate_ThrowsInvalidInput() {
    	MoviesInputModel moviesModel = new MoviesInputModel();
    	moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate(null);
        
        InvalidInputException thrown = Assertions.assertThrows(InvalidInputException.class, () -> moviesService.addMovie(moviesModel));
        
        Assertions.assertEquals("Release Date cannot be null", thrown.getMessage());
    }
    
    @Test
    public void testAddMovieTooLongMovieName_ThrowsInvalidInput() {
    	MoviesInputModel moviesModel = new MoviesInputModel();
    	moviesModel.setMovieName("123456789123456789123456789123456789123456789123456789");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate(LocalDate.of(2023, 06, 27));
        
        InvalidInputException thrown = Assertions.assertThrows(InvalidInputException.class, () -> moviesService.addMovie(moviesModel));
        
        Assertions.assertEquals("Movie name cannot be longer than 50 characters", thrown.getMessage());
    }
    
    @Test
    public void testAddMovieTooLongMovieLang_ThrowsInvalidInput() {
    	MoviesInputModel moviesModel = new MoviesInputModel();
    	moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("123456789123456789123456789123456789");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate(LocalDate.of(2023, 06, 27));
        
        InvalidInputException thrown = Assertions.assertThrows(InvalidInputException.class, () -> moviesService.addMovie(moviesModel));
        
        Assertions.assertEquals("Language cannot be longer than 30 characters", thrown.getMessage());
    }
	 */