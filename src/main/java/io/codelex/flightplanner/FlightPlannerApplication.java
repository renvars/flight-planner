package io.codelex.flightplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "io.codelex.flightplanner.repository")
public class FlightPlannerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightPlannerApplication.class, args);
    }

}
