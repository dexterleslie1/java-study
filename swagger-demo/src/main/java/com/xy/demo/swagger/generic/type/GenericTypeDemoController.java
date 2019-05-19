package com.xy.demo.swagger.generic.type;

import com.xy.demo.swagger.SwaggerModel;
import com.xy.demo.swagger.UserModel;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Api(value = "演示BaseResponse", description = "演示BaseResponse")
@RestController
@RequestMapping(value = "/demo")
public class GenericTypeDemoController {
    /**
     * 演示BaseResponse<UserModel>
     * @param loginname
     * @param password
     * @return
     */
    @ApiOperation(value = "演示BaseResponse<UserModel>", notes = "演示BaseResponse<UserModel>")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "loginname", value = "用户登录名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 50001, message = "手机号码已注册", response = UserModel.class),
            @ApiResponse(code = 50002, message = "手机号码格式错误", response = SwaggerModel.class),
            @ApiResponse(code = 50003, message = "请填写手机号码")})
    @RequestMapping(value = "/base/response/user", method = RequestMethod.POST)
    public ResponseEntity<UserModel> demoUserModel(
            @RequestParam(defaultValue = "") String loginname,
            @RequestParam(defaultValue = "") String password) {
        UserModel user = new UserModel();
        return ResponseEntity.ok(user);
    }

    /**
     * 演示BaseResponse<SwaggerModel> 分页数据
     * @param loginname
     * @param password
     * @return
     */
    @ApiOperation(value = "演示BaseResponse<Swagger>", notes = "演示BaseResponse<Swagger>")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "loginname", value = "用户登录名", required = true, dataType = "String", paramType = "query"),
                    @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 50001, message = "手机号码已注册", response = UserModel.class),
            @ApiResponse(code = 50002, message = "手机号码格式错误", response = SwaggerModel.class),
            @ApiResponse(code = 50003, message = "请填写手机号码")})
    @RequestMapping(value = "/base/response/swaggerPage", method = RequestMethod.POST)
    public ResponseEntity<Pagination<SwaggerModel>> demoSwaggerModel(
            @RequestParam(defaultValue = "") String loginname,
            @RequestParam(defaultValue = "") String password) {
        Pagination<SwaggerModel> pagination = new Pagination<>();
        List<SwaggerModel> models = new ArrayList();
        SwaggerModel model = new SwaggerModel();
        models.add(model);
        model = new SwaggerModel();
        models.add(model);
        pagination.setDataObject(models);
        return ResponseEntity.ok(pagination);
    }


    /**
     * 演示单一对象返回以及对象参数
     * @param swaggerModel
     * @return
     */
    @ApiOperation(value = "演示BaseResponse<Swagger>传输单一对象，以及返回", notes = "演示BaseResponse<Swagger>")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "loginname", value = "用户登录名", required = true, dataType = "String", paramType = "query"),
                    @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 50001, message = "手机号码已注册", response = UserModel.class),
            @ApiResponse(code = 50002, message = "手机号码格式错误", response = SwaggerModel.class),
            @ApiResponse(code = 50003, message = "请填写手机号码")})
    @RequestMapping(value = "/base/response/swagger", method = RequestMethod.POST)
    public ResponseEntity<SwaggerModel> SwaggerModel(@RequestBody @ApiParam(name = "swagger",value="Model")SwaggerModel swaggerModel) {
        SwaggerModel model = new SwaggerModel();
        model.setLiuning("liuning");
        model.setName(24);
        return ResponseEntity.ok(model);
    }


    /**
     * 演示单一对象返回以及对象参数
     * @param swaggerModel
     * @return
     */
    @ApiOperation(value = "演示BaseResponse<Swagger>", notes = "演示BaseResponse<Swagger>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 50001, message = "手机号码已注册", response = UserModel.class),
            @ApiResponse(code = 50002, message = "手机号码格式错误", response = SwaggerModel.class),
            @ApiResponse(code = 50003, message = "请填写手机号码")})
    @RequestMapping(value = "/base/response/swaggerList", method = RequestMethod.POST)
    public ResponseEntity<List<SwaggerModel>> SwaggerModelList(@RequestBody @ApiParam(name = "swagger",value="Model")SwaggerModel swaggerModel) {
        List<SwaggerModel> models = new ArrayList();
        SwaggerModel model = new SwaggerModel();
        SwaggerModel mode2 = new SwaggerModel();
        model.setLiuning("liuning1");
        model.setName(24);
        return ResponseEntity.ok(models);
    }
}
