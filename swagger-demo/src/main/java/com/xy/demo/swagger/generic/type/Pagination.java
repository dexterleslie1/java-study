package com.xy.demo.swagger.generic.type;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 分页
 */
public class Pagination<T>{
    @ApiModelProperty(value = "总页数")
    private int totalPage;
    @ApiModelProperty(value = "当前页")
    private int currentPage;
    @ApiModelProperty(value = "每页记录数")
    private int pageSize;
    @ApiModelProperty(value = "返回数据")
    private List<T> dataObject;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getDataObject() {
        return dataObject;
    }

    public void setDataObject(List<T> dataObject) {
        this.dataObject = dataObject;
    }
}
