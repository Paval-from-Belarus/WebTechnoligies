<table class="user-lot common-block">
    <div class="lot-title">
        ${lot.getTitle()}
    </div>
    <tbody class="lot-info">
    <c:if test="${pageType} == UserPageType.ADMIN">
        <tr>
            <th>
                <fmt:message key="lot.id"/>
            </th>
            <td>
                    ${lot.getId()}
            </td>
        </tr>
    </c:if>
    <c:if test="${lot.getCustomerId()} != null">
        <tr>
            <th>
                <fmt:message key="lot.customer-title"/>
            </th>
            <td>
                <a href="${pageContext.request.contextPath}/client?client_id=${lot.getCustomerId()}"
                   class="lot-action common-button">
                    <fmt:message key="lot.customer-link"/>
                </a>
            </td>
        </tr>
    </c:if>

    <c:if test="${pageType} == UserPageType.CLIENT">
        <tr>
            <th>
                <fmt:message key="lot.auction-title"/>
            </th>
            <td>
                <c:choose>
                    <c:when test="${lot.getAuctionId()} == null">
                        <div>
                            <a href="${pageContext.request.contextPath}/api/lot/delete?lot_id=${lot.getId()}">
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
    </c:if>
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