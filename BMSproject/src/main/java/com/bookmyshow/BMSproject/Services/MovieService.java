package com.bookmyshow.BMSproject.Services;

import com.bookmyshow.BMSproject.Enum.Language;
import com.bookmyshow.BMSproject.Models.Movie;
import com.bookmyshow.BMSproject.Repositories.MovieRepository;
import com.bookmyshow.BMSproject.Requests.AddMovieRequest;
import com.bookmyshow.BMSproject.Requests.UpdateRequest;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public String addMovie(AddMovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setMovieName(movieRequest.getMovieName());
        movie.setMovieRatings(movieRequest.getMovieRatings());
        movie.setGenre(movieRequest.getGenre());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setDuration(movieRequest.getDuration());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie = movieRepository.save(movie);
        return "Movie with movieId: " + movie.getMovieId() + " is added to the database.";
    }

    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    public ResponseEntity<String> deleteMovie(Long id) {
        if(!movieRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with movie ID" + id + "is not found");
        }

        movieRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Movie with movie id "+id+" is deleted.");
    }

    public ResponseEntity<String> topRatedMovie(){
        if(movieRepository.count() > 0){
            Movie movie = movieRepository.topRatedMovie();
            return ResponseEntity.status(HttpStatus.OK).body("The top rated movie is : "+ movie.getMovieName() + " .");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no movies exists currently.");
    }

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


    //this api is updating the movie ratings, language
    //need to fix the language if the language is not in the enum then it is breaking
    public ResponseEntity<String> update(UpdateRequest updateRequest){
        //check is the movie with movie name exists or not
        Movie movie = movieRepository.findMovieByMovieName(updateRequest.getMovieName());

        if(movie == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("movie with movie name " + updateRequest.getMovieName()+ "doesn't exists");
        }

        String temp = updateRequest.getLanguage().toString();
        temp = temp.toUpperCase();
        updateRequest.setLanguage(Language.valueOf(temp));

        Language languageEnum;
        Language lang = updateRequest.getLanguage();

        movie.setMovieRatings(updateRequest.getMovieRatings());
        movie.setLanguage(updateRequest.getLanguage());

        movieRepository.save(movie);

        return ResponseEntity.status(HttpStatus.OK).body("movie with movie name " + updateRequest.getMovieName() + "is updated");
    }

}
