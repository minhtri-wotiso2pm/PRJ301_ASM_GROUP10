/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import utils.DbUtils;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

/**
 *
 * @author nzero
 */
public class UserDAO {

    private final static String GET_ALL_USER = "SELECT * FROM tblUsers";
    private final static String GET_ALL_USER_BY_ID = "SELECT * FROM tblUsers WHERE userID=?";
    private final static String GET_USER_BY_PHONE = "SELECT * FROM tblUsers WHERE phone=? OR email=?";
    private final static String CREATE_USER = "INSERT INTO tblUSers(fullName,phone,email,password,roleID,gender,birthday,city,status) VALUES (?,?,?,?,?,?,?,?,?)";
    public static final String UPDATE_USER = "UPDATE tblUsers SET password=? WHERE phone = ? OR email= ?";
    public static final String UPDATE_PROFILE = "UPDATE tblUsers SET(gender = ?,birthday= ?,city=?) WHERE userID=?";

    public List<UserDTO> allUser() {
        List<UserDTO> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_ALL_USER);
            rs = pr.executeQuery();

            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserID(rs.getInt("userID"));
                user.setFullName(rs.getString("fullName"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRoleID(rs.getString("roleID"));
                user.setStatus(rs.getBoolean("status"));

                users.add(user);
            }
        } catch (Exception e) {
            System.out.println("Error output allUSer" + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return users;
    }

    public UserDTO getAllUserById(int userID) {
        UserDTO user = new UserDTO();
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            pr = conn.prepareStatement(GET_ALL_USER_BY_ID);
            pr.setInt(1, userID);
            rs = pr.executeQuery();

            if (rs.next()) {
                user.setUserID(rs.getInt("userID"));
                user.setFullName(rs.getString("fullName"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRoleID(rs.getString("roleID"));
                user.setStatus(rs.getBoolean("status"));
            }
        } catch (Exception e) {
            System.out.println("Error output allUSer" + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return user;
    }

    public UserDTO getUserByPE(String phoneUser, String emailUser) {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();

            pr = conn.prepareStatement(GET_USER_BY_PHONE);
            pr.setString(1, phoneUser);
            pr.setString(2, emailUser);

            rs = pr.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUserID(rs.getInt("userID"));
                user.setFullName(rs.getString("fullName"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRoleID(rs.getString("roleID"));
                user.setStatus(rs.getBoolean("status"));
            }
        } catch (Exception e) {
            System.out.println("No user matches the condition. Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, rs);
        }
        return user;
    }

    public boolean login(UserDTO uDTO, String password) {
        if (uDTO != null) {
            if (uDTO.getPassword().equals(password)) {
                if (uDTO.isStatus()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean CreateUser(UserDTO user) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();

            pr = conn.prepareStatement(CREATE_USER);
            pr.setString(1, user.getFullName());
            pr.setString(2, user.getPhone());
            pr.setString(3, user.getEmail());
            pr.setString(4, user.getPassword());
            pr.setString(5, user.getRoleID());
            pr.setString(6, user.getGender());
            pr.setDate(7, new java.sql.Date(user.getBirthday().getTime()));
            pr.setString(8, user.getCity());
            pr.setBoolean(9, user.isStatus());

            int rowsAffected = pr.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.out.println("No user matches the condition. Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return success;
    }

    public boolean updatePassword(String password, String phone, String email) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();

            pr = conn.prepareStatement(UPDATE_USER);
            pr.setString(1, password);
            pr.setString(2, phone);
            pr.setString(3, email);

            int rowsAffected = pr.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.out.println("No user matches the condition. Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, pr, null);
        }
        return success;
    }

    public boolean updateProfile(int userID, String gender, Date birthday,String city) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement pr = null;
        try {
            conn = DbUtils.getConnection();

            pr = conn.prepareStatement(UPDATE_PROFILE);
            pr.setString(1, gender);
            pr.setDate(2, (java.sql.Date) birthday);
            pr.setString(3, city);
            pr.setInt(4, userID);

            int rowsAffected = pr.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.out.println("Error updatePassword: " + e.getMessage());
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
