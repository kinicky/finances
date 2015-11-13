package com.kinicky.finances.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.render.JsonRenderer;

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
    
    @RequestMapping(value = "/lineChart", method = RequestMethod.POST)
    public @ResponseBody String lineChart() {

        logger.info("lineChart - BEGIN");
        
        DataTable data = new DataTable();
        ArrayList<ColumnDescription> cols = new ArrayList<ColumnDescription>();
        cols.add(new ColumnDescription("date", ValueType.TEXT, "Date"));
        cols.add(new ColumnDescription("withdrawal", ValueType.NUMBER, "Withdrawal"));
        cols.add(new ColumnDescription("deposit", ValueType.NUMBER, "Deposit"));
        cols.add(new ColumnDescription("balance", ValueType.NUMBER, "Bxalance"));

        data.addColumns(cols);

        try {
            data.addRowFromValues("2004-01-01", 10, 0,590);
            data.addRowFromValues("2004-01-01", 10, 0,520);
            data.addRowFromValues("2004-01-02", 10, 0,540);
            data.addRowFromValues("2004-01-02", 0, 1000,800);
            data.addRowFromValues("2004-01-03", 40, 0,780);
            data.addRowFromValues("2004-01-04", 20, 0,500);
            data.addRowFromValues("2004-01-04", 20, 0,500);
            data.addRowFromValues("2004-01-04", 60, 0,500);
            data.addRowFromValues("2004-01-05", 10, 0,500);
            data.addRowFromValues("2004-01-06", 20, 0,500);
            data.addRowFromValues("2004-01-07", 0, 1000,500);
            data.addRowFromValues("2004-01-08", 10, 0,500);
          } catch (TypeMismatchException e) {
            System.out.println("Invalid type!");
          }
        
        JsonNode root = null;
        String json = JsonRenderer.renderDataTable(data, true, false).toString();

        try{
            JsonParser parser = new JsonFactory().createParser(json)
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
                 root = new ObjectMapper().readTree(parser);
            }catch(Exception e){
               System.out.println(e.toString());
            }
        String result = root.toString();
        logger.info("lineChart - END data: " + result);
        
       return result;
    }

}