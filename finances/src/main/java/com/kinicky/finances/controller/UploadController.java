package com.kinicky.finances.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.objectify.ObjectifyService;
import com.kinicky.finances.Transaction;

@Controller
public class UploadController {

    private final static Logger logger = Logger.getLogger(UploadController.class.getName()); 
    
    @RequestMapping("/upload")
    public ModelAndView upload(HttpServletRequest req, HttpServletResponse resp) {

        logger.info("upload - BEGIN");


        
        logger.info("upload - END");
        
        return new ModelAndView("upload");
    }
    
    @RequestMapping(value = "/uploadFile2", method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) {

        logger.info("uploadFile - BEGIN");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            
            DateFormat df = new SimpleDateFormat("yyyy/mm/dd");
            
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] lineArray = line.replace("\"", "").split(",");

                Transaction txn = new Transaction();
                txn.setBank(lineArray[0]);
                txn.setAccount(lineArray[1]);
                txn.setCode(lineArray[2]);
                //txn.setDate(df.parse(lineArray[3]));
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

                ObjectifyService.ofy().save().entity(txn).now();
                logger.info("saving txn: " + txn.getId() + " - " + txn.getDescription());
            }
            
            
            logger.info("listing......");
            List<Transaction> txns = ObjectifyService.ofy().load().type(Transaction.class)
                    .limit(100) 
                    .list();
            
            for (Transaction savedTxn : txns) {
                logger.info(savedTxn.getId() + " - " + savedTxn.getDescription());
            }
            logger.info("listing DONE");
            
        } catch (IOException e) {
            logger.info("uploadFile - ERROR " + e);
        }

        
        logger.info("uploadFile - END");
        
        return new ModelAndView("upload");
    }
}
