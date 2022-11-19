package io.codelex.flightplanner.controllers.clientController;

import io.codelex.flightplanner.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/airports")
    @ResponseBody
    public ResponseEntity<List<Airport>> getAirports(@RequestParam String search) {
        return clientService.getAirports(search);
    }

    @PostMapping("/flights/search")
    @ResponseBody
    synchronized public ResponseEntity<PageResult<CorrectFlight>> getSpecificFlights(@RequestBody SearchFlightsRequest searchFlightsRequest) {
        return clientService.getSpecificFlights(searchFlightsRequest);
    }

    @GetMapping("/flights/{id}")
    @ResponseBody
    public ResponseEntity<CorrectFlight> fetchFlight(@PathVariable String id) {
        return clientService.fetchFlight(id);
    }

}
