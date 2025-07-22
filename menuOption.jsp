<%-- 
    Document   : menuOption
    Created on : Jul 18, 2025, 4:46:51 PM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="${not empty currentUser}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/menuOption.css"/>
    </head>
    <body>
        <a href="MainController?action=setPriceHotel">Back</a>
        <c:choose>
            <c:when test="${isLoggin}">
                <div class="body-container">
                    <div class="option">
                        <jsp:include page="option.jsp"/>
                    </div>

                    <div class="form-container">
                        <c:choose>
                            <c:when test="${requestScope.option eq 'userAction' or empty requestScope.option}">
                                <jsp:include page="userDashboard.jsp"/>
                            </c:when>
                            <c:when test="${requestScope.option eq 'bookAction'}">
                                <jsp:include page="bookingDashboard.jsp"/>
                            </c:when>
                            <c:when test="${requestScope.option eq 'paymentAction'}">
                                <jsp:include page="paymentUser.jsp"/>
                            </c:when>
                            <c:when test="${requestScope.option eq 'checkPaymentAction'}">
                                <jsp:include page="checkPaymentDashboard.jsp"/>
                            </c:when>
                            <c:when test="${requestScope.option eq 'bookPlaneAction'}">
                                <jsp:include page="planeBookingDashboard.jsp"/>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </body>
</html>
