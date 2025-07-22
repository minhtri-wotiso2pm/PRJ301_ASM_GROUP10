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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.HotelDAO;
import model.HotelDTO;
import utils.AuthUtils;

/**
 *
 * @author nzero
 */
@WebServlet(name = "HotelController", urlPatterns = {"/HotelController"})
public class HotelController extends HttpServlet {

    HotelDAO hDAO = new HotelDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = "";
        try {
            if (action.equals("addHotel")) {
                url = handleAddHotel(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private String handleAddHotel(HttpServletRequest request, HttpServletResponse response) {
        String check1 = "";
        String check2 = "";
        String check3 = "";
        String check4 = "";
        String message = "";
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String mainImageUrl = request.getParameter("mainImageUrl");
        String description = request.getParameter("description");
        String rate = request.getParameter("rate");
        String price = request.getParameter("price");
        String type = request.getParameter("type");
        String status = request.getParameter("status");

        if (name.length() < 2) {
            check1 = "Your name must be longer than one character.";
        }
        if (name.matches(".*\\d.*")) {
            check1 += "<br/>Your name must not contain any numbers.";
        }
        if (name.matches(".*[^a-zA-Z ].*")) {
            check1 += "<br/>Your name must not contain any special characters.";
        }

        if (address.length() < 10) {
            check2 = "Your name must be longer than ten character.";
        }
        if (address.matches(".*[^a-zA-Z0-9 /.,-].*")) {
            check2 += "<br/>Your name must not contain special characters except '/ , . -'";
        }

        double valid_rate = 0;
        try {
            valid_rate = Double.parseDouble(rate);
        } catch (Exception e) {
        }

        if (valid_rate < 0) {
            check3 = "rate must greather than zero";
        }
        if (valid_rate > 10) {
            check3 += "<br/>rate must smaller than ten";
        }

        double valid_price = 0;
        try {
            valid_price = Double.parseDouble(price);
        } catch (Exception e) {
        }

        if (valid_price < 0) {
            check4 = "rate must greather than zero";
        }

        Boolean valid_status = true;
        try {
            valid_status = Boolean.parseBoolean(status);
        } catch (Exception e) {
        }

        if (check1.isEmpty() && check2.isEmpty() && check3.isEmpty() && check4.isEmpty()) {
            HotelDTO hotel = new HotelDTO(name, address, city, mainImageUrl, description, valid_rate, valid_price, type, valid_status);
            if (hDAO.crateHotel(hotel)) {
                message = "Add hotel successfully!";
            }
            request.setAttribute("message", message);
            request.setAttribute("hotel", hotel);
        }
        request.setAttribute("check1", check1);
        request.setAttribute("check2", check2);
        request.setAttribute("check3", check3);
        request.setAttribute("check4", check4);
        return "hotelForm.jsp";
    }
}
