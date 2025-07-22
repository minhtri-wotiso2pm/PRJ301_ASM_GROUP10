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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.BookingDAO;
import model.BookingDTO;
import model.PaymentDAO;
import model.PaymentDTO;
import model.RoomDAO;
import model.RoomDTO;
import model.UserDTO;
import utils.EmailUtils;

/**
 *
 * @author nzero
 */
@WebServlet(name = "PaymentController", urlPatterns = {"/PaymentController"})
public class PaymentController extends HttpServlet {

    PaymentDAO pDAO = new PaymentDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = "";
        try {
            if (action.equals("processPayment")) {
                url = handleProcessPayment(request, response);
            } else if (action.equals("backToDashboard")) {
                url = handleBackToDashboard(request, response);
            } else if (action.equals("displayPayment")) {
                url = handleDisplayPayment(request, response);
            } else if (action.equals("updatePaymentSatus")) {
                url = handleUpdatePaymentSatus(request, response);
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

    private String handleProcessPayment(HttpServletRequest request, HttpServletResponse response) {
        String bookingID = request.getParameter("bookingID");
        String userID = request.getParameter("userID");
        String roomID = request.getParameter("roomID");
        String totalPrice = request.getParameter("totalPrice");
        String paymentMethod = request.getParameter("paymentMethod");
        System.out.println("amountPaid" + totalPrice);

        int valid_booking_id = 0;
        try {
            valid_booking_id = Integer.parseInt(bookingID);
        } catch (Exception e) {
        }

        int valid_user_id = 0;
        try {
            valid_user_id = Integer.parseInt(userID);
        } catch (Exception e) {
        }

        int valid_amountPaid = 0;
        try {
            valid_amountPaid = Integer.parseInt(totalPrice);
        } catch (Exception e) {
        }

        String qrPath = "";
        switch (paymentMethod) {
            case "VPBank":
                qrPath = "https://qr.sepay.vn/img?acc=0378263237&bank=VPBANK&amount="
                        + valid_amountPaid
                        + "&des=BK" + bookingID + "User" + userID;
                break;
            case "VietQR":
                qrPath = "https://img.vietqr.io/image/TPBank-05617899801-compact2.png?amount=" + valid_amountPaid + "&addInfo=BK" + bookingID + "User" + userID + "&accountName=Le%20Ngoc%20Quoc%20Anh";
                break;
        }

        PaymentDTO payment = new PaymentDTO(valid_booking_id, valid_user_id, valid_amountPaid, new Date(), paymentMethod, false, "BK" + bookingID + "User" + userID);
        pDAO.createPayment(payment);

        request.setAttribute("payment", payment);
        request.setAttribute("qrPath", qrPath);
        request.setAttribute("bookingID", bookingID);
        request.setAttribute("userID", userID);
        request.setAttribute("roomID", roomID);
        request.setAttribute("totalPrice", valid_amountPaid);
        request.setAttribute("selectedMethod", paymentMethod);
        return "paymentRole.jsp";
    }

    private String handleBackToDashboard(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("selectedMethod", request.getParameter("selectedMethod"));
        request.setAttribute("roomID", request.getParameter("roomID"));
        request.setAttribute("userID", request.getParameter("userID"));
        request.setAttribute("bookingID", request.getParameter("bookingID"));
        request.setAttribute("totalPrice", request.getParameter("totalPrice"));
        System.out.println("selectedMethod" + request.getParameter("selectedMethod"));
        return "paymentDashboard.jsp";
    }

    private String handleDisplayPayment(HttpServletRequest request, HttpServletResponse response) {
        String userID = request.getParameter("userID");
        int valid_user_id = 0;
        try {
            valid_user_id = Integer.parseInt(userID);
            List<PaymentDTO> paymentList = pDAO.getPaymentsByStatus(valid_user_id, true);
            if (paymentList != null && !paymentList.isEmpty()) {
                request.setAttribute("paymentList", paymentList);
            } else {
                request.setAttribute("massage", "don't have any payment");
            }
        } catch (Exception e) {
        }
        request.setAttribute("option", "paymentAction");
        return "menuOption.jsp";
    }

    private String handleCheckStatusPayment(HttpServletRequest request, HttpServletResponse response) {
        String userID = request.getParameter("userID");
        int valid_user_id = 0;
        try {
            valid_user_id = Integer.parseInt(userID);
            List<PaymentDTO> paymentList = pDAO.getPaymentsByStatus(valid_user_id, false);
            if (paymentList != null && !paymentList.isEmpty()) {
                request.setAttribute("paymentList", paymentList);
            } else {
                request.setAttribute("massage", "don't have any payment");
            }
        } catch (Exception e) {
        }
        request.setAttribute("option", "checkPaymentAction");
        return "menuOption.jsp";
    }

    private String handleUpdatePaymentSatus(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String id = request.getParameter("id");
        UserDTO user = (UserDTO) session.getAttribute("user");
        String checkin = (String) session.getAttribute("checkin");
        String checkout = (String) session.getAttribute("checkout");
        int valid_user_id = 0;
        try {
            valid_user_id = Integer.parseInt(id);
            pDAO.updatePayment(true, valid_user_id);
            request.setAttribute("userID", user.getUserID());
            PaymentDTO payment = pDAO.getPaymentById(valid_user_id);
            String amountStr = String.valueOf(payment.getAmountPaid());
            String bookingID = String.valueOf(payment.getBookingID());
            EmailUtils.sendTransactionEmail("Email người dùng: " + user.getEmail(), "ID booking: " + bookingID, "Tên người dùng" + user.getFullName(), "SDT: " + user.getPhone(), "Tổng số tiền: " + amountStr, "id thanh toán: " + id, "Ngày dặt khách sạn: " + checkin, "Ngày trả khách sạn: " + checkout);
        } catch (Exception e) {
        }
        return handleDisplayPayment(request, response);
    }
}
