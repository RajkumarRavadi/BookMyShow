package com.bookmyshow.BMSproject.Controllers;

import com.bookmyshow.BMSproject.Requests.AddTheaterRequest;
import com.bookmyshow.BMSproject.Requests.RequestTheaterSeats;
import com.bookmyshow.BMSproject.Services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Theater")


public class TheaterController {

    //addTheater, updateTheater, removeTheater, getAllTheaters, findByCity,
    //findByMovie, findByType, getTheaterScreens, findByScreens, getShows,
    @Autowired
    private TheaterService theaterService;


    @PostMapping("add")
    public ResponseEntity<String> addTheater(@RequestBody AddTheaterRequest theaterRequest){
        ResponseEntity<String> response  = theaterService.addTheater(theaterRequest);
        return response;
    }

    @PutMapping("associateSeats")
    public ResponseEntity<String> associateSeats(@RequestBody RequestTheaterSeats requestTheaterSeats){
        return theaterService.associateTheaterSeats(requestTheaterSeats);
    }
}
