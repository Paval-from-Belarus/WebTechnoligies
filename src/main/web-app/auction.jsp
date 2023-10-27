<%--
  Created by IntelliJ IDEA.
  User: Paval
  Date: 27/10/2023
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auction</title>
</head>
<body>
    <h1 class="auction-name">
        ${auction_name}
    </h1>
    <h2 class="auction-manager">
        ${auction_manager}
    </h2>
    <section class="auction-item">
        <h3 class="auction-item-name">
            ${auction-item-name}
        </h3>
        <p class="auction-item-description">
            ${auction-item-description}
        </p>
    </section>
    <section class="lot-bet">
        <input type="texl" value=${}>
    </section>
</body>
</html>
