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
public class ServiceDAO {
    
    private static final String GET_ALL_SERVICE = "SELECT*FROM tblServices";
    private static final String GET_SERVICE_BY_HOTEL_ID = "SELECT s.serviceID,s.hotelID,s.serviceName,s.description,s.price,s.isAvailable FROM tblServices s join tblHotels h on s.hotelID=h.hotelID where h.hotelID=?";
    
    public static List<ServiceDTO> getServiceByHotelId(int hotelId){
        List<ServiceDTO> service = new ArrayList<>();
        Connection conn =null;
        PreparedStatement pr =null;
        ResultSet rs =null;
        try {
            conn=DbUtils.getConnection();
            pr =conn.prepareStatement(GET_SERVICE_BY_HOTEL_ID);
            pr.setInt(1, hotelId);
            rs = pr.executeQuery();
            while(rs.next()){
                ServiceDTO sDTO = new ServiceDTO();
                sDTO.setServiceID(rs.getInt("serviceID"));
                sDTO.setHotelID(rs.getInt("hotelID"));
                sDTO.setServiceName(rs.getString("serviceName"));
                sDTO.setDescription(rs.getString("description"));
                sDTO.setPrice(rs.getDouble("price"));
                sDTO.setIsAvailable(rs.getBoolean("isAvailable"));
                
                service.add(sDTO);
            }
        } catch (Exception e) {
            System.out.println("Error in getServiceByHotelId()" + e.getMessage());
            e.getStackTrace();
        }finally{
            closeResources(conn, pr, rs);
        }
        return  service;
    }
    
    public List<ServiceDTO> getAllService(){
        List<ServiceDTO> service = new ArrayList<>();
        Connection conn =null;
        PreparedStatement pr =null;
        ResultSet rs =null;
        try {
            conn=DbUtils.getConnection();
            pr =conn.prepareStatement(GET_ALL_SERVICE);
            rs = pr.executeQuery();
            while(rs.next()){
                ServiceDTO sDTO = new ServiceDTO();
                sDTO.setServiceID(rs.getInt("serviceID"));
                sDTO.setHotelID(rs.getInt("hotelID"));
                sDTO.setServiceName(rs.getString("serviceName"));
                sDTO.setDescription(rs.getString("description"));
                sDTO.setPrice(rs.getDouble("price"));
                sDTO.setIsAvailable(rs.getBoolean("isAvailable"));
                
                service.add(sDTO);
            }
        } catch (Exception e) {
            System.out.println("Error in getServiceByHotelId()" + e.getMessage());
            e.getStackTrace();
        }finally{
            closeResources(conn, pr, rs);
        }
        return  service;
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
