package io.codelex.flightplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Airports")
public class Airport {
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    @Id
    @Column(name = "airport")
    private String airport;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "from")
    private List<Flight> flightsFrom;
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "to")
    private List<Flight> flightsTo;

    public Airport(String country, String city, String airport, List<Flight> flightsFrom, List<Flight> flightsTo) {
        this.country = country;
        this.city = city;
        this.airport = airport;
        this.flightsFrom = flightsFrom;
        this.flightsTo = flightsTo;
    }

    public Airport() {
    }

    public List<Flight> getFlightsFrom() {
        return flightsFrom;
    }

    public void setFlightsFrom(List<Flight> flightsFrom) {
        this.flightsFrom = flightsFrom;
    }

    public List<Flight> getFlightsTo() {
        return flightsTo;
    }

    public void setFlightsTo(List<Flight> flightsTo) {
        this.flightsTo = flightsTo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return country.equalsIgnoreCase(airport1.country.trim())
                && city.equalsIgnoreCase(airport1.city.trim()) && airport.equalsIgnoreCase(airport1.airport.trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }

    public boolean sameAirport(String phrase) {
        return country.toLowerCase().contains(phrase.toLowerCase().trim()) || city.toLowerCase().contains(phrase.toLowerCase().trim())
                || airport.toLowerCase().contains(phrase.toLowerCase().trim());
    }

    @Override
    public String toString() {
        return "Airport{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", airport='" + airport + '\'' +
                '}';
    }
}
