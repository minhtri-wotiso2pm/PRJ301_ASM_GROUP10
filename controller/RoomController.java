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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import model.RoomDAO;
import model.RoomDTO;

/**
 *
 * @author nzero
 */
@WebServlet(name = "RoomController", urlPatterns = {"/RoomController"})
public class RoomController extends HttpServlet {

    RoomDAO rDAO = new RoomDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = "";
        try {
            if (action.equals("infoBooking")) {
                url = handleInfoBooking(request, response);
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

    private String handleInfoBooking(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String roomID = request.getParameter("roomID");
        String hotelID = request.getParameter("hotelID");
        String name = request.getParameter("name");
        String city = request.getParameter("city");
        String mainImageUrl = request.getParameter("mainImageUrl");
        String count = request.getParameter("count");
        String average = request.getParameter("average");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        System.out.println("roomID"+roomID);
        System.out.println("hotelID"+hotelID);
        System.out.println("name"+name);
        System.out.println("city"+city);
        System.out.println("mainImageUrl"+mainImageUrl);
        System.out.println("count"+count);
        System.out.println("average"+average);
        System.out.println("checkin"+checkin);
        System.out.println("checkout"+checkout);
        LocalDate now = LocalDate.now();
        int valid_room_id = 0;
        try {
            double taxRate = 0.08;
            valid_room_id = Integer.parseInt(roomID);
            RoomDTO room = rDAO.getRoomsByID(valid_room_id);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String checkinFormatted = null;
            String checkoutFormatted = null;
            LocalDate startDate = null;
            LocalDate endDate = null;
            if (checkin != null && !checkin.trim().isEmpty()) {
                startDate = LocalDate.parse(checkin, formatter); // thêm formatter
                checkinFormatted = startDate.format(formatter);
            } else {
                startDate = now;
                checkinFormatted = startDate.format(formatter);
                session.setAttribute("checkin", checkinFormatted);
            }

            if (checkout != null && !checkout.trim().isEmpty()) {
                endDate = LocalDate.parse(checkout, formatter); // thêm formatter
                checkoutFormatted = endDate.format(formatter);
            } else {
                endDate = now.plusDays(1);
                checkoutFormatted = endDate.format(formatter);
                session.setAttribute("checkout", checkoutFormatted);
            }

            long dayBetween = ChronoUnit.DAYS.between(startDate, endDate);
            int total_fee = (int) (dayBetween * room.getPricePerNight());
            double tax = taxRate * total_fee;
            int final_price = (int) (total_fee + tax);
            request.setAttribute("room", room);
            request.setAttribute("dayBetween", dayBetween);
            request.setAttribute("total_fee", total_fee);
            request.setAttribute("tax", tax);
            request.setAttribute("final_price", final_price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("hotelID", hotelID);
        request.setAttribute("name", name);
        request.setAttribute("city", city);
        request.setAttribute("mainImageUrl", mainImageUrl);
        request.setAttribute("count", count);
        request.setAttribute("average", average);
        return "bookDashboard.jsp";
    }

}
