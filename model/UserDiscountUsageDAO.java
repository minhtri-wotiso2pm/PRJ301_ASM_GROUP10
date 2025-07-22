/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DbUtils;

/**
 *
 * @author nzero
 */
public class UserDiscountUsageDAO {

    private static final String UPDATE_USAGECOUNT = "UPDATE UserDiscountUsage SET usageCount=? where userID=? and discountCode = ?";
    private static final String GET_USAGE = "SELECT*FROM UserDiscountUsage where userID=? and discountCode = ?";
    
    public UserDiscountUsageDTO getUsageCount(int userID, String description) {
        UserDiscountUsageDTO udu = new UserDiscountUsageDTO();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs =null;
        try {
            conn=DbUtils.getConnection();
            pr=conn.prepareStatement(GET_USAGE);
            pr.setInt(1, userID);
            pr.setString(2, description);
            rs=pr.executeQuery();
            if(rs.next()){
                udu.setUserID(rs.getInt("userID"));
                udu.setDiscountCode(rs.getString("discountCode"));
                udu.setUsageCount(rs.getInt("usageCount"));
            }
        } catch (Exception e) {
            System.out.println("Error getDiscountFlUserId():"+e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return udu;
    }
    
    public boolean getUpdateUsageCount(int usageCount,int userID, String description) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn=DbUtils.getConnection();
            pr=conn.prepareStatement(UPDATE_USAGECOUNT);
            pr.setInt(1, usageCount);
            pr.setInt(2, userID);
            pr.setString(3, description);
            
            int ressult = pr.executeUpdate();
            status=(ressult>0);
        } catch (Exception e) {
            System.out.println("Error getDiscountFlUserId():"+e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return status;
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
            System.out.println("Error closeResources():"+e.getMessage());
            e.printStackTrace();
        }
    }
}
