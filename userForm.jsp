<%-- 
    Document   : userForm
    Created on : Jun 9, 2025, 6:17:03 PM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="not empty currentUser"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/userForm.css"/>
    </head>
    <body>
        <div class="login-container">
            <form action="MainController" method="post" class="login-form">
                <div class="avatar">
                    <img src="assets/images/img-login (1).png">
                </div>

                <input type="hidden" name="action" value="register">
                <div>
                    <label for="name">name*: </label>
                    <input type="text" id="name" name="name" required="required"/><br/>
                </div>
                <div>
                    <label for="phone">phone*: </label>
                    <input type="text" id="phone" name="phone" required="required" value="${currentUser.phone}"/><br/>
                </div>
                <div>
                    <label for="email">email*: </label>
                    <input type="text" id="email" name="email" required="required" value="${currentUser.email}"/><br/>
                </div>
                <div>
                    <label>password*: </label>
                    <input type="password" name="password"/><br/>
                </div>
                <div>
                    <input type="submit" value="Đăng ký"/>
                </div>
            </form>
        </div>
    </body>
</html>
