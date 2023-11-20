<%--@elvariable id="lot" type="by.bsuir.poit.bean.Lot"--%>
<div class="lot-title">
    ${lot.getTitle}
</div>
<div class="lot-start-price">
    ${lot.getStartPrice}
</div>
<c:if test="${lot.getCustomerId != null}">
    <div class="lot-customer">
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