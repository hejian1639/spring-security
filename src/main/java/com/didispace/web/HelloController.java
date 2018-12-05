package com.didispace.web;

import com.didispace.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.didispace.com
 */
@Controller
public class HelloController {

    @RequestMapping({"/"})
    public String index() {
        return "index";
    }


    @RequestMapping("/hello")
    public String hello() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (null != securityContext) {
            Authentication authentication = securityContext.getAuthentication();
            if (null != authentication) {
                Object loginUser = authentication.getPrincipal();
                System.out.println(loginUser);
            }
        }

        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginwithsso", method = RequestMethod.GET)
    @ResponseBody
    public void loginWithSSO(@RequestParam("user") String user, @RequestParam("password") String password,
                             HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {
        request.login(user, password);
    }



}