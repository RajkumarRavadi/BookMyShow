package com.bookmyshow.BMSproject.Services;

import com.bookmyshow.BMSproject.Enum.Language;
import com.bookmyshow.BMSproject.Models.Movie;
import com.bookmyshow.BMSproject.Repositories.MovieRepository;
import com.bookmyshow.BMSproject.Requests.AddMovieRequest;
import com.bookmyshow.BMSproject.Requests.UpdateRequest;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;


    /// addMovie handles duplicate movie names (same name and language),
    /// validates input fields, and prevents unintended field manipulation.
    public ResponseEntity<String> addMovie(@Valid @RequestBody AddMovieRequest movieRequest) {
        //check for null values in the request
        if(movieRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie request can not be null");
        }

        //check if the movie is already exists
        if(movieRepository.existsByMovieName(movieRequest.getMovieName())){
            //In the db we may have a movie with same name multiple times with diff language
            //so we need to get the list of all the movies with matching movie names
            List<Movie> movies = movieRepository.findAll();

            //after getting the movies list now we are going to check every movie weather the language is
            //matching or not if matching then return movie already exits other wise check with remaining
            // movies if not exits then add movie to db.
            for(Movie m : movies){
                if(m.getMovieName().equals(movieRequest.getMovieName())){
                    if(m.getLanguage().equals(movieRequest.getLanguage())){
                        return ResponseEntity.status(HttpStatus.IM_USED).body("Movie with movie name "+ movieRequest.getMovieName() + " and the language "+ movieRequest.getLanguage().toString() + "is already exists");
                    }
                }
            }
        }

        Movie movie = new Movie();
        movie.setMovieName(movieRequest.getMovieName());
        movie.setMovieRatings(movieRequest.getMovieRatings());
        movie.setGenre(movieRequest.getGenre());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setDuration(movieRequest.getDuration());
        movie.setReleaseDate(movieRequest.getReleaseDate());

        try {
            // Attempt to save the new movie to the database.
            movie = movieRepository.save(movie);
        } catch (DataIntegrityViolationException e) {
            // This exception is thrown when a database constraint is violated (e.g., unique constraint).
            // It indicates that the data being saved violates a rule defined in the database schema.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving movie to the db. Possible constrain violation.");
        } catch (Exception e) {
            // This is a general exception catch block for any other unexpected errors during the save operation.
            // It handles any other exceptions that might occur during the database save process.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving movie to the db.");
        }

        movie = movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body("Movie with movie name" + movie.getMovieName() + "is successfully added to the db.");
    }

    /// This api uses simple CURD method findAll to find the list of all movies.
    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    /// deleteMovie handles edge cases like weather the movieId exits in the db or not
    /// is it null or the type is different etc...
    public ResponseEntity<String> deleteMovie(Long id) {
        //check for null id
        if(id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Movie id can not be null");
        }

        //check if the movie already exists
        if(!movieRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with movie ID" + id + "is not found");
        }

        //before saving to db we may get any unexpected errors while saving the info in db
        //handling the database related edge cases
        try{
            movieRepository.deleteById(id);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occur while trying to delete the movie.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Movie with movie id "+id+" is deleted.");
    }


    /// in this api we are going to get the top-rated movies
    /// to do that we need to get the ratings for all the movies
    /// if any movie has a rating of 7 above then we are going to add it to the
    /// top-rated movies list and finally going to return the list
    public ResponseEntity<List<String>> topRatedMovie(){
        //get all the movies
        List<Movie> allMovies = movieRepository.findAll();

        //create a list to return the top-rated movies
        List<String> topRatedMovies = new ArrayList<>();

        //set the conditional rating to be a top-rated movie
        BigDecimal reqRating = new BigDecimal("7.0"); // Correct way to create BigDecimal

        //check all the movies one by one
        for(Movie m : allMovies){
            if(m.getMovieRatings().compareTo(reqRating) >= 0) { // Correct comparison
                //check if the movie name is not already exists
                if(!topRatedMovies.contains(m.getMovieName())){
                    topRatedMovies.add(m.getMovieName());
                }
            }
        }

        //edge case if the topRated Movies is empty
        if(topRatedMovies.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(topRatedMovies);
        }

        return ResponseEntity.status(HttpStatus.OK).body(topRatedMovies);
    }

    /// With this api we are going to update the language of the movie
    /// To simplify from the client side we are going to take the movie id
    public ResponseEntity<String> updateMovieLanguage(Long id, String lang) {
        // Check if the movie with the given ID exists
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not exists with movieID: " + id);
        }

        // Movie exists, now get the movie entity with the movie ID given
        Movie movie = movieRepository.getReferenceById(id);

        // Convert the string to the corresponding enum value
        Language languageEnum;
        try {
            languageEnum = Language.valueOf(lang.toUpperCase());  // Convert the string to enum
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + lang);
        }

        // Update the movie's language
        movie.setLanguage(languageEnum);

        // Save the updated movie entity
        movieRepository.save(movie);

        // Return response indicating successful update
        return ResponseEntity.status(HttpStatus.OK).body("Movie language updated to " + lang + " for movie with movieId: " + id);
    }

    /// With this api we are going to update the ratings of a movie
    /// to simplify the operation we are taking the movie id from the user.
    public ResponseEntity<String> updateMovieRatings(Long id, BigDecimal newRating){
        //check movie exists or not
        if(!movieRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("movie with movieId" + id + "doesn't exists");
        }

        Movie movie = new Movie();
        movie = movieRepository.getReferenceById(id);

        movie.setMovieRatings(newRating);

        movieRepository.save(movie);

        return ResponseEntity.status(HttpStatus.OK).body("Movie with movieId" + id + "Ratings has been updated");
    }


    ///this api is updating the movie ratings, language
    ///need to fix the language if the language is not in the enum then it is breaking
    ///need to fix rating too if it is greater then 10 we need to perform some sort of actions
    public ResponseEntity<String> update(UpdateRequest updateRequest){
        //check is the movie with movie name exists or not
        Movie movie = movieRepository.findMovieByMovieName(updateRequest.getMovieName());

        if(movie == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("movie with movie name " + updateRequest.getMovieName()+ "doesn't exists");
        }

        // Validate and convert language to enum
        Language languageEnum;
        try{
            languageEnum = Language.valueOf(updateRequest.getLanguage().toString().toUpperCase());
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid language: " + updateRequest.getLanguage());
        }

        // Update the language in the request object
        updateRequest.setLanguage(languageEnum);

        // Update the movie's language
        movie.setLanguage(languageEnum);



        //we need to check the rating are in between 0.0 to 10.0 are not
        if(updateRequest.getMovieRatings().compareTo(new BigDecimal("10.0")) >
                0 || updateRequest.getMovieRatings().compareTo(new BigDecimal("0.0")) < 0){
            //if they are not in valid range return ratings are not in range
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ratings are not in valid range");
        }

        movie.setMovieRatings(updateRequest.getMovieRatings());
        movieRepository.save(movie);
        return ResponseEntity.status(HttpStatus.OK).body("movie with movie name " + updateRequest.getMovieName() + "is updated");
    }

}
