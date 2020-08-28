package com.future.study.ngrok;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/")
public class ApiController {
    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);


    /**
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "event")
    public ResponseEntity<String> event(
            HttpServletRequest request,
            HttpServletResponse response) {
        return ResponseEntity.ok("Hello");
    }

    /**
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "")
    public void redirect(
            HttpServletResponse response) throws IOException {
        // String url = "https://www.alipay.com/?appId=09999988&actionType=toCard&sourceId=bill&bankAccount=%E9%BB%84%E7%BB%A7%E6%9E%97&cardNo=622568***7406&money=&bankMark=GDB&bankName=%E5%B9%BF%E5%8F%91%E9%93%B6%E8%A1%8C&cardIndex=1708251832351956030&cardNoHidden=true&cardChannel=HISTORY_CARD&orderSource=from";
        String url = "https://www.alipay.com/?appId=09999988&actionType=toCard&sourceId=bill&bankAccount=%E5%BC%A0%E4%B8%89&cardNo=622848***9845&money=&bankMark=ABC&bankName=%E4%B8%AD%E5%9B%BD%E5%86%9C%E4%B8%9A%E9%93%B6%E8%A1%8C&cardIndex=1605021025747091843&cardNoHidden=true&cardChannel=HISTORY_CARD&orderSource=from";
        response.sendRedirect(url);
    }
}