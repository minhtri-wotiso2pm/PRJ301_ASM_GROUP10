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
<c:set var="typeHotel" value="${sessionScope.typeHotelList}"/>
<c:set var="serviceName" value="${sessionScope.serviceName}"/>
<c:set var="searchList" value="${requestScope.searchList}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/searchDashboard.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <jsp:include page="search.jsp"/>

        <div class="body-content">
            <div class="main-content">
                <div class="search-results-header">
                    <div class="results-count">
                        Tìm thấy ${requestScope.count} ${sessionScope.accommodationType}
                    </div>
                    <form action="MainController" method="post" id="sortForm">
                        <input type="hidden" name="action" value="searchHotel" />
                        <input type="hidden" name="keyword" value="${sessionScope.keyword}" />
                        <input type="hidden" name="checkin" value="${sessionScope.checkin}" />
                        <input type="hidden" name="checkout" value="${sessionScope.checkout}" />
                        <input type="hidden" name="adults" value="${sessionScope.adults}" />
                        <input type="hidden" name="children" value="${sessionScope.children}" />
                        <input type="hidden" name="rooms" value="${sessionScope.rooms}" />
                        <input type="hidden" name="accommodationType" value="${sessionScope.accommodationType}" />
                        <div class="sort-options">
                            <label for="filter">Sắp xếp theo:</label>
                            <select name="filter" id="filter" onchange="document.getElementById('sortForm').submit()">
                                <option value="">Select</option>
                                <option value="sortLowPrice" ${sessionScope.filter eq 'sortLowPrice'?'selected':''}>Giá thấp đến cao</option>
                                <option value="sortHighPrice" ${sessionScope.filter eq 'sortHighPrice'?'selected':''}>Giá cao đến thấp</option>
                            </select>
                        </div>
                    </form>
                </div>

                <c:forEach var="h" items="${searchList}">
                    <c:set var="hotel" value="${h.hotel}" />
                    <div class="hotel-card">
                        <div class="hotel-image">
                            <div class="main-image">
                                <img src="${hotel.mainImageUrl}">
                            </div>
                            <div class="thumbnail-row">
                                <c:forEach var="i" items="${h.imageList}">
                                    <img src="${i.imageUrl}">
                                </c:forEach>
                            </div>
                        </div>
                        <div class="hotel-info">
                            <div class="hotel-name">${hotel.name} Hotel</div>
                            <div class="hotel-address">${hotel.address}, ${hotel.city}</div>
                            <div class="hotel-rating">
                                <div class="rating-score">${hotel.rate}</div>
                            </div>
                            <div class="amenities">
                                <c:forEach var="s" items="${h.serviceList}" varStatus="status">
                                    ${s.serviceName}
                                    <c:choose>
                                        <c:when test="${!status.last}">, </c:when>
                                        <c:otherwise>.</c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                            <div class="hotel-type">${hotel.type}</div>
                        </div>
                        <div class="hotel-pricing">
                            <div class="price-display">
                                <div class="price"><fmt:formatNumber value="${hotel.price}" type="number" groupingUsed="true" /> VND</div>
                            </div>
                            <form action="MainController" method="post">
                                <input type="hidden" name="action" value="displayByHotelId">
                                <input type="hidden" name="hotelID" value="${hotel.hotelID}">
                                <button class="book-btn">Chọn phòng</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <script src="assets/js/hotelDashboard.js"></script>
    </body>
</html>