package com.xy.demo.swagger;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="User")
public class UserModel {
    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "用户密码")
    private String passWord;
    @ApiModelProperty(value = "用户userName")
    private String userName;
    @ApiModelProperty(value = "性别")
    private long sex;
    @ApiModelProperty(value = "用户account")
    private long acount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getSex() {
        return sex;
    }

    public void setSex(long sex) {
        this.sex = sex;
    }

    public long getAcount() {
        return acount;
    }

    public void setAcount(long acount) {
        this.acount = acount;
    }
}
