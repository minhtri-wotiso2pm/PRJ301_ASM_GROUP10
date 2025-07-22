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
public class RoomDAO {
    
    private static final String GET_ALL_ROOM = "SELECT * FROM tblRooms";
    private static final String GET_ALL_ROOM_BY_ID = "SELECT * FROM tblRooms where roomID = ?";
    private static final String GET_ROOM_BY_HOTEL_ID = "SELECT r.roomID,r.hotelID,r.roomType,r.capacity,r.pricePerNight,r.isAvailable,r.description,r.imageRoom,r.bedInfo,r.quantity,h.name,h.city FROM tblRooms r JOIN tblHotels h on r.hotelID = h.hotelID WHERE h.hotelID=? AND r.isAvailable = 1";
    private static final String UPDATE_QUANTITY_ROOM = "UPDATE tblRooms SET quantity=? where roomID=?";
    
    public static List<RoomDTO> getAllRooms() {
        List<RoomDTO> room = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_ALL_ROOM);
            rs = pr.executeQuery();
            while (rs.next()) {
                RoomDTO rDTO = new RoomDTO();
                rDTO.setRoomID(rs.getInt("roomID"));
                rDTO.setHotelID(rs.getInt("hotelID"));
                rDTO.setRoomType(rs.getString("roomType"));
                rDTO.setCapacity(rs.getInt("capacity"));
                rDTO.setPricePerNight(rs.getDouble("pricePerNight"));
                rDTO.setIsAvailable(rs.getBoolean("isAvailable"));
                rDTO.setDescription(rs.getString("description"));
                rDTO.setImageRoom(rs.getString("imageRoom"));
                rDTO.setBedInfo(rs.getString("bedInfo"));
                rDTO.setQuantity(rs.getInt("quantity"));
                room.add(rDTO);
            }
        } catch (Exception e) {
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return room;
    }
    
    public static RoomDTO getRoomsByID(int roomID) {
        RoomDTO room = new RoomDTO();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_ALL_ROOM_BY_ID);
            pr.setInt(1, roomID);
            rs = pr.executeQuery();
            while (rs.next()) {
                room.setRoomID(rs.getInt("roomID"));
                room.setHotelID(rs.getInt("hotelID"));
                room.setRoomType(rs.getString("roomType"));
                room.setCapacity(rs.getInt("capacity"));
                room.setPricePerNight(rs.getDouble("pricePerNight"));
                room.setIsAvailable(rs.getBoolean("isAvailable"));
                room.setDescription(rs.getString("description"));
                room.setImageRoom(rs.getString("imageRoom"));
                room.setBedInfo(rs.getString("bedInfo"));
                room.setQuantity(rs.getInt("quantity"));
            }
        } catch (Exception e) {
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return room;
    }
    
    public static boolean updateQuantityRoom(int quantity,int roomID) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(UPDATE_QUANTITY_ROOM);
            pr.setInt(1, quantity);
            pr.setInt(2, roomID);
            int result = pr.executeUpdate();
            status=(result>0);
        } catch (Exception e) {
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return status;
    }
    
    public static List<RoomDTO> getRoomsByHotelID(int hotelID) {
        List<RoomDTO> room = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_ROOM_BY_HOTEL_ID);
            pr.setInt(1, hotelID);
            rs = pr.executeQuery();
            while (rs.next()) {
                RoomDTO rDTO = new RoomDTO();
                rDTO.setRoomID(rs.getInt("roomID"));
                rDTO.setHotelID(rs.getInt("hotelID"));
                rDTO.setRoomType(rs.getString("roomType"));
                rDTO.setCapacity(rs.getInt("capacity"));
                rDTO.setPricePerNight(rs.getDouble("pricePerNight"));
                rDTO.setIsAvailable(rs.getBoolean("isAvailable"));
                rDTO.setDescription(rs.getString("description"));
                rDTO.setImageRoom(rs.getString("imageRoom"));
                rDTO.setBedInfo(rs.getString("bedInfo"));
                rDTO.setQuantity(rs.getInt("quantity"));
                room.add(rDTO);
            }
        } catch (Exception e) {
            System.out.println("Error in Get_All_Hotels()" + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return room;
    }
    
    public static boolean canAccommodate(List<RoomDTO> rooms, int requiredRooms, int totalGuests) {
        return backtrack(rooms, 0, requiredRooms, 0, totalGuests);
    }

    private static boolean backtrack(List<RoomDTO> rooms, int index, int roomsLeft, int currentCapacity, int totalGuests) {
        if (roomsLeft < 0) {
            return false;
        }
        if (currentCapacity >= totalGuests && roomsLeft == 0) {
            return true;
        }
        if (index >= rooms.size()) {
            return false;
        }

        RoomDTO room = rooms.get(index);
        int maxQuantity = Math.min(room.getQuantity(), roomsLeft);

        for (int i = 0; i <= maxQuantity; i++) {
            if (backtrack(rooms, index + 1, roomsLeft - i, currentCapacity + i * room.getCapacity(), totalGuests)) {
                return true;
            }
        }

        return false;
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
