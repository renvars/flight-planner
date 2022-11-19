package io.codelex.flightplanner.controllers.clientController;

import io.codelex.flightplanner.domain.*;
import io.codelex.flightplanner.repository.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final FlightsRepository flightsRepository;


    @Autowired
    public ClientService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public ResponseEntity<List<Airport>> getAirports(String search) {
        search = search.toLowerCase().trim();
        List<Airport> result = new ArrayList<>();
        for (Flights flights : flightsRepository.findAll()) {
            if (flights.getToAirport().toLowerCase().contains(search) ||
                    flights.getToCity().toLowerCase().contains(search) ||
                    flights.getToCountry().toLowerCase().contains(search)) {
                result.add(new Airport(flights.getId(), flights.getFromCountry(), flights.getToCity(), flights.getFromAirport()));
            } else if (flights.getFromCity().toLowerCase().contains(search) ||
                    flights.getFromCountry().toLowerCase().contains(search) ||
                    flights.getFromAirport().toLowerCase().contains(search)) {
                result.add(new Airport(flights.getId(), flights.getFromCountry(), flights.getFromCity(), flights.getFromAirport()));
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public ResponseEntity<PageResult<CorrectFlight>> getSpecificFlights(SearchFlightsRequest search) {
        if (checkSearchFlights(search)) {
            List<CorrectFlight> list = new ArrayList<>();
            PageResult<CorrectFlight> pageResult = new PageResult<>(0, 0, list);
            Flights flights = checkForEqualFLights(search);
            if (flights != null) {
                pageResult.addItems(transformToCorrectJson(flights));
            }
            return ResponseEntity.status(HttpStatus.OK).body(pageResult);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    public Flights checkForEqualFLights(SearchFlightsRequest searchFlightsRequest) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Flights flights : flightsRepository.findAll()) {
            if (flights.getFromAirport().equalsIgnoreCase(searchFlightsRequest.getFrom()) &&
                    flights.getToAirport().equalsIgnoreCase(searchFlightsRequest.getTo()) &&
                    LocalDate.parse(flights.getDepartureTime().split(" ")[0], format)
                            .equals(LocalDate.parse(searchFlightsRequest.getDepartureDate().split(" ")[0], format))) {
                return flights;
            }
        }
        return null;
    }

    public boolean checkSearchFlights(SearchFlightsRequest search) {
        return search.getDepartureDate() != null &&
                search.getFrom() != null && search.getTo() != null
                && !search.getFrom().equalsIgnoreCase(search.getTo());
    }

    public ResponseEntity<CorrectFlight> fetchFlight(String id) {
        long idLong = Long.parseLong(id);
        long idFlightFirst = flightsRepository.count() != 0 ? getIdFirstFlight() : 0;
        if (idLong <= flightsRepository.count() + idFlightFirst && idLong > 0) {
            Flights flights = getFlights(idLong);
            return flights == null
                    ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
                    : ResponseEntity.status(HttpStatus.OK).body(transformToCorrectJson(flights));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);


    }

    public long getIdFirstFlight() {
        long id = 1;
        while (true) {
            if (flightsRepository.findById(id).isPresent()) {
                return flightsRepository.findById(id).get().getId();
            }
            id++;
        }
    }

    public Flights getFlights(Long id) {
        Optional<Flights> flights = flightsRepository.findById(id);
        return flights.orElse(null);
    }

    public CorrectFlight transformToCorrectJson(Flights flights) {
        Airport airportTo = new Airport(flights.getId(), flights.getToCountry(), flights.getToCity(), flights.getToAirport());
        Airport airportFrom = new Airport(flights.getId(), flights.getFromCountry(), flights.getFromCity(), flights.getFromAirport());
        CorrectFlight correctFlight = new CorrectFlight(airportFrom, airportTo, flights.getCarrier(), flights.getDepartureTime(), flights.getArrivalTime());
        correctFlight.setId(flights.getId());
        return correctFlight;
    }

}
