package com.xy.demo.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Api(value = "用户api", description = "用户增删改查api")
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final static Logger logger = Logger.getLogger(UserController.class);

    @ApiOperation(value = "新增用户", notes = "新增用户对象")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "loginname", value = "用户登录名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query")})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(
            @RequestParam(defaultValue = "") String loginname,
            @RequestParam(defaultValue = "") String password) {
        logger.info(String.format("添加用户%s", loginname));
        return "ok";
    }
}
