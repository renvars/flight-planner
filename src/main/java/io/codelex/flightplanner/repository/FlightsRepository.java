package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Flights;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightsRepository extends CrudRepository<Flights, Long> {
}
