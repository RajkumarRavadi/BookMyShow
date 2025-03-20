package com.bookmyshow.BMSproject.Requests;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserRequest {
    private String userName;

    private String userPhoneNumber;

    private String userEmail;

    private Integer userAge;
}
