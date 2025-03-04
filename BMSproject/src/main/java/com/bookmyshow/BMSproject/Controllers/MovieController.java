package com.bookmyshow.BMSproject.Controllers;

import com.bookmyshow.BMSproject.Models.Movie;
import com.bookmyshow.BMSproject.Requests.AddMovieRequest;
import com.bookmyshow.BMSproject.Requests.UpdateRequest;
import com.bookmyshow.BMSproject.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    //addMovie deleteMovie UpdateMovie  topRatedMovie findMoviesByGenre upcomingMovies findMoviesByLanguage
    //getAllMovies getMovieById searchMovies getMoviesByDurationRange getMoviesByReleaseYear
    // upcomingMovies

    @Autowired
    private MovieService movieService;

    @PostMapping("add")
    public ResponseEntity<String> addMovie(@RequestBody AddMovieRequest movieRequest) {
        String message = movieService.addMovie(movieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    //return an api to return the list of all movies
    @GetMapping("/allMovies")
    public List<Movie> allMovies(){
        return movieService.allMovies();
    }

    @DeleteMapping("deleteMovie")
    public ResponseEntity<String> deleteMovie(@RequestParam("id") Long id){
        return movieService.deleteMovie(id);
    }

    @GetMapping("topRatedMovie")
    public ResponseEntity<String> topRatedMovie(){
        return movieService.topRatedMovie();
    }


    //create an api to update the movie language
    //first we need input as movieId
    //check if that movieId exists or not
    //if not exists return movie not exists
    //if yes then update the movie language
    @PutMapping("updateMovieLanguage")
    public ResponseEntity<String> updateMovieLanguage(@RequestParam("id") Long id, @RequestParam("lang") String lang) {
        // Call the movieService method to get the message (just a String)
        ResponseEntity<String> message = movieService.updateMovieLanguage(id, lang);

        // Return the message in a ResponseEntity
        return message;
    }

    @PutMapping("updateMovieRatings")
    public ResponseEntity<String> updateMovieRatings(@RequestParam("id") Long id, @RequestParam("rating") BigDecimal newRating){
        ResponseEntity<String> response = movieService.updateMovieRatings(id, newRating);
        return response;
    }

    @PutMapping("update")
    public ResponseEntity<String> updateMovie(@RequestBody UpdateRequest updateRequest){
        return movieService.update(updateRequest);
    }









}
