<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
    google.setOnLoadCallback(drawSummaryCharts);

    function drawSummaryCharts() {

        var jsonData = $.ajax({
            type : 'post',
            contentType : "application/json",
            url : "/summary/drawSummaryLineChart",
            dataType : "json",
            async : false
        }).responseText;

        var data = new google.visualization.DataTable(jsonData);

        var options = {
            title : 'Summary',
            legend : {
                position : 'bottom'
            }
        };

        var chart = new google.visualization.LineChart(document.getElementById('summary_line_chart'));

        chart.draw(data, options);
    }
</script>
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
            <td colspan="2">Year:</td>
            <td colspan="3">Month:</td>
        </tr>
        <tr>
            <td colspan="5">Summary of 2015
                <div id="summary_line_chart"></div>
                <div id="summary_pie_chart"></div>
            </td>
        </tr>
        <tr>
            <td colspan="5">Income summary of 2015
                <div id="income_line_chart"></div>
                <div id="income_pie_chart"></div>
            </td>
        </tr>
        <tr>
            <td colspan="5">Expenses summary of 2015
                <div id="income_line_chart"></div>
                <div id="income_pie_chart"></div>
            </td>
        </tr>
    </table>
</body>
</html>