package com.kinicky.finances.controller;

import java.io.IOException;
import java.util.Properties;
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
public class GuestbookController {

    private final static Logger logger = Logger.getLogger(GuestbookController.class.getName()); 
    
    public ModelAndView helloWorld() {

        String message = "<br><div style='text-align:center;'>"
                + "<h3>********** Hello World, Spring MVC Tutorial</h3>This message is coming from CrunchifyHelloWorld.java **********</div><br><br>";
        return new ModelAndView("welcome", "message", message);
    }
    
    @RequestMapping("/guestbook")
    public ModelAndView guestbook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
        logger.info("guestbook - BEGIN");
        
        if (req.getParameter("testing") == null) {
            /*resp.setContentType("text/plain");
            resp.getWriter().println("Hello, this is a testing servlet. \n\n");
            Properties p = System.getProperties();
            p.list(resp.getWriter());*/
            logger.info("IF");
            
        } else {
            logger.info("ELSE");
            UserService userService = UserServiceFactory.getUserService();
            User currentUser = userService.getCurrentUser();

            if (currentUser != null) {
                resp.setContentType("text/plain");
                resp.getWriter().println("Hello, " + currentUser.getNickname());
            } else {
                logger.info("URI: " + req.getRequestURI());
                logger.info("URL: " + req.getRequestURL());
                resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
            }
        }
        logger.info("guestbook - END");
        return new ModelAndView("guestbook");
    }

}