package com.future.study.spring.boot.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dexterleslie.Chan
 */
@Controller
public class DemoController {
    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(Model model){
        model.addAttribute("value1","value11");
        return "folder1/hello";
    }
}
