package io.codelex.flightplanner.domain;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static io.codelex.flightplanner.utils.Utilities.DATE_TIME_FORMATTER;

public class AddFlightRequest {


    @Valid
    @NotNull
    private Airport from;
    @Valid
    @NotNull
    private Airport to;
    @NotBlank

    private String carrier;
    @NotBlank

    private String departureTime;
    @NotBlank
    private String arrivalTime;

    public AddFlightRequest(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Flight changeToFlight(int id) {
        return new Flight(id, getFrom(), getTo(), getCarrier(), LocalDateTime.parse(getDepartureTime(), DATE_TIME_FORMATTER)
                , LocalDateTime.parse(getArrivalTime(), DATE_TIME_FORMATTER));
    }
}
