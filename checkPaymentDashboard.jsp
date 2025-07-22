<%-- 
    Document   : checkPaymentDashboard
    Created on : Jul 22, 2025, 1:07:52 AM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm"%>
<c:set var="paymentList" value="${requestScope.paymentList}"/>
<c:set var="hasPayment" value="${fm:length(paymentList)}"/>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="${not empty currentUser}"/>
<c:set var="isAdmin" value="${currentUser.roleID eq 'admin'}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/checkPaymentDashboard.css"/>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggin}">
                <h2>Danh sách đặt chỗ</h2>
                <c:choose>
                    <c:when test="${hasPayment==0}">
                        ${requestScope.message}
                    </c:when>
                    <c:when test="${hasPayment>0}">
                        <table>
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>id</th>
                                    <th>Tổng số tiền</th>
                                    <th>Ngày thanh toán</th>
                                    <th>Phương thức thanh toán</th>
                                    <th>Nội dung chuyển khoản</th>
                                    <th>Tool</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="p" items="${paymentList}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index+1}</td>
                                        <td>${p.paymentID}</td>
                                        <td>${p.amountPaid}</td>
                                        <td>${p.paymentDate}</td>
                                        <td>${p.paymentMethod}</td>
                                        <td>${p.ref_code}</td>
                                        <td>
                                            <form action="MainController" method="post">
                                                <input type="hidden" name="action" value="updatePaymentSatus">
                                                <input type="hidden" name="id" value="${p.paymentID}">
                                                <input type="submit" value="Đã chuyển khoản">
                                            </form>
                                        </td>
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
