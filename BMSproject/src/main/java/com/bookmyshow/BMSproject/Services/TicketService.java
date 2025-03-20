package com.bookmyshow.BMSproject.Services;

import com.bookmyshow.BMSproject.Enum.SeatType;
import com.bookmyshow.BMSproject.Models.Show;
import com.bookmyshow.BMSproject.Models.ShowSeat;
import com.bookmyshow.BMSproject.Models.Ticket;
import com.bookmyshow.BMSproject.Models.User;
import com.bookmyshow.BMSproject.Repositories.ShowRepository;
import com.bookmyshow.BMSproject.Repositories.ShowSeatRepository;
import com.bookmyshow.BMSproject.Repositories.TicketRepository;
import com.bookmyshow.BMSproject.Repositories.UserRepository;
import com.bookmyshow.BMSproject.Requests.BookTicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    public ResponseEntity<String> bookTicket(BookTicketRequest bookTicketRequest){
        //1. find the show
        Show show = showRepository.findById(bookTicketRequest.getShowId()).get();

        //2. find the user entity
        User user = userRepository.findById(bookTicketRequest.getUserId()).get();

        //3. update the seats as booked and calculate the totalAmount
        Integer totalAmount = 0;
        List<ShowSeat> showSeatList = show.getShowSeatList();

        for(ShowSeat showSeat : showSeatList){
            //get seatNo
            String seatNo = showSeat.getSeatNo();

            if(bookTicketRequest.getRequestedSeats().contains(seatNo)){
                //check if the seat is already booked by someone
                if(showSeat.getIsBooked()){
                    return ResponseEntity.status(HttpStatus.IM_USED).body("Requested seat with seat number : " + showSeat.getSeatNo() + "is already booked by someone.");
                }

                //if it is not booked by someone then we can update the status as booked for the current user.
                showSeat.setIsBooked(Boolean.TRUE);
                if(showSeat.getSeatType().equals(SeatType.CLASSIC)){
                    totalAmount = totalAmount + 100;
                }else{
                    totalAmount = totalAmount + 150;
                }
            }
        }

        //4. create a ticket entity and update it
        Ticket ticket = Ticket.builder().showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .theaterName(show.getTheater().getName())
                .movieName(show.getMovie().getMovieName())
                .totalAmount(totalAmount)
                .show(show)
                .user(user)
                .bookedSeats(bookTicketRequest.getRequestedSeats().toString())
                .build();

        showSeatRepository.saveAll(showSeatList);
        ticketRepository.save(ticket);

        return ResponseEntity.status(HttpStatus.OK).body("Tickets booked.");
    }
}
