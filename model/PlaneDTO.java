package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaneDTO {

    private int flightId;
    private String airline;
    private String departureAirport;
    private String arrivalAirport;
    private String departureTime;
    private String arrivalTime;
    private String flightNumber;

    // Getters and Setters
    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
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

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFormattedDepartureTime() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
            Date date = inputFormat.parse(this.departureTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return departureTime; // fallback nếu lỗi
        }
    }

    public String getFormattedArrivalTime() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
            Date date = inputFormat.parse(this.arrivalTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return arrivalTime;
        }
    }

    public String getFormattedDepartureDateTime() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = inputFormat.parse(this.departureTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return departureTime;
        }
    }

    public String getFormattedArrivalDateTime() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = inputFormat.parse(this.arrivalTime);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return arrivalTime;
        }
    }

    private PlanePriceDTO priceInfo;

public PlanePriceDTO getPriceInfo() {
    return priceInfo;
}

public void setPriceInfo(PlanePriceDTO priceInfo) {
    this.priceInfo = priceInfo;
}
}
