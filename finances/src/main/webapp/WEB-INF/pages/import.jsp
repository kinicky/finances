<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />

</head>

<body>

    <%
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            pageContext.setAttribute("user", user);
    %>
    <p>
        Hello, ${fn:escapeXml(user.nickname)}! (You can <a href="<%=userService.createLogoutURL("/summary")%>">sign out</a>.)
    </p>
    <%
        } else {
    %>
    <p>
        Hello! <a href="<%=userService.createLoginURL("/summary")%>">Sign in</a> to include your name with greetings you post.
    </p>
    <%
        }
    %>

    <table>
        <tr>
            <td><a href="/summary">Summary</a></td>
            <td>Income</td>
            <td>Expenses</td>
            <td><a href="/categories">Categories</a></td>
            <td><a href="/import">Import data</a></td>
        </tr>
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
</body>
</html>