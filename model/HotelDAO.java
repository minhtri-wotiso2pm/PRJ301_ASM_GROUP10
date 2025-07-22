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
public class HotelDAO {

    private static final String HOTEL_BY_TYPE = "SELECT*FROM tblHotels where type=?";
    private static final String HOTEL_BY_ID = "SELECT*FROM tblHotels WHERE hotelID = ?";
    private static final String HOTEL_BY_CITY = "SELECT*FROM tblHotels WHERE city = ?";
    private static final String HOTEL_BY_TYPE_Name_Or_City = "SELECT*FROM tblHotels WHERE (name like ? or city like ?) and type=?";
    private static final String CREATE_HOTEL = "INSERT INTO tblHotels (name,address,city,mainImageUrl,description,rate,price,type,status) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_HOTEL = "UPDATE tblHotels SET name=?,address=?,city=?,mainImageUrl=?,description=?,rate=?,price=?,type=?,status=? WHERE hotelID = ?";
    private static final String UPDATE_PRICE_HOTEL = "UPDATE tblHotels SET price=? WHERE hotelID = ?";

    public static List<HotelDTO> getHotelsByType(String type) {
        List<HotelDTO> hotels = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(HOTEL_BY_TYPE);
            pr.setString(1, type);
            rs = pr.executeQuery();
            while (rs.next()) {
                HotelDTO hotel = new HotelDTO();
                hotel.setHotelID(rs.getInt("hotelID"));
                hotel.setName(rs.getString("name"));
                hotel.setAddress(rs.getString("address"));
                hotel.setCity(rs.getString("city"));
                hotel.setMainImageUrl(rs.getString("mainImageUrl"));
                hotel.setDescription(rs.getString("description"));
                hotel.setRate(rs.getDouble("rate"));
                hotel.setPrice(rs.getDouble("price"));
                hotel.setType(rs.getString("type"));
                hotel.setStatus(rs.getBoolean("status"));

                hotels.add(hotel);
            }
        } catch (Exception e) {
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.getStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return hotels;
    }

    public static List<HotelDTO> getHotelByCity(String city) {
        List<HotelDTO> hotels = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(HOTEL_BY_CITY);
            pr.setString(1, city);
            rs = pr.executeQuery();
            while (rs.next()) {
                HotelDTO hotel = new HotelDTO();
                hotel.setHotelID(rs.getInt("hotelID"));
                hotel.setName(rs.getString("name"));
                hotel.setAddress(rs.getString("address"));
                hotel.setCity(rs.getString("city"));
                hotel.setMainImageUrl(rs.getString("mainImageUrl"));
                hotel.setDescription(rs.getString("description"));
                hotel.setRate(rs.getDouble("rate"));
                hotel.setPrice(rs.getDouble("price"));
                hotel.setType(rs.getString("type"));
                hotel.setStatus(rs.getBoolean("status"));

                hotels.add(hotel);
            }
        } catch (Exception e) {
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.getStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return hotels;
    }

    public static HotelDTO getHotelsById(int id) {
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        HotelDTO hotel = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(HOTEL_BY_ID);
            pr.setInt(1, id);
            rs = pr.executeQuery();

            if (rs.next()) {
                hotel = new HotelDTO();
                hotel.setHotelID(rs.getInt("hotelID"));
                hotel.setName(rs.getString("name"));
                hotel.setAddress(rs.getString("address"));
                hotel.setCity(rs.getString("city"));
                hotel.setMainImageUrl(rs.getString("mainImageUrl"));
                hotel.setDescription(rs.getString("description"));
                hotel.setRate(rs.getDouble("rate"));
                hotel.setPrice(rs.getDouble("price"));
                hotel.setType(rs.getString("type"));
                hotel.setStatus(rs.getBoolean("status"));

            }
        } catch (Exception e) {
            System.out.println("Error in getHotelsById ()" + e.getMessage());
            e.getStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return hotel;
    }

    public static List<HotelDTO> getHotelsByNameOrCity(String type, String nameOrCity) {
        List<HotelDTO> hotel = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(HOTEL_BY_TYPE_Name_Or_City);
            pr.setString(1, "%" + nameOrCity + "%");
            pr.setString(2, "%" + nameOrCity + "%");
            pr.setString(3, type);
            rs = pr.executeQuery();

            while (rs.next()) {
                HotelDTO hDTO = new HotelDTO();
                hDTO.setHotelID(rs.getInt("hotelID"));
                hDTO.setName(rs.getString("name"));
                hDTO.setAddress(rs.getString("address"));
                hDTO.setCity(rs.getString("city"));
                hDTO.setMainImageUrl(rs.getString("mainImageUrl"));
                hDTO.setDescription(rs.getString("description"));
                hDTO.setRate(rs.getDouble("rate"));
                hDTO.setPrice(rs.getDouble("price"));
                hDTO.setType(rs.getString("type"));
                hDTO.setStatus(rs.getBoolean("status"));

                hotel.add(hDTO);
            }
        } catch (Exception e) {
            System.out.println("Error in getHotelsByNameOrCity()" + e.getMessage());
            e.getStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return hotel;
    }

    public Boolean crateHotel(HotelDTO hotel) {
        Boolean success = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(CREATE_HOTEL);
            pr.setString(1, hotel.getName());
            pr.setString(2, hotel.getAddress());
            pr.setString(3, hotel.getCity());
            pr.setString(4, hotel.getMainImageUrl());
            pr.setString(5, hotel.getDescription());
            pr.setDouble(6, hotel.getRate());
            pr.setDouble(7, hotel.getPrice());
            pr.setString(8, hotel.getType());
            pr.setBoolean(9, hotel.isStatus());

            int rowsAffected = pr.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (Exception e) {
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.getStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return success;
    }

    public Boolean updateHotel(HotelDTO hotel) {
        Boolean success = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(UPDATE_HOTEL);
            pr.setString(1, hotel.getName());
            pr.setString(2, hotel.getAddress());
            pr.setString(3, hotel.getCity());
            pr.setString(4, hotel.getMainImageUrl());
            pr.setString(5, hotel.getDescription());
            pr.setDouble(6, hotel.getRate());
            pr.setDouble(7, hotel.getPrice());
            pr.setString(8, hotel.getType());
            pr.setBoolean(9, hotel.isStatus());
            pr.setInt(10, hotel.getHotelID());

            int rowsAffected = pr.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (Exception e) {
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.getStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return success;
    }

    public static Boolean updatePriceHotel(double price, int hotelID) {
        Boolean success = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(UPDATE_PRICE_HOTEL);
            pr.setDouble(1, price);
            pr.setInt(2, hotelID);

            int rowsAffected = pr.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }

        } catch (Exception e) {
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.getStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return success;
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
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.getStackTrace();
        }
    }
}
