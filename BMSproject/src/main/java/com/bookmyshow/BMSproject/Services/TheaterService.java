package com.bookmyshow.BMSproject.Services;

import com.bookmyshow.BMSproject.Enum.SeatType;
import com.bookmyshow.BMSproject.Models.Theater;
import com.bookmyshow.BMSproject.Models.TheaterSeats;
import com.bookmyshow.BMSproject.Repositories.TheaterRepository;
import com.bookmyshow.BMSproject.Repositories.TheaterSeatsRepository;
import com.bookmyshow.BMSproject.Requests.AddTheaterRequest;
import com.bookmyshow.BMSproject.Requests.RequestTheaterSeats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private TheaterSeatsRepository theaterSeatsRepository;

    public ResponseEntity<String> addTheater(AddTheaterRequest theaterRequest){
        Theater theater = new Theater();
        theater.setName(theaterRequest.getName());
        theater.setTotalScreens(theaterRequest.getTotalScreens());
        theater.setTheaterType(theaterRequest.getTheaterType());
        theater.setCity(theaterRequest.getCity());
        theater.setAddress(theaterRequest.getAddress());
        theater.setContactNumber(theaterRequest.getContactNumber());

        theaterRepository.save(theater);

        return ResponseEntity.status(HttpStatus.OK).body("Theater with name: " + theaterRequest.getName() + " has been added to the DB.");
    }

    @Transactional
    public ResponseEntity<String> associateTheaterSeats(RequestTheaterSeats requestTheaterSeats) {
        // Null check for input values
        if (requestTheaterSeats.getTheaterId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Theater ID is required.");
        }
        if (requestTheaterSeats.getNoOfClassicSeats() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Number of classic seats is required.");
        }
        if (requestTheaterSeats.getNoOfPremiumSeats() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Number of premium seats is required.");
        }

        int theaterId = requestTheaterSeats.getTheaterId();
        int noOfClassicSeats = requestTheaterSeats.getNoOfClassicSeats();
        int noOfPremiumSeats = requestTheaterSeats.getNoOfPremiumSeats();

        // Get the theater entity
        Theater theater = theaterRepository.findById(theaterId).orElse(null);

        if (theater == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theater with ID " + theaterId + " not found.");
        }

        if(theaterSeatsRepository.existsById(theaterId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Seats are already associated with this theater.");
        }

        List<TheaterSeats> theaterSeatsList = new ArrayList<>();

        // Generate classic seats
        int noOfRowsInClassic = (noOfClassicSeats + 4) / 5;  // Handle rounding up
        int noOfSeatsInLastRowClassic = noOfClassicSeats % 5;

        int row;
        for (row = 1; row <= noOfRowsInClassic; row++) {
            for (int j = 1; j <= 5; j++) {
                if (row == noOfRowsInClassic && j > noOfSeatsInLastRowClassic) {
                    break; // Skip seat creation for rows with fewer than 5 seats
                }
                char ch = (char) ('A' + j - 1);
                String seatNo = "" + row + ch;
                TheaterSeats theaterSeats = TheaterSeats.builder()
                        .seatNo(seatNo)
                        .seatType(SeatType.CLASSIC)
                        .theater(theater)
                        .build();
                theaterSeatsList.add(theaterSeats);
            }
        }

        // Generate premium seats
        int noOfRowsInPremium = (noOfPremiumSeats + 4) / 5;  // Handle rounding up
        int noOfSeatsInLastRowPremium = noOfPremiumSeats % 5;

        int currentRow = row;
        if (noOfSeatsInLastRowClassic > 0) {
            currentRow++;  // Skip to next row if classic seats have a non-full last row
        }

        for (row = currentRow; row < currentRow + noOfRowsInPremium; row++) {
            for (int j = 1; j <= 5; j++) {
                if (row == currentRow + noOfRowsInPremium - 1 && j > noOfSeatsInLastRowPremium) {
                    break; // Skip seat creation for rows with fewer than 5 seats
                }
                char ch = (char) ('A' + j - 1);
                String seatNo = "" + row + ch;
                TheaterSeats theaterSeats = TheaterSeats.builder()
                        .seatNo(seatNo)
                        .seatType(SeatType.PREMIUM)
                        .theater(theater)
                        .build();
                theaterSeatsList.add(theaterSeats);
            }
        }

        // Check if we have any seats to save
        if (theaterSeatsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No seats to associate with the theater.");
        }


        theater.setTheaterSeatsList(theaterSeatsList);
        theaterRepository.save(theater);


        // Save the theater seats to the DB
        theaterSeatsRepository.saveAll(theaterSeatsList);

        return ResponseEntity.status(HttpStatus.OK).body("Theater and seats are successfully associated.");
    }

}
