package com.cineme.cinemeapp.service.impl;

import com.cineme.cinemeapp.dao.MoviesRepository;
import com.cineme.cinemeapp.entity.Movies;
import com.cineme.cinemeapp.entity.Ratings;
import com.cineme.cinemeapp.entity.Reviews;
import com.cineme.cinemeapp.entity.Users;
import com.cineme.cinemeapp.entity.WatchListEntries;
import com.cineme.cinemeapp.exception.InvalidInputException;
import com.cineme.cinemeapp.exception.MovieNotFoundException;
import com.cineme.cinemeapp.exception.RatingNotFoundException;
import com.cineme.cinemeapp.exception.ReviewNotFoundException;
import com.cineme.cinemeapp.exception.WatchListEntryNotFoundException;
import com.cineme.cinemeapp.model.MoviesInputModel;
import com.cineme.cinemeapp.model.MoviesOutputModel;
import com.cineme.cinemeapp.utility.MoviesUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class MoviesServiceImplTest {

    @Mock
    private MoviesRepository movieRepository;

    @Mock
    private MoviesUtility moviesUtility;

    @InjectMocks
    private MoviesServiceImpl moviesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddMovie() throws InvalidInputException {
        MoviesInputModel moviesModel = new MoviesInputModel();
        moviesModel.setMovieName("Movie Name");
        moviesModel.setMovieLang("English");
        moviesModel.setSynopsis("Synopsis");
        moviesModel.setPoster("Poster");
        moviesModel.setReleaseDate("27/06/2023");

        Movies movie = new Movies();
        movie.setMovieId(1);
        movie.setMovieName("Movie Name");
        movie.setMovieLang("English");
        movie.setSynopsis("Synopsis");
        movie.setPoster("Poster");
        movie.setReleaseDate(LocalDate.of(2023, 06, 27));

        when(movieRepository.save(any(Movies.class))).thenReturn(movie);
        when(moviesUtility.validateInputModel(moviesModel)).thenReturn(true);
        when(moviesUtility.parseEntityFromInput(moviesModel)).thenReturn(movie);

        Movies result = moviesService.addMovie(moviesModel);
        
        Assertions.assertEquals(movie, result);

        verify(movieRepository, times(1)).save(any(Movies.class));
        verify(moviesUtility, times(1)).validateInputModel(moviesModel);
        verify(moviesUtility, times(1)).parseEntityFromInput(moviesModel);
    }
    
    

    @Test
    public void testGetMovieById() throws MovieNotFoundException {
        int movieId = 1;

        Movies movie = new Movies();
        movie.setMovieId(movieId);
        movie.setMovieName("Movie Name");
        movie.setReleaseDate(LocalDate.of(2023, 06, 27));

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Movies result = moviesService.getMovieById(movieId);
        
        Assertions.assertEquals(movie, result);

        verify(movieRepository, times(1)).findById(movieId);
    }
    
    @Test
    public void testGetMovieByIdNotExist_ThrowsNotFound() {
    	int movieId = 1;
    	
        when(movieRepository.findById(movieId)).thenReturn(null);
        
        MovieNotFoundException thrown = Assertions.assertThrows(MovieNotFoundException.class, () -> moviesService.getMovieById(movieId));

        Assertions.assertEquals("Movie Not Found", thrown.getMessage());
        
        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    public void testGetAllMovies() throws MovieNotFoundException {
        Movies movie1 = new Movies();
        movie1.setMovieId(1);
        movie1.setMovieName("Movie 1");
        movie1.setReleaseDate(LocalDate.of(2023, 06, 27));

        Movies movie2 = new Movies();
        movie2.setMovieId(2);
        movie2.setMovieName("Movie 2");
        movie2.setReleaseDate(LocalDate.of(2020, 05, 20));

        List<Movies> moviesList = Arrays.asList(movie1, movie2);

        when(movieRepository.findAll(Sort.by(Sort.Direction.DESC, "releaseDate"))).thenReturn(moviesList);

        List<Movies> result = moviesService.getAllMovies();
        
        Assertions.assertEquals(moviesList, result);

        verify(movieRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "releaseDate"));
    }
    
    @Test
    public void testGetAllMoviesNoMoviesExist_ThrowsNotFound() {
    	
        when(movieRepository.findAll(Sort.by(Sort.Direction.DESC, "releaseDate"))).thenReturn(new ArrayList<>());
        
        MovieNotFoundException thrown = Assertions.assertThrows(MovieNotFoundException.class, () -> moviesService.getAllMovies());

        Assertions.assertEquals("No Movies Found In Database", thrown.getMessage());
        
        verify(movieRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "releaseDate"));
    }

    
    @Test
    public void testGetMoviesMatchingNameNotExist_ThrowsNotFound() {
	    String nameSearch = "Movie";
	    
	    when(movieRepository.getMoviesMatchingName(nameSearch)).thenReturn(new ArrayList<>());
	
	    MovieNotFoundException thrown = Assertions.assertThrows(MovieNotFoundException.class, () -> moviesService.getMoviesMatchingName(nameSearch));

        Assertions.assertEquals("No Movies Found with name matching "+nameSearch, thrown.getMessage());
	
	    verify(movieRepository, times(1)).getMoviesMatchingName(nameSearch);	
	}

	@Test
	public void testGetMoviesOfLanguage() throws MovieNotFoundException {
	    String language = "English";
	    String notLanguage = "Hindi";
	
	    Movies movie1 = new Movies();
	    movie1.setMovieId(1);
	    movie1.setMovieName("Movie 1");
	    movie1.setMovieLang(language);
	    movie1.setReleaseDate(LocalDate.of(2023, 06, 27));
	
	    Movies movie2 = new Movies();
	    movie2.setMovieId(2);
	    movie2.setMovieName("Movie 2");
	    movie2.setMovieLang(notLanguage);
	    movie2.setReleaseDate(LocalDate.of(2020, 06, 27));
	
	    List<Movies> moviesList = Arrays.asList(movie1);
	
	    when(movieRepository.getMoviesOfLanguage(language.toLowerCase())).thenReturn(moviesList);
	
	    List<Movies> result = moviesService.getMoviesOfLanguage(language);
	    
	    Assertions.assertEquals(moviesList, result);
	
	    verify(movieRepository, times(1)).getMoviesOfLanguage(language.toLowerCase());
	}

	@Test
	public void testGetMoviesByYearOfRelease() throws MovieNotFoundException {
	    int minYear = 2020;
	    int maxYear = 2023;
	
	    Movies movie1 = new Movies();
	    movie1.setMovieId(1);
	    movie1.setMovieName("Movie 1");
	    movie1.setReleaseDate(LocalDate.of(2023, 06, 27));
	
	    Movies movie2 = new Movies();
	    movie2.setMovieId(2);
	    movie2.setMovieName("Movie 2");
	    movie2.setReleaseDate(LocalDate.of(2021, 06, 27));
	    
	    Movies movie3 = new Movies();
	    movie3.setMovieId(3);
	    movie3.setMovieName("Movie 3");
	    movie3.setReleaseDate(LocalDate.of(2020, 06, 27));
	    
	    Movies movie4 = new Movies();
	    movie4.setMovieId(4);
	    movie4.setMovieName("Movie 4");
	    movie4.setReleaseDate(LocalDate.of(2010, 06, 27));
	
	    List<Movies> moviesList = Arrays.asList(movie1, movie2, movie3);
	
	    when(movieRepository.getMoviesByYearOfRelease(minYear, maxYear)).thenReturn(moviesList);
	
	    List<Movies> result = moviesService.getMoviesByYearOfRelease(minYear, maxYear);
	    
	    Assertions.assertEquals(moviesList, result);
	
	    verify(movieRepository, times(1)).getMoviesByYearOfRelease(minYear, maxYear);
	}


	@Test
	public void testGetMoviesWithMinAvgRating() throws MovieNotFoundException {
	    int minAvgRating = 3;
	    
	    Ratings rating1 = new Ratings(); rating1.setScore(5);
	    Ratings rating2 = new Ratings(); rating2.setScore(3);
	
	    Movies movie1 = new Movies();
	    movie1.setMovieId(1);
	    movie1.setMovieName("Movie 1");
	    movie1.setReleaseDate(LocalDate.of(2023, 06, 27));
	    movie1.setRatingsList(Arrays.asList(rating1));
	    
	    Movies movie2 = new Movies();
	    movie2.setMovieId(2);
	    movie2.setMovieName("Movie 2");
	    movie2.setReleaseDate(LocalDate.of(2020, 06, 27));
	    movie2.setRatingsList(Arrays.asList(rating2));
	
	    MoviesOutputModel outputModel1 = new MoviesOutputModel();
	    outputModel1.setMovieId(movie1.getMovieId());
	    outputModel1.setMovieName(movie1.getMovieName());
	    outputModel1.setAvgRating(5f);
	
	    MoviesOutputModel outputModel2 = new MoviesOutputModel();
	    outputModel2.setMovieId(2);
	    outputModel2.setMovieName("Movie 2");
	    outputModel2.setAvgRating(3f);
	
	    List<MoviesOutputModel> outputList = Arrays.asList(outputModel1, outputModel2);
	
	    when(movieRepository.findAll(Sort.by(Sort.Direction.DESC, "releaseDate"))).thenReturn(Arrays.asList(movie1,movie2));
	    when(moviesUtility.parseOutputFromEntityList(anyList())).thenReturn(outputList);
	
	    List<MoviesOutputModel> result = moviesService.getMoviesWithMinAvgRating(minAvgRating);
	    
	    Assertions.assertEquals(outputList, result);
	
	    verify(moviesUtility, times(1)).parseOutputFromEntityList(anyList());
	    verify(movieRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "releaseDate"));
	}

	@Test
	public void testGetMoviesWithMinWatchListAdditions() throws MovieNotFoundException {
	    int minAdditions = 1;
	    
	    List<WatchListEntries> entryList = new ArrayList<>();
	    entryList.add(new WatchListEntries());
	
	    Movies movie1 = new Movies();
	    movie1.setMovieId(1);
	    movie1.setMovieName("Movie 1");
	    movie1.setReleaseDate(LocalDate.of(2023, 06, 27));
	    movie1.setWatchListEntriesList(entryList);
	    
	    Movies movie2 = new Movies();
	    movie2.setMovieId(2);
	    movie2.setMovieName("Movie 2");
	    movie2.setReleaseDate(LocalDate.of(2020, 06, 27));
	    movie2.setWatchListEntriesList(new ArrayList<>());
	
	    MoviesOutputModel outputModel1 = new MoviesOutputModel();
	    outputModel1.setMovieId(movie1.getMovieId());
	    outputModel1.setMovieName(movie1.getMovieName());
	    outputModel1.setTotalWatchlistAdd(1);
	
	    MoviesOutputModel outputModel2 = new MoviesOutputModel();
	    outputModel2.setMovieId(2);
	    outputModel2.setMovieName("Movie 2");
	    outputModel2.setTotalWatchlistAdd(0);
	
	    List<MoviesOutputModel> outputList = Arrays.asList(outputModel1);
	
	    when(movieRepository.findAll(Sort.by(Sort.Direction.DESC, "releaseDate"))).thenReturn(Arrays.asList(movie1,movie2));
	    when(moviesUtility.parseOutputFromEntityList(anyList())).thenReturn(outputList);
	
	    List<MoviesOutputModel> result = moviesService.getMoviesWithMinWatchListAdditions(minAdditions);
	    
	    Assertions.assertEquals(outputList, result);
	    
	    verify(moviesUtility, times(1)).parseOutputFromEntityList(anyList());
	    verify(movieRepository, times(1)).findAll(Sort.by(Sort.Direction.DESC, "releaseDate"));
	}

	@Test
	public void testGetAllReviewsOfMovie() throws MovieNotFoundException, ReviewNotFoundException {
	    int movieId = 1;
	
	    Movies movie = new Movies();
	    movie.setMovieId(movieId);
	    movie.setMovieName("Movie Name");
	    movie.setReleaseDate(LocalDate.of(2020, 06, 27));
	
	    Reviews review1 = new Reviews();
	    review1.setReviewId(1);
	    review1.setMovie(movie);
	    review1.setBody("Review 1");
	
	    Reviews review2 = new Reviews();
	    review2.setReviewId(2);
	    review2.setMovie(movie);
	    review2.setBody("Review 2");
	
	    List<Reviews> reviewsList = Arrays.asList(review1, review2);
	    movie.setReviewsList(reviewsList);
	
	    when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
	    
	
	    List<Reviews> result = moviesService.getAllReviewsOfMovie(movieId);
	    
	    Assertions.assertEquals(reviewsList, result);
	
	    
	}

	@Test
	public void testGetAllRatingsOfMovie() throws MovieNotFoundException, RatingNotFoundException {
	    int movieId = 1;
	
	    Movies movie = new Movies();
	    movie.setMovieId(movieId);
	    movie.setMovieName("Movie Name");
	    movie.setReleaseDate(LocalDate.of(2020, 06, 27));
	
	    Ratings rating1 = new Ratings();
	    rating1.setRatingId(1);
	    rating1.setMovie(movie);
	    rating1.setScore(4);
	
	    Ratings rating2 = new Ratings();
	    rating2.setRatingId(2);
	    rating2.setMovie(movie);
	    rating2.setScore(3);
	
	    List<Ratings> ratingsList = Arrays.asList(rating1, rating2);
	    movie.setRatingsList(ratingsList);
	
	    when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
	    
	
	    List<Ratings> result = moviesService.getAllRatingsOfMovie(movieId);
	    
	    Assertions.assertEquals(ratingsList, result);
	
	    
	}

	@Test
	public void testGetAllWatchListEntriesOfMovie() throws MovieNotFoundException, WatchListEntryNotFoundException {
	    int movieId = 1;
	
	    Movies movie = new Movies();
	    movie.setMovieId(1);
	    movie.setMovieName("Movie Name");
	    movie.setReleaseDate(LocalDate.of(2020, 06, 27));
	    
	    Users user1 = new Users();
	    user1.setUserId(1);
	    user1.setUserName("User Name1");
	    user1.setEmail("email1");
	    user1.setPassword("password1");
	    
	    Users user2 = new Users();
	    user2.setUserId(2);
	    user2.setUserName("User Name2");
	    user2.setEmail("email2");
	    user2.setPassword("password2");
	    
	    WatchListEntries entry1 = new WatchListEntries();
	    entry1.setEntryId(1);
	    entry1.setMovie(movie);
	    entry1.setUser(user1);
	
	    WatchListEntries entry2 = new WatchListEntries();
	    entry2.setEntryId(2);
	    entry2.setMovie(movie);
	    entry2.setUser(user2);
	
	    List<WatchListEntries> entryList = Arrays.asList(entry1, entry2);
	    movie.setWatchListEntriesList(entryList);
	
	    when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
	    
	
	    List<WatchListEntries> result = moviesService.getAllWatchListEntriesOfMovie(movieId);
	    
	    Assertions.assertEquals(entryList, result);
	
	    
	}

	@Test
	public void testDeleteMovie() throws MovieNotFoundException {
	    int movieId = 1;
	
	    Movies movie = new Movies();
	    movie.setMovieId(movieId);
	    movie.setMovieName("Movie Name");
	    movie.setReleaseDate(LocalDate.of(2020, 06, 27));
	
	    when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
	
	    Movies result = moviesService.deleteMovie(movieId);
	    
	    Assertions.assertEquals(movie, result);
		
	    verify(movieRepository, times(1)).deleteById(movieId);
	    verify(movieRepository, times(1)).findById(movieId);
	}
	
	
	@Test
    public void testGetMoviesMatchingName() throws MovieNotFoundException {
	    String nameSearch = "Movie";
	
	    Movies movie1 = new Movies();
	    movie1.setMovieId(1);
	    movie1.setMovieName("Movie 1");
	    movie1.setReleaseDate(LocalDate.of(2023, 06, 27));
	
	    Movies movie2 = new Movies();
	    movie2.setMovieId(2);
	    movie2.setMovieName("Movie 2");
	    movie2.setReleaseDate(LocalDate.of(2020, 06, 27));
	
	    List<Movies> moviesList = Arrays.asList(movie1, movie2);
	
	    when(movieRepository.getMoviesMatchingName(nameSearch.toLowerCase())).thenReturn(moviesList);
	
	    List<Movies> result = moviesService.getMoviesMatchingName(nameSearch);
	    
	    Assertions.assertEquals(moviesList, result);
	
	    verify(movieRepository, times(1)).getMoviesMatchingName(nameSearch);	
	}
	
}

