<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tạo chuyến bay</title>
        <link rel="stylesheet" href="assets/css/planeCreate.css">

    </head>
    <body>
        <h2>Tạo chuyến bay mới</h2>

        <%-- Hiển thị thông báo nếu có --%>
        <%
            String error = (String) request.getAttribute("error");
            String message = (String) request.getAttribute("message");
        %>
        <% if (error != null) { %>
        <div class="error"><%= error %></div>
        <% } else if (message != null) { %>
        <div class="message"><%= message %></div>
        <% } %>

        <form action="MainController" method="post">
            <input type="hidden" name="action" value="createPlane"/>

            <label>Hãng hàng không:</label>
            <input type="text" name="airline" required/>

            <label>Sân bay đi:</label>
            <input type="text" name="departureAirport" required/>

            <label>Sân bay đến:</label>
            <input type="text" name="arrivalAirport" required/>

            <label>Thời gian khởi hành:</label>
            <input type="datetime-local" name="departureTime" required/>

            <label>Thời gian đến:</label>
            <input type="datetime-local" name="arrivalTime" required/>

            <label>Mã chuyến bay:</label>
            <input type="text" name="flightNumber" required/>

            <input type="submit" value="Tạo chuyến bay"/>
        </form>

        <a href="PlaneController?action=displayAllPlane" class="btn-back">← Quay lại danh sách chuyến bay</a>
    </body>
</html>
