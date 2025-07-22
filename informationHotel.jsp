<%-- 
    Document   : hotelSelect
    Created on : Jul 7, 2025, 1:12:39 PM
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
<c:set var="searchList" value="${requestScope.searchList}"/>
<c:set var="hotel" value="${searchList.hotel}"/>
<c:set var="reviewComment" value="${requestScope.reviewComment}"/>
<c:set var="userName" value="${requestScope.userName}"/>
<c:set var="rate" value="${requestScope.rate}"/>
<c:set var="count" value="${requestScope.count}"/>
<c:set var="average" value="${requestScope.average}"/>
<c:set var="indexList" value="${requestScope.indexList}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/informationHotel.css"/>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <jsp:include page="search.jsp"/>
        <div class="container">
            <div class="image-gallery">
                <div class="main-image">
                    <img src="${hotel.mainImageUrl}">
                </div>
                <div class="thumbnail-grid">
                    <c:forEach var="i" items="${searchList.imageList}">
                        <div class="thumbnail">
                            <img src="${i.imageUrl}">
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="content">
                <div class="hotel-header">
                    <div class="hotel-title">
                        <h1>${hotel.name} Hotel ${hotel.city}</h1>
                        <div class="hotel-category">${hotel.type}</div>
                    </div>
                    <div class="price-section">
                        <div class="price"><fmt:formatNumber value="${hotel.price}" type="number" groupingUsed="true" /> VND</div>
                    </div>
                </div>

                <div class="main-content">
                    <div class="rating-section">
                        <div class="rating-score">
                            <div class="score">${average}/10</div>
                            <div class="score-detail">
                                <div>/10</div>
                                <div style="color: #3498db; font-weight: 600;">${average<4 and average>0?'Tệ':average<=6?'Trung bình':average<8?'Khá':average<=9?'Tốt':'Rất tốt'}</div>
                                <div style="color: #3498db; font-size: 12px;">${count} đánh giá</div>
                            </div>
                        </div>

                        <div class="review-container">
                            <c:forEach var="i" items="${indexList}" varStatus="loop">
                                <div class="review">
                                    <div class="score">
                                        <div>
                                            <span>${userName[loop.index]}</span>
                                        </div>
                                        <div>
                                            <span style="color: #3498db; font-weight: bold;">${rate[loop.index]}</span>
                                            <span style="color: #666;">/ 10</span>
                                        </div>
                                    </div>
                                    <div style="font-size: 14px; color: #666;">
                                        <p>${reviewComment[loop.index]}</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="location-section">
                        <h3>Trong khu vực</h3>
                        <a href="https://www.google.com/maps?q=${hotel.address}+${hotel.city}" target="_blank"" class="view-more">Xem bản đồ</a>

                        <div class="location-item">
                            <i class="fas fa-map-marker-alt"></i>
                            <span class="location-name">${hotel.address} - ${hotel.city}</span>
                        </div>
                    </div>

                    <div class="amenities-section">
                        <h3>Tiện ích chính</h3>

                        <div class="amenities-grid">
                            <c:forEach var="s" items="${searchList.serviceList}">
                                <div class="amenity-item">
                                    <i class="fas fa-snowflake"></i>
                                    <span class="amenity-name">${s.serviceName}</span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div>
                    <c:forEach var="r" items="${searchList.roomList}">
                        <h2>${r.roomType}</h2>
                        <div class="room-card">
                            <div class="room-image">
                                <img src="${r.imageRoom}" alt="${r.roomType}">
                            </div>
                            <div class="room-info">
                                <div>
                                    <p>description</p>
                                    <div class="room-detail">${r.description}</div>
                                </div>
                                <div>
                                    <p>capacity</p>
                                    <div class="room-detail">👤 ${r.capacity} khách</div>
                                </div>
                                <div>
                                    <p>bedInfo</p>
                                    <div class="room-detail">🛏 Bao gồm: ${r.bedInfo}</div>
                                </div>
                                <div>
                                    <p>Price</p>
                                    <div class="price"><fmt:formatNumber value="${r.pricePerNight}" type="number" groupingUsed="true" /> VND</div>
                                </div>
                                <div class="room-actions">
                                    <form action="MainController" method="get">
                                        <input type="hidden" name="action" value="infoBooking">
                                        <input type="hidden" name="roomID" value="${r.roomID}">
                                        <input type="hidden" name="name" value="${hotel.hotelID}">
                                        <input type="hidden" name="name" value="${hotel.name}">
                                        <input type="hidden" name="city" value="${hotel.city}">
                                        <input type="hidden" name="mainImageUrl" value="${hotel.mainImageUrl}">
                                        <input type="hidden" name="count" value="${count}">
                                        <input type="hidden" name="average" value="${average}">
                                        <input type="hidden" name="checkin" value="${sessionScope.checkin}">
                                        <input type="hidden" name="checkout" value="${sessionScope.checkout}">
                                        <input type="submit" class="select-btn" value="Chọn phòng">
                                    </form>
                                    <div class="quantity-left">Chỉ còn ${r.quantity} phòng</div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
