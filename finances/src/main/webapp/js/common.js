function reloadCharts() {
    drawSummaryCharts();
    drawIncomeCharts();
    drawExpenseCharts();
}

function drawSummaryCharts() {

    var req = {
            "selectedYear" : $("#yearDropdown").val(),
            "selectedMonth" : $("#monthDropdown").val()
        };

    var jsonData = $.ajax({
        type : 'post',
        data : JSON.stringify(req),
        contentType : "application/json",
        url : "/summary/drawSummaryLineChart",
        dataType : "json",
        async : false
    }).responseText;

    var data = new google.visualization.DataTable(jsonData);

    var options = {
        height: 250,
        width: 1000,
        title : 'Summary',
        legend : {
            position : 'top'
        },
        hAxis: {slantedText:true,textStyle:{fontSize:10}},
        explorer: { maxZoomIn: .001 }
    };

    var chart = new google.visualization.LineChart(document.getElementById('summary_line_chart'));

    chart.draw(data, options);
}

function drawIncomeCharts() {

    var req = {
            "selectedYear" : $("#yearDropdown").val(),
            "selectedMonth" : $("#monthDropdown").val()
        };
    
    var jsonData = $.ajax({
        type : 'post',
        data : JSON.stringify(req),
        contentType : "application/json",
        url : "/summary/drawIncomeLineChart",
        dataType : "json",
        async : false
    }).responseText;

    var data = new google.visualization.DataTable(jsonData);

    var options = {
        title : 'Incomes',
        legend : {
            position : 'top'
        },
        hAxis: {slantedText:true,textStyle:{fontSize:10}},
        explorer: { maxZoomIn: .001 }
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('income_column_chart'));

    chart.draw(data, options);
}

function drawExpenseCharts() {

    var req = {
            "selectedYear" : $("#yearDropdown").val(),
            "selectedMonth" : $("#monthDropdown").val()
        };
    
    var jsonData = $.ajax({
        type : 'post',
        data : JSON.stringify(req),
        contentType : "application/json",
        url : "/summary/drawExpenseLineChart",
        dataType : "json",
        async : false
    }).responseText;

    var data = new google.visualization.DataTable(jsonData);

    var options = {
        title : 'Expenses',
        legend : {
            position : 'top'
        },
        hAxis: {slantedText:true,textStyle:{fontSize:10}},
        explorer: { maxZoomIn: .001 }
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('expense_column_chart'));

    chart.draw(data, options);
}

function selectCategory(idx, txnId, category) {

    $('#txn_' + idx).html(category);

    var json = {
        "txnId" : txnId,
        "category" : category
    };

    $.ajax({
        type : 'post',
        data : JSON.stringify(json),
        contentType : "application/json",
        url : "/categoriesSave",
        async : false
    });
}

function populateMonthDropdown() {

    var json = {
            "selectedYear" : $("#yearDropdown").val()
        };
    
    $.ajax({
        type : 'post',
        data : JSON.stringify(json),
        contentType : "application/json",
        url : "/summary/populateMonthDropdown",
        dataType : "json",
        async : false,
        success : function(json) {

            var $monthDropdown = $("#monthDropdown");

            $monthDropdown.empty(); // remove old options
            $.each(json, function(value, key) {

                $monthDropdown.append($("<option></option>").attr("value", key).text(key));
            });
        }
    });

    $('#monthSelect').val();
}

function handleTabSelect() {

}