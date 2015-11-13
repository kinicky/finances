package com.kinicky.finances.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.objectify.ObjectifyService;
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

            DateFormat df = new SimpleDateFormat("yyyy/mm/dd");

            List<Transaction> txns = new ArrayList<Transaction>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.replace("\"", "").split(",");

                Transaction txn = new Transaction();
                txn.setBank(lineArray[0]);
                txn.setAccount(lineArray[1]);
                txn.setCode(lineArray[2]);
                txn.setDate(lineArray[3]);
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

}