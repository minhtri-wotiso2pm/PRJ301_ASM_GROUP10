<%-- 
    Document   : bookDashboard
    Created on : Jul 11, 2025, 1:52:32 PM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="${not empty currentUser}"/>
<c:set var="room" value="${requestScope.room}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/bookDashboard.css"/>
    </head>
    <body>
        <div class="hotel-info">
            <h2>${requestScope.name} ${requestScope.city}</h2>
            <p>(${requestScope.name} ${requestScope.city})</p>
            <p>${requestScope.average} (${requestScope.count})</p>

            <img src="${requestScope.mainImageUrl}" alt="Hotel image" />

            <div class="date-section">
                <div>
                    <strong>Nhận phòng</strong>
                    <p>${sessionScope.checkin}</p>
                </div>
                <div style="text-align: center;">
                    <p>${requestScope.dayBetween} ngày</p>
                </div>
                <div>
                    <strong>Trả phòng</strong>
                    <p>${sessionScope.checkout}</p>
                </div>
            </div>

            <div class="room-info">
                <p><strong>${room.description}</strong></p>
                <p>${room.bedInfo}</p>
                <p>Còn <strong>${room.quantity}</strong> phòng</p>
            </div>

            <div class="price-section">
                <div>Giá phòng (1 ngày): <fmt:formatNumber value="${requestScope.total_fee}" type="number" groupingUsed="true" /></div>
                <div>Thuế và phí: <fmt:formatNumber value="${requestScope.tax}" type="number" groupingUsed="true" /></div>
                <div class="total-price">Tổng giá: <fmt:formatNumber value="${requestScope.final_price}" type="number" groupingUsed="true" /></div>
            </div>

            <form action="MainController" method="post" class="booking-form">
                <input type="hidden" name="action" value="addBooking">
                <input type="hidden" name="roomID" value="${room.roomID}">
                <input type="hidden" name="userID" value="${currentUser.userID}">
                <input type="hidden" name="checkin" value="${sessionScope.checkin}">
                <input type="hidden" name="checkout" value="${sessionScope.checkout}">
                <input type="hidden" name="final_price" value="${requestScope.final_price}">
                <input type="submit" value="Thanh toán">
            </form>
        </div>
    </body>
</html>
