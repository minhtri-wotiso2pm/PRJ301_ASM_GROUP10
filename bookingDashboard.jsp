<%-- 
    Document   : bookingDashboard
    Created on : Jul 21, 2025, 9:08:20 AM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm"%>
<c:set var="bookList" value="${requestScope.bookList}"/>
<c:set var="hasBook" value="${fm:length(bookList)}"/>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="${not empty currentUser}"/>
<c:set var="isAdmin" value="${currentUser.roleID eq 'admin'}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/bookingDashboard.css"/>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggin}">
                <h2>Danh sách đặt chỗ</h2>
                <c:choose>
                    <c:when test="${hasBook==0}">
                        ${requestScope.message}
                    </c:when>
                    <c:when test="${hasBook>0}">
                        <table>
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>bookingID</th>
                                    <th>roomID</th>
                                    <th>Check In Date</th>
                                    <th>Check Out Date</th>
                                    <th>Price</th>
                                    <th>Status</th>
                                    <th>Tool</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="b" items="${bookList}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index+1}</td>
                                        <td>${b.bookingID}</td>
                                        <td>${b.roomID}</td>
                                        <td>${b.checkInDate}</td>
                                        <td>${b.checkOutDate}</td>
                                        <td>${b.totalPrice}</td>
                                        <td>${b.paymentStatus?'OK':'Thanh toán thất bại'}</td>
                                        <c:if test="${b.paymentStatus eq '1'}">
                                            <td>
                                                <form action="MainController" method="post">
                                                    <input type="hidden" name="action" value="deleteBooking">
                                                    <input type="hidden" name="bookingID" value="${b.bookingID}">
                                                    <input type="submit" value="Delete">
                                                </form>
                                            </td>
                                        </c:if>s
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>
                <a href="welcome.jsp">You must login first before saw the book List</a>
            </c:otherwise>
        </c:choose>
    </body>
</html>
