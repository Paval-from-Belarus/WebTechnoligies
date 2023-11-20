<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>User page</title>
</head>
<body>
<h1>
    Hello, ${param.username}
</h1>
<p>
    <fmt:message key="label.changeLang"/>
</p>
<section>
    <c:if test="${param.currentPage != 1}">
        <a href="${pageContext.request.contextPath}/api/client?currentPage=${param.currentPage - 1}">Previous</a>
    </c:if>
    <span>Page = ${param.currentPage}</span>
    <c:if test="${param.currentPage < param.pagesCount}">
        <a href="${pageContext.request.contextPath}/api/client?currentPage=${param.currentPage + 1}">Next</a>
    </c:if>
</section>
<form class="auction-form" action="api/auction/new">
    <label for="price_step">
        <fmt:message key="admin.auction-price-step"/>
    </label>
    <input type="number" id="price_step"/>
    <label for="event-date">
        <fmt:message key="admin.auction-event-date"/>
    </label>
    <input type="date" id="event-date"/>
    <label for="auction-type">
        <fmt:message key="admin.auction-type"/>
    </label>
    <select id="auction-type">
        <option value="1">
            <fmt:message key="auction.type-english"/>
        </option>
        <option value="2">
            <fmt:message key="auction.type-blitz"/>
        </option>
        <option value="3">
            <fmt:message key="auction.type-blind"/>
        </option>
    </select>
    <c:forEach var="lot" items="${param.lots}">
        <jsp:include page="templates/lot.jsp"/>
    </c:forEach>
</form>
</body>
</html>
