package com.kinicky.finances.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.objectify.ObjectifyService;
import com.kinicky.finances.AvailableYearMonth;
import com.kinicky.finances.Transaction;
import com.kinicky.finances.exception.DuplicatedTransactionException;

@Controller
public class ImportController {

    private final static String LID = "ImportController: ";
    private final static Logger logger = Logger.getLogger(ImportController.class.getName());

    @RequestMapping("/import")
    public ModelAndView show() throws IOException {

        logger.info(LID + "show import - BEGIN");

        logger.info(LID + "show import - END");
        return new ModelAndView("import");
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, Model model) {

        logger.info(LID + "uploadFile - BEGIN");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            boolean endOfStatement = false;
            List<Transaction> txns = new ArrayList<Transaction>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.replace("\"", "").split(",");

                if (StringUtils.isNotBlank(lineArray[0]) && !endOfStatement) {
                    
                    if ("Service charges".equals(lineArray[5]) || "Fixed service charges".equals(lineArray[5])) {
                        endOfStatement = true;
                    }
                    
                    Transaction txn = new Transaction();
                    txn.setBank(lineArray[0]);
                    txn.setAccount(lineArray[1]);
                    txn.setCode(lineArray[2]);
                    txn.setDate(df.parse(lineArray[3]));
                    txn.setLineNumber(Integer.parseInt(lineArray[4]));
                    txn.setDescription(lineArray[5]);
                    txn.setBlank1(lineArray[6]);
                    txn.setWithdrawal(lineArray[7]);
                    txn.setDeposit(lineArray[8]);
                    txn.setBlank2(lineArray[9]);
                    txn.setBlank3(lineArray[10]);
                    txn.setBlank4(lineArray[11]);
                    txn.setBlank5(lineArray[12]);
                    txn.setBalance(lineArray[13]);
     
                    txn.setYearMonth(saveAvailableYearMonth(txn.getDate()));
                    
                    List<Transaction> dbTxns = ObjectifyService.ofy().load().type(Transaction.class)
                        .filter("date", txn.getDate())
                        .filter("description", txn.getDescription())
                        .filter("withdrawal", txn.getWithdrawal())
                        .filter("deposit", txn.getDeposit())
                        .list();
                    
                    logger.info(LID + "dbTxns found: " + dbTxns.size());
                    
                    if (dbTxns.isEmpty()) {
                        logger.info(LID + " - " + txn.getDescription());
                        txns.add(txn);
                    } else {
                        throw new DuplicatedTransactionException("dupplicated txn: " + txn);
                    }
                }
            }
            
            logger.info(LID + " before transactions saved.");
            
            ObjectifyService.ofy().save().entities(txns).now();
            
            logger.info(LID + txns.size() + " transactions saved.");
            
            model.addAttribute("txns", txns);

        } catch (IOException e) {
            logger.info(LID + "uploadFile - ERROR " + e);
        } catch (DuplicatedTransactionException e) {
            logger.info(LID + "DuplicatedTransactionException - ERROR - " + e.getMessage());
        } catch (Exception e) {
            logger.info(LID + "EXCEPTION - ERROR " + e);
        }

        logger.info(LID + "uploadFile - END");

        return new ModelAndView("import");
    }
    
    private String saveAvailableYearMonth(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        logger.info(LID + "saveAvailableYearMonth - date:" + date);
        AvailableYearMonth ym = new AvailableYearMonth(String.valueOf(cal.get(Calendar.YEAR)), String.valueOf(cal.get(Calendar.MONTH)));

        List<AvailableYearMonth> yms = ObjectifyService.ofy().load().type(AvailableYearMonth.class).filter("yearMonth", ym.getYearMonth()).list();
        if (yms.isEmpty()) {
            ObjectifyService.ofy().save().entity(ym).now();
            logger.info(LID + " Year-Month saved: " + ym);
        }
        return ym.getYearMonth();
    }

}