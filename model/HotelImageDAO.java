/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author nzero
 */
public class HotelImageDAO {

    private static final String GET_IMAGE_BY_HOTEL_ID = "SELECT hi.id,hi.hotelId,hi.imageUrl FROM HotelImages hi join tblHotels h on hi.hotelID=h.hotelID where h.hotelID=?";

    public static List<HotelImageDTO> getImageByHotelId(int HotelID) {
        List<HotelImageDTO> image = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_IMAGE_BY_HOTEL_ID);
            pr.setInt(1, HotelID);
            rs = pr.executeQuery();
            while (rs.next()) {
                HotelImageDTO hiDTO = new HotelImageDTO();
                hiDTO.setId(rs.getInt("id"));
                hiDTO.setHotelId(rs.getInt("hotelID"));
                hiDTO.setImageUrl(rs.getString("imageUrl"));
                
                image.add(hiDTO);
            }
        } catch (Exception e) {
            System.out.println("Error getImageByHotelId:" + e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, rs);
        }
        return image;
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
