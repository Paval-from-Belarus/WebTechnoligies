<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<jsp:include page="templates/localization.jsp"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Main page</title>
    <script src="https://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>
    <script src="./js/lobby.js" type="text/javascript"></script>
</head>
<body>
<section>
    <blockquote>
        Information about site
    </blockquote>
</section>
<section>
    <blockquote>
        Information about nearest auction
    </blockquote>
</section>
<section>
    <div class="authorization-form">
        <h1>
            <fmt:message key="label.authorizationTitle"/>
        </h1>
        <form id="authorization-form" action="${param.request.contextPath}/api/auth" method="post">
            <label for="auth_user_name"><fmt:message key="label.username"/></label>
            <input type="text" id="auth_user_name" value="">
            <label for="auth_password"><fmt:message key="label.password"/></label>
            <input type="password" , id="auth_password" value="">
            <input type="button" value="authorize">
        </form>
    </div>
    <div class="registration-form">
        <h1 class="form-title">
            <fmt:message key="label.registrationTitle"/>
        </h1>
        <form id="registration-form" action="${param.request.contextPath}/api/reg" method="post">
            <label for="reg-username"><fmt:message key="label.username"/></label>
            <input type="text" id="reg-username" value="">
            <label for="user-email"><fmt:message key="label.email"/></label>
            <input type="text" id="user-email">
            <label for="reg-password"><fmt:message key="label.password"/></label>
            <input type="password" id="reg-password" value="">
            <label for="admin-role"><fmt:message key="label.adminRole"/></label>
            <input type="checkbox" id="admin-role">
            <input type="button", value="register">
        </form>
    </div>
</section>
</body>
</html>