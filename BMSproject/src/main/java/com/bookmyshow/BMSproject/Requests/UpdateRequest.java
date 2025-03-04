package com.bookmyshow.BMSproject.Requests;

import com.bookmyshow.BMSproject.Enum.Language;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateRequest {
    private String movieName;
    private Language language;
    private BigDecimal movieRatings;
}
