package com.bookmyshow.BMSproject.Models;

import com.bookmyshow.BMSproject.Enum.Genre;
import com.bookmyshow.BMSproject.Enum.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.GeneratedReferenceTypeDelegate;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @Column(nullable = false, unique = true)
    private String movieName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Genre genre;

    //what if the movie released in multiple languages?
    @Enumerated(value = EnumType.STRING)
    private Language language;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @Column(nullable = false, precision = 3, scale = 1) // Supports values like 9.5, 10.0
    private BigDecimal movieRatings;
}
