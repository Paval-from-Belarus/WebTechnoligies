<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="localization"/>
<html>
<head>
    <title>
        <fmt:message key="label.user-page-name"/>
    </title>
    <script src="${pageContext.request.contextPath}/js/navigation.js"
            type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/lot.css">
</head>
<body>
<%@include file="../templates/navbar.jsp" %>
<div class="client-page__title common__title">
    Hello, ${username}! My dear client...
</div>
<div class="client-page__content">
    <section class="client-info">
        <div class="client-info__next-lot">
            <form id="select-table" class="form__select-table" method="post" action="${pageContext.request.contextPath}/api/lot/create">
                <div class="select-table common-table">
                    <div class="select-table__title common-table__title">
                        <fmt:message key="label.lot-table-title"/>
                    </div>
                    <table class="select-table__body common-table__body">
                        <tbody>
                        <tr>
                            <th class="select-table__item common_plain">
                                <fmt:message key="label.lot-title"/>
                            </th>
                            <td>
                                <input class="select-table__selector" name="lot_title" type="text" required>
                            </td>
                        </tr>
                        <tr>
                            <th class="select-table__item common_plain">
                                <fmt:message key="lot.start-price"/>
                            </th>
                            <td>
                                <input class="select-table__selector" name="lot_start_price" type="number" step="0.01" required>
                            </td>
                        </tr>
                        <tr>
                            <th class="select-table__item common__plain">
                                <fmt:message key="auction.type-title"/>
                            </th>
                            <td>
                                <select id="auction_type_id" name="auction_type_id"
                                        class="select-table__selector button-bordered">
                                    <option value="1" selected="">
                                        <fmt:message key="auction.type-english"/>
                                    </option>
                                    <option value="2">
                                        <fmt:message key="auction.type-blitz"/>
                                    </option>
                                    <option value="3">
                                        <fmt:message key="auction.type-blind"/>
                                    </option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input class="select-table__accept button-bordered" type="submit" value="Submit">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </form>
        </div>
    </section>
    <section class="client-lot__history">
        <div class="client-lot-list">
            <jsp:useBean id="lots" scope="request" type="java.util.List"/>
            <c:forEach  var="lot" items="${lots}" varStatus="lots">
                <table class="client-lot common-block">
                    <div class="lot-title">
                            ${lot.getTitle()}
                    </div>
                    <tbody class="lot-info">
                    <c:if test="${lot.getCustomerId()} != null">
                        <tr>
                            <th>
                                <fmt:message key="lot.customer-title"/>
                            </th>
                            <td>
                                <a href="/client?client_id=${lot.getCustomerId()}" class="lot-action common-button">
                                    <fmt:message key="lot.customer-link"/>
                                </a>
                            </td>
                        </tr>
                    </c:if>
                    <tr>
                        <th>
                            <fmt:message key="lot.auction-title"/>
                        </th>
                        <td>
                            <c:choose>
                                <c:when test="${lot.getAuctionId()} == null">
                                    <div>
                                        <a href="/api/lot/delete?lot_id=${lot.getId()}">
                                            <fmt:message key="lot.delete"/>
                                        </a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="lot-auction">
                                        <a href="${pageContext.request.contextPath}/api/auction?auctionId=${lot.getAuctionId()}">
                                            <fmt:message key="lot.auction-link"/>
                                        </a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr class="lot-status">
                        <th>
                            <fmt:message key="lot.status-title"/>
                        </th>
                        <td>
                            <fmt:message key="lot.status-value-${lot.getStatus()}"/>
                        </td>
                    </tr>
                    <tr class="lot-start-price">
                        <th class="lot-start-price-title">
                            <fmt:message key="lot.start-price"/>
                        </th>
                        <td class="lot-start-price-value">
                                ${lot.getStartPrice()}
                        </td>
                    </tr>
                    <c:if test="${lot.getActualPrice() != null}">
                        <tr class="lot-actual-price">
                            <th class="lot-actual-price-title">
                                <fmt:message key="lot.actual-price"/>
                            </th>
                            <td class="lot-actual-price-value">
                                    ${lot.getActualPrice()}
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${feedbacks.get(lot)} != null">
                        <tr class="lot-ranking">
                            <th class="lot-ranking-title">
                                <fmt:message key="lot.ranking"/>
                            </th>
                            <td>
                                    ${feedbacks.get(lot).getRanking()}
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </c:forEach>
        </div>
        <c:if test="!${lots.isEmpty()}">
            <div class="common__select-switch">
                <input type="text" class="select-switch__chosen-item" readonly="" value="${currentPage}">
                <div class="select-switch__group">
                    <div class="select-switch__middles">
                        <c:if test="${currentPage != 1}">
                            <input type="button" class="button-bordered" id="prev-switcher-item" value="<"
                                   onclick="changePage('${pageContext.request.contextPath}/api/client?currentPage=${currentPage - 1}')">
                        </c:if>
                        <c:if test="${currentPage < pagesCount}">
                            <input type="button" class="button-bordered " id="next-switcher-item" value=">"
                                   onclick="changePage('${pageContext.request.contextPath}/api/client?currentPage=${currentPage + 1}')">
                        </c:if>
                    </div>
                </div>
            </div>
        </c:if>
    </section>
</div>
<%@include file="../templates/footer.jsp" %>
</body>
</html>
