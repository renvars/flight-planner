package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.Service.FlightsService;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.PageResult;
import io.codelex.flightplanner.domain.SearchFlightRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ClientController {

    private final FlightsService flightsService;

    @Autowired
    public ClientController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam String search) {
        return flightsService.searchAirports(search);
    }

    @PostMapping("/flights/search")
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightRequest searchFlightRequest) {
        return flightsService.searchFlights(searchFlightRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight fetchFlight(@PathVariable String id) {
        return flightsService.fetchFlight(id);
    }


}
