<%-- 
    Document   : bookHotel
    Created on : Jun 16, 2025, 5:41:37 PM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="${not empty currentUser}"/>
<c:set var="isAdmin" value="${currentUser.roleID eq 'admin'}"/>
<%@ page import="model.PlaneDTO" %>
<%@ page import="utils.AuthUtils" %>
<%@ page import="java.util.List" %>
<%--<c:set var="typeHotel" value="${sessionScope.typeHotelList}"/>
<c:set var="serviceName" value="${sessionScope.serviceName}"/>--%>
<%--<c:set var="searchList" value="${requestScope.searchList}"/>--%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/planeX.css">
        <style>
            * {
                font-family: 'Segoe UI', 'Roboto', 'Helvetica Neue', sans-serif;
            }

        </style>
    </head>
    <body>
        <jsp:include page="header.jsp"/>


        <div class="container">
            <div class="header2">
                <h1>Tìm vé máy bay</h1>
            </div>

            <form action="MainController" method="post">
                <input type="hidden" name="action" value="searchPlane">

                <div class="search-card">
                    <!--                    <div class="trip-type">
                                            <button type="button" class="trip-option active">Một chiều</button>
                                            <button type="button" class="trip-option">Khứ hồi</button>
                                        </div>-->

                    <div class="search-form">
                        <div class="location-group">
                            <div class="location-input">
                                <label for="from">Từ</label>
                                <input type="text" id="from" name="from" value="${param.from}" placeholder="Thành phố khởi hành">
                            </div>
                        </div>

                        <button class="swap-button" type="button" title="Hoán đổi">🔁</button>

                        <div class="location-group">
                            <div class="location-input">
                                <label for="to">Đến</label>
                                <input type="text" id="to" name="to" value="${param.to}" placeholder="Thành phố đến">
                            </div>
                        </div>
                    </div>
                    <div class="form-container">        
                        <div class="date-passenger-row">
                            <div class="form-group">
                                <label for="departure">Ngày khởi hành</label>
                                <input type="date" id="departure" name="departureDate" value="${param.departureDate}">
                            </div>

                            <!--                            <div class="form-group">
                                                            <label for="return">Khứ hồi</label>
                                                            <input type="date" id="return" name="returnDate" value="${param.returnDate}">
                                                        </div>-->

                        </div>
                    </div>


                    <button class="search-button1" type="submit">🔍 Tìm chuyến bay</button>
                </div>
            </form>
            <form class="filter-form" action="PlaneController" method="get">
                <div class="airline-filter-box">
                    <label for="airline" class="filter-label">Bộ lọc hãng bay</label>
                    <input type="hidden" name="action" value="filterByAirline" />
                    <select id="airline" name="airline" class="filter-select">
                        <option value="">-- Tất cả hãng --</option>
                        <option value="VietJet Air" <c:if test="${selectedAirline == 'VietJet Air'}">selected</c:if>>VietJet Air</option>
                        <option value="Vietnam Airlines" <c:if test="${selectedAirline == 'Vietnam Airlines'}">selected</c:if>>Vietnam Airlines</option>
                        <option value="Bamboo Airways" <c:if test="${selectedAirline == 'Bamboo Airways'}">selected</c:if>>Bamboo Airways</option>
                        </select>
                        <button type="submit" class="filter-btn">Lọc</button>
                    </div>
                </form>
                        
                       
          
            
            <h2 class="DScb">Danh sách chuyến bay</h2>
            <table border="1">
                <tr>
                    <th>Flight Number</th>
                    <th>Airline</th>
                    <th>Departure</th>
                    <th>Arrival</th>
                    <th>Departure Time</th>
                    <th>Arrival Time</th>
                    <th>Xem chi tiết</th>
                    <th>QL vé</th>
                </tr>
                <c:forEach var="p" items="${planeList}">
                    <tr>
                        <td>${p.flightNumber}</td>
                        <td>${p.airline}</td>
                        <td>${p.departureAirport}</td>
                        <td>${p.arrivalAirport}</td>
                        <td>${p.departureTime}</td>
                        <td>${p.arrivalTime}</td>
                        <td>
                            <form action="MainController" method="post">
                                <input type="hidden" name="action" value="displayDetailPlaneBooking" />
                                <input type="hidden" name="flightId" value="${p.flightId}" />
                                <input type="submit" value="Chọn" />
                            </form>
                        </td><td>
                            <form action="PlaneBookingController" method="post">
                                <input type="hidden" name="action" value="displayAllBookingByPlaneID" />
                                <input type="hidden" name="flightId" value="${p.flightId}" />
                                <input type="submit" value="Chọn" />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </div>
        <script src="assets/js/planedashboard.js"></script>

        <jsp:include page="footer.jsp"/>
    </body>
</html>