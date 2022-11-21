package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.Service.FlightsService;
import io.codelex.flightplanner.domain.CorrectFlight;
import io.codelex.flightplanner.domain.Flights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private final FlightsService flightsService;

    @Autowired
    public AdminController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @PutMapping("/flights")
    @ResponseBody
    synchronized public ResponseEntity<CorrectFlight> addFlight(@RequestBody CorrectFlight correctFlight) {
        return flightsService.addFlight(correctFlight);
    }

    @DeleteMapping("/flights/{id}")
    synchronized public void deleteFlight(@PathVariable String id) {
        flightsService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    @ResponseBody
    public ResponseEntity<CorrectFlight> fetchFlight(@PathVariable String id) {
        return flightsService.fetchFlight(id);
    }


}
