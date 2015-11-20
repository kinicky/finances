package com.kinicky.finances.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.render.JsonRenderer;
import com.googlecode.objectify.ObjectifyService;
import com.kinicky.finances.AvailableYearMonth;
import com.kinicky.finances.Transaction;
import com.kinicky.finances.common.FinancesHelper;

@Controller
public class SummaryController {

    private final static String LID = "SummaryController: ";
    private final static Logger logger = Logger.getLogger(SummaryController.class.getName());

    @RequestMapping("/summary")
    public ModelAndView show(Model model) throws IOException {

        logger.info(LID + "show summary - BEGIN");

        Map<String, String> yearList = new LinkedHashMap<String, String>();
        Map<String, String> monthList = new LinkedHashMap<String, String>();
        String mostRecentYear = "";
        
        List<AvailableYearMonth> yms = ObjectifyService.ofy().load().type(AvailableYearMonth.class).order("yearMonth").list();
        
        if (!yms.isEmpty()) {
            mostRecentYear = yms.get(0).getYear();
            for (AvailableYearMonth ym : yms) {
                
                yearList.putIfAbsent(ym.getYear(), ym.getYear());
                
                if (StringUtils.equals(ym.getYear(), mostRecentYear)) {
                    monthList.putIfAbsent(ym.getMonth(), ym.getMonth());
                }
            }
            
            model.addAttribute("yearList", yearList);
            model.addAttribute("monthList", monthList);
        }

        String selectedYear = "";
        String selectedMonth = "";
        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("selectedMonth", selectedMonth);
        
        logger.info(LID + "show summary - END");
        return new ModelAndView("summary");
    }

    @RequestMapping(value = "/drawSummaryLineChart", method = RequestMethod.POST)
    public @ResponseBody String drawSummaryLineChart() {

        logger.info(LID + "drawSummaryLineChart - BEGIN");

        DataTable data = new DataTable();
        ArrayList<ColumnDescription> cols = new ArrayList<ColumnDescription>();
        cols.add(new ColumnDescription("date", ValueType.TEXT, "Date"));
        cols.add(new ColumnDescription("withdrawal", ValueType.NUMBER, "Withdrawal"));
        cols.add(new ColumnDescription("deposit", ValueType.NUMBER, "Deposit"));
        cols.add(new ColumnDescription("balance", ValueType.NUMBER, "Balance"));

        data.addColumns(cols);

        try {
            List<Transaction> txns = ObjectifyService.ofy().load().type(Transaction.class).order("date").list();
            for (Transaction txn : txns) {
                data.addRowFromValues(txn.getDate(), FinancesHelper.parseDouble(txn.getWithdrawal()), FinancesHelper.parseDouble(txn.getDeposit()), FinancesHelper.parseDouble(txn.getBalance()));
            }
        } catch (TypeMismatchException e) {
            System.out.println("Invalid type!");
        }

        JsonNode root = null;
        String json = JsonRenderer.renderDataTable(data, true, false).toString();

        try {
            JsonParser parser = new JsonFactory().createParser(json).enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                    .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
            root = new ObjectMapper().readTree(parser);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        String result = root.toString();
        logger.info(LID + "drawSummaryLineChart - END data: " + result);

        return result;
    }
    
    @RequestMapping(value = "/drawIncomeLineChart", method = RequestMethod.POST)
    public @ResponseBody String drawIncomeLineChart() {

        logger.info(LID + "drawIncomeLineChart - BEGIN");

        DataTable data = new DataTable();
        ArrayList<ColumnDescription> cols = new ArrayList<ColumnDescription>();
        cols.add(new ColumnDescription("date", ValueType.TEXT, "Date"));
        cols.add(new ColumnDescription("withdrawal", ValueType.NUMBER, "Withdrawal"));
        cols.add(new ColumnDescription("deposit", ValueType.NUMBER, "Deposit"));
        cols.add(new ColumnDescription("balance", ValueType.NUMBER, "Balance"));

        data.addColumns(cols);

        try {
            List<Transaction> txns = ObjectifyService.ofy().load().type(Transaction.class).order("date").list();
            for (Transaction txn : txns) {
                data.addRowFromValues(txn.getDate(), FinancesHelper.parseDouble(txn.getWithdrawal()), FinancesHelper.parseDouble(txn.getDeposit()), FinancesHelper.parseDouble(txn.getBalance()));
            }
        } catch (TypeMismatchException e) {
            System.out.println("Invalid type!");
        }

        JsonNode root = null;
        String json = JsonRenderer.renderDataTable(data, true, false).toString();

        try {
            JsonParser parser = new JsonFactory().createParser(json).enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                    .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
            root = new ObjectMapper().readTree(parser);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        String result = root.toString();
        logger.info(LID + "drawIncomeLineChart - END data: " + result);

        return result;
    }
    
    @RequestMapping(value = "/drawExpenseLineChart", method = RequestMethod.POST)
    public @ResponseBody String drawExpenseLineChart() {

        logger.info(LID + "drawExpenseLineChart - BEGIN");

        DataTable data = new DataTable();
        ArrayList<ColumnDescription> cols = new ArrayList<ColumnDescription>();
        cols.add(new ColumnDescription("date", ValueType.TEXT, "Date"));
        cols.add(new ColumnDescription("withdrawal", ValueType.NUMBER, "Withdrawal"));
        cols.add(new ColumnDescription("deposit", ValueType.NUMBER, "Deposit"));
        cols.add(new ColumnDescription("balance", ValueType.NUMBER, "Balance"));

        data.addColumns(cols);

        try {
            List<Transaction> txns = ObjectifyService.ofy().load().type(Transaction.class).order("date").list();
            for (Transaction txn : txns) {
                data.addRowFromValues(txn.getDate(), FinancesHelper.parseDouble(txn.getWithdrawal()), FinancesHelper.parseDouble(txn.getDeposit()), FinancesHelper.parseDouble(txn.getBalance()));
            }
        } catch (TypeMismatchException e) {
            System.out.println("Invalid type!");
        }

        JsonNode root = null;
        String json = JsonRenderer.renderDataTable(data, true, false).toString();

        try {
            JsonParser parser = new JsonFactory().createParser(json).enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                    .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
            root = new ObjectMapper().readTree(parser);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        String result = root.toString();
        logger.info(LID + "drawExpenseLineChart - END data: " + result);

        return result;
    }
    
    @RequestMapping(value = "/populateMonthDropdown", method = RequestMethod.POST)
    public @ResponseBody String populateMonthDropdown(@RequestBody String json) {
        ObjectMapper mapper = new ObjectMapper();

        logger.info(LID + "populateMonthDropdown - BEGIN data: " + json);
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        try {
            map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String selectedYear = (String) map.get("selectedYear");
        List<AvailableYearMonth> yms = ObjectifyService.ofy().load().type(AvailableYearMonth.class).filter("year", selectedYear).list();
        
        List<String> monthList = new ArrayList<String>();

        for (AvailableYearMonth ym : yms) {
            monthList.add(ym.getMonth());
        }
        
        String result = "";
        try {
            result = mapper.writeValueAsString(monthList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info(LID + "populateMonthDropdown - END data: " + result);
        
        return result;
    }

}