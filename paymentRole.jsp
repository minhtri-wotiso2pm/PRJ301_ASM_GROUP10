<%-- 
    Document   : paymentRole
    Created on : Jul 16, 2025, 7:34:04 PM
    Author     : nzero
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="payment" value="${requestScope.payment}"/>
<c:set var="user" value="${sessionScope.user}"/>
<c:set var="payment" value="${requestScope.payment}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thanh toán qua mã QR</title>
        <link rel="stylesheet" href="assets/css/paymentRole.css"/>
    </head>
    <body>
        ${payment.paymentID}
        <form action="PaymentController" method="get">
            <input type="hidden" name="action" value="backToDashboard">
            <input type="hidden" name="selectedMethod" value="${requestScope.selectedMethod}">
            <input type="hidden" name="roomID" value="${requestScope.roomID}">
            <input type="hidden" name="userID" value="${requestScope.userID}">
            <input type="hidden" name="bookingID" value="${requestScope.bookingID}">
            <input type="hidden" name="totalPrice" value="${requestScope.totalPrice}">
            <button type="submit">← Quay lại</button>
        </form>
        <div class="container">
            <div class="section-box">
                <h3>🧾 Mã QR thanh toán</h3>
                <div class="qr-section">
                    <c:if test="${not empty requestScope.qrPath}">
                        <img src="${requestScope.qrPath}" alt="QR Code for payment">
                    </c:if>
                    <p>Vui lòng sử dụng ứng dụng ngân hàng hoặc ví điện tử để quét mã QR.</p>
                </div>
            </div>

            <div class="section-box">
                <h3>📌 Hướng dẫn thanh toán</h3>
                <div class="qr-section">
                    <ol>
                        <li data-step="1">Mở ứng dụng ngân hàng hoặc ví điện tử (Momo, ZaloPay, Vietcombank,...)</li>
                        <li data-step="2">Chọn chức năng <strong>"Quét mã QR"</strong></li>
                        <li data-step="3">Quét mã QR hiển thị phía bên trái</li>
                        <li data-step="4">Nhập số tiền cần thanh toán (nếu chưa có sẵn)</li>
                        <li data-step="5">Xác nhận thanh toán và lưu lại biên lai</li>
                    </ol>
                </div>
            </div>
        </div>

        <div class="section-box">
            <form action="MainController" method="post">
                <input type="hidden" name="action" value="updateBookingRoom">
                <input type="hidden" name="selectedMethod" value="${requestScope.selectedMethod}">
                <input type="hidden" name="roomID" value="${requestScope.roomID}">
                <input type="hidden" name="userID" value="${requestScope.userID}">
                <input type="hidden" name="bookingID" value="${requestScope.bookingID}">
                <input type="hidden" name="totalPrice" value="${requestScope.totalPrice}">   
                <input type="hidden" name="paymentID" value="${payment.paymentID}"> 
                <div class="center">
                    <button class="continue-btn">Kiểm tra thanh toán</button>
                </div>
            </form>
        </div>

        <!-- Thông báo kết quả thanh toán -->
        <div class="section-box">
            <c:if test="${not empty requestScope.message}">
                <h4>Kết quả từ SePay:</h4>
                <pre>${requestScope.message}</pre>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="${not empty sessionScope.keyword?'searchHotel':'displayAllHotel'}">
                    <input type="hidden" name="keyword" value="${sessionScope.keyword}">
                    <input type="hidden" name="checkin" value="${sessionScope.checkin}">
                    <input type="hidden" name="checkout" value="${sessionScope.checkout}">
                    <input type="hidden" name="adults" value="${sessionScope.adults}">
                    <input type="hidden" name="children" value="${sessionScope.children}">
                    <input type="hidden" name="rooms" value="${sessionScope.rooms}">
                    <input type="hidden" name="filter" value="${sessionScope.filter}">
                    <input type="hidden" name="accommodationType" value="${sessionScope.accommodationType}">
                    <div class="center">
                        <button class="continue-btn" onclick="confirmPayment()">Tiếp tục chọn khách sạn</button>
                    </div>
                </form>
            </c:if>
        </div>
        <script src="assets/js/paymentRole.js"></script>
    </body>
</html>