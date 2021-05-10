package com.future.demo.security.authorization.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApiController {
    @RequestMapping(value = "index", method = RequestMethod.POST)
    public String index() {
        return "redirect:index.html";
    }

    @RequestMapping(value = "loginFailed", method = RequestMethod.POST)
    public String loginFailed() {
        return "redirect:loginFailed.html";
    }

    // 判断是否拥有admin角色
    @Secured(value = {"ROLE_admin"})
    @RequestMapping(value = "securedAnnotation", method = RequestMethod.GET)
    public String securedAnnotation() {
        return "redirect:securedAnnotation.html";
    }

    // 判断是否拥有admin角色
    @PreAuthorize(value = "hasRole('admin')")
    @RequestMapping(value = "preAuthorize", method = RequestMethod.GET)
    public String preAuthorize() {
        return "redirect:preAuthorize.html";
    }
}
