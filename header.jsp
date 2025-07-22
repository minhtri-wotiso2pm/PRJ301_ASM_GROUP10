<%-- 
    Document   : header
    Created on : Jul 5, 2025, 12:10:41 PM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="${not empty currentUser}"/>
<c:set var="isAdmin" value="${currentUser.roleID eq 'admin'}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/header.css"/>
    </head>
    <body>
        <div class="header-contain" id="box">
            <div class="header">
                <div class="logo">
                    <a href="welcome.jsp">
                        <img id="logo-loka" src="assets/images/d4969644-5d07-445a-b9ba-b38d158b34ce-removebg-preview.png" alt="Logo">
                    </a>
                </div>
                <div class="menu-header">
                    <div class="font-img">
                        <a><img src="assets/images/Flag_of_Vietnam.svg.webp" alt="Vietnam"> VI|VND</a>
                        <a><img src="assets/images/z6617569047609_e399b667d5c7329885d626bf441edd58-removebg-preview.png" alt="discount"> Khuyến mãi</a>
                        <a href="#">Hỗ trợ</a>
                        <a href="#">Hợp tác với chúng tôi</a>
                        <a href="#">Đặt chỗ của tôi</a>
                    </div>
                    <div class="button">
                        <c:choose>
                            <c:when test="${isLoggin}">
                                <div class="login-wel" onclick="toggleDropdown()">
                                    <img src="assets/images/img-login (1).png" alt="Avatar">
                                    <span>${currentUser.fullName}</span>
                                </div>
                                <div class="dropdown-menu" id="menu">
                                    <div class="task-header">
                                        <h3>${currentUser.userName}</h3> 
                                        <h4>
                                            <img src="assets/images/hchuong-removebg-preview.png" alt="Medal Icon"/>
                                            Bronze Priority
                                        </h4>
                                    </div>
                                    <ul>
                                        <a href="menuOption.jsp"><li><img src="assets/images/account-removebg-preview.png" alt="account"/> Chỉnh sửa hồ sơ</li></a>
                                        <li>
                                            <form action="MainController" method="post">
                                                <input type="hidden" name="action" value="displayBooking">
                                                <input type="hidden" name="userID" value="${currentUser.userID}">
                                                <label>
                                                    <img src="assets/images/book-removebg-preview.png" alt="list"/>
                                                </label>
                                                <input type="submit" value="Đặt chỗ của tôi">
                                            </form>
                                        </li>
                                        <li>
                                            <form action="MainController" method="post">
                                                <input type="hidden" name="action" value="displayPayment">
                                                <input type="hidden" name="userID" value="${currentUser.userID}">
                                                <label>
                                                    <img src="assets/images/ds-removebg-preview.png" alt="your bookingh"/>
                                                </label>
                                                <input type="submit" value="Danh sách giao dịch">
                                            </form>
                                        </li>
                                        <c:if test="${isAdmin}">
                                            <li>
                                                <form action="MainController" method="post">
                                                    <input type="hidden" name="action" value="checkStatusPayment">
                                                    <input type="hidden" name="userID" value="${currentUser.userID}">
                                                    <label>
                                                        <img src="assets/images/ds-removebg-preview.png" alt="your bookingh"/>
                                                    </label>
                                                    <input type="submit" value="Xác thực thanh toán">
                                                </form>
                                            </li>
                                        </c:if>
                                        <li>
                                            <form action="MainController" method="post">
                                                <input type="hidden" name="action" value="displayPlaneBooking">
                                                <input type="hidden" name="userID" value="${currentUser.userID}">
                                                <label>
                                                    <img src="assets/images/book-removebg-preview.png" alt="list"/>
                                                </label>
                                                <input type="submit" value="Đặt vé của tôi">
                                            </form>
                                        </li>
                                        <li>
                                            <a href="MainController?action=logout">
                                                <img src="assets/images/logout-removebg-preview.png" alt="logout"> Đăng xuất
                                            </a>
                                        </li>
                                        <c:if test="${isAdmin}">
                                            <li>
                                                <a href="hotelForm.jsp">
                                                    Thêm khách sạn
                                                </a>
                                            </li>
                                            <li>
                                                <a href="planeCreate.jsp">
                                                    Thêm máy bay
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <a class="login" href="login.jsp">
                                    <img id="user-logo-login" src="assets/images/user-white.png" alt="login icon">
                                    Đăng nhập
                                </a>
                                <a class="register" href="userForm.jsp">Đăng ký</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <div class="menu">
                <a href="MainController?action=displayAllHotel">Khách sạn</a>
                <a href="MainController?action=displayAllPlane">Vé máy bay</a>
            </div>           
        </div>
        <script src="assets/js/header.js"></script>
    </body>
</html>
