package io.codelex.flightplanner.controllers;

import io.codelex.flightplanner.Service.FlightsService;
import io.codelex.flightplanner.domain.*;
import io.codelex.flightplanner.repository.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final FlightsService flightsService;

    @Autowired
    public ClientController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @GetMapping("/airports")
    @ResponseBody
    public ResponseEntity<List<Airport>> getAirports(@RequestParam String search) {
        return flightsService.getAirports(search);
    }

    @PostMapping("/flights/search")
    @ResponseBody
    synchronized public ResponseEntity<PageResult<CorrectFlight>> getSpecificFlights(@RequestBody SearchFlightsRequest searchFlightsRequest) {
        return flightsService.getSpecificFlights(searchFlightsRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseBody
    public ResponseEntity<CorrectFlight> fetchFlight(@PathVariable String id) {
        return flightsService.fetchFlight(id);
    }

}
