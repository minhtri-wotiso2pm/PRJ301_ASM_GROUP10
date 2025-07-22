/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import model.UserDAO;
import model.UserDTO;

/**
 *
 * @author nzero
 */
public class PassWordUtils {

    public static String encrypeSHA256(String password) {
        if (password.isEmpty() || password == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not available: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error during SHA-256 encryption: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        UserDAO uDAO = new UserDAO();
        List<UserDTO> list = uDAO.allUser();
        if (list != null) {
            for (UserDTO u : list) {
                uDAO.updatePassword(encrypeSHA256(u.getPassword()),u.getPhone(),u.getEmail());
            }
        }

    }
}
