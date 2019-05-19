package com.xy.demo.swagger;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Api(value = "用户api", description = "用户增删改查api")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    /**
     * 传递单个参数
     * @param loginname
     * @param password
     * @return
     */
    @ApiOperation(value = "新增用户", notes = "新增用户对象")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "loginname", value = "用户登录s名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query")})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(
            @RequestParam(defaultValue = "") String loginname,
            @RequestParam(defaultValue = "") String password) {
        return "ok";
    }


    /**
     * 对象参数
     * @param swaggerModel
     * @return
     */
    @RequestMapping(value = "/addModel", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户", notes = "新增用户对象")
    public String add(@RequestBody @ApiParam(name="用户",value="Model")SwaggerModel swaggerModel) {
        return "ok";
    }



    /**
     * 对象参数
     * @param userModel
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户", notes = "新增用户对象")
    public String addUser(@RequestBody @ApiParam(name="用户",value="Model")UserModel userModel) {
        return "ok";
    }
}
