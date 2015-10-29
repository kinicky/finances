package com.kinicky.finances.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {

    private final static Logger logger = Logger.getLogger(UploadController.class.getName()); 
    
    @RequestMapping("/upload")
    public ModelAndView upload(HttpServletRequest req, HttpServletResponse resp) {

        logger.info("upload - BEGIN");


        
        logger.info("upload - END");
        
        return new ModelAndView("upload");
    }
    
    @RequestMapping("/uploadFile")
    public ModelAndView uploadFile(HttpServletRequest req, HttpServletResponse resp) {

        logger.info("uploadFile - BEGIN");


        
        logger.info("uploadFile - END");
        
        return new ModelAndView("upload");
    }
}
