package com.kinicky.finances.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.googlecode.objectify.ObjectifyService;
import com.kinicky.finances.Transaction;

@Controller
public class CategoriesController {

    private final static String LID = "CategoriesController: ";
    private final static Logger logger = Logger.getLogger(CategoriesController.class.getName());

    @RequestMapping("/categories")
    public ModelAndView show(Model model) throws IOException {

        logger.info(LID + "show categories - BEGIN");

        List<Transaction> txns = ObjectifyService.ofy().load().type(Transaction.class).order("date").list();

        model.addAttribute("txns", txns);
        
        logger.info(LID + "show categories - END");
        return new ModelAndView("categories");
    }

    @RequestMapping(value = "/categories/save", method = RequestMethod.POST)
    public ModelAndView save() {

        logger.info(LID + "categories save - BEGIN");

        logger.info(LID + "categories save - END");

        return new ModelAndView("categories");
    }

}