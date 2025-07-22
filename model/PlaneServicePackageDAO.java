package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.PlaneServicePackageDTO;
import utils.DbUtils;

public class PlaneServicePackageDAO {

    public PlaneServicePackageDAO() {
    }

    public List<PlaneServicePackageDTO> getAllPackages() {
        List<PlaneServicePackageDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM PlaneServicePackage";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new PlaneServicePackageDTO(
                        rs.getInt("packageId"),
                        rs.getString("ticketClass"),
                        rs.getString("baggageAllowance"),
                        rs.getString("mealOption"),
                        rs.getFloat("extraPrice"),
                        rs.getString("serviceName")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public PlaneServicePackageDTO getPackageById(int id) {
        String sql = "SELECT * FROM PlaneServicePackage WHERE packageId = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PlaneServicePackageDTO(
                        rs.getInt("packageId"),
                        rs.getString("ticketClass"),
                        rs.getString("baggageAllowance"),
                        rs.getString("mealOption"),
                        rs.getFloat("extraPrice"),
                        rs.getString("serviceName")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
