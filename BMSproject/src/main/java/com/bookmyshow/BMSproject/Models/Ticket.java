package com.bookmyshow.BMSproject.Models;

import com.bookmyshow.BMSproject.Repositories.ShowRepository;
import com.bookmyshow.BMSproject.Repositories.ShowSeatRepository;
import com.bookmyshow.BMSproject.Repositories.TicketRepository;
import com.bookmyshow.BMSproject.Repositories.UserRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticketId;

    public String bookedSeats;

    private LocalDate showDate;

    private LocalTime showTime;

    private String theaterName;

    private String movieName;

    private Integer totalAmount;

    @JoinColumn
    @ManyToOne
    private Show show;

    @JoinColumn
    @ManyToOne
    private User user;
}
