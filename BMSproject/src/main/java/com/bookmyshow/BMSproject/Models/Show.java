package com.bookmyshow.BMSproject.Models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "shows")
@Builder
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showId;

    private LocalDate showDate;

    private LocalTime showTime;

    @JoinColumn
    @ManyToOne
    private Movie movie;

    @JoinColumn
    @ManyToOne
    private Theater theater;
}
