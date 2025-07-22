<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.PlaneDTO"%>
<%@page import="model.PlanePriceDTO"%>
<%@page import="utils.AuthUtils"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.UserDTO"%>
<%
    UserDTO user = AuthUtils.getCurrentUser(request);
%>

<%
    PlaneDTO plane = (PlaneDTO) request.getAttribute("planeDetail");
    PlanePriceDTO priceInfo = plane != null ? plane.getPriceInfo() : null;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Xác nhận thông tin đặt vé</title>
        <link rel="stylesheet" href="assets/css/planeBookingReview.css">

    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="loading-overlay" id="loadingOverlay">
            <div class="loading-spinner"></div>
        </div>

        <div class="container">
            <div class="plane-review">
                <div class="header">
                    <h2>Chọn thông tin đặt vé</h2>

                </div>
                <p class="subtitle">Vui lòng kiểm tra thông tin chuyến bay và chọn gói dịch vụ</p>
                <div class="flight-info-card">
                    <div class="flight-route">
                        <div class="airport">
                            <div class="airport-code"><%= plane.getDepartureAirport() %></div>
                            <div class="airport-name">Điểm đi</div>
                        </div>

                        <div class="flight-path">
                            <div class="flight-line">
                                <div class="plane-icon">✈️</div>
                            </div>
                        </div>

                        <div class="airport">
                            <div class="airport-code"><%= plane.getArrivalAirport() %></div>
                            <div class="airport-name">Điểm đến</div>
                        </div>
                    </div>

                    <div class="flight-details">
                        <div class="detail-item">
                            <div class="detail-label">Hãng bay</div>
                            <div class="detail-value"><%= plane.getAirline() %></div>
                        </div>
                        <div class="detail-item">
                            <div class="detail-label">Chuyến bay</div>
                            <div class="detail-value"><%= plane.getFlightNumber() %></div>
                        </div>
                        <div class="detail-item">
                            <div class="detail-label">Giờ đi</div>
                            <div class="detail-value"><%= plane.getFormattedDepartureTime() %></div>
                        </div>
                        <div class="detail-item">
                            <div class="detail-label">Giờ đến</div>
                            <div class="detail-value"><%= plane.getFormattedArrivalTime() %></div>
                        </div>
                    </div>
                </div>

                <div class="price-highlight">
                    💰 Giá vé: <%= priceInfo.getDynamicPrice() %>₫
                </div>

                <form action="MainController" method="post" id="bookingForm">
                    <input type="hidden" name="action" value="confirmPlaneBooking"/>
                    <div class="service-section">
                        <h3>Chọn gói dịch vụ</h3>
                        <div class="select-wrapper">
                            <select name="packageId" required>
                                <option value="" disabled selected>-- Chọn gói dịch vụ phù hợp --</option>
                                <c:forEach var="pkg" items="${packages}">
                                    <option value="${pkg.packageId}">
                                        ${pkg.serviceName} - ${pkg.ticketClass} - ${pkg.baggageAllowance} - 
                                        ${pkg.mealOption} (+${pkg.extraPrice}₫)
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>


                    <input type="hidden" name="flightId" value="<%= plane.getFlightId() %>"/>
                    <input type="hidden" name="userId" value="<%= user != null ? user.getUserID() : "" %>"/>
                    <input type="hidden" name="price" value="<%= priceInfo.getDynamicPrice() %>"/>
                    <input type="hidden" name="total" value="<%= priceInfo.getDynamicPrice() %>"/>


                    <button type="submit" class="confirm-btn">Xác nhận chọn</button>
                </form>

            </div>
        </div>

        <script>
            document.getElementById('bookingForm').addEventListener('submit', function () {
                document.getElementById('loadingOverlay').style.display = 'flex';
            });

            // Add smooth scroll animation
            document.addEventListener('DOMContentLoaded', function () {
                const elements = document.querySelectorAll('.plane-review > *');
                elements.forEach((el, index) => {
                    el.style.animationDelay = (index * 0.1) + 's';
                });
            });
        </script>
        <jsp:include page="footer.jsp"/>

    </body>
</html>