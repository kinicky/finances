<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.googlecode.objectify.Key"%>
<%@ page import="com.googlecode.objectify.ObjectifyService"%>
<%@ page import="com.kinicky.finances.Transaction"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>

<body>

    
    <%

        List<Transaction> txns = ObjectifyService.ofy().load().type(Transaction.class)
                .limit(100) 
                .list();

        if (txns.isEmpty()) {
    %>
    <p>No Transactions</p>
    <%
        } else {

            %>
            <b>Date - Description - Withdrawal - Deposit - Balance</b>
            <%
            
            for (Transaction txn : txns) {
                pageContext.setAttribute("date", txn.getDate());
                pageContext.setAttribute("description", txn.getDescription());
                pageContext.setAttribute("withdrawal", txn.getWithdrawal());
                pageContext.setAttribute("deposit", txn.getDeposit());
                pageContext.setAttribute("balance", txn.getBalance());
                %>
                <b>${fn:escapeXml(date)} - ${fn:escapeXml(description)} - ${fn:escapeXml(withdrawal)} - ${fn:escapeXml(deposit)} - ${fn:escapeXml(balance)}</b>
                <%
            }
        }

    %>
    

    <form action="/uploadFile" method="post" enctype="multipart/form-data">
        <div>
            <input type="file" name="file">
            <input type="submit" value="Upload" />
        </div>
    </form>


</body>
</html>