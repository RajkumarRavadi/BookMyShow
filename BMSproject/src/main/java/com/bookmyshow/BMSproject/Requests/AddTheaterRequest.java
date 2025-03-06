package com.bookmyshow.BMSproject.Requests;

import com.bookmyshow.BMSproject.Enum.TheaterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTheaterRequest {
    private String name;
    private Integer totalScreens;
    private TheaterType theaterType;
    private String city;
    private String address;
    private String contactNumber;
}
