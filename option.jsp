<%-- 
    Document   : option
    Created on : Jul 20, 2025, 6:49:37 PM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="${not empty currentUser}"/>
<c:set var="isAdmin" value="${currentUser.roleID eq 'admin'}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/option.css"/>
    </head>
    <body>
        <div class="button">
            <c:choose>
                <c:when test="${isLoggin}">
                    <div class="dropdown-menu" id="menu">
                        <div class="task-header">
                            <h3>${currentUser.userName}</h3> 
                            <h4>
                                <img src="assets/images/hchuong-removebg-preview.png" alt="Medal Icon"/>
                                Bronze Priority
                            </h4>
                        </div>
                        <ul>
                            <li><a href="menuOption.jsp"><img src="assets/images/account-removebg-preview.png" alt="account"/> Chỉnh sửa hồ sơ</a></li>
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
            </c:choose>
        </div>
    </body>
</html>
