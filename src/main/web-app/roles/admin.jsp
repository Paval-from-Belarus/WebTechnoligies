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
<div class="admin-page__title common__title">
    Hello, ${username}! The greatest admin...
</div>
<div class="user-page__content">
    <section class="user-info">
        <div class="admin-info__next-auction">
            <form id="select-table" class="form__select-table" method="post"
                  action="${pageContext.request.contextPath}/api/auction/new">
                <div class="select-table common-table">
                    <div class="select-table__title common-table__body">
                        <fmt:message key="label.auction-table-creation"/>
                    </div>
                    <table>
                        <tbody>
                        <tr>
                            <th class="select-table__item common__plain">
                                <label for="price_step">
                                    <fmt:message key="auction.price-step"/>
                                </label>
                            </th>
                            <td>
                                <input name="price_step" type="number" id="price_step" required/>
                            </td>
                        </tr>
                        <tr>
                            <th class="select-table__item common__plain">
                                <label for="event-date">
                                    <fmt:message key="auction.event-date"/>
                                </label>
                            </th>
                            <td>
                                <input name="event_date" type="date" id="event-date" required/>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label for="auction-type">
                                    <fmt:message key="auction.type-title"/>
                                </label>
                            </th>
                            <td>
                                <select id="auction-type" name="auction_type_id">
                                    <option value="1" selected>
                                        <fmt:message key="auction.type-1"/>
                                    </option>
                                    <option value="2">
                                        <fmt:message key="auction.type-2"/>
                                    </option>
                                    <option value="3">
                                        <fmt:message key="auction.type-3"/>
                                    </option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input class="select-table__accept button-bordered" type="submit"
                                       value="<fmt:message key="label.submit-button"/>">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </form>
        </div>
    </section>
    <section class="admin-auction__history">
        <div class="admin-auction-list">
            <jsp:useBean id="auctionList" scope="request" type="java.util.List"/>
            <c:forEach var="auction" items="${auctionList}">
                <table>
                    <thead>
                    #${auction.getId()}
                    </thead>
                    <tr>
                        <th>
                            <fmt:message key="auction.type-title"/>
                        </th>
                        <td>
                            <fmt:message key="auction.type-${auction.getAuctionTypeId()}"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <fmt:message key="auction.event-date"/>
                        </th>
                        <td>
                            <fmt:formatDate value="${auction.getEventDate()}"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="auction-info-link">
                                <a href="${pageContext.request.contextPath}/api/auction/info?auction_id=${auction.getId()}">
                                    <fmt:message key="label.auction-link"/>
                                </a>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="auction-lot-link">
                                <a href="${pageContext.request.contextPath}/api/auction/assign?auction_id=${auction.getId()}">
                                    <fmt:message key="label.auction-assign-lot"/>
                                </a>
                            </div>
                        </td>
                    </tr>
                </table>
            </c:forEach>
            <c:if test="${auctionList.size() > 0}">
                <%@include file="../templates/page-switcher.jsp" %>
            </c:if>
        </div>
    </section>
</div>
</body>
</html>
