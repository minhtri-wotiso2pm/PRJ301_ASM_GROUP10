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
import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.UserDAO;
import model.UserDTO;
import org.json.JSONObject;
import utils.DbUtils;

/**
 *
 * @author nzero
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String WELCOME = "welcome.jsp";

    private boolean isUserValid(String action) {
        return action.equals("login")
                || action.equals("logout")
                || action.equals("register")
                || action.equals("updateProfile");
    }

    private boolean isHotelValid(String action) {
        return action.equals("addHotel");
    }

    private boolean isRoomValid(String action) {
        return action.equals("infoBooking");
    }

    private boolean isSearchValid(String action) {
        return action.equals("searchHotel")
                || action.equals("displayByHotelId")
                || action.equals("displayAllHotel");
    }

    private boolean isbookingValid(String action) {
        return action.equals("addBooking")
                || action.equals("applyDiscount")
                || action.equals("displayBooking")
                || action.equals("updateBookingRoom");
    }

    private boolean ispaymentValid(String action) {
        return action.equals("processPayment")
                || action.equals("backToDashboard")
                || action.equals("displayPayment")
                || action.equals("updatePaymentSatus");
    }

    private boolean isPlaneValid(String action) {
        return action.equals("displayAllPlane")
                || action.equals("searchPlane")
                || action.equals("searchPlaneft")
                || action.equals("filterByAirline")
                || action.equals("displayDetailPlaneBooking")
                || action.equals("createPlane")
                || action.equals("returnPlaneMenu");
    }

    private boolean isPlaneBookingValid(String action) {
        return action.equals("reviewBooking")
                || action.equals("confirmPlaneBooking")
                || action.equals("submitPlaneBooking")
                || action.equals("displayPlaneBooking");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";
        try {
            String action = request.getParameter("action");

            if (action == null || action.isEmpty()) {
                url = "SearchController?action=setPriceHotel";
                System.out.println("hi");
            } else if (isUserValid(action)) {
                url = "/UserController";
            } else if (isHotelValid(action)) {
                url = "/HotelController";
            } else if (isSearchValid(action)) {
                url = "/SearchController";
            } else if (isRoomValid(action)) {
                url = "/RoomController";
            } else if (isbookingValid(action)) {
                url = "/BookingController";
            } else if (ispaymentValid(action)) {
                url = "/PaymentController";
            } else if (isPlaneValid(action)) {
                url = "/PlaneController";
            } else if (isPlaneBookingValid(action)) {
                url = "/PlaneBookingController";
            } else {
                url = WELCOME;
            }
        } catch (Exception e) {
            e.printStackTrace(); // để biết lỗi gì đang xảy ra
            url = WELCOME; // fallback nếu có lỗi
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

        String contentType = request.getContentType();

        if (contentType != null && contentType.contains("application/json")) {
            // Đây là webhook SePay gửi về
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            System.out.println("Webhook JSON = [" + json + "]");

            try {
                JSONObject obj = new JSONObject(json);

                String code = obj.getString("code"); // ví dụ: "BK122User1"
                int amount = obj.getInt("transferAmount");
                String paymentDateStr = obj.getString("transactionDate");
                java.sql.Date paymentDate = java.sql.Date.valueOf(paymentDateStr.split(" ")[0]);
                String paymentMethod = obj.getString("gateway");
                String message = obj.getString("referenceCode");
                boolean status = true;

                int bookingID = Integer.parseInt(code.substring(2, code.indexOf("User")));
                int userID = Integer.parseInt(code.substring(code.indexOf("User") + 4));

                System.out.println("Webhook code = " + code);
                System.out.println("=> bookingID = " + bookingID + ", userID = " + userID);

                Connection conn = DbUtils.getConnection();

                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO tblPayments (bookingID, userID, amountPaid, paymentDate, paymentMethod, paymentStatus, ref_code) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
                ps.setInt(1, bookingID);
                ps.setInt(2, userID);
                ps.setInt(3, amount);
                ps.setDate(4, paymentDate);
                ps.setString(5, paymentMethod);
                ps.setBoolean(6, status);
                ps.setString(7, message);
                ps.executeUpdate();
                conn.close();

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("OK");
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("FAIL: " + e.getMessage());
            }
        } else {
            // Khi không phải là JSON (ví dụ từ form), thì gọi lại processRequest như cũ
            processRequest(request, response);
        }
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

}
