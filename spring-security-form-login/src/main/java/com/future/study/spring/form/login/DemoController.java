package com.future.study.spring.form.login;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Dexterleslie.Chan
 */
@Controller
public class DemoController {
    /**
     *
     * @param model
     * @param error
     * @param logout
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model,String error,String logout){
        model.addAttribute("var1","Controller设置的值");
        return "login";
    }

    @RequestMapping(value = "/welcome")
    public String welcome(Model model, Principal principal){
        model.addAttribute("username",principal.getName());
        return "welcome";
    }
}
