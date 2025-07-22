<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggin" value="${not empty currentUser}"/>
<c:set var="isAdmin" value="${currentUser.roleID eq 'admin'}"/>
<c:set var="hotel" value="${requestScope.hotel}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add Hotel Form</title>
        <link rel="stylesheet" href="assets/css/hotelForm.css">
    </head>
    <body>
        <c:choose>
            <c:when test="${isAdmin}">
                <div class="form-container">
                    <div class="form-header">Add New Hotel</div>
                    <form action="MainController" method="post">
                        <input type="hidden" name="action" value="addHotel"/>

                        <div class="form-group">
                            <label for="name">Name*:</label>
                            <input type="text" name="name" id="name" required value="${hotel.name}"/>
                            <c:if test="${not empty requestScope.check1}">
                                <div class="error-msg">${requestScope.check1}</div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label for="address">Address*:</label>
                            <input type="text" name="address" id="address" placeholder="e.g 481/15/37 Điện Biên Phủ Hồ Chí Minh" required value="${hotel.address}"/>
                            <c:if test="${not empty requestScope.check2}">
                                <div class="error-msg">${requestScope.check2}</div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label for="city">City*:</label>
                            <select name="city" id="city" required>
                                <option value="">-- Select Type --</option>
                                <option value="Hồ Chí Minh" ${hotel.type eq 'Hồ Chí Minh' ? 'selected' : ''}>Hồ Chí Minh</option>
                                <option value="Đà Nẵng" ${hotel.type eq 'Đà Nẵng' ? 'selected' : ''}>Đà Nẵng</option>
                            </select>
                        </div>

                        <div class="form-group full-width">
                            <label for="imageURL">imageURL</label>
                            <textarea name="imageURL" id="imageURL">${hotel.imageURL}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="rate">Rate:</label>
                            <input type="number" name="rate" id="rate" value="${hotel.rate}"/>
                            <c:if test="${not empty requestScope.check3}">
                                <div class="error-msg">${requestScope.check3}</div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label for="price">Price*:</label>
                            <input type="number" name="price" id="price" required value="${hotel.price}"/>
                            <c:if test="${not empty requestScope.check4}">
                                <div class="error-msg">${requestScope.check4}</div>
                            </c:if>
                        </div>

                        <div class="form-group">
                            <label for="type">Type*:</label>
                            <select name="type" id="type" required>
                                <option value="">-- Select Type --</option>
                                <option value="Khách sạn" ${hotel.city eq 'Khách sạn' ? 'selected' : ''}>Khách sạn</option>
                                <option value="Biệt thự" ${hotel.city eq 'Biệt thự' ? 'selected' : ''}>Biệt thự</option>
                            </select>
                        </div>

                        <div class="form-group full-width">
                            <label>Status:</label>
                            <div class="radio-group">
                                <label>
                                    <input type="radio" name="status" value="true" ${hotel.status ? 'checked' : ''}/> Active
                                </label>
                                <label>
                                    <input type="radio" name="status" value="false" ${not hotel.status ? 'checked' : ''}/> Invalid
                                </label>
                            </div>
                        </div>

                        <div class="form-actions">
                            <input type="submit" value="Add Hotel"/>
                            <input type="reset" value="Reset"/>
                        </div>

                        <c:if test="${not empty message}">
                            <div class="success-msg">${message}</div>
                        </c:if>
                    </form>
                </div>
            </c:when>
        </c:choose>
    </body>
</html>