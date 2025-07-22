/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DbUtils;

/**
 *
 * @author nzero
 */
public class BookingDiscountDAO {

    private static final String CREATE_BOOKING_DISCOUNT = "INSERT INTO BookingDiscount VALUES(bookingID=?,discountCode=?)";
    private static final String GET_DISCOUNT = "SELECT * FROM BookingDiscount WHERE bookingID=?";

    public BookingDiscountDTO getDiscountByBookingId(int bookingID) {
        BookingDiscountDTO bk = null;
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_DISCOUNT);
            pr.setInt(1, bookingID);
            rs=pr.executeQuery();
            if(rs.next()){
                bk = new BookingDiscountDTO();
                bk.setBookingID(rs.getInt("bookingID"));
                bk.setDiscountCode(rs.getString("discountCode"));
            }
        } catch (Exception e) {
            System.out.println("Error createBookingDiscount:" + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return bk;
    }

    public boolean createBookingDiscount(int bookingID, String discount) {
        boolean status = true;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(CREATE_BOOKING_DISCOUNT);
            pr.setInt(1, bookingID);
            pr.setString(2, discount);
            int result = pr.executeUpdate();
            status = (result > 0);
        } catch (Exception e) {
            System.out.println("Error createBookingDiscount:" + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return status;
    }

    private static void closeResources(Connection conn, PreparedStatement pr, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            } else if (pr != null) {
                pr.close();
            } else {
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Error closeResources:" + e.getMessage());
            e.printStackTrace();
        }
    }

}
