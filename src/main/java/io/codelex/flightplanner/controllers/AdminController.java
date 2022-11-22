package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.Service.FlightsService;
import io.codelex.flightplanner.domain.AddFlightRequest;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private final FlightsService flightsService;

    @Autowired
    public AdminController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    synchronized public Flight addFlight(@Valid @RequestBody AddFlightRequest addFlightRequest) {
        return flightsService.addFlight(addFlightRequest);
    }

    @DeleteMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    synchronized public void deleteFlight(@PathVariable String id) {
        flightsService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight fetchFlight(@PathVariable String id) {
        return flightsService.fetchFlight(id);
    }


}
