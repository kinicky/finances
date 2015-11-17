<%@include file="header.jsp" %>

    <table>
        <tr>
            <td colspan="5">
                <br />
                <br />
                <form action="/uploadFile" method="post" enctype="multipart/form-data">
                    <div>
                        <input type="file" name="file"> <input type="submit" value="Upload" />
                    </div>
                </form>
                <c:if test="${not empty txns}">
                    <br />
                    <br />
                    Imported transactions:
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