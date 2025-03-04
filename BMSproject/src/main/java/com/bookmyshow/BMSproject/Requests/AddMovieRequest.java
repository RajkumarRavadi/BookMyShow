package com.bookmyshow.BMSproject.Requests;

import com.bookmyshow.BMSproject.Enum.Genre;
import com.bookmyshow.BMSproject.Enum.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMovieRequest {
    private String movieName;
    private Genre genre;
    private Language language;
    private String duration;
    private Date releaseDate;
    private BigDecimal movieRatings;
}
