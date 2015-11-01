<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.kinicky.finances.Greeting"%>
<%@ page import="com.kinicky.finances.Guestbook"%>
<%@ page import="com.kinicky.finances.Transaction"%>
<%@ page import="com.googlecode.objectify.Key"%>
<%@ page import="com.googlecode.objectify.ObjectifyService"%>
<%@ page import="java.util.List"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>

<body>

    <%
        String guestbookName = request.getParameter("guestbookName");
        if (guestbookName == null) {
            guestbookName = "default";
        }
        pageContext.setAttribute("guestbookName", guestbookName);
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            pageContext.setAttribute("user", user);
    %>

    <p>
        Hello, ${fn:escapeXml(user.nickname)}! (You can <a href="<%=userService.createLogoutURL("/guestbook")%>">sign out</a>.)
    </p>
    <%
        } else {
    %>
    <p>
        Hello! <a href="<%=userService.createLoginURL("/guestbook")%>">Sign in</a> to include your name with greetings you post.
    </p>
    <%
        }
    %>

    <%
        // Create the correct Ancestor key
        Key<Guestbook> theBook = Key.create(Guestbook.class, guestbookName);

        // Run an ancestor query to ensure we see the most up-to-date
        // view of the Greetings belonging to the selected Guestbook.
        List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class) // We want only Greetings
                .ancestor(theBook) // Anyone in this book
                .order("-date") // Most recent first - date is indexed.
                .limit(5) // Only show 5 of them.
                .list();

        if (greetings.isEmpty()) {
    %>
    <p>Guestbook '${fn:escapeXml(guestbookName)}' has no messages.</p>
    <%
        } else {
    %>
    <p>Messages in Guestbook '${fn:escapeXml(guestbookName)}'.</p>
    <%
        // Look at all of our greetings
            for (Greeting greeting : greetings) {
                pageContext.setAttribute("greeting_content", greeting.content);
                String author;
                if (greeting.author_email == null) {
                    author = "An anonymous person";
                } else {
                    author = greeting.author_email;
                    String author_id = greeting.author_id;
                    if (user != null && user.getUserId().equals(author_id)) {
                        author += " (You)";
                    }
                }
                pageContext.setAttribute("greeting_user", author);
    %>
    <p>
        <b>${fn:escapeXml(greeting_user)}</b> wrote:
    </p>
    <blockquote>${fn:escapeXml(greeting_content)}</blockquote>
    <%
        }
        }

        List<Transaction> txns = ObjectifyService.ofy().load().type(Transaction.class)
                .limit(100) 
                .list();

        if (txns.isEmpty()) {
    %>
    <p>No Transactions</p>
    <%
        } else {
            
            %>
             <table>
             <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Withdrawal</th>
                <th>Deposit</th>
                <th>Balance</th>
             </tr>
            <%
            
            for (Transaction txn : txns) {
                pageContext.setAttribute("date", txn.getDate());
                pageContext.setAttribute("description", txn.getDescription());
                pageContext.setAttribute("withdrawal", txn.getWithdrawal());
                pageContext.setAttribute("deposit", txn.getDeposit());
                pageContext.setAttribute("balance", txn.getBalance());
                %>
                <tr>
                <td><fmt:formatDate type="both" value="${date}" /></td>
                <td>${fn:escapeXml(description)}</td>
                <td>${fn:escapeXml(withdrawal)}</td>
                <td>${fn:escapeXml(deposit)}</td>
                <td>${fn:escapeXml(balance)}</td>
                </tr>
                <%
            }
        }

    %>
            </table>
    <form action="/upload" method="post">
        <div>
            <input type="submit" value="Upload" />
        </div>
    </form>


    <form action="/sign" method="post">
        <div>
            <textarea name="content" rows="3" cols="60"></textarea>
        </div>
        <div>
            <input type="submit" value="Post Greeting" />
        </div>
        <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}" />
    </form>

    <form action="/guestbook" method="get">
        <div>
            <input type="text" name="guestbookName" value="${fn:escapeXml(guestbookName)}" />
        </div>
        <div>
            <input type="submit" value="Switch Guestbook" />
        </div>
    </form>

</body>
</html>