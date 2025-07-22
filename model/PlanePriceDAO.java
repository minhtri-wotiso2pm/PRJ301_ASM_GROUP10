package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.PlanePriceDTO;
import utils.DbUtils;

public class PlanePriceDAO {

    public PlanePriceDTO getPlanePriceByFlightId(String flightId) {
        PlanePriceDTO dto = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM PlanePrice WHERE flightId = ?";

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, flightId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dto = new PlanePriceDTO();
                dto.setFlightId(rs.getInt("flightId"));
                dto.setBasePrice(rs.getDouble("basePrice"));
                dto.setTotalSeats(rs.getInt("totalSeats"));
                dto.setBookedSeats(rs.getInt("bookedSeats"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, rs);
        }

        return dto;
    }

    public boolean create(PlanePriceDTO dto) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO PlanePrice (flightId, basePrice, totalSeats, bookedSeats) VALUES (?, ?, ?, ?)";

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dto.getFlightId()); // giả sử flightId là String (UUID) thì không cần parse
            ps.setDouble(2, dto.getBasePrice());
            ps.setInt(3, dto.getTotalSeats());
            ps.setInt(4, dto.getBookedSeats());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeResources(conn, ps, null);
        }

        return false;
    }

}
