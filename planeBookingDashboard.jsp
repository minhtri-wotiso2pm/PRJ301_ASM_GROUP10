<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="bookList" value="${requestScope.bookList}" />
<c:set var="hasBook" value="${fn:length(bookList)}" />
<c:set var="currentUser" value="${sessionScope.user}" />
<c:set var="isLoggin" value="${not empty currentUser}" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách vé đã đặt</title>
        <link rel="stylesheet" href="assets/css/bookingDashboard.css" />
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggin}">
                <h2 style="text-align:center;">Danh sách vé</h2>
                <c:choose>
                    <c:when test="${hasBook == 0}">
                        <p style="text-align:center; color: red;">Bạn chưa có vé máy bay nào!</p>
                    </c:when>
                    <c:when test="${hasBook > 0}">
                        <table>
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Mã vé</th>
                                    <th>Chuyến bay</th>
                                    <th>Thời gian đặt</th>
                                    <th>Tổng tiền</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="b" items="${bookList}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${b.getBookingId()}</td>
                                        <td>${b.getFlightId()}</td>
                                        <td>${b.getBookingDate()}</td>
                                        <td>${b.getTotal()} VND</td>
                                      
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>
                <p style="text-align:center; color:red;">Vui lòng đăng nhập để xem danh sách vé!</p>
            </c:otherwise>
        </c:choose>
    </body>
</html>
