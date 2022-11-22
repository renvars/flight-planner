package io.codelex.flightplanner.Service;

import io.codelex.flightplanner.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlightsService {

    void clear();

    Flight addFlight(AddFlightRequest addFlightRequest);

    void deleteFlight(String id);

    Flight fetchFlight(String id);

    List<Airport> searchAirports(String string);

    PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest);
}
