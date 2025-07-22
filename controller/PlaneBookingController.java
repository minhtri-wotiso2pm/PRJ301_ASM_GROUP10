/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import model.PlaneServicePackageDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.PlaneBookingDAO;
import model.PlaneDAO;
import model.PlanePriceDAO;
import model.PlaneServicePackageDAO;
import model.PlaneBookingDTO;
import model.PlaneDTO;
import model.PlanePriceDTO;
import model.PlaneServicePackageDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "PlaneBookingController", urlPatterns = {"/PlaneBookingController"})
public class PlaneBookingController extends HttpServlet {

    PlaneBookingDAO dao = new PlaneBookingDAO();
    PlaneDAO planedao = new PlaneDAO();
    PlanePriceDAO pricedao = new PlanePriceDAO();
    PlaneServicePackageDAO serviceDAO = new PlaneServicePackageDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";

        try {
            String action = request.getParameter("action");
            if (action != null) {
                switch (action) {
                    case "displayDetailPlaneBooking":
                        url = handleViewDetailPlaneBooking(request);
                        break;
                    case "reviewBooking":
                        url = handlePlaneBookingReview(request);
                        break;
                    case "confirmPlaneBooking":
                        url = handleConfirmPlaneBooking(request);
                        break;
//                    case "submitPlaneBooking":
//                        url = handleSubmitPlaneBooking(request);
//                        break;
                    case "displayPlaneBooking":
                        url = handledisplayPlaneBooking(request);
                        break;
                        case "displayAllBookingByPlaneID":
                        url = handledisplayAllBookingByPlaneID(request);
                        break;
                    default:
                        request.setAttribute("error", "Unknown action: " + action);
                        url = "plane.jsp";
                        break;
                }

            }
        } catch (Exception e) {
            request.setAttribute("error", "Unexpected error occurred.");
            url = "plane.jsp";
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

    private String handleViewDetailPlaneBooking(HttpServletRequest request) {
        String flightId = request.getParameter("flightId");
        PlaneDTO plane = planedao.getPlaneById(flightId);
        request.setAttribute("planeDetail", plane);
        return "planeDetail.jsp";
    }

//    private String handlePlaneBookingReview(HttpServletRequest request) {
//        String flightId = request.getParameter("flightId");
//        PlaneDTO plane = planedao.getPlaneById(flightId);
//        PlanePriceDTO priceInfo = pricedao.getPlanePriceByFlightId(flightId);
//        plane.setPriceInfo(priceInfo);
//        request.setAttribute("planeDetail", plane);
//        List<PlaneServicePackageDTO> packages = serviceDAO.getAllPackages();
//        request.setAttribute("packages", packages);
//        
//        return "planeBookingReview.jsp";
//    }
    private String handlePlaneBookingReview(HttpServletRequest request) {
        String flightId = request.getParameter("flightId");
        String packageIdRaw = request.getParameter("packageId");

        // Lấy thông tin chuyến bay
        PlaneDTO plane = planedao.getPlaneById(flightId);
        PlanePriceDTO priceInfo = pricedao.getPlanePriceByFlightId(flightId);
        plane.setPriceInfo(priceInfo);
        request.setAttribute("planeDetail", plane);

        // Lấy danh sách các gói dịch vụ
        List<PlaneServicePackageDTO> packages = serviceDAO.getAllPackages();
        request.setAttribute("packages", packages);

        // Nếu người dùng đã chọn gói dịch vụ thì tính tổng
        if (packageIdRaw != null && !packageIdRaw.isEmpty()) {
            int packageId = Integer.parseInt(packageIdRaw);
            PlaneServicePackageDTO selectedPackage = serviceDAO.getPackageById(packageId);
            request.setAttribute("selectedPackage", selectedPackage);

            float dynamicPrice = (float) plane.getPriceInfo().getDynamicPrice();
            float extraPrice = selectedPackage.getExtraPrice();
            float total = dynamicPrice + extraPrice;
            request.setAttribute("total", total);
        }

        return "planeBookingReview.jsp";
    }

    private String handleConfirmPlaneBooking(HttpServletRequest request) {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int flightId = Integer.parseInt(request.getParameter("flightId"));
            int packageId = Integer.parseInt(request.getParameter("packageId"));
            float price = Float.parseFloat(request.getParameter("price"));     // giá vé cơ bản hoặc dynamic

            String status = "Pending";  // hoặc "Confirmed", tùy bạn
            PlaneServicePackageDAO packageDAO = new PlaneServicePackageDAO();
            float extraPrice = packageDAO.getPackageById(packageId).getExtraPrice();
            float total = price + extraPrice;
            PlaneBookingDAO bookingDAO = new PlaneBookingDAO();
            bookingDAO.createBooking(userId, flightId, packageId, price, total, status);

            request.setAttribute("message", "Đặt vé thành công!");
            return "planeBookingSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi khi xác nhận đặt vé.");
            return "error.jsp";
        }
    }
//    private String handleConfirmPlaneBooking(HttpServletRequest request) {
//        String flightId = request.getParameter("flightId");
//        String packageId = request.getParameter("packageId");
//        int packageID_int = Integer.getInteger(packageId);
//
//        PlaneDAO planeDAO = new PlaneDAO();
//        PlaneDTO plane = planeDAO.getPlaneById(flightId);
//
//        PlaneServicePackageDAO pkgDAO = new PlaneServicePackageDAO();
//        PlaneServicePackageDTO selectedPackage = pkgDAO.getPackageById(packageID_int);
//
//        request.setAttribute("planeDetail", plane);
//        request.setAttribute("selectedPackage", selectedPackage);
//
//        return "planeBooking.jsp"; // Forward ở MainController
//    }
//
//    private String handleSubmitPlaneBooking(HttpServletRequest request) {
//        String userId = request.getParameter("userId");
//        int flightId = Integer.getInteger(request.getParameter("flightId"));
//        int packageId = Integer.getInteger(request.getParameter("packageId"));
//        int totalPrice = Integer.parseInt(request.getParameter("totalPrice"));
//
//        PlaneServicePackageDAO pkgDAO = new PlaneServicePackageDAO();
//        PlaneServicePackageDTO selectedPackage = pkgDAO.getPackageById(packageId);
//        PlaneBookingDAO bookingDAO = new PlaneBookingDAO();
//        
//        double price = totalPrice - selectedPackage.getExtraPrice();
//        PlaneBookingDTO bookingDTO = new PlaneBookingDTO(userId, flightId, price, totalPrice, userId);
//        boolean success = bookingDAO.create(bookingDTO);
//
//        if (success) {
//            request.setAttribute("message", "Đặt vé thành công!");
//            return "planeBookingSuccess.jsp";
//        } else {
//            request.setAttribute("error", "Có lỗi xảy ra khi đặt vé.");
//            return "planeBooking.jsp";
//        }
//    }

    private String handledisplayPlaneBooking(HttpServletRequest request) {
        String userID = request.getParameter("userID");
        int valid_userID = 0;
        try {
            valid_userID = Integer.parseInt(userID);
            List<PlaneBookingDTO> bookList = dao.getBookingsByUserId(valid_userID);
            if (bookList != null && !bookList.isEmpty()) {
                request.setAttribute("bookList", bookList);
            } else {
                request.setAttribute("message", "Hiện tại chưa có đặt vé nào");
            }
        } catch (Exception e) {
        }
        request.setAttribute("option", "bookPlaneAction");
        return "menuOption.jsp";
    }
    private String handledisplayAllBookingByPlaneID(HttpServletRequest request) {
        String userID = request.getParameter("flightId");
        int valid_userID = 0;
        try {
            valid_userID = Integer.parseInt(userID);
            List<PlaneBookingDTO> bookList = dao.getBookingsByPlaneId(valid_userID);
            if (bookList != null && !bookList.isEmpty()) {
                request.setAttribute("bookList", bookList);
            } else {
                request.setAttribute("message", "Hiện tại chưa có đặt vé nào");
            }
        } catch (Exception e) {
        }
        request.setAttribute("option", "bookPlaneAction");
        return "menuOption_1.jsp";
    }
}