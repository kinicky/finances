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

function drawIncomeCharts() {

    var jsonData = $.ajax({
        type : 'post',
        contentType : "application/json",
        url : "/summary/drawIncomeLineChart",
        dataType : "json",
        async : false
    }).responseText;

    var data = new google.visualization.DataTable(jsonData);

    var options = {
        title : 'Incomes',
        legend : {
            position : 'bottom'
        }
    };

    var chart = new google.visualization.LineChart(document.getElementById('income_line_chart'));

    chart.draw(data, options);
}

function drawExpenseCharts() {

    var jsonData = $.ajax({
        type : 'post',
        contentType : "application/json",
        url : "/summary/drawExpenseLineChart",
        dataType : "json",
        async : false
    }).responseText;

    var data = new google.visualization.DataTable(jsonData);

    var options = {
        title : 'Expenses',
        legend : {
            position : 'bottom'
        }
    };

    var chart = new google.visualization.LineChart(document.getElementById('expense_line_chart'));

    chart.draw(data, options);
}

function selectCategory(idx, txnId, category) {
    
    $('#txn_' + idx).html(category);
 
    var json = {"txnId" : txnId, "category" : category};
    
     $.ajax({
        type : 'post',
        data: JSON.stringify(json),
        contentType : "application/json",
        url : "/categoriesSave",
        async : false
    });
}

function handleTabSelect() {
    
}