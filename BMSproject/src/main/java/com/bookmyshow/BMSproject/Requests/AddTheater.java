package com.bookmyshow.BMSproject.Requests;

import com.bookmyshow.BMSproject.Enum.TheaterType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AddTheater {
    private String name;
    private Integer totalScreens;
    private TheaterType theaterType;
    private String city;
    private String address;
    private String contactNumber;
}
