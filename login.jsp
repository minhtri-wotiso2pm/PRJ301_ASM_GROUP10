<%-- 
    Document   : login
    Created on : May 23, 2025, 9:50:25 AM
    Author     : nzero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="redirectCheck" value="${requestScope.redirect}"/>
<c:set var="target" value="${sessionScope.redirectAfterLogin}"/>
<c:set var="userToLogin" value="${requestScope.userID}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/login.css"/>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn}">
                <c:redirect url="welcome.jsp"/>
            </c:when>
            <c:otherwise>
                <div class="login-container">
                    <form action="MainController" method="post" class="login-form">
                        <div class="avatar">
                            <img src="assets/images/img-login (1).png"/>
                        </div>
                        <input type="hidden" name="action" value="login">
                        <div>
                            <label for="userPE">phone/email*: </label>
                            <input type="text" id="userPE" name="userPE" required="required" value="${userToLogin}" ${not empty userToLogin?'readonly':''}/><br/>
                        </div>

                        <c:choose>
                            <c:when test="${requestScope.isRegistedUser eq true and not empty userToLogin}">
                                <div>
                                    <label>password*: </label>
                                    <input type="password" name="password"/><br/>
                                </div>

                                <c:if test="${not empty param.password}">
                                    <a style="color: red">${requestScope.password}</a>
                                </c:if>

                                <div>
                                    <input type="submit" value="Đăng nhập"/>
                                </div>
                            </c:when>

                            <c:when test="${not empty userToLogin}">
                                <p style="color:red;">${requestScope.messageUser}</p>
                                <div>
                                    <input type="button" value="Đăng ký" onclick="window.location.href = 'userForm.jsp'">
                                </div>
                            </c:when>

                            <c:otherwise>
                                <input type="submit" value="Tiếp theo"/>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </body>
</html>