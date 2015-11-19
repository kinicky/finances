package com.kinicky.finances.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.objectify.ObjectifyService;
import com.kinicky.finances.Transaction;

@Controller
public class CategoriesController {

    private final static String LID = "CategoriesController: ";
    private final static Logger logger = Logger.getLogger(CategoriesController.class.getName());

    @RequestMapping(value = "/categoriesShow")
    public ModelAndView show(Model model) throws IOException {

        logger.info(LID + "show categories - BEGIN");

        List<Transaction> txns = ObjectifyService.ofy().load().type(Transaction.class).order("date").list();

        model.addAttribute("txns", txns);
        
        logger.info(LID + "show categories - END txns: " + txns.size());
        return new ModelAndView("categories");
    }

    @RequestMapping(value = "/categoriesSave", method = RequestMethod.POST)
    public @ResponseBody String save(@RequestBody String json) {

        logger.info(LID + "categories save - BEGIN json: " + json );

        
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = new HashMap<String, Object>();

        // convert JSON string to Map
        try {
            map = mapper.readValue(json, new TypeReference<Map<String, String>>(){});
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        Transaction txn = ObjectifyService.ofy().load().type(Transaction.class).id(Long.parseLong((String) map.get("txnId"))).now();
        

        txn.setCategory((String) map.get("category"));
        logger.info(LID + " saving " + txn);
        ObjectifyService.ofy().save().entities(txn).now();

        
        //List<Transaction> txns = ObjectifyService.ofy().load().type(Transaction.class).filter("id", arg1)
        
        logger.info(LID + "categories save - END");
        
        return "AAAA";
    }
    
}