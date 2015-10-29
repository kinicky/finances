package com.kinicky.finances.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;
import com.kinicky.finances.Greeting;

@Controller
public class SignController {

    private final static Logger logger = Logger.getLogger(GuestbookController.class.getName()); 
    
    @RequestMapping("/sign")
    public ModelAndView sign(HttpServletRequest req, HttpServletResponse resp) {
        Greeting greeting;

        logger.info("sign - BEGIN");
        
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser(); // Find out who the user is.

        String guestbookName = req.getParameter("guestbookName");
        String content = req.getParameter("content");
        if (user != null) {
            greeting = new Greeting(guestbookName, content, user.getUserId(), user.getEmail());
        } else {
            greeting = new Greeting(guestbookName, content);
        }

        // Use Objectify to save the greeting and now() is used to make the call synchronously as we
        // will immediately get a new page using redirect and we want the data to be present.
        ObjectifyService.ofy().save().entity(greeting).now();

        
        logger.info("sign - END");
        
        return new ModelAndView("guestbook");
    }
}
