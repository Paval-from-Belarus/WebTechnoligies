<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../templates/localization.jsp"/>
<html lang="${param.lang}">
<head>
    <title>Client page</title>
    <link rel="stylesheet" href="../css/roles.css">
</head>
<body>
<h1> Hello, ${param.username}</h1>
<p>
    <fmt:message key="label.changeLang"/>
</p>
<c:if test="${param.currentPage != 1}">
    <a href="${pageContext.request.contextPath}/api/client?currentPage=${param.currentPage - 1}">Previous</a>
</c:if>
<span>Page = ${param.currentPage}</span>
<c:if test="${param.currentPage < param.pagesCount}">
    <a href="${pageContext.request.contextPath}/api/client?currentPage=${param.currentPage + 1}">Next</a>
</c:if>
<section class="client-lots">
    <c:forEach var="lot" items="${param.lots}">
        <div class="client-lot">
            <div class="lot-title">
                    ${lot.getTitle}
            </div>
            <div class="lot-status">
                    ${param.lotStatuses.get(lot.getStatus)}
            </div>
            <div class="lot-start-price">
                    ${lot.getStartPrice}
            </div>
            <c:if test="${lot.getCustomerId != null}">
                <div class="lot-seller">
                    <a href="${pageContext.request.contextPath}/api/client?clientId=${lot.getCustomerId}">
                        Click to see customer page
                    </a>
                </div>
            </c:if>
            <c:if test="${lot.getAuctionId != null}">
                <div class="lot-auction">
                    <a href="${pageContext.request.contextPath}/api/auction?auctionId=${lot.getAuctionId}">
                        Click to see auction where the lot was sold
                    </a>
                </div>
            </c:if>
            <c:if test="${lot.getActualPrice != null}">
                <div class="lot-actual-price">
                    The actual price is ${lot.getActualPrice}
                </div>
            </c:if>
            <div class="lot-ranking">
                The ranking is ${param.feedbacks.get(lot).getRanking}
            </div>
        </div>
    </c:forEach>
</section>
</body>
</html>
