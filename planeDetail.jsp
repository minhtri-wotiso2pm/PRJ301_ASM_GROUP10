<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.PlaneDTO"%>
<%@page import="model.PlanePriceDTO"%>
<%
    PlaneDTO plane = (PlaneDTO) request.getAttribute("planeDetail");
    PlanePriceDTO priceInfo = plane != null ? plane.getPriceInfo() : null;
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết chuyến bay</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="assets/css/planeDetail.css">
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="plane-return-container">
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="returnPlaneMenu" />
                <button type="submit" class="plane-return-btn">
                    ← Quay về danh sách chuyến bay
                </button>
            </form>
        </div>

        <div class="flight-detail-container">
            <!-- Header Section -->
            <div class="flight-header">
                <h1 class="flight-title">Chi tiết chuyến bay</h1>
                <p class="flight-subtitle">Thông tin đầy đủ về chuyến bay</p>
            </div>

            <% if (plane != null) { %>
            <!-- Flight Route Section -->
            <div class="flight-route-section">
                <div class="route-container">
                    <div class="airport-info">
                        <div class="airport-code"><%= plane.getDepartureAirport() %></div>
                        <div class="airport-name">Điểm khởi hành</div>
                    </div>
                    <div class="route-line"></div>
                    <div class="airport-info">
                        <div class="airport-code"><%= plane.getArrivalAirport() %></div>
                        <div class="airport-name">Điểm đến</div>
                    </div>
                </div>
                <div class="flight-duration">
                    <i class="fas fa-clock"></i> Thời gian bay: <%= plane.getFormattedDepartureTime() %> - <%= plane.getFormattedArrivalTime() %>
                </div>
            </div>

            <!-- Flight Info Section -->
            <div class="flight-info-section">
                <div class="info-grid">
                    <!-- Flight Details Card -->
                    <div class="info-card">
                        <div class="card-title">
                            <i class="fas fa-plane"></i>
                            Thông tin chuyến bay
                        </div>
                        <div class="info-row">
                            <span class="info-label">
                                <i class="fas fa-hashtag"></i>
                                Mã chuyến bay
                            </span>
                            <span class="info-value"><%= plane.getFlightId() %></span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">
                                <i class="fas fa-building"></i>
                                Hãng bay
                            </span>
                            <span class="info-value airline-value"><%= plane.getAirline() %></span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">
                                <i class="fas fa-tag"></i>
                                Số hiệu
                            </span>
                            <span class="info-value"><%= plane.getFlightNumber() %></span>
                        </div>
                    </div>

                    <!-- Schedule Card -->
                    <div class="info-card">
                        <div class="card-title">
                            <i class="fas fa-calendar-alt"></i>
                            Lịch trình
                        </div>
                        <div class="info-row">
                            <span class="info-label">
                                <i class="fas fa-plane-departure"></i>
                                Giờ khởi hành
                            </span>
                            <span class="info-value time-value"><%= plane.getFormattedDepartureTime() %></span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">
                                <i class="fas fa-plane-arrival"></i>
                                Giờ đến
                            </span>
                            <span class="info-value time-value"><%= plane.getFormattedArrivalTime() %></span>
                        </div>
                    </div>

                    <% if (priceInfo != null) { %>
                    <!-- Availability Card -->
                    <div class="info-card">
                        <div class="card-title">
                            <i class="fas fa-chair"></i>
                            Tình trạng ghế
                        </div>
                        <div class="info-row">
                            <span class="info-label">
                                <i class="fas fa-users"></i>
                                Số ghế còn lại
                            </span>
                            <span class="info-value seats-value"><%= priceInfo.getAvailableSeats() %> ghế</span>
                        </div>
                    </div>
                    <% } %>
                </div>

                <% if (priceInfo != null) { %>
                <!-- Pricing Section -->
                <div class="pricing-section">
                    <div class="pricing-card">
                        <div class="price-header">
                            <div class="price-title">
                                <i class="fas fa-money-bill-wave"></i>
                                Thông tin giá vé
                            </div>
                        </div>
                        <div class="info-row">
                            <span class="info-label">
                                <i class="fas fa-tag"></i>
                                Giá cơ bản
                            </span>
                            <span class="info-value price-value"><%= priceInfo.getBasePrice() %> VND</span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">
                                <i class="fas fa-chart-line"></i>
                                Giá động hiện tại
                            </span>
                            <span class="final-price">
                                <i class="fas fa-money-check-alt"></i>
                                <%= priceInfo.getDynamicPrice() %> VND
                            </span>
                        </div>
                    </div>
                </div>
                <form action="MainController" method="post" class="plane-booking-form">
                    <input type="hidden" name="action" value="reviewBooking" />
                    <input type="hidden" name="flightId" value="<%= plane.getFlightId() %>" />
                    <button type="submit" class="plane-booking-btn">
                        ✈ Đặt vé
                    </button>
                </form>


                <% } else { %>
                <div class="error-state">
                    <div class="error-icon">💰</div>
                    <div class="error-message">Không có thông tin giá vé</div>
                </div>
                <% } %>
            </div>
            <% } else { %>
            <!-- Error State -->
            <div class="error-state">
                <div class="error-icon">
                    <i class="fas fa-exclamation-triangle"></i>
                </div>
                <div class="error-message">Không tìm thấy thông tin chuyến bay</div>
            </div>
            <% } %>
        </div>
        <jsp:include page="footer.jsp"/>

    </body>
</html>