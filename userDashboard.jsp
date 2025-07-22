<%-- 
    Document   : updateDashboard
    Created on : Jul 20, 2025, 9:05:12 PM
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
        <link rel="stylesheet" href="assets/css/userDashboard.css"/>
    </head>
    <body>
        <h2>Dữ liệu cá nhân</h2>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="updateProfile">
            <input type="hidden" name="userID" value="${currentUser.userID}">
            <label for="fullname">Tên đầy đủ</label>
            <input type="text" name="fullname" id="fullname" value="${currentUser.userName}" />

            <p class="hint">Tên trong hồ sơ được rút ngắn từ họ tên của bạn.</p>

            <div class="row">
                <div>
                    <label for="gender">Giới tính</label>
                    <select name="gender" id="gender">
                        <option value="null">--</option>
                        <option value="nam">Nam</option>
                        <option value="nữ">nữ</option>
                        <option value="khác">khác</option>
                    </select>
                </div>
                <div>
                    <label>Ngày sinh</label>
                    <div class="dob">
                        <select name="daySelect" id="daySelect"><option disabled selected>Ngày</option></select>
                        <select name="monthSelect" id="monthSelect"><option disabled selected>Tháng</option></select>
                        <select name="yearSelect" id="yearSelect"><option disabled selected>Năm</option></select>
                    </div>
                </div>
            </div>

            <label for="city">Thành phố cư trú</label>
            <input type="text" name="city" id="city" placeholder="Thành phố cư trú"/>

            <div class="buttons">
                <input type="button" onclick="history.back()" value="Có lẽ để sau">
                <input type="submit" class="save" value="Lưu">
            </div>
        </form>

        <div class="section">
            <h3>Email</h3>
            <div class="email-box">
                <span>1. <strong>${currentUser.email}</strong></span>
                <span class="green">Nơi nhận thông báo</span>
            </div>
        </div>

        <div class="section">
            <h3>Số di động</h3>
            <div class="phone-box">
                <span>1. <strong>${currentUser.phone}</strong></span>
                <span class="green">Nơi nhận thông báo</span>
            </div>
        </div>
        <script src="assets/js/menuOption.js"></script>
    </body>
</html>
