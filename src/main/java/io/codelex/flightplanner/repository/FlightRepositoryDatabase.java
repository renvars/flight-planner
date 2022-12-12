package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepositoryDatabase extends JpaRepository<Flight, Long> {
    List<Flight> findFlightsByFromAndTo(Airport from, Airport to);

    Optional<Flight> findByArrivalTimeAndCarrierAndDepartureTimeAndFromAndTo(LocalDateTime arrivalTime, String carrier, LocalDateTime departureTime, Airport from, Airport to);
}
