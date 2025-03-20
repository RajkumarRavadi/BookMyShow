package com.bookmyshow.BMSproject.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String userPhoneNumber;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private Integer userAge;
}
