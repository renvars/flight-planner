package io.codelex.flightplanner.domain;

import javax.validation.constraints.NotBlank;

import static io.codelex.flightplanner.utils.Utilities.DATE_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SearchFlightRequest {
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    @NotBlank
    private String departureDate;

    public SearchFlightRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getTime() {
        return LocalDate.parse(departureDate, DATE_FORMATTER);
    }
}
