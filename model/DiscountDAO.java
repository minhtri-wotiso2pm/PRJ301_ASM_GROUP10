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
public class DiscountDAO {

    private static final String GET_ALL_DISCOUNT = "SELECT*FROM tblDiscounts where isActive = 1";
    private static final String GET_ALL_DISCOUNT_BY_CODE = "SELECT*FROM tblDiscounts where discountCode like ? and isActive = 1";

    public List<DiscountDTO> getAllDiscount() {
        List<DiscountDTO> discount = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_ALL_DISCOUNT);
            rs = pr.executeQuery();
            while(rs.next()){
                DiscountDTO dDTO = new DiscountDTO();
                dDTO.setDiscountCode(rs.getString("discountCode"));
                dDTO.setDiscountTitle(rs.getString("discountTitle"));
                dDTO.setDescription(rs.getString("description"));
                dDTO.setDiscountPercent(rs.getInt("discountPercent"));
                dDTO.setMaxDiscount(rs.getInt("maxDiscount"));
                dDTO.setStartDate(rs.getDate("startDate"));
                dDTO.setEndDate(rs.getDate("endDate"));
                dDTO.setIsActive(rs.getBoolean("isActive"));
                dDTO.setMinBookingAmount(rs.getDouble("minBookingAmount"));
                dDTO.setUsageLimit(rs.getInt("usageLimit"));
                
                discount.add(dDTO);
            }
        } catch (Exception e) {
            System.out.println("Error getAllDiscount:"+e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, rs);
        }
        return discount;
    }
    
     public DiscountDTO getDiscountByCode(String discountCode) {
        DiscountDTO discount = null;
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_ALL_DISCOUNT_BY_CODE);
            pr.setString(1,discountCode);
            rs = pr.executeQuery();
            while(rs.next()){
                discount = new DiscountDTO();
                discount.setDiscountCode(rs.getString("discountCode"));
                discount.setDiscountTitle(rs.getString("discountTitle"));
                discount.setDescription(rs.getString("description"));
                discount.setDiscountPercent(rs.getInt("discountPercent"));
                discount.setMaxDiscount(rs.getInt("maxDiscount"));
                discount.setStartDate(rs.getDate("startDate"));
                discount.setEndDate(rs.getDate("endDate"));
                discount.setIsActive(rs.getBoolean("isActive"));
                discount.setMinBookingAmount(rs.getDouble("minBookingAmount"));
                discount.setUsageLimit(rs.getInt("usageLimit"));
            }
        } catch (Exception e) {
            System.out.println("Error getDiscountByCode:"+e.getMessage());
            e.printStackTrace();
        }finally{
            closeResources(conn, pr, rs);
        }
        return discount;
    }
    
    private static void closeResources(Connection conn,PreparedStatement pr, ResultSet rs){
        try {
            if(conn!=null){
                conn.close();
            }else if(pr!=null){
                pr.close();
            }else{
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Error closeResources:"+e.getMessage());
            e.printStackTrace();
        }
    }
}
