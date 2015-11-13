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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script type="text/javascript"
          src="https://www.google.com/jsapi?autoload={
            'modules':[{
              'name':'visualization',
              'version':'1',
              'packages':['corechart']
            }]
          }"></script>

    <script type="text/javascript">
      google.setOnLoadCallback(drawChart);

      function drawChart() {
          
          var jsonData = $.ajax({
              type : 'post',
              contentType: "application/json",
              url: "/guestbook/lineChart",
              dataType: "json",
              async: false
            }).responseText;

          
          alert("HI: " + jsonData);

            var data = new google.visualization.DataTable(jsonData);
          
          /*
          var data = new google.visualization.DataTable();
          data.addColumn('string', 'Date');
          data.addColumn('number', 'Withdrawal');
          data.addColumn('number', 'Deposit');
          data.addColumn('number', 'Balance');
          data.addRows([
                        ['2004-01-01', 10, 0, 500],
                        ['2004-01-01', 4, 0, 500],
                        ['2004-01-02', 0, 1120, 500],
                        ['2004-01-02', 40, 0, 490],
                        ['2004-01-02', 10, 0, 480],
                        ['2004-01-02', 50, 0, 450],
                        ['2004-01-03', 70, 0, 440],
                        ['2004-01-03', 70, 0, 440],
                        ['2004-01-04', 10, 0, 300],
                        ['2004-01-04', 90, 0, 300],
                        ['2004-01-05', 0, 1120, 800],
                        ['2004-01-06', 20, 0, 800],
                        ['2004-01-07', 30, 0, 800],
                        ['2004-01-07', 10, 0, 800]        
		  ]);
*/

        var options = {
          title: 'Company Performance',
          legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
      }
    </script>

</head>

<body>

<div id="curve_chart" style="width: 900px; height: 500px"></div>

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