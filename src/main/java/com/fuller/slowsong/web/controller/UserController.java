package com.fuller.slowsong.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuller.slowsong.entity.User;
import com.fuller.slowsong.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    public static final String KEY_USER = "__user__";

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
        User user = (User) session.getAttribute(KEY_USER);
        Map<String, Object> model = new HashMap<>();
        if (user != null){
            model.put("user", model);
        }
        return new ModelAndView("index.html", model);
    }

    @GetMapping("/signIn")
    public ModelAndView signIn(HttpSession session) throws Exception{
        User user = (User) session.getAttribute(KEY_USER);
        if (user != null){
            return new ModelAndView("redirect:/home");
        }
        return new ModelAndView("signIn.html");
    }

    @PostMapping("/signIn")
    public ModelAndView doSignIn(@RequestParam("email") String email,
                                 @RequestParam("password") String password, HttpSession session) throws Exception{
        try {
            User user = userService.signIn(email, password);
            session.setAttribute(KEY_USER, user);
        } catch (RuntimeException e){
            return new ModelAndView("signIn.html", Map.of("email", email, "error", "SignIn failed"));
        }
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/signOut")
    public String signOut(HttpSession session) throws Exception{
        session.removeAttribute(KEY_USER);
        return "redirect:/signIn";
    }

    @GetMapping("/register")
    public ModelAndView register() throws Exception{
        return new ModelAndView("register.html");
    }

    @PostMapping("/register")
    public ModelAndView doRegister(@RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("name") String name) throws Exception{
        try {
            User user = userService.register(email, password, name);
            logger.info("user register:{}", user.getEmail());
        } catch (RuntimeException e){
            return new ModelAndView("register.html", Map.of("email", email, "error", "Register failed"));
        }
        return new ModelAndView("redirect:/signIn");
    }

    @GetMapping("/home")
    public ModelAndView home(HttpSession session) throws Exception{
        User user = (User) session.getAttribute(KEY_USER);
        if (user == null){
            return new ModelAndView("redirect:/signIn");
        }
        return new ModelAndView("home.html", Map.of("user", user));
    }

    @GetMapping("/newOperation")
    public ModelAndView newOperation() throws Exception{
        throw new UnsupportedOperationException("Not supported yet!");
    }
}
