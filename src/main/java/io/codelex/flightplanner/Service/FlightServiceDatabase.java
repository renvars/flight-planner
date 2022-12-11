package io.codelex.flightplanner.Service;

import io.codelex.flightplanner.domain.*;
import io.codelex.flightplanner.repository.AirportsRepositoryDatabase;
import io.codelex.flightplanner.repository.FlightRepositoryDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.codelex.flightplanner.utils.Utilities.DATE_TIME_FORMATTER;

@Service
@ConditionalOnProperty(prefix = "flightapp", name = "appmode", havingValue = "database")
public class FlightServiceDatabase implements FlightsService {

    private final FlightRepositoryDatabase flightRepo;
    private final AirportsRepositoryDatabase airportRepo;

    @Autowired
    public FlightServiceDatabase(FlightRepositoryDatabase flightRepo, AirportsRepositoryDatabase airportRepo) {
        this.flightRepo = flightRepo;
        this.airportRepo = airportRepo;
    }

    @Override
    public void clear() {
        flightRepo.deleteAll();
        airportRepo.deleteAll();
    }

    @Override
    public Flight addFlight(AddFlightRequest addFlightRequest) {
        Flight flight = addFlightRequest.changeToFlight();
        Optional<Flight> searchFlight = flightRepo.findByArrivalTimeAndCarrierAndDepartureTimeAndFromAndTo(flight.getArrivalTime(), flight.getCarrier(), flight.getDepartureTime(), flight.getFrom(), flight.getTo());
        if (searchFlight.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        if (flight.getFrom().equals(flight.getTo()) || flight.getArrivalTime().isBefore(flight.getDepartureTime())
                || flight.getArrivalTime().isEqual(flight.getDepartureTime())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        saveAirports(flight);
        flightRepo.save(flight);
        return flight;
    }

    public void saveAirports(Flight flight) {
        Airport airportFrom = flight.getFrom();
        Airport airportTo = flight.getTo();
        if (airportRepo.findByAirportAndCityAndCountry(airportTo.getAirport(), airportTo.getCity(), airportTo.getCountry()).isEmpty()) {
            airportRepo.save(airportTo);
        }
        if (airportRepo.findByAirportAndCityAndCountry(airportFrom.getAirport(), airportFrom.getCity(), airportFrom.getCountry()).isEmpty()) {
            airportRepo.save(airportFrom);
        }
    }

    @Override
    public void deleteFlight(String id) {
        Long longId = Long.parseLong(id);
        Optional<Flight> optionalFlight = flightRepo.findById(longId);
        if (optionalFlight.isPresent()) {
            flightRepo.deleteById(longId);
        }
    }

    @Override
    public Flight fetchFlight(String id) {
        Optional<Flight> optionalFlight = flightRepo.findById(Long.parseLong(id));
        if (optionalFlight.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return optionalFlight.get();

    }

    @Override
    public List<Airport> searchAirports(String string) {
        String finalString = string.trim();
        System.out.println(airportRepo.findAirportsByAirportContainsIgnoreCaseOrCityContainsIgnoreCaseOrCountryContainingIgnoreCase(finalString, finalString, finalString));
        return airportRepo.findAirportsByAirportContainsIgnoreCaseOrCityContainsIgnoreCaseOrCountryContainingIgnoreCase(finalString, finalString, finalString);
    }

    @Override
    public PageResult<Flight> searchFlights(SearchFlightRequest searchFlightRequest) {
        if (searchFlightRequest.getFrom().equals(searchFlightRequest.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<Flight> flightList = flightRepo.findFlightsByFromAndTo(
                airportRepo.findAirportByAirport(searchFlightRequest.getFrom()),
                airportRepo.findAirportByAirport(searchFlightRequest.getTo())
        ).stream().filter(flight -> flight.getDepartureTime().toLocalDate().isEqual(searchFlightRequest.getTime())).toList();
        return new PageResult<>(0, flightList.size(), flightList);
    }
}
