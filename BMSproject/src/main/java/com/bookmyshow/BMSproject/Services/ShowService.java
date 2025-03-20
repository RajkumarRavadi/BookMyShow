package com.bookmyshow.BMSproject.Services;

import com.bookmyshow.BMSproject.Models.*;
import com.bookmyshow.BMSproject.Repositories.MovieRepository;
import com.bookmyshow.BMSproject.Repositories.ShowRepository;
import com.bookmyshow.BMSproject.Repositories.ShowSeatRepository;
import com.bookmyshow.BMSproject.Repositories.TheaterRepository;
import com.bookmyshow.BMSproject.Requests.AddShowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;


    public ResponseEntity<String> addShow(AddShowRequest showRequest){

        //check if the movie name from the request is there in the movieRepo
        Movie movie = movieRepository.findMovieByMovieName(showRequest.getMovieName());
        if(!movieRepository.existsById(movie.getMovieId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with movie name " + movie.getMovieName() + "is not found.");
        }

        //check if the theater is there in the theater repo
        Theater theater = theaterRepository.getReferenceById(showRequest.getTheaterId());
        if(!theaterRepository.existsById(showRequest.getTheaterId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theater with theater id: " + showRequest.getTheaterId() + "is not found");
        }

        Show show = Show.builder().showDate(showRequest.getShowData())
                .showTime(showRequest.getShowTime())
                .movie(movie)
                .theater(theater)
                .build();

        showRepository.save(show);


        //as we are creating a new show we also need to associate it with seats
        //for the specific show.
        List<TheaterSeats> theaterSeatsList = theater.getTheaterSeatsList();

        List<ShowSeat> showSeatsList = new ArrayList<>();

        for(TheaterSeats theaterSeats : theaterSeatsList){
            ShowSeat showSeat = ShowSeat.builder().seatType(theaterSeats.getSeatType())
                    .seatNo(theaterSeats.getSeatNo())
                    .isBooked(Boolean.FALSE)
                    .isFoodAttached(Boolean.FALSE)
                    .show(show)
                    .build();
            showSeatsList.add(showSeat);
        }


        //setting the bidirectional mapping
        show.setShowSeatList(showSeatsList);


        showSeatRepository.saveAll(showSeatsList);

        return ResponseEntity.status(HttpStatus.OK).body("Show is successfully save to the DB with show id : " + show.getShowId());
    }
}
