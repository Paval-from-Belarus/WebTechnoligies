<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>
        <fmt:message key="auction.page-title"/>
    </title>
    <script src="${pageContext.request.contextPath}/js/navigation.js"
            type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/lot.css">
</head>
<body>
<%@include file="/templates/navbar.jsp" %>
<section class="auction-page__description">
    <div class="auction-page__title common__title">
        <fmt:message key="auction.type-${auctionType}"/>
    </div>
    <p>
        <fmt:message key="auction.description-${auctionType}"/>
    </p>
</section>
<section class="auction-page__content">
    <table class="auction__common-info">
        <tr>
            <th>
                <fmt:message key="auction.price-step"/>
            </th>
            <td>
                ${priceStep}
            </td>
        </tr>
        <c:if test="${auctionType == 3}">
            <tr>
                <th>
                    <fmt:message key="auction.blind-bet-limit"/>
                </th>
                <td>
                        ${blindAuctionBetLimit}
                </td>
            </tr>
            <tr>
                <th>
                    <fmt:message key="auction.blind-bet-timeout"/>
                </th>
                <td>
                        ${blindAuctionTimeout}
                </td>
            </tr>
        </c:if>
        <c:if test="${auctionType == 2}">
            <tr>
                <th>
                    <fmt:message key="auction.blitz-exclude-count"/>
                </th>
                <td>
                        ${blitzAuctionExcludeCount}
                </td>
            </tr>
        </c:if>
    </table>
</section>
<div>
    The page type is ${pageType}
</div>
<section class="auction-lot-list">
    <c:forEach var="lot" items="${lotList}">
        <%@include file="/templates/lot.jsp" %>
        <c:if test="${empty lot.getCustomerId() && pageType == 0}">
            <form action="${pageContext.request.contextPath}/api/auction/bet/new" method="post">
                <c:choose>
                    <c:when test="${empty lot.getActualPrice()}">
                        <input name="bet" type="number" step="${priceStep}" value="${lot.getStartPrice() + priceStep}"/>
                    </c:when>
                    <c:otherwise>
                        <input name="bet" type="number" step="${priceStep}" value="${lot.getActualPrice() + priceStep}"/>
                    </c:otherwise>
                </c:choose>
                <input name="auction_id" type="hidden" value="${auctionId}"/>
                <input name="lot_id" type="hidden" value="${lot.getId()}"/>
            </form>
        </c:if>
    </c:forEach>
</section>
<%@include file="/templates/footer.jsp" %>
</body>
</html>
