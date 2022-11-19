package io.codelex.flightplanner.controllers.testingController;

import io.codelex.flightplanner.repository.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TestingService {
    private final FlightsRepository flightsRepository;

    @Autowired
    public TestingService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public void clear() {
        flightsRepository.deleteAll();

    }

}
