<%@include file="header.jsp" %>

    <table>
        <tr>
            <td colspan="5">
                <c:if test="${empty txns}">
                    <br />
                    <br />
                    <p>No Transactions found</p>
                </c:if> 
                <c:if test="${not empty txns}">
                    <br />
                    <br />
                    ${fn:length(txns)} transactions found:
                    <table>
                        <tr>
                            <th>Date</th>
                            <th>Description</th>
                            <th>Withdrawal</th>
                            <th>Deposit</th>
                            <th>Balance</th>
                        </tr>
                        <c:forEach items="${txns}" var="txn">
                            <tr>
                                <td>${fn:escapeXml(txn.date)}</td>
                                <td>${fn:escapeXml(txn.description)}</td>
                                <td>${fn:escapeXml(txn.withdrawal)}</td>
                                <td>${fn:escapeXml(txn.deposit)}</td>
                                <td>${fn:escapeXml(txn.balance)}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </td>
        </tr>
        </table>
<%@include file="footer.jsp" %>