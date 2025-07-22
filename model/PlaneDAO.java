package model;

import model.PlaneDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.PlanePriceDTO;
import utils.DbUtils;

public class PlaneDAO {

    public PlaneDAO() {
    }

    // SQL Queries for Plane
    private static final String GET_ALL_PLANES = "SELECT flightId, airline, departureAirport, arrivalAirport, departureTime, arrivalTime, flightNumber FROM Plane";
    private static final String GET_PLANE_BY_ID = "SELECT flightId, airline, departureAirport, arrivalAirport, departureTime, arrivalTime, flightNumber FROM Plane WHERE flightId = ?";
    private static final String CREATE_PLANE = "INSERT INTO Plane (airline, departureAirport, arrivalAirport, departureTime, arrivalTime, flightNumber) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PLANE = "UPDATE Plane SET airline = ?, departureAirport = ?, arrivalAirport = ?, departureTime = ?, arrivalTime = ?, flightNumber = ? WHERE flightId = ?";
    private static final String DELETE_PLANE = "DELETE FROM Plane WHERE flightId = ?";

    // All
    public List<PlaneDTO> getAll() {
        List<PlaneDTO> lists = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_PLANES);
            rs = ps.executeQuery();

            while (rs.next()) {
                PlaneDTO dto = new PlaneDTO();
                dto.setFlightId(rs.getInt("flightId"));
                dto.setAirline(rs.getString("airline"));
                dto.setDepartureAirport(rs.getString("departureAirport"));
                dto.setArrivalAirport(rs.getString("arrivalAirport"));
                dto.setDepartureTime(rs.getString("departureTime"));
                dto.setArrivalTime(rs.getString("arrivalTime"));
                dto.setFlightNumber(rs.getString("flightNumber"));
                lists.add(dto);
            }

            // Lọc các chuyến bay còn lại từ hôm nay trở đi
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(new Date());

            lists.removeIf(p -> {
                String departureDate = p.getDepartureTime().substring(0, 10);
                return departureDate.compareTo(today) < 0;
            });

        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, rs);
        }

        return lists;
    }

    // By ID
    public PlaneDTO getById(int flightId) {
        PlaneDTO dto = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PLANE_BY_ID);
            ps.setInt(1, flightId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dto = new PlaneDTO();
                dto.setFlightId(rs.getInt("flightId"));
                dto.setAirline(rs.getString("airline"));
                dto.setDepartureAirport(rs.getString("departureAirport"));
                dto.setArrivalAirport(rs.getString("arrivalAirport"));
                dto.setDepartureTime(rs.getString("departureTime"));
                dto.setArrivalTime(rs.getString("arrivalTime"));
                dto.setFlightNumber(rs.getString("flightNumber"));
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
    public boolean create(PlaneDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_PLANE);
            ps.setString(1, dto.getAirline());
            ps.setString(2, dto.getDepartureAirport());
            ps.setString(3, dto.getArrivalAirport());

            // Chuyển String -> LocalDateTime -> Timestamp
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime depTime = LocalDateTime.parse(dto.getDepartureTime(), formatter);
            LocalDateTime arrTime = LocalDateTime.parse(dto.getArrivalTime(), formatter);

            ps.setTimestamp(4, Timestamp.valueOf(depTime));
            ps.setTimestamp(5, Timestamp.valueOf(arrTime));

            ps.setString(6, dto.getFlightNumber());

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, null);
        }

        return success;
    }

    public String createAndReturnId(PlaneDTO plane) {
        String sql = "INSERT INTO Plane (airline, departureAirport, arrivalAirport, departureTime, arrivalTime, flightNumber) VALUES (?, ?, ?, ?, ?, ?)";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, plane.getAirline());
            ps.setString(2, plane.getDepartureAirport());
            ps.setString(3, plane.getArrivalAirport());
            ps.setString(4, plane.getDepartureTime());
            ps.setString(5, plane.getArrivalTime());
            ps.setString(6, plane.getFlightNumber());

            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getString(1); // trả về flightId
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update
    public boolean update(PlaneDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_PLANE);
            ps.setString(1, dto.getAirline());
            ps.setString(2, dto.getDepartureAirport());
            ps.setString(3, dto.getArrivalAirport());
            ps.setString(4, dto.getDepartureTime());
            ps.setString(5, dto.getArrivalTime());
            ps.setString(6, dto.getFlightNumber());
            ps.setInt(7, dto.getFlightId());

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
    public boolean delete(int flightId) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(DELETE_PLANE);
            ps.setInt(1, flightId);

            success = ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in delete(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, null);
        }

        return success;
    }

    public List<PlaneDTO> searchPlanes(String from, String to, String departureDate) {
        List<PlaneDTO> result = new ArrayList<>();
        for (PlaneDTO p : getAll()) {
            String departureDayOnly = p.getDepartureTime().substring(0, 10); // lấy phần "yyyy-MM-dd"

            boolean match
                    = (from == null || from.isEmpty() || p.getDepartureAirport().toLowerCase().contains(from.toLowerCase()))
                    && (to == null || to.isEmpty() || p.getArrivalAirport().toLowerCase().contains(to.toLowerCase()))
                    && (departureDate == null || departureDate.isEmpty() || departureDayOnly.equals(departureDate));

            if (match) {
                result.add(p);
            }
        }
        return result;
    }

    public List<PlaneDTO> searchPlanesft(String from, String to) {
        List<PlaneDTO> result = new ArrayList<>();
        for (PlaneDTO p : getAll()) {

            boolean match
                    = (from == null || from.isEmpty() || p.getDepartureAirport().toLowerCase().contains(from.toLowerCase()))
                    && (to == null || to.isEmpty() || p.getArrivalAirport().toLowerCase().contains(to.toLowerCase()));

            if (match) {
                result.add(p);
            }
        }
        return result;
    }

    public List<PlaneDTO> getPlanesByAirline(String airlineName) {
        List<PlaneDTO> lists = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM Plane WHERE airline = ?";

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, airlineName);
            rs = ps.executeQuery();

            while (rs.next()) {
                PlaneDTO dto = new PlaneDTO();
                dto.setFlightId(rs.getInt("flightId"));
                dto.setAirline(rs.getString("airline"));
                dto.setDepartureAirport(rs.getString("departureAirport"));
                dto.setArrivalAirport(rs.getString("arrivalAirport"));
                dto.setDepartureTime(rs.getString("departureTime"));
                dto.setArrivalTime(rs.getString("arrivalTime"));
                dto.setFlightNumber(rs.getString("flightNumber"));
                lists.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, rs);
        }

        return lists;
    }

    public PlaneDTO getPlaneById(String flightId) {
        PlaneDTO dto = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT p.flightId, p.airline, p.departureAirport, p.arrivalAirport, "
                + "p.departureTime, p.arrivalTime, p.flightNumber, "
                + "pp.basePrice, pp.totalSeats, pp.bookedSeats "
                + "FROM Plane p "
                + "JOIN PlanePrice pp ON p.flightId = pp.flightId "
                + "WHERE p.flightId = ?";

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, flightId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dto = new PlaneDTO();
                dto.setFlightId(rs.getInt("flightId"));
                dto.setAirline(rs.getString("airline"));
                dto.setDepartureAirport(rs.getString("departureAirport"));
                dto.setArrivalAirport(rs.getString("arrivalAirport"));
                dto.setDepartureTime(rs.getString("departureTime"));
                dto.setArrivalTime(rs.getString("arrivalTime"));
                dto.setFlightNumber(rs.getString("flightNumber"));

                // Gắn thêm thông tin giá vé
                PlanePriceDTO priceDTO = new PlanePriceDTO();
                priceDTO.setFlightId(rs.getInt("flightId"));
                priceDTO.setBasePrice(rs.getDouble("basePrice"));
                priceDTO.setTotalSeats(rs.getInt("totalSeats"));
                priceDTO.setBookedSeats(rs.getInt("bookedSeats"));

                dto.setPriceInfo(priceDTO); // bạn cần thêm thuộc tính này vào PlaneDTO
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, rs);
        }

        return dto;
    }

}
