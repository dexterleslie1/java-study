package com.future.demo.java.spring.boot.internationalization;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dexterleslie@gmail.com
 */
@Controller
public class SpringInternationalizationController {

    @GetMapping("/")
    public String view() {
        return "i18n";
    }

}
