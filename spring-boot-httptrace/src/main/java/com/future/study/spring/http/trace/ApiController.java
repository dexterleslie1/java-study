package com.future.study.spring.http.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/api")
public class ApiController {
    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="test1")
    public ResponseEntity<String> test1(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "name") String name){
        logger.info("Api for testing is called.");
        return ResponseEntity.ok("Hello " + name);
    }

    /**
     *
     * @param user
     * @param password
     * @return
     */
    @RequestMapping(value = "loginWithPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<LoginSuccessVO>> Login(
            /*@RequestBody @ApiParam(name = "PassWordLoginPojo", value = "pojo") PassWordLoginPojo swaggerModel*/
            @RequestParam(value = "user", defaultValue = "") String user,
            @RequestParam(value = "password", defaultValue = "") String password
    ) {
        LoginSuccessVO loginSuccessVO = new LoginSuccessVO();
        loginSuccessVO.setUserId(8833393);
        loginSuccessVO.setToken(UUID.randomUUID().toString());
        ObjectResponse response = new ObjectResponse();
        response.setData(loginSuccessVO);
        return ResponseEntity.ok(response);
    }
}
