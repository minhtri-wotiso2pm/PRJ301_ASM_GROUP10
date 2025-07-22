/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nzero
 */
public class BookingDiscountDTO {
    private int bookingID;
    private String discountCode;

    public BookingDiscountDTO() {
    }

    public BookingDiscountDTO(int bookingID, String discountCode) {
        this.bookingID = bookingID;
        this.discountCode = discountCode;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }
    
}
