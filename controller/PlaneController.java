package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PlaneDAO;
import model.PlaneDTO;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PlaneController", urlPatterns = {"/PlaneController"})
public class PlaneController extends HttpServlet {

    PlaneDAO dao = new PlaneDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";

        try {
            String action = request.getParameter("action");
            if (action != null) {
                switch (action) {
                    case "displayAllPlane":
                        url = handleViewAllPlanes(request);
                        break;
                    case "searchPlane":
                        url = handleSearchPlane(request);
                        break;
                    case "searchPlaneft":
                        url = handleSearchPlanefromto(request);
                        break;
                    case "filterByAirline":
                        url = handleFilterByAirline(request);
                        break;
                    case "displayDetailPlaneBooking":
                        url = handleViewDetailPlaneBooking(request);
                        break;
                    case "createPlane":
                        url = handleCreatePlane(request);
                        break;
                    case "returnPlaneMenu":
                        url = handleViewAllPlanes(request);
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

    private String handleViewAllPlanes(HttpServletRequest request) {
        List<PlaneDTO> list = dao.getAll();
        request.setAttribute("planeList", list);
        return "plane.jsp";
    }

    private String handleSearchPlane(HttpServletRequest request) {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String departureDate = request.getParameter("departureDate");
        String returnDate = request.getParameter("returnDate");

        List<PlaneDTO> result = dao.searchPlanes(from, to, departureDate);
        request.setAttribute("planeList", result);
        return "plane.jsp"; // hoặc chuyển đến trang hiển thị kết quả tìm kiếm
    }

    private String handleSearchPlanefromto(HttpServletRequest request) {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
      

        List<PlaneDTO> result = dao.searchPlanesft(from, to);
        request.setAttribute("planeList", result);
        return "plane.jsp"; // hoặc chuyển đến trang hiển thị kết quả tìm kiếm
    }

    private String handleFilterByAirline(HttpServletRequest request) {
        String airline = request.getParameter("airline");
        List<PlaneDTO> list;

        if (airline != null && !airline.isEmpty()) {
            list = dao.getPlanesByAirline(airline);
        } else {
            list = dao.getAll();
        }
        request.setAttribute("selectedAirline", airline);
        request.setAttribute("planeList", list);
        return "plane.jsp";
    }

    private String handleViewDetailPlaneBooking(HttpServletRequest request) {
        String flightId = request.getParameter("flightId");
        PlaneDTO plane = dao.getPlaneById(flightId);
        request.setAttribute("planeDetail", plane);
        return "planeDetail.jsp";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private String handleCreatePlane(HttpServletRequest request) {
        try {
            String airline = request.getParameter("airline");
            String departureAirport = request.getParameter("departureAirport");
            String arrivalAirport = request.getParameter("arrivalAirport");
            String departureTime = request.getParameter("departureTime");// định dạng yyyy-MM-dd HH:mm:ss
            String arrivalTime = request.getParameter("arrivalTime");
            String flightNumber = request.getParameter("flightNumber");

            // Validate dữ liệu đầu vào
            if (airline == null || airline.trim().isEmpty()
                    || departureAirport == null || departureAirport.trim().isEmpty()
                    || arrivalAirport == null || arrivalAirport.trim().isEmpty()
                    || departureTime == null || departureTime.trim().isEmpty()
                    || arrivalTime == null || arrivalTime.trim().isEmpty()
                    || flightNumber == null || flightNumber.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin chuyến bay.");
                request.setAttribute("airline", airline);
                request.setAttribute("departureAirport", departureAirport);
                request.setAttribute("arrivalAirport", arrivalAirport);
                request.setAttribute("departureTime", departureTime);
                request.setAttribute("arrivalTime", arrivalTime);
                request.setAttribute("flightNumber", flightNumber);

                return "planeCreate.jsp"; // quay lại form nhập
            }

            // Kiểm tra thời gian (optional: kiểm tra định dạng yyyy-MM-dd HH:mm:ss)
            if (departureTime.compareTo(arrivalTime) >= 0) {
                request.setAttribute("error", "Thời gian đến phải sau thời gian khởi hành.");
                request.setAttribute("airline", airline);
                request.setAttribute("departureAirport", departureAirport);
                request.setAttribute("arrivalAirport", arrivalAirport);
                request.setAttribute("departureTime", departureTime);
                request.setAttribute("arrivalTime", arrivalTime);
                request.setAttribute("flightNumber", flightNumber);

                return "planeCreate.jsp";
            }

            PlaneDTO plane = new PlaneDTO();
            plane.setAirline(airline.trim());
            plane.setDepartureAirport(departureAirport.trim());
            plane.setArrivalAirport(arrivalAirport.trim());
            plane.setDepartureTime(departureTime.trim());
            plane.setArrivalTime(arrivalTime.trim());
            plane.setFlightNumber(flightNumber.trim());

            boolean success = dao.create(plane);

            if (success) {
                request.setAttribute("message", "Tạo chuyến bay thành công!");
            } else {
                request.setAttribute("error", "Không thể tạo chuyến bay.");
            }
            return "planeCreate.jsp"; // ⚠️ GIỮ LẠI TRANG TẠO
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            return "planeCreate.jsp";
        }

    }

}