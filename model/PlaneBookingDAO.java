package model;

import model.PlaneBookingDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.PlaneBookingDTO;
import model.PlaneDTO;
import utils.DbUtils;

public class PlaneBookingDAO {

    public PlaneBookingDAO() {
    }

    // SQL Queries for PlaneBooking
    private static final String GET_ALL_BOOKINGS = "SELECT bookingId, userId, flightId, ticketId, price, total, bookingDate, status FROM PlaneBooking";
    private static final String GET_BOOKING_BY_ID = "SELECT bookingId, userId, flightId, ticketId, price, total, bookingDate, status FROM PlaneBooking WHERE bookingId = ?";
    private static final String CREATE_BOOKING = "INSERT INTO PlaneBooking (userId, flightId, ticketId, price, total, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BOOKING = "UPDATE PlaneBooking SET userId = ?, flightId = ?, ticketId = ?, price = ?, total = ?, bookingDate = ?, status = ? WHERE bookingId = ?";
    private static final String DELETE_BOOKING = "DELETE FROM PlaneBooking WHERE bookingId = ?";

    //All
    public List<PlaneBookingDTO> getAll() {
        List<PlaneBookingDTO> lists = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_BOOKINGS);
            rs = ps.executeQuery();

            while (rs.next()) {
                PlaneBookingDTO dto = new PlaneBookingDTO();
                dto.setBookingId(rs.getInt("bookingId"));
                dto.setUserId(rs.getString("userId"));
                dto.setFlightId(rs.getInt("flightId"));
                dto.setTicketId(rs.getInt("ticketId"));
                dto.setPrice(rs.getDouble("price"));
                dto.setTotal(rs.getDouble("total"));
                dto.setBookingDate(rs.getString("bookingDate"));
                dto.setStatus(rs.getString("status"));
                lists.add(dto);
            }
        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, rs);
        }

        return lists;
    }

    //ByID
    public PlaneBookingDTO getById(int bookingId) {
        PlaneBookingDTO dto = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_BOOKING_BY_ID);
            ps.setInt(1, bookingId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dto = new PlaneBookingDTO();
                dto.setBookingId(rs.getInt("bookingId"));
                dto.setUserId(rs.getString("userId"));
                dto.setFlightId(rs.getInt("flightId"));
                dto.setTicketId(rs.getInt("ticketId"));
                dto.setPrice(rs.getDouble("price"));
                dto.setTotal(rs.getDouble("total"));
                dto.setBookingDate(rs.getString("bookingDate"));
                dto.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            System.err.println("Error in getById(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, rs);
        }

        return dto;
    }

    //Create
    public boolean create(PlaneBookingDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_BOOKING);
            ps.setString(1, dto.getUserId());
            ps.setInt(2, dto.getFlightId());
            ps.setInt(3, dto.getTicketId());
            ps.setDouble(4, dto.getPrice());
            ps.setDouble(5, dto.getTotal());
            ps.setString(6, dto.getStatus()); // bookingDate lấy mặc định trong DB

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, null);
        }

        return success;
    }

    //Update
    public boolean update(PlaneBookingDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_BOOKING);
            ps.setString(1, dto.getUserId());
            ps.setInt(2, dto.getFlightId());
            ps.setInt(3, dto.getTicketId());
            ps.setDouble(4, dto.getPrice());
            ps.setDouble(5, dto.getTotal());
            ps.setString(6, dto.getBookingDate());
            ps.setString(7, dto.getStatus());
            ps.setInt(8, dto.getBookingId());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, null);
        }

        return success;
    }

    public void createBooking(int userId, int flightId, int packageId, float price, float total, String status) {
        String sql = "INSERT INTO PlaneBooking (userId, flightId, packageId, price, total, status) VALUES (?, ?, ?, ?, ?, ?)";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, flightId);
            ps.setInt(3, packageId);
            ps.setFloat(4, price);
            ps.setFloat(5, total);
            ps.setString(6, status);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// Get all bookings by userId

    public List<PlaneBookingDTO> getBookingsByUserId(int userId) {
        List<PlaneBookingDTO> bookings = new ArrayList<>();
        String sql = "SELECT bookingId, userId, flightId, price, total, bookingDate, status FROM PlaneBooking WHERE userId = ?";

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PlaneBookingDTO dto = new PlaneBookingDTO();
                    dto.setBookingId(rs.getInt("bookingId"));
                    dto.setUserId(rs.getString("userId"));
                    dto.setFlightId(rs.getInt("flightId"));
                    dto.setPrice(rs.getDouble("price"));
                    dto.setTotal(rs.getDouble("total"));
                    dto.setBookingDate(rs.getString("bookingDate"));
                    dto.setStatus(rs.getString("status"));
                    bookings.add(dto);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in getBookingsByUserId(): " + e.getMessage());
            e.printStackTrace();
        }

        return bookings;
    }
public List<PlaneBookingDTO> getBookingsByPlaneId(int userId) {
        List<PlaneBookingDTO> bookings = new ArrayList<>();
        String sql = "SELECT bookingId, userId, flightId, price, total, bookingDate, status FROM PlaneBooking WHERE flightId = ?";

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PlaneBookingDTO dto = new PlaneBookingDTO();
                    dto.setBookingId(rs.getInt("bookingId"));
                    dto.setUserId(rs.getString("userId"));
                    dto.setFlightId(rs.getInt("flightId"));
                    dto.setPrice(rs.getDouble("price"));
                    dto.setTotal(rs.getDouble("total"));
                    dto.setBookingDate(rs.getString("bookingDate"));
                    dto.setStatus(rs.getString("status"));
                    bookings.add(dto);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in getBookingsByUserId(): " + e.getMessage());
            e.printStackTrace();
        }

        return bookings;
    }
}
