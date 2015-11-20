<%@include file="header.jsp" %>

<script type="text/javascript">
    google.setOnLoadCallback(drawSummaryCharts);
</script>

    <table style="width: 1010px;">
        <tr>
            <td colspan="2">Year: <form:select id="yearDropdown" path="selectedYear" items="${yearList}" onchange="populateMonthDropdown()" /></td>
            <td colspan="3">Month: <form:select id="monthDropdown" path="selectedMonth" items="${monthList}" /></td>
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
    
<%@include file="footer.jsp" %>