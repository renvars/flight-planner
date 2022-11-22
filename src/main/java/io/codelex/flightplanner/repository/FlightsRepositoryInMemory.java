package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.Service.FlightsService;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.domain.PageResult;
import io.codelex.flightplanner.domain.SearchFlightRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightsRepositoryInMemory {

    private List<Flight> flightList = new ArrayList<>();

    public FlightsRepositoryInMemory(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public void clear() {
        flightList.clear();
    }

    public Flight addFlight(Flight flight) {
        flightList.add(flight);
        return flightList.get(flightList.size() - 1);
    }

    public int getSize() {
        return flightList.size();
    }

    public boolean contains(Flight flight) {
        return flightList.stream().anyMatch(flight::equals);
    }

    public Flight getFlight(String id) {
        return flightList.stream().filter(flight -> flight.getId() == Integer.parseInt(id)).findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteFlight(String id) {
        flightList.removeIf(flight -> flight.getId() == Integer.parseInt(id));
    }

    public List<Airport> searchAirports(String string) {
        return flightList.stream().map(Flight::getFrom)
                .filter(airport -> airport.sameAirport(string)).toList();
    }

    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        List<Flight> response = flightList.stream().filter(flight -> flight.sameFlightRequest(searchFlightRequest)).toList();

        return new PageResult<Flight>(0, response.size(), response);
    }
}
