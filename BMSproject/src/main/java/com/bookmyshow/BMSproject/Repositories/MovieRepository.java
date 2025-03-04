package com.bookmyshow.BMSproject.Repositories;

import com.bookmyshow.BMSproject.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT * FROM movies ORDER BY movie_ratings DESC LIMIT 1", nativeQuery = true)
    Movie topRatedMovie();

    Movie findMovieByMovieName(String movieName);
}
