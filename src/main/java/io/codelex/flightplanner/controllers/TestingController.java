package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.Service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testing-api")
public class TestingController {


    private final FlightsService flightsService;

    @Autowired
    public TestingController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }


    @PostMapping("/clear")
    @ResponseStatus(code = HttpStatus.OK)
    public void clear() {
        flightsService.clear();
    }


}
