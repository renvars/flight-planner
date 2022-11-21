package io.codelex.flightplanner.Service;

import io.codelex.flightplanner.domain.*;
import io.codelex.flightplanner.repository.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FlightsService {
    private final FlightsRepository flightsRepository;

    @Autowired
    public FlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public ResponseEntity<CorrectFlight> addFlight(CorrectFlight correctFlight) {
        if (validateData(correctFlight)) {
            Flights flights = new Flights(correctFlight.getFrom(), correctFlight.getTo(),
                    correctFlight.getCarrier(), correctFlight.getDepartureTime(), correctFlight.getArrivalTime());
            if ((validateSameFlight(flights))) {
                correctFlight.getFrom().setAirport(correctFlight.getFrom().getAirport().toUpperCase());
                correctFlight.getTo().setAirport(correctFlight.getTo().getAirport().toUpperCase());
                flightsRepository.save(flights);
                return ResponseEntity.status(HttpStatus.CREATED).body(transformToCorrectJson(getFlights(flights.getId())));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public void deleteFlight(String id) {
        Long intID = Long.parseLong(id);
        for (Flights flight : flightsRepository.findAll()) {
            if (flight.getId().equals(intID)) {
                flightsRepository.deleteById(intID);
            }
        }
    }

    public ResponseEntity<CorrectFlight> fetchFlight(String id) {
        Long idLong = Long.parseLong(id);
        Flights flights = getFlights(idLong);
        return flights == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
                : ResponseEntity.status(HttpStatus.OK).body(transformToCorrectJson(flights));

    }


    private boolean validateData(CorrectFlight correctFlight) {
        Airport to = correctFlight.getTo();
        Airport from = correctFlight.getFrom();
        if (to == null || from == null) {
            return false;
        }

        return validateAirports(to, from) && validateOtherValues(correctFlight);
    }

    private boolean validateAirports(Airport to, Airport from) {
        if (to.getAirport() == null || from.getAirport() == null ||
                to.getCity() == null || from.getCity() == null ||
                to.getCountry() == null || from.getCountry() == null) {
            return false;
        }
        if (to.getAirport().isEmpty() || from.getAirport().isEmpty() ||
                to.getCity().isEmpty() || from.getCity().isEmpty() ||
                to.getCountry().isEmpty() || from.getCountry().isEmpty()) {
            return false;
        }

        return !to.getAirport().equalsIgnoreCase(from.getAirport()) && !to.getCountry().equalsIgnoreCase(from.getCity()) && !to.getCity().equalsIgnoreCase(from.getCity());
    }


    private boolean validateOtherValues(CorrectFlight correctFlight) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (correctFlight.getDepartureTime() == null || correctFlight.getArrivalTime() == null || correctFlight.getCarrier() == null) {
            return false;
        }
        if (correctFlight.getCarrier().isEmpty() || correctFlight.getArrivalTime().isEmpty() || correctFlight.getDepartureTime().isEmpty()) {
            return false;
        }
        String arrivalTime = correctFlight.getArrivalTime();
        String departureTime = correctFlight.getDepartureTime();
        return LocalDateTime.parse(departureTime, format).isBefore(LocalDateTime.parse(arrivalTime, format)) &&
                !LocalDateTime.parse(arrivalTime, format).isEqual(LocalDateTime.parse(departureTime, format));
    }

    private boolean validateSameFlight(Flights flights) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Flights fli : flightsRepository.findAll()) {
            if (LocalDateTime.parse(fli.getArrivalTime(), format).equals(LocalDateTime.parse(flights.getArrivalTime(), format)) &&
                    LocalDateTime.parse(fli.getDepartureTime(), format).equals(LocalDateTime.parse(flights.getDepartureTime(), format)) &&
                    fli.getCarrier().equalsIgnoreCase(flights.getCarrier()) &&
                    fli.getFromAirport().equalsIgnoreCase(flights.getFromAirport()) &&
                    fli.getFromCity().equalsIgnoreCase(flights.getFromCity()) &&
                    fli.getFromCountry().equalsIgnoreCase(flights.getFromCountry()) &&
                    fli.getToCountry().equalsIgnoreCase(flights.getToCountry()) &&
                    fli.getToCity().equalsIgnoreCase(flights.getToCity()) &&
                    fli.getToAirport().equalsIgnoreCase(flights.getToAirport())) {
                return false;
            }
        }
        return true;
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


    public long getIdFirstFlight() {
        long id = 1;
        while (true) {
            if (flightsRepository.findById(id).isPresent()) {
                return flightsRepository.findById(id).get().getId();
            }
            id++;
        }
    }

    public void clear() {
        flightsRepository.deleteAll();
    }


}
