package com.bookmyshow.BMSproject.Controllers;

import com.bookmyshow.BMSproject.Requests.AddShowRequest;
import com.bookmyshow.BMSproject.Services.ShowService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shows")

public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("addShow")
    public ResponseEntity<String> addShow(@RequestBody AddShowRequest showRequest){
        return showService.addShow(showRequest);
    }
}
