package com.bookmyshow.BMSproject.Models;

import com.bookmyshow.BMSproject.Enum.TheaterType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Theaters")

public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theaterId;

    @Column(nullable = false)
    private Integer totalScreens;

    @Column(nullable = false, unique = false)
    private String name;

    @Column(nullable = false)
    private String city;

    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private TheaterType theaterType;

    private String address;

    //Bidirectional mapping in the parent to a record of child
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<TheaterSeats> theaterSeatsList = new ArrayList<>();
}
