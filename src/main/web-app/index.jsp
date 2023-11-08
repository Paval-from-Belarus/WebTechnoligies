<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
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
    <form id="authorization-form"action="${pageContext.request.contextPath}/api/auth" method="post">
        <table>
            <thead>
            Authorization form
            </thead>
            <tbody>
            <tr>
                <th>
                    User name
                </th>
                <td>
                    <input type="text" id="auth_user_name" value="">
                </td>
            </tr>
            <tr>
                <th>
                    Password
                </th>
                <td>
                    <input type="text" id="auth_password" value="">
                </td>
            </tr>
            </tbody>
        </table>
        <input type="button" value="Authenticate" onclick="onAuthorizationBtnClicked()">
    </form>
    <form id="registration-form" action="${pageContext.request.contextPath}/api/reg" method="post">
        <table>
            <thead>
            Registration form
            </thead>
            <tbody>
            <tr>
                <th>
                    User name
                </th>
                <td>
                    <input type="text" id="reg_user_name" value="">
                </td>
            </tr>
            <tr>
                <th>Password</th>
                <td>
                    <input type="password" id="reg_password" value="">
                </td>
            </tr>
            <tr>
                <th>Email</th>
                <td>
                    <input type="email" id="user_email" value="">
                </td>
            </tr>
            <tr>
                <th>Admin role</th>
                <td>
                    <input type="checkbox" id="admin_role">
                </td>
            </tr>
            </tbody>
        </table>
        <input type="button" value="Registry" onclick="onRegistrationBtnClicked()">
    </form>
</section>
</body>
</html>