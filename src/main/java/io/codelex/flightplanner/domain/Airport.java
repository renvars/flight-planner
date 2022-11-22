package io.codelex.flightplanner.domain;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Airport {

    @NotBlank

    private String country;
    @NotBlank

    private String city;
    @NotBlank

    private String airport;

    public Airport(String country, String city, String airport) {
        this.country = country.trim();
        this.city = city.trim();
        this.airport = airport.trim();
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
}
