package com.fuller.slowsong.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuller.slowsong.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleUnknowException(Exception ex){
        return new ModelAndView("500.html", Map.of("error", ex.getClass().getSimpleName(), "message", ex.getMessage()));
    }

    @GetMapping("/")
    public ModelAndView index(HttpSession session) throws Exception{
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView login(HttpSession session) throws Exception{
        return new ModelAndView("login.html");
    }
}
