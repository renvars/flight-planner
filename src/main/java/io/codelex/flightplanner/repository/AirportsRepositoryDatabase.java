package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Repository
public interface AirportsRepositoryDatabase extends JpaRepository<Airport, Long> {
    Optional<Airport> findByAirportAndCityAndCountry(@NotBlank String airport, @NotBlank String city, @NotBlank String country);

    List<Airport> findAirportsByAirportContainsIgnoreCaseOrCityContainsIgnoreCaseOrCountryContainingIgnoreCase(@NotBlank String airport, @NotBlank String city, @NotBlank String country);

    Airport findAirportByAirport(@NotBlank String airport);
}