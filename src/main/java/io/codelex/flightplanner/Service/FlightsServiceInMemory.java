package io.codelex.flightplanner.Service;

import io.codelex.flightplanner.domain.*;
import io.codelex.flightplanner.repository.FlightsRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Repository
@ConditionalOnProperty(prefix = "flightapp", name = "appmode", havingValue = "inmemory")
public class FlightsServiceInMemory implements FlightsService {
    private final FlightsRepositoryInMemory flightsRepositoryInMemory;

    @Autowired
    public FlightsServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory) {
        this.flightsRepositoryInMemory = flightsRepositoryInMemory;
    }


    @Override
    public void clear() {
        flightsRepositoryInMemory.clear();
    }

    @Override
    public Flight addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = addFlightRequest.changeToFlight(flightsRepositoryInMemory.getSize());
        if (flightsRepositoryInMemory.contains(flight)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (flight.getFrom().equals(flight.getTo()) || flight.getArrivalTime().isBefore(flight.getDepartureTime())
                || flight.getArrivalTime().isEqual(flight.getDepartureTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return flightsRepositoryInMemory.addFlight(flight);
    }

    @Override
    public void deleteFlight(String id) {
        flightsRepositoryInMemory.deleteFlight(id);
    }

    @Override
    public Flight fetchFlight(String id) {
        return flightsRepositoryInMemory.getFlight(id);
    }


    @Override
    public List<Airport> searchAirports(String string) {
        return flightsRepositoryInMemory.searchAirports(string);
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getFrom().equals(searchFlightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return flightsRepositoryInMemory.searchFlights(searchFlightRequest);
    }


}
