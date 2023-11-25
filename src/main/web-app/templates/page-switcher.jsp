
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