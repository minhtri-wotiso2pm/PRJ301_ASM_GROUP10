/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.UserDAO;
import model.UserDTO;
import utils.AuthUtils;
import utils.PassWordUtils;

/**
 *
 * @author nzero
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final String LOGIN = "login.jsp";
    private static final String WELCOME = "welcome.jsp";

    UserDAO uDAO = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN;
        try {
            String action = request.getParameter("action");
            if (action.equals("login")) {
                url = handleLogin(request, response);
            } else if (action.equals("logout")) {
                url = handleLogout(request, response);
            } else if (action.equals("register")) {
                url = handleRegister(request, response);
            } else if (action.equals("updateProfile")) {
                url = handleUpdateProfile(request, response);
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleLogin(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN;
        HttpSession session = request.getSession();
        String userPE = request.getParameter("userPE");
        String userID = null;
        String email = null;
        String phone = null;
        if (userPE.matches("\\d+")) {
            phone = userPE;
            userID = phone;
        } else {
            email = userPE;
            userID = email;
        }
        String password = request.getParameter("password");
        request.setAttribute("userID", userID);
        try {
            UserDTO user = uDAO.getUserByPE(phone, email);
            if (user != null) {
                request.setAttribute("isRegistedUser", true);
                String encryp_password = PassWordUtils.encrypeSHA256(password);
                if (uDAO.login(user, encryp_password)) {
                    url = WELCOME;
                    session.setAttribute("user", user);
                } else {
                    request.setAttribute("password", "userID or password is incorrect");
                }
            } else {
                request.setAttribute("messageUser", "Your acccount didn't exist yet!!");
                request.setAttribute("isRegistedUser", false);
                return url;
            }
        } catch (Exception e) {
            request.setAttribute("message", "error addProject: " + e.getMessage());
            e.printStackTrace();
        }
        return url;
    }

    private String handleLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            UserDTO user = (UserDTO) session.getAttribute("user");
            if (user != null) {
                session.invalidate();
            }
        }
        return "SearchController?action=setPriceHotel";
    }

    private String handleRegister(HttpServletRequest request, HttpServletResponse response) {
        String check1 = "";
        String check2 = "";
        String check3 = "";
        String check4 = "";
        String message = "";
        String name = request.getParameter("name").trim();
        String phone = request.getParameter("phone").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        String encrype_password = PassWordUtils.encrypeSHA256(password);

        if (name.length() < 2) {
            check1 = "Your name must be longer than one character.";
        }
        if (name.matches(".*\\d.*")) {
            check1 += "<br/>Your name must not contain any numbers.";
        }
        if (name.matches(".*[^a-zA-Z ].*")) {
            check1 += "<br/>Your name must not contain any special characters.";
        }

        if (!phone.startsWith("0")) {
            check2 = "Your phone number must begin with zero.<br/>";
        }
        if (phone.length() != 10) {
            check2 += "Your phone number must be exactly 10 digits.<br/>";
        }
        if (!phone.matches("\\d{10}")) {
            check2 += "Your phone number must contain only digits.<br/>";
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            check3 = "Your email is invalid.";
        }

        if (password.length() < 8) {
            check4 = "Password must be at least 8 characters long.";
        }
        if (!password.matches(".*[A-Z].*")) {
            check4 += "<br/>Password must contain at least one uppercase letter.";
        }
        if (!password.matches(".*[a-z].*")) {
            check4 += "<br/>Password must contain at least one lowercase letter.";
        }
        if (!password.matches(".*\\d.*")) {
            check4 += "<br/>Password must contain at least one number.";
        }
        if (!password.matches(".*[@#$%^&+=!].*")) {
            check4 += "<br/>Password must contain at least one special character (e.g. @, #, $, %, etc).";
        }
        if (password.matches(".*\\s.*")) {
            check4 += "<br/>Password must not contain any whitespace.";
        }

        if (check1.isEmpty() && check2.isEmpty() && check3.isEmpty() && check4.isEmpty()) {
            message = "Register successfully!!";
        }

        UserDTO user = new UserDTO(name, phone, email, encrype_password, "MB", null, null, null, true);
        uDAO.CreateUser(user);
        request.setAttribute("isRegistedUser", "true");

        request.setAttribute("check1", check1);
        request.setAttribute("check2", check2);
        request.setAttribute("check3", check3);
        request.setAttribute("check4", check4);
        request.setAttribute("message", message);
        return LOGIN;
    }

    private String handleUpdateProfile(HttpServletRequest request, HttpServletResponse response) {
        String check1 = "";
        String check2 = "";
        String message = "";
        String userID = request.getParameter("userID");
        String fullname = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String daySelect = request.getParameter("daySelect");
        String monthSelect = request.getParameter("monthSelect");
        String yearSelect = request.getParameter("yearSelect");
        String city = request.getParameter("city");

        int valid_user_id = 0;
        try {
            valid_user_id = Integer.parseInt(userID);
        } catch (Exception e) {
        }

        if (fullname.length() < 2) {
            check1 = "Your name must be longer than one character.";
        }
        if (fullname.matches(".*\\d.*")) {
            check1 += "<br/>Your name must not contain any numbers.";
        }
        if (fullname.matches(".*[^a-zA-Z ].*")) {
            check1 += "<br/>Your name must not contain any special characters.";
        }

        if (city.length() < 3) {
            check2 = "Your name must be longer than one character.";
        }
        if (city.length() > 100) {
            check2 += "<br/>Thành phố không vượt quá 100 chữ";
        }

        String birthday = yearSelect + "-" + monthSelect + "-" + daySelect;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date valid_date = null;
        try {
            valid_date = sf.parse(birthday.trim());
        } catch (Exception e) {
        }

        if (check1 == null || check2 == null) {
            if (uDAO.updateProfile(valid_user_id, gender, valid_date, city)) {
                message = "Update profile successfully";
            }
        }
        request.setAttribute("check1", check1);
        request.setAttribute("check2", check2);
        request.setAttribute("message", message);
        request.setAttribute("userID", userID);
        request.setAttribute("fullname", fullname);
        request.setAttribute("gender", gender);
        request.setAttribute("daySelect", daySelect);
        request.setAttribute("monthSelect", monthSelect);
        request.setAttribute("yearSelect", yearSelect);
        request.setAttribute("city", city);
        request.setAttribute("option", "userAction");
        return "menuOption.jsp";
    }
}
