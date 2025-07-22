<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.PlaneDTO"%>
<%@page import="model.PlanePriceDTO"%>
<%@page import="model.PlaneServicePackageDTO"%>
<%@page import="utils.AuthUtils"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.UserDTO"%>

<%
    UserDTO user = AuthUtils.getCurrentUser(request);
    PlaneDTO plane = (PlaneDTO) request.getAttribute("planeDetail");
    PlanePriceDTO priceInfo = plane != null ? plane.getPriceInfo() : null;
    PlaneServicePackageDTO selectedPackage = (PlaneServicePackageDTO) request.getAttribute("selectedPackage");

    int basePrice = priceInfo.getDynamicPrice();
    int extraFee = selectedPackage != null ? selectedPackage.getExtraPrice() : 0;
    int totalPrice = basePrice + extraFee;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Xác nhận đặt vé</title>
    <link rel="stylesheet" href="assets/css/planeBooking.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <div class="plane-review">
        <div class="header">
            <h2>Xác nhận đặt vé</h2>
        </div>

        <div class="flight-info-card">
            <div class="flight-route">
                <div class="airport">
                    <div class="airport-code"><%= plane.getDepartureAirport() %></div>
                    <div class="airport-name">Điểm đi</div>
                </div>
                <div class="flight-path">
                    <div class="flight-line"><div class="plane-icon">✈️</div></div>
                </div>
                <div class="airport">
                    <div class="airport-code"><%= plane.getArrivalAirport() %></div>
                    <div class="airport-name">Điểm đến</div>
                </div>
            </div>

            <div class="flight-details">
                <div class="detail-item"><div class="detail-label">Hãng bay</div><div class="detail-value"><%= plane.getAirline() %></div></div>
                <div class="detail-item"><div class="detail-label">Chuyến bay</div><div class="detail-value"><%= plane.getFlightNumber() %></div></div>
                <div class="detail-item"><div class="detail-label">Giờ đi</div><div class="detail-value"><%= plane.getFormattedDepartureTime() %></div></div>
                <div class="detail-item"><div class="detail-label">Giờ đến</div><div class="detail-value"><%= plane.getFormattedArrivalTime() %></div></div>
            </div>
        </div>

        <div class="service-summary">
            <h3>Gói dịch vụ đã chọn:</h3>
            <p><strong>Tên dịch vụ:</strong> <%= selectedPackage.getServiceName() %></p>
            <p><strong>Hạng vé:</strong> <%= selectedPackage.getTicketClass() %></p>
            <p><strong>Hành lý:</strong> <%= selectedPackage.getBaggageAllowance() %></p>
            <p><strong>Suất ăn:</strong> <%= selectedPackage.getMealOption() %></p>
            <p><strong>Phí dịch vụ:</strong> <%= extraFee %>₫</p>
        </div>

        <div class="price-summary">
            <p>💺 Giá vé: <%= basePrice %>₫</p>
            <p>➕ Dịch vụ thêm: <%= extraFee %>₫</p>
            <p><strong>💰 Tổng cộng: <%= totalPrice %>₫</strong></p>
        </div>

        <form action="MainController" method="post">
            <input type="hidden" name="action" value="submitPlaneBooking" />
            <input type="hidden" name="flightId" value="<%= plane.getFlightId() %>" />
            <input type="hidden" name="packageId" value="<%= selectedPackage.getPackageId() %>" />
            <input type="hidden" name="userId" value="<%= user.getUserID() %>" />
            <input type="hidden" name="totalPrice" value="<%= totalPrice %>" />
            <button type="submit" class="confirm-btn">✅ Xác nhận đặt vé</button>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
