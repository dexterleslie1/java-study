package com.future.study.jQuery.extend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author dexterleslie@gmail.com
 */
@Controller
public class RouterController {
    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/")
    public String hello(Model model){
        return "index.html";
    }
}
