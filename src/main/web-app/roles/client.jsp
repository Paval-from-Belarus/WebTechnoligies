<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Client page</title>
    <link rel="stylesheet" href="../css/roles.css">
</head>
<body>
<h1> Hello, ${requestScope.username}</h1>
<c:if test="${requestScope.currentPage != 1}">
    <a href="${pageContext.request.contextPath}/api/client?currentPage=${requestScope.currentPage - 1}">Previous</a>
</c:if>
<span>Page = ${requestScope.currentPage}</span>
<c:if test="${requestScope.currentPage < requestScope.pagesCount}">
    <a href="${pageContext.request.contextPath}/api/client?currentPage=${requestScope.currentPage + 1}">Next</a>
</c:if>
<section class="client-next-lot">
    <form method="post" action="/api/">

    </form>
</section>
<section class="client-lots">
    <c:forEach var="lot" items="${requestScope.lots}">
        <div class="client-lot">
            <div class="lot-title">
                    ${lot.getTitle}
            </div>
            <div class="lot-status">
                    ${requestScope.lotStatuses.get(lot.getStatus)}
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
                The ranking is ${requestScope.feedbacks.get(lot).getRanking}
            </div>
        </div>
    </c:forEach>
</section>
</body>
</html>
