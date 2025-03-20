package com.bookmyshow.BMSproject.Repositories;

import com.bookmyshow.BMSproject.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String> {

}
