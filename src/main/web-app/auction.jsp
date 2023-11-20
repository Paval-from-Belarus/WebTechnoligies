<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>
        <fmt:message key="auction.pageTitle"/>
    </title>
</head>
<body>
<section>
    <h3>
        <fmt:message key="auction.type-${param.auctionTypeName}"/>
    </h3>
    <p>
        <fmt:message key="auction.description-${param.auctionTypeName}"/>
    </p>
    <table class="auction__common-info">
        <tr>
            <th>
                <fmt:message key="auction.price-step"/>
            </th>
            <td>
                ${param.priceStep}
            </td>
        </tr>
        <c:if test="${param.auctionTypeName == \"blind\"}">
            <tr>
                <th>
                    <fmt:message key="auction.blind-bet-limit"/>
                </th>
                <td>
                        ${param.blindAuctionBetLimit}
                </td>
            </tr>
            <tr>
                <th>
                    <fmt:message key="auction.blind-bet-timeout"/>
                </th>
                <td>
                        ${param.blindAuctionTimeout}
                </td>
            </tr>
        </c:if>
        <c:if test="${param.auctionTypeName} == \"blitz\"">
            <tr>
                <th>
                    <fmt:message key="auction.blitz-exclude-count"/>
                </th>
                <td>
                        ${param.blitzAuctionExcludeCount}
                </td>
            </tr>
        </c:if>
    </table>
</section>
<section class="auction-lot-list">
    <c:forEach var="lot" items="${param.lotList}>">
    <div class="client-lot">
        <div class="lot-title">
                ${lot.getTitle}
        </div>
        <div class="lot-status">
                ${pageContext.request.lotStatuses.get(lot.getStatus)}
        </div>
        <div class="lot-start-price">
                ${lot.getStartPrice}
        </div>
        <div class="lot-seller">
            <a href="${pageContext.request.contextPath}/api/client?clientId=${lot.getCustomerId}">
                Click to see seller page
            </a>
        </div>
        <c:choose>
        <c:when test="${lot.getCustomerId != null}">
        <div class="lot-customer">
            <a href="${pageContext.request.contextPath}/api/client?clientId=${lot.getCustomerId}">
                Click to see customer page
            </a>
        </div>
        </c:when>
        <c:otherwise>
        <form action="${pageContext.request.contextPath}/api/auction/bet/new" method="post">
            <label for="bet">
                <fmt:message key="auction-bet.input-title"/>
            </label>
            <input id="bet" type="number" step="${param.priceStep}" value/>
            <input id="auction_id" type="hidden" value="${param.auctionId}"/>
            <input id="lot_id" , type="hidden" value="${lot.getId}"/>
        </form>
        </c:otherwise>
        </c:choose>
        </c:forEach>
</section>
</body>
</html>
