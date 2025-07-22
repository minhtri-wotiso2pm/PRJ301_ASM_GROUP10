<%-- 
    Document   : searchHotel
    Created on : Jul 8, 2025, 1:51:06 PM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/search.css"/>
    </head>
    <body>
        <div class="search-hotel">
            <div class="search-bar">
                <form action="MainController" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="action" value="searchHotel">
                    <input type="hidden" name="accommodationType" id="accommodationType" value="Khách sạn">
                    <!-- Thành phố -->
                    <div class="search-group-1">
                        <img src="assets/images/location-removebg-preview.png" alt="Location icon" />
                        <input type="text" name="keyword" id="keyword" placeholder="Thành phố, khách sạn, điểm đến" required="required" value="${sessionScope.keyword}"/>
                    </div>

                    <!-- Ngày nhận -->
                    <div class="search-group-2">
                        <img src="assets/images/lịch-removebg-preview.png" alt="Calendar icon" />
                        <input type="date" id="checkin" name="checkin" required="required" value="${sessionScope.checkin}"/>
                    </div>

                    <!-- Ngày trả -->
                    <div class="search-group-3">
                        <img src="assets/images/lịch-removebg-preview.png" alt="Calendar icon" />
                        <input type="date" id="checkout" name="checkout" required="required" value="${sessionScope.checkout}"/>
                    </div>

                    <!-- Khách & phòng -->
                    <div class="search-group-4">
                        <img src="assets/images/lịch-removebg-preview.png" alt="Calendar icon" />

                        <div class="capacity-inputs">
                            <label>
                                <input type="number" name="adults" id="adults" min="1" value="${sessionScope.adults}"/> người lớn
                            </label>
                            <label>
                                <input type="number" name="children" id="children" min="0" value="${sessionScope.children}"/> trẻ em
                            </label>
                            <label>
                                <input type="number" name="rooms" id="rooms" min="1" value="${sessionScope.rooms}"/> phòng
                            </label>
                        </div>
                    </div>

                    <!-- Nút tìm kiếm -->
                    <button class="search-button" type="submit">
                        <img src="assets/images/search (1).png" alt="Search icon"/>
                    </button>
                </form>
            </div>
        </div>
        <script src="assets/js/search.js"></script>
    </body>
</html>
