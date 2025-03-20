package com.bookmyshow.BMSproject.Services;

import com.bookmyshow.BMSproject.Models.User;
import com.bookmyshow.BMSproject.Repositories.UserRepository;
import com.bookmyshow.BMSproject.Requests.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<String> addUser(AddUserRequest addUserRequest){
        User user = User.builder()
                .userAge(addUserRequest.getUserAge())
                .userEmail(addUserRequest.getUserEmail())
                .userName(addUserRequest.getUserName())
                .userPhoneNumber(addUserRequest.getUserPhoneNumber())
                .build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(addUserRequest.getUserEmail());
        mailMessage.setFrom("contact.networx.temp@gmail.com");
        mailMessage.setSubject("Welcome to Book My Show App.");

        String body = "Hi " + addUserRequest.getUserName() + "welcome to BookMyShow. Get 2 movie tickets for free as a joining offer. To get it reply to this mail as 'my2freeShows' offer valid up to 48 hours only. ";

        mailMessage.setText(body);

        javaMailSender.send(mailMessage);


        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body("User is successfully added to the db.");
    }
}
