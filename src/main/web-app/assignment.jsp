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
<%@include file="/templates/navbar.jsp" %>
<div class="admin-page__title common__title">
    Choose lots for auction
</div>
<section>
    <c:if test="${lots.size() > 0}">
        <div class="assignment-form">
            <form method="post" action="${pageContext.request.contextPath}/api/lot/assign">
                <table>
                    <tbody>
                    <tr>
                        <th class="select-table__item common__plain">
                            <fmt:message key="label.assign-title"/>
                        </th>
                        <td>
                            <input name="auction_id" value="${auctionId}" hidden="hidden">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <select name="lot_id" class="select-table__selector button-bordered">
                                <c:forEach var="lot" items="${lots}">
                                    <option value="${lot.getId()}">
                                        <div>
                                                ${lot.getId()}
                                        </div>
                                    </option>
                                </c:forEach>
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
            </form>
        </div>
    </c:if>
    <div class="lot-list">
        <c:forEach var="lot" items="${lots}">
            <%@include file="/templates/lot.jsp" %>
        </c:forEach>
    </div>
</section>
<%@include file="/templates/footer.jsp" %>
</body>
</html>
