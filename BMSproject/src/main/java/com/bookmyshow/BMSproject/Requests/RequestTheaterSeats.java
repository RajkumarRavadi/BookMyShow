package com.bookmyshow.BMSproject.Requests;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestTheaterSeats {
    private Integer theaterId;
    private Integer  noOfClassicSeats;
    private Integer noOfPremiumSeats;
}
