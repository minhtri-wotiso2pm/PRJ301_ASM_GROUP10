/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.PlanePaymentDTO;
import utils.DbUtils;

/**
 *
 * @author nzero
 */
public class PlanePaymentDAO {

    private static final String CREATE_PLANEPAYMENT = "INSERT INTO PlanePayments (bookingID, userID, amountPaid, paymentDate, paymentMethod, paymentStatus) VALUES (?, ?, ?, ?, ?, ?)";

    public boolean createPlanePayment(PlanePaymentDTO pay) {
        boolean status = true;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(CREATE_PLANEPAYMENT);
            pr.setInt(1, pay.getBookingID());
            pr.setInt(2, pay.getUserID());
            pr.setDouble(3, pay.getAmountPaid());
            pr.setDate(4, new java.sql.Date(pay.getPaymentDate().getTime()));
            pr.setString(5, pay.getPaymentMethod());
            pr.setBoolean(6, pay.isPaymentStatus());
            int result = pr.executeUpdate();
            status = (result > 0);
        } catch (Exception e) {
            System.out.println("Error closeResources:" + e.getMessage());
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
            System.out.println("Error closeResources:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
