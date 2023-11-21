<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>
        <fmt:message key="application.name"/>
    </title>
    <script src="https://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/navigation.js"
            type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css"
          type="text/css">
</head>
<body>
<%@include file="/templates/navbar.jsp" %>
<div>
    <blockquote>
        Information about site
    </blockquote>
</div>
<div>
    <blockquote>
        Information about nearest auction
    </blockquote>
</div>
<section>
    <div class="authorization-form">
        <h1>
            <fmt:message key="label.authorization-title"/>
        </h1>
        <form id="authorization-form" action="${pageContext.request.contextPath}/api/auth" method="post">
            <label for="auth_user_name"><fmt:message key="label.username"/></label>
            <input name="user_name" type="text" id="auth_user_name" value="" required>
            <label for="auth_password"><fmt:message key="label.password"/></label>
            <input name="password" type="password" id="auth_password" value="" required>
            <input type="submit" value="authorize">
        </form>
    </div>
    <div class="registration-form">
        <h1 class="form-title">
            <fmt:message key="label.registration-title"/>
        </h1>
        <form id="registration-form" action="${pageContext.request.contextPath}/api/reg" method="post">
            <label for="reg_user_name"><fmt:message key="label.username"/></label>
            <input name="user_name" type="text" id="reg_user_name" value="" required>
            <label for="email"><fmt:message key="label.email"/></label>
            <input name="email" type="text" id="email" required>
            <label for="reg-password">
                <fmt:message key="label.password"/>
            </label>
            <input name="password" type="password" id="reg-password" value="" required>
            <label for="role">
                <fmt:message key="label.user-role"/>
            </label>
            <select name="role" id="role">
                <option value="1">
                    <fmt:message key="label.user-role"/>
                </option>
                <option value="2">
                    <fmt:message key="label.admin-role"/>
                </option>
            </select>
            <input type="submit" value="Submit registration">
        </form>
    </div>
</section>
<%@include file="/templates/footer.jsp"%>
</body>
</html>