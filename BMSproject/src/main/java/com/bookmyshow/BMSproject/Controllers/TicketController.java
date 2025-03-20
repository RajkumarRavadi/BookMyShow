package com.bookmyshow.BMSproject.Controllers;

import com.bookmyshow.BMSproject.Models.Ticket;
import com.bookmyshow.BMSproject.Requests.BookTicketRequest;
import com.bookmyshow.BMSproject.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("bookTicket")
    public ResponseEntity<String> bookTicket(@RequestBody BookTicketRequest bookTicketRequest){
        return ticketService.bookTicket(bookTicketRequest);
    }
}
