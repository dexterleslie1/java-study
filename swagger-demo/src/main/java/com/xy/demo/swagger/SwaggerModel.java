package com.xy.demo.swagger;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="swagger")
public class SwaggerModel {
    @ApiModelProperty(value="刘宁",required = false)
    private String liuning;
    @ApiModelProperty(value="名字",required = true)
    private long name;

    public String getLiuning() {
        return liuning;
    }

    public void setLiuning(String liuning) {
        this.liuning = liuning;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }
}
