/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author nzero
 */
public class BookingDAO {

    private static final String CREATE_BOOKING = "INSERT INTO tblBookings(userID,roomID,checkInDate,checkOutDate,totalPrice,bookingStatus,paymentStatus) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE_BOOKING = "UPDATE tblBookings SET totalPrice=? where bookingID=?";
    private static final String UPDATE_STATUS_BOOKING = "UPDATE tblBookings SET paymentStatus=? where bookingID=?";
    private static final String GET_BOOKING = "SELECT * FROM tblBookings WHERE bookingID=?";
    private static final String GET_BOOKING_BY_USER_ID = "SELECT * FROM tblBookings where userID = ? and bookingStatus =1";

    public boolean createBooking(BookingDTO book) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(CREATE_BOOKING, PreparedStatement.RETURN_GENERATED_KEYS);
            pr.setInt(1, book.getUserID());
            pr.setInt(2, book.getRoomID());
            pr.setDate(3, new java.sql.Date(book.getCheckInDate().getTime()));
            pr.setDate(4, new java.sql.Date(book.getCheckOutDate().getTime()));
            pr.setInt(5, book.getTotalPrice());
            pr.setBoolean(6, book.isBookingStatus());
            pr.setBoolean(7, book.isPaymentStatus());

            int rowsAffected = pr.executeUpdate();

            if (rowsAffected > 0) {
                rs = pr.getGeneratedKeys();
                if (rs.next()) {
                    int bookingID = rs.getInt(1);
                    book.setBookingID(bookingID); // gán lại vào DTO
                }
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace(); // nên log để dễ debug
        } finally {
            closeResources(conn, pr, rs); // phải đóng rs
        }
        return status; // ✅ return ở cuối
    }

    public boolean updateBooking(int price, int bookID) {
        boolean status = true;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(UPDATE_BOOKING);
            pr.setInt(1, price);
            pr.setInt(2, bookID);

            int result = pr.executeUpdate();
            status = (result > 0);
        } catch (Exception e) {
        } finally {
            closeResources(conn, pr, null);
        }
        return status;
    }
    
    public boolean updateStatusBooking(boolean status, int bookID) {
        boolean result = true;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(UPDATE_BOOKING);
            pr.setBoolean(1, status);
            pr.setInt(2, bookID);

            int i = pr.executeUpdate();
            result = (i > 0);
        } catch (Exception e) {
        } finally {
            closeResources(conn, pr, null);
        }
        return result;
    }

    public BookingDTO getBooking(int bookID) {
        BookingDTO book = new BookingDTO();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_BOOKING);
            pr.setInt(1, bookID);
            rs = pr.executeQuery();
            if (rs.next()) {
                book.setBookingID(rs.getInt("bookingID"));
                book.setUserID(rs.getInt("userID"));
                book.setRoomID(rs.getInt("roomID"));
                book.setCheckInDate(rs.getDate("checkInDate"));
                book.setCheckOutDate(rs.getDate("checkOutDate"));
                book.setTotalPrice(rs.getInt("totalPrice"));
                book.setBookingStatus(rs.getBoolean("bookingStatus"));
                book.setPaymentStatus(rs.getBoolean("paymentStatus"));
            }
        } catch (Exception e) {
        } finally {
            closeResources(conn, pr, rs);
        }
        return book;
    }

    public List<BookingDTO> getBookingByUserId(int userID) {
        List<BookingDTO> book = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_BOOKING_BY_USER_ID);
            pr.setInt(1, userID);
            rs = pr.executeQuery();
            while (rs.next()) {
                BookingDTO bDTO = new BookingDTO();
                bDTO.setBookingID(rs.getInt("bookingID"));
                bDTO.setUserID(rs.getInt("userID"));
                bDTO.setRoomID(rs.getInt("roomID"));
                bDTO.setCheckInDate(rs.getDate("checkInDate"));
                bDTO.setCheckOutDate(rs.getDate("checkOutDate"));
                bDTO.setTotalPrice(rs.getInt("totalPrice"));
                bDTO.setBookingStatus(rs.getBoolean("bookingStatus"));
                bDTO.setPaymentStatus(rs.getBoolean("paymentStatus"));

                book.add(bDTO);
            }
        } catch (Exception e) {
        } finally {
            closeResources(conn, pr, rs);
        }
        return book;
    }

    private void closeResources(Connection conn, PreparedStatement pr, ResultSet rs) {
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
