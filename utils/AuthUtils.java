/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.RoomDTO;
import model.SearchDTO;
import model.UserDTO;

/**
 *
 * @author nzero
 */
public class AuthUtils {

    public static UserDTO getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            return (UserDTO) session.getAttribute("user");
        }
        return null;
    }

    public static boolean isLoggedIn(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    public static boolean hasRole(HttpServletRequest request, String role) {
        UserDTO user = getCurrentUser(request);
        if (user != null) {
            return user.getRoleID().equals(role);
        }
        return false;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        return hasRole(request, "admin");
    }

    public static boolean isUser(HttpServletRequest request) {
        return hasRole(request, "user");
    }

    public static String getLoginURL() {
        return "MainController?action=login";
    }

    public static String getAccessDeniedMessage(String action) {
        return "you can't access to this " + action + " .Please contact administrator.";
    }

    public static void sortByValue(List<RoomDTO> list) {
        Comparator<Object> tmp = new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                if (o1 instanceof RoomDTO && o2 instanceof RoomDTO) {
                    return Double.compare(((RoomDTO) o1).getPricePerNight(), ((RoomDTO) o2).getPricePerNight());
                }
                return 0;
            }
        };
        Collections.sort(list, tmp);
    }
}
