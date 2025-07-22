package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class PaymentDAO {

    private static final String CREATE_PAYMENT = "INSERT INTO tblPayments(bookingID, userID, amountPaid, paymentDate, paymentMethod, paymentStatus) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PAYMENT = "UPDATE tblPayments SET paymentStatus =? where paymentID =?";
    private static final String GET_PAYMENT_BY_STATUS = "SELECT * FROM tblPayments WHERE userID = ? AND paymentStatus = ?";
    private static final String GET_PAYMENT_BY_ID = "SELECT * FROM tblPayments WHERE paymentID = ?";

    // Tạo bản ghi thanh toán vào DB
    public boolean createPayment(PaymentDTO pay) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs =null;
        try {
            conn = DbUtils.getConnection();

            // Thêm RETURN_GENERATED_KEYS để lấy ID vừa insert
            pr = conn.prepareStatement(CREATE_PAYMENT, Statement.RETURN_GENERATED_KEYS);

            pr.setInt(1, pay.getBookingID());
            pr.setInt(2, pay.getUserID());
            pr.setDouble(3, pay.getAmountPaid());
            pr.setDate(4, new java.sql.Date(pay.getPaymentDate().getTime()));
            pr.setString(5, pay.getPaymentMethod());
            pr.setBoolean(6, pay.isPaymentStatus());

            int result = pr.executeUpdate();
            status = (result > 0);

            // Lấy paymentID nếu insert thành công
            if (status) {
                rs = pr.getGeneratedKeys();
                if (rs.next()) {
                    int generatedID = rs.getInt(1);
                    pay.setPaymentID(generatedID); // <- Gán lại vào DTO
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    public List<PaymentDTO> getPaymentsByStatus(int userID, boolean status) {
        List<PaymentDTO> paymentList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_PAYMENT_BY_STATUS);
            pr.setInt(1, userID);
            pr.setBoolean(2, status);

            rs = pr.executeQuery();
            while (rs.next()) {
                PaymentDTO pDto = new PaymentDTO();
                pDto.setPaymentID(rs.getInt("paymentID"));
                pDto.setBookingID(rs.getInt("bookingID"));
                pDto.setUserID(rs.getInt("userID"));
                pDto.setAmountPaid(rs.getInt("amountPaid"));
                pDto.setPaymentDate(rs.getDate("paymentDate"));
                pDto.setPaymentMethod(rs.getString("paymentMethod"));
                pDto.setPaymentStatus(rs.getBoolean("paymentStatus"));

                paymentList.add(pDto);
            }
        } catch (Exception e) {
            System.out.println("Error getPaymentsByStatus: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }

        return paymentList;
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

    public boolean updatePayment(boolean status, int id) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(UPDATE_PAYMENT);
            pr.setBoolean(1, status);
            pr.setInt(2, id);

            int i = pr.executeUpdate();
            result = (i > 0);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public static PaymentDTO getPaymentById(int id) {
        PaymentDTO payment = null;
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_PAYMENT_BY_ID);
            pr.setInt(1, id);
            rs = pr.executeQuery();
            if (rs.next()) {
                payment = new PaymentDTO();
                payment.setPaymentID(rs.getInt("paymentID"));
                payment.setBookingID(rs.getInt("bookingID"));
                payment.setUserID(rs.getInt("userID"));
                payment.setAmountPaid(rs.getInt("amountPaid"));
                payment.setPaymentDate(rs.getDate("paymentDate"));
                payment.setPaymentMethod(rs.getString("paymentMethod"));
                payment.setPaymentStatus(rs.getBoolean("paymentStatus"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }
}
