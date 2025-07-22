package model;

import model.PlaneTicketDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class PlaneTicketDAO {

    public PlaneTicketDAO() {
    }

    // SQL Queries for PlaneTicket
    private static final String GET_ALL_TICKETS = "SELECT ticketId, seatNumber, classType, passengerName FROM PlaneTicket";
    private static final String GET_TICKET_BY_ID = "SELECT ticketId, seatNumber, classType, passengerName FROM PlaneTicket WHERE ticketId = ?";
    private static final String CREATE_TICKET = "INSERT INTO PlaneTicket (seatNumber, classType, passengerName) VALUES (?, ?, ?)";
    private static final String UPDATE_TICKET = "UPDATE PlaneTicket SET seatNumber = ?, classType = ?, passengerName = ? WHERE ticketId = ?";
    private static final String DELETE_TICKET = "DELETE FROM PlaneTicket WHERE ticketId = ?";

    // All
    public List<PlaneTicketDTO> getAll() {
        List<PlaneTicketDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_TICKETS);
            rs = ps.executeQuery();

            while (rs.next()) {
                PlaneTicketDTO dto = new PlaneTicketDTO();
                dto.setTicketId(rs.getInt("ticketId"));
                dto.setSeatNumber(rs.getString("seatNumber"));
                dto.setClassType(rs.getString("classType"));
                dto.setPassengerName(rs.getString("passengerName"));
                list.add(dto);
            }
        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, rs);
        }

        return list;
    }

    // By ID
    public PlaneTicketDTO getById(int ticketId) {
        PlaneTicketDTO dto = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_TICKET_BY_ID);
            ps.setInt(1, ticketId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dto = new PlaneTicketDTO();
                dto.setTicketId(rs.getInt("ticketId"));
                dto.setSeatNumber(rs.getString("seatNumber"));
                dto.setClassType(rs.getString("classType"));
                dto.setPassengerName(rs.getString("passengerName"));
            }
        } catch (Exception e) {
            System.err.println("Error in getById(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, rs);
        }

        return dto;
    }

    // Create
    public boolean create(PlaneTicketDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_TICKET);
            ps.setString(1, dto.getSeatNumber());
            ps.setString(2, dto.getClassType());
            ps.setString(3, dto.getPassengerName());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, null);
        }

        return success;
    }

    // Update
    public boolean update(PlaneTicketDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_TICKET);
            ps.setString(1, dto.getSeatNumber());
            ps.setString(2, dto.getClassType());
            ps.setString(3, dto.getPassengerName());
            ps.setInt(4, dto.getTicketId());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, null);
        }

        return success;
    }

    // Delete
    public boolean delete(int ticketId) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(DELETE_TICKET);
            ps.setInt(1, ticketId);

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in delete(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, null);
        }

        return success;
    }
}
