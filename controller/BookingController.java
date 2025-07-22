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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.BookingDAO;
import model.BookingDTO;
import model.BookingDiscountDAO;
import model.BookingDiscountDTO;
import model.DiscountDAO;
import model.DiscountDTO;
import model.PaymentDAO;
import model.PaymentDTO;
import model.RoomDAO;
import model.RoomDTO;
import model.UserDiscountUsageDAO;
import model.UserDiscountUsageDTO;

/**
 *
 * @author nzero
 */
@WebServlet(name = "BookingController", urlPatterns = {"/BookingController"})
public class BookingController extends HttpServlet {

    BookingDAO bDAO = new BookingDAO();
    DiscountDAO dDAO = new DiscountDAO();
    UserDiscountUsageDAO uduDAO = new UserDiscountUsageDAO();
    BookingDiscountDAO bkDAO = new BookingDiscountDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";
        String action = request.getParameter("action");
        System.out.println("action" + action);
        try {
            if (action.equals("addBooking")) {
                url = handleAddBooking(request, response);
            } else if (action.equals("applyDiscount")) {
                url = handleApplyDiscount(request, response);
            } else if (action.equals("displayBooking")) {
                url = handleDisplayBooking(request, response);
            } else if (action.equals("updateBookingRoom")) {
                url = handleUpdateBookingRoom(request, response);
            }
        } catch (Exception e) {
        }
        request.getRequestDispatcher(url).forward(request, response);
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

    private String handleAddBooking(HttpServletRequest request, HttpServletResponse response) {
        String userID = request.getParameter("userID");
        String roomID = request.getParameter("roomID");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String final_price = request.getParameter("final_price");
        System.out.println("userID" + userID);
        System.out.println("roomID" + roomID);
        System.out.println("checkin" + checkin);
        System.out.println("checkout" + checkout);
        System.out.println("final_price" + final_price);
        int valid_user_id = 0;
        try {
            valid_user_id = Integer.parseInt(userID);
        } catch (Exception e) {
        }

        int valid_room_id = 0;
        try {
            valid_room_id = Integer.parseInt(roomID);
        } catch (Exception e) {
        }

        int valid_final_price = 0;
        try {
            valid_final_price = Integer.parseInt(final_price);
        } catch (Exception e) {
        }

        Date valid_checkIn = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            valid_checkIn = sdf.parse(checkin);
        } catch (Exception e) {
        }

        Date valid_checkOut = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            valid_checkOut = sdf.parse(checkout);
        } catch (Exception e) {
        }

        System.out.println("valid_checkIn = " + valid_checkIn);
        System.out.println("valid_checkOut = " + valid_checkOut);

        BookingDTO book = new BookingDTO(valid_user_id, valid_room_id, valid_checkIn, valid_checkOut, valid_final_price, true, false);
        if (bDAO.createBooking(book)) {
            request.setAttribute("book", book);
        }
        request.setAttribute("roomID", roomID);
        return "paymentDashboard.jsp";
    }

    private String handleApplyDiscount(HttpServletRequest request, HttpServletResponse response) {
        String check1 = "";
        String bookingID = request.getParameter("bookingID");
        String userID = request.getParameter("userID");
        String discountCode = request.getParameter("discountCode");

        try {
            int valid_book_id = Integer.parseInt(bookingID);
            int valid_user_id = Integer.parseInt(userID);

            BookingDTO book = bDAO.getBooking(valid_book_id);
            DiscountDTO discount = dDAO.getDiscountByCode(discountCode);
            UserDiscountUsageDTO user = uduDAO.getUsageCount(valid_user_id, discountCode);
            BookingDiscountDTO bookingDiscount = bkDAO.getDiscountByBookingId(valid_book_id);

            int price = book.getTotalPrice();

            boolean alreadyUsed = (bookingDiscount != null
                    && bookingDiscount.getDiscountCode().equalsIgnoreCase(discountCode));

            if (!alreadyUsed) {
                if (user.getUsageCount() < discount.getUsageLimit()) {
                    if (price >= discount.getMinBookingAmount()) {
                        if (discount.getDiscountPercent() > 0) {
                            price = (int) (price * (1 - discount.getDiscountPercent() / 100.0));
                        } else if (discount.getMaxDiscount() > 0) {
                            price = price - discount.getMaxDiscount();
                        }
                        // Cập nhật usage + liên kết booking-discount
                        uduDAO.getUpdateUsageCount(user.getUsageCount() + 1, valid_user_id, discountCode);
                        bkDAO.createBookingDiscount(valid_book_id, discountCode);
                    } else {
                        check1 = "Không đạt mức tối thiểu để áp dụng mã giảm giá";
                    }
                } else {
                    check1 = "Mã giảm giá '" + discountCode + "' đã hết lượt sử dụng";
                }
            } else {
                check1 = "Mã giảm giá đã được áp dụng cho booking này trước đó";
            }

            bDAO.updateBooking(price, valid_book_id);
            book = bDAO.getBooking(valid_book_id);
            request.setAttribute("book", book);

        } catch (Exception e) {
            check1 = "Lỗi trong quá trình xử lý giảm giá: " + e.getMessage();
        }

        request.setAttribute("check1", check1);
        request.setAttribute("discountCode", discountCode);

        return "paymentDashboard.jsp";
    }

    private String handleDisplayBooking(HttpServletRequest request, HttpServletResponse response) {
        String userID = request.getParameter("userID");
        int valid_userID = 0;
        try {
            valid_userID = Integer.parseInt(userID);
            List<BookingDTO> bookList = bDAO.getBookingByUserId(valid_userID);
            if (bookList != null && !bookList.isEmpty()) {
                request.setAttribute("bookList", bookList);
            } else {
                request.setAttribute("message", "Hiện tại chưa có đặt chỗ nào");
            }
        } catch (Exception e) {
        }
        request.setAttribute("option", "bookAction");
        return "menuOption.jsp";
    }

    private String handleUpdateBookingRoom(HttpServletRequest request, HttpServletResponse response) {
        String bookingID = request.getParameter("bookingID");
        String paymentID = request.getParameter("paymentID");
        System.out.println("me may: "+paymentID);
        String selectedMethod = request.getParameter("selectedMethod");
        String roomID = request.getParameter("roomID");
        String userID = request.getParameter("userID");
        String totalPrice = request.getParameter("totalPrice");
        int valid_payment_id = 0;
        try {
            valid_payment_id = Integer.parseInt(paymentID);
            PaymentDTO payment = PaymentDAO.getPaymentById(valid_payment_id);
            if (payment.isPaymentStatus()) {
                int valid_book_id = 0;
                try {
                    valid_book_id = Integer.parseInt(bookingID);
                    BookingDTO book = bDAO.getBooking(valid_book_id);
                    bDAO.updateStatusBooking(true, valid_book_id);
                    RoomDTO room = RoomDAO.getRoomsByID(book.getRoomID());
                    RoomDAO.updateQuantityRoom(room.getQuantity() - 1, room.getRoomID());
                    request.setAttribute("message", "Thanh toán thành công");
                } catch (Exception e) {
                }
            } else {
                request.setAttribute("message", "Thanh toán thất bại");
            }
        } catch (Exception e) {
        }
        request.setAttribute("bookingID", bookingID);
        request.setAttribute("selectedMethod", selectedMethod);
        request.setAttribute("roomID", roomID);
        request.setAttribute("totalPrice", totalPrice);
        return "paymentRole.jsp";
    }
}
