package com.bookmyshow.BMSproject.Controllers;

import com.bookmyshow.BMSproject.Requests.AddUserRequest;
import com.bookmyshow.BMSproject.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("addUser")
    public ResponseEntity<String> addUser(@RequestBody AddUserRequest addUserRequest){
        return userService.addUser(addUserRequest);
    }



}
