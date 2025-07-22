package model;

public class PlanePriceDTO {
    private int flightId;
    private double basePrice;
    private int totalSeats;
    private int bookedSeats;

    public PlanePriceDTO() {
    }

    public PlanePriceDTO(int flightId, double basePrice, int totalSeats, int bookedSeats) {
        this.flightId = flightId;
        this.basePrice = basePrice;
        this.totalSeats = totalSeats;
        this.bookedSeats = bookedSeats;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(int bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public int getAvailableSeats() {
        return totalSeats - bookedSeats;
    }

    public double getDynamicPrice() {
        double seatRatio = (double) getAvailableSeats() / totalSeats;

        if (seatRatio > 0.7) {
            return basePrice * 0.9; // giảm 10%
        } else if (seatRatio < 0.3) {
            return basePrice * 1.2; // tăng 20%
        } else {
            return basePrice; // giữ nguyên
        }
    }
}
