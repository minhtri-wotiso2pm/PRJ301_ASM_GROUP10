<%-- 
    Document   : payment
    Created on : Jul 13, 2025, 2:13:37 PM
    Author     : nzero
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="${not empty currentUser}"/>
<c:set var="book" value="${requestScope.book}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thanh toán - Hotel Booking</title>
        <link rel="stylesheet" href="assets/css/paymentDashboard.css"/>
    </head>
    <body>
        <div class="payment-container">
            <div class="payment-header">
                <h2>Bạn muốn thanh toán thế nào?</h2>
            </div>
            <form action="MainController" method="post" id="paymentForm">
                <input type="hidden" name="userID" value="${currentUser.userID}">
                <input type="hidden" name="bookingID" value="${book.bookingID}">
                <input type="hidden" name="roomID" value="${requestScope.roomID}">
                <input type="hidden" name="totalPrice" value="${book.totalPrice}">
                <input type="hidden" name="selectedMethod" value="${selectedMethod}">
                <div class="coupon-section">
                    <h3>Thêm mã giảm giá</h3>
                    <input type="text" name="discountCode" class="coupon-input" placeholder="Nhập mã giảm giá nếu có">
                    <button type="submit" name="action" value="applyDiscount" class="add-coupon-btn" formnovalidate>
                        Áp dụng mã giảm giá
                    </button>
                    <c:if test="${not empty check1}">
                        <p style="color:red">${requestScope.check1}</p>
                    </c:if>
                </div>

                <div class="price-display">
                    <div class="price-amount">
                        <c:if test="${not empty book.totalPrice}">
                            <fmt:formatNumber value="${book.totalPrice}" type="number" groupingUsed="true" /> VND
                        </c:if>
                        <c:if test="${not empty requestScope.totalPrice}">
                            <fmt:formatNumber value="${requestScope.totalPrice}" type="number" groupingUsed="true" /> VND
                        </c:if>
                    </div>
                </div>

                <!-- Payment Methods -->
                <div class="payment-methods">
                    <h3>Chọn phương thức thanh toán</h3>
                    <label data-method="Momo">
                        <input type="radio" name="paymentMethod" value="VPBank" ${selectedMethod eq 'VPBank'?'checked="checked"':''}> 
                        <span>VP Bank</span>
                    </label>
                </div>

                <div class="submit-section">
                    <button type="submit" name="action" value="processPayment" class="submit-btn">
                        Xử lý thanh toán
                    </button>
                </div>
            </form>
        </div>
    </body>
</html>