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
<div class="user-page__content">
    <div class="user-info">
        <div class="client-info__next-lot">
            <form id="select-table" class="form__select-table" method="post"
                  action="${pageContext.request.contextPath}/api/lot/new">
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
                                <input class="select-table__selector" name="lot_start_price" type="number" step="0.01"
                                       required>
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
    </div>
    <div class="client-lot__history">
        <div class="client-lot-list">
            <jsp:useBean id="lots" scope="request" type="java.util.List"/>
            <c:forEach var="lot" items="${lots}">
                <%@include file="../templates/lot.jsp" %>
            </c:forEach>
        </div>
        <c:if test="${lots.size() > 0}">
            <%@include file="../templates/page-switcher.jsp" %>
        </c:if>
    </div>
</div>
<%@include file="../templates/footer.jsp" %>
</body>
</html>
