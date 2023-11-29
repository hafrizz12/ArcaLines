package org.hafidzmrizky.smkbi.model;

import java.util.Date;

public class Schedule extends Model{
    private long id;
    private long departureId;
    private long arrivalId;
    private Date departureDate;
    private String flightNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDepartureId() {
        return departureId;
    }

    public void setDepartureId(long departureId) {
        this.departureId = departureId;
    }

    public long getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(long arrivalId) {
        this.arrivalId = arrivalId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
