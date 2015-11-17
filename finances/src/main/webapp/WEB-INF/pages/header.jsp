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
    <!-- MENU -->

    <!-- contains the top header items -->
    <div class="topMenuBox">
        <div style="width: 300px; height: 35px; float: left; text-align: left;">LOGO IMG</div>
        <div style="width: 700px; height: 35px; padding-top: 8px; float: left; text-align: right; font-size: 1.1em;">
            <span class="lightLbl"> build_20151116 | </span> 
            <span class="welcome"> 
            <%
                 UserService userService = UserServiceFactory.getUserService();
                 User user = userService.getCurrentUser();
                 if (user != null) {
                     pageContext.setAttribute("user", user);
             %>
                    Hello, ${fn:escapeXml(user.nickname)}! (<a href="<%=userService.createLogoutURL("/summary")%>">Sign off</a>)
             <%
                 } else {
             %>
                    <a href="<%=userService.createLoginURL("/summary")%>">Sign in</a>.
             <%
                 }
             %>
            </span>
        </div>
    </div>
    <div class="mainMenu">
        <div class="innerMainMenu">
            |
            <div class="menuItem" onclick="handleTabSelect('tabSummary')"><a href="/summary">Summary</a></div>
            |
            <div class="menuItem" onclick="handleTabSelect('tabWithdrawal')">Withdrawal</div>
            |
            <div class="menuItem" onclick="handleTabSelect('tabDeposit')">Deposit</div>
            |
            <div class="menuItem" onclick="handleTabSelect('tabCategories')"><a href="/categories">Categories</a></div>
            |
            <div class="menuItem" onclick="handleTabSelect('tabImport')"><a href="/import">Import Data</a></div>
            |
        </div>
    </div>
    <div class="subMenu">
        <div class="innerMainMenu">
            <div class="subMenuItem" onclick="handleSubMenu('showStatement.htm')">Test 1</div>
            |
            <div class="subMenuItem" onclick="handleSubMenu('showStatement.htm')">Test 2</div>
            |
            <div class="subMenuItem" onclick="handleSubMenu('showStatement.htm')">Test 3</div>
            |
        </div>
    </div>
    <div class="rootContainer">
        <div class="contentBox">