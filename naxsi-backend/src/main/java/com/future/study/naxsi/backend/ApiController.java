package com.future.study.naxsi.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author dexterleslie@gmail.com
 */
@Controller
public class ApiController {
    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/api/v1/update", method = RequestMethod.GET)
    public String update(){
        return "update";
    }
}
