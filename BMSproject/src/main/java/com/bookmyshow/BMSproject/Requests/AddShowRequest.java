package com.bookmyshow.BMSproject.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddShowRequest {
    private LocalDate showData;
    private LocalTime showTime;
    private String movieName;
    private Integer theaterId;
}
