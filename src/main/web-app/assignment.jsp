<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <title>
        <fmt:message key="label.auction-assignment-page"/>
    </title>
    <script src="${pageContext.request.contextPath}/js/navigation.js"
            type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/lot.css">
</head>
<body>
<div class="admin-page__title common__title">
    Choose lots for auction
</div>
<section>
    <div class="assignment-form">
        <form method="post" action="google.com">
            <label for="price_step">
                Any label
            </label>
            <input name="assignment" type="number" id="price_step" required/>
        </form>
    </div>
    <div class="lot-list">
        <c:forEach var="lot" items="${lots}">
            <%@include file="/templates/lot.jsp"%>
        </c:forEach>
    </div>
</section>
</body>
</html>
