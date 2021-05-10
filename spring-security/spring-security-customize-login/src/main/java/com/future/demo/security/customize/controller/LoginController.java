package com.future.demo.security.customize.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @RequestMapping(value = "index", method = RequestMethod.POST)
    public String index() {
        return "redirect:index.html";
    }

    @RequestMapping(value = "loginFailed", method = RequestMethod.POST)
    public String loginFailed() {
        return "redirect:loginFailed.html";
    }
}
