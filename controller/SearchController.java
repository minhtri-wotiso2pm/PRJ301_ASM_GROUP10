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
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.HotelDAO;
import model.HotelDTO;
import model.ReviewsDAO;
import model.ReviewsDTO;
import model.RoomDTO;
import model.SearchDAO;
import model.SearchDTO;
import model.ServiceDTO;
import model.UserDAO;
import model.UserDTO;
import utils.AuthUtils;

/**
 *
 * @author nzero
 */
@WebServlet(name = "SearchController", urlPatterns = {"/SearchController"})
public class SearchController extends HttpServlet {

    HotelDAO hDAO = new HotelDAO();
    SearchDAO sDAO = new SearchDAO();
    ReviewsDAO rDAO = new ReviewsDAO();
    UserDAO uDAO = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // THÊM DÒNG NÀY
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String url = "welcome.jsp";
        try {
            if (action.equals("searchHotel")) {
                url = handleSearchHotel(request, response);
            } else if (action.equals("displayByHotelId")) {
                url = handleDisplayByHotelId(request, response);
            } else if (action.equals("displayAllHotel")) {
                url = handleDisplayAllHotel(request, response);
            } else if (action.equals("setPriceHotel")) {
                url = handleSetPriceHotel(request, response);
            } else if (action.equals("filterSearch")) {
                url = handleFilterSearch(request, response);
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

    private String handleSearchHotel(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String accommodationType = request.getParameter("accommodationType");
        System.out.println("accommodationType Test: "+accommodationType);
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String keyword = request.getParameter("keyword");
        String adults = request.getParameter("adults");
        String children = request.getParameter("children");
        String rooms = request.getParameter("rooms");
        int count = 0;

        int valid_adult = 0;
        try {
            valid_adult = Integer.parseInt(adults);
        } catch (Exception e) {
        }

        int valid_room = 0;
        try {
            valid_room = Integer.parseInt(rooms);
        } catch (Exception e) {
        }
        List<SearchDTO> searchList = sDAO.searchHotelWithDetail(accommodationType, keyword, valid_adult, valid_room);
        for (SearchDTO s : searchList) {
            count++;
        }
        if (searchList != null) {
            System.out.println("searchList mm:" + searchList);
            if (checkin != null && !checkin.trim().isEmpty()) {
                session.setAttribute("checkin", checkin);
            }
            if (checkout != null && !checkout.trim().isEmpty()) {
                session.setAttribute("checkout", checkout);
            }
            request.setAttribute("searchList", searchList);
            session.setAttribute("keyword", keyword);
            session.setAttribute("adults", adults);
            session.setAttribute("children", children);
            session.setAttribute("rooms", rooms);
            request.setAttribute("count", count);
            session.setAttribute("accommodationType", accommodationType);
        }
        return handleFilterSearch(request, response);
    }

    private String handleDisplayByHotelId(HttpServletRequest request, HttpServletResponse response) {
        String hotelID = request.getParameter("hotelID");
        try {
            int valid_hotel_id = Integer.parseInt(hotelID);
            SearchDTO searchList = sDAO.displayHotelById(valid_hotel_id);
            List<ReviewsDTO> reviews = rDAO.getReviewHotel(valid_hotel_id);
            List<String> reviewComment = new ArrayList<>();
            List<String> userName = new ArrayList<>();
            List<Double> rate = new ArrayList<>();
            List<Integer> indexList = new ArrayList<>();
            List<Integer> userID = new ArrayList<>();
            int count = 0;
            double rateAll = 0.0;
            if (reviews != null && !reviews.isEmpty()) {
                for (ReviewsDTO r : reviews) {
                    reviewComment.add(r.getComment());
                    rate.add(r.getRating());
                    userID.add(r.getUserID());
                    count++;
                    rateAll += r.getRating();
                }
            }

            for (int i = 0; i < reviews.size(); i++) {
                indexList.add(i);
            }

            for (int i : userID) {
                userName.add(uDAO.getAllUserById(i).getFullName());
            }

            double average = count > 0 ? rateAll / count : 0.0;

            request.setAttribute("searchList", searchList);
            request.setAttribute("reviewComment", reviewComment);
            request.setAttribute("userName", userName);
            request.setAttribute("rate", rate);
            request.setAttribute("average", average);
            request.setAttribute("count", count);
            request.setAttribute("indexList", indexList);

            return "informationHotel.jsp";

        } catch (Exception e) {
            request.setAttribute("error", "Invalid hotel ID");
            e.printStackTrace();
            return "searchDashboard.jsp";
        }
    }

    private String handleDisplayAllHotel(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String accommodationType = "Khách sạn";
        List<SearchDTO> searchList = sDAO.getAllInfoHotel(accommodationType);
        int count = 0;
        for (SearchDTO s : searchList) {
            count++;
        }
        request.setAttribute("searchList", searchList);
        request.setAttribute("count", count);
        session.setAttribute("accommodationType", accommodationType);
        return handleFilterSearch(request, response);
    }

    private String handleSetPriceHotel(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String city = request.getParameter("city");
        if (city == null || city.isEmpty()) {
            city = "Đà Nẵng";
        }

        try {
            List<SearchDTO> searchList = sDAO.setPriceHotel(city);
            if (searchList == null) {
                searchList = new ArrayList<>();
            }

            Set<String> typeHotelList = new HashSet<>();
            Set<String> serviceName = new HashSet<>();
            List<HotelDTO> listHotel = new ArrayList<>();

            for (SearchDTO s : searchList) {
                if (s == null) {
                    continue;
                }

                HotelDTO hotel = s.getHotel();
                if (hotel == null) {
                    continue;
                }

                List<RoomDTO> roomList = s.getRoomList();
                List<ServiceDTO> serviceList = s.getServiceList();

                if (roomList == null) {
                    roomList = new ArrayList<>();
                }
                if (serviceList == null) {
                    serviceList = new ArrayList<>();
                }

                AuthUtils.sortByValue(roomList);
                listHotel.add(hotel);
                typeHotelList.add(hotel.getType());

                if (!roomList.isEmpty()) {
                    double lowestPrice = roomList.get(0).getPricePerNight();
                    hDAO.updatePriceHotel(lowestPrice, hotel.getHotelID());
                }

                for (ServiceDTO service : serviceList) {
                    if (service != null && service.getServiceName() != null) {
                        serviceName.add(service.getServiceName());
                    }
                }
            }

            // Set attribute để kiểm tra trong JSP
            request.setAttribute("hasHotel", listHotel.size());
            session.setAttribute("listHotel", listHotel);
            session.setAttribute("typeHotelList", typeHotelList);
            session.setAttribute("serviceName", serviceName);
            request.setAttribute("city", city);
        } catch (Exception e) {
        }

        return "DiscountController?action=displayAllDiscount";
    }

    private String handleFilterSearch(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        List<SearchDTO> searchList = (List<SearchDTO>) request.getAttribute("searchList");
        System.out.println("searchList: "+searchList);
        int count = (int) request.getAttribute("count");
        String filter = request.getParameter("filter");
        if (filter != null) {
            switch (filter) {
                case "sortLowPrice":
                    searchList.sort((a, b) -> {
                        double priceA = a.getHotel().getPrice();
                        double priceB = b.getHotel().getPrice();
                        return Double.compare(priceA, priceB);
                    });
                    break;

                case "sortHighPrice":
                    searchList.sort((a, b) -> {
                        double priceA = a.getHotel().getPrice();
                        double priceB = b.getHotel().getPrice();
                        return Double.compare(priceB, priceA);
                    });
                    break;
            }
            session.setAttribute("filter", filter);
        }

        request.setAttribute("searchList", searchList);
        request.setAttribute("count", count);
        return "searchDashboard.jsp";
    }
}
