package io.codelex.flightplanner.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Airport {

    private String country;
    private String city;
    private String airport;

    public Airport(Long id, String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
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


}
