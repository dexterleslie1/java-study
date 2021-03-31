package com.future.demo.chat.server.common;

import java.util.List;

public class PageResponse<T> extends BaseResponse {
    private int pageNum;    //当前页,从请求那边传过来。
    private int pageSize;    //每页显示的数据条数。
    private int totalRecord;    //总的记录条数。查询数据库得到的数据
    private int totalPage;    //总页数，通过totalRecord和pageSize计算可以得来

//    private int startIndex;


    //将每页要显示的数据放在list集合中
    private List<T> data;

//    private int start;
//    private int end;

    /**
     *
     */
    public PageResponse(){
    }

    //通过pageNum，pageSize，totalRecord计算得来tatalPage和startIndex
    //构造方法中将pageNum，pageSize，totalRecord获得
    public PageResponse(int pageNum, int pageSize, int totalRecord) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;

        //totalPage 总页数
        if(totalRecord%pageSize==0){
            this.totalPage = totalRecord / pageSize;
        }else{
            this.totalPage = (totalRecord + pageSize) / pageSize;
        }
//        //开始索引
//        this.startIndex = (pageNum-1)*pageSize ;
//        //显示5页，这里自己可以设置，想显示几页就自己通过下面算法修改
//        this.start = 1;
//        this.end = 5;
//        //显示页数的算法
//        if(totalPage <=5){
//            //总页数都小于5，那么end就为总页数的值了。
//            this.end = this.totalPage;
//        }else{
//            //总页数大于5，那么就要根据当前是第几页，来判断start和end为多少了，
//            this.start = pageNum - 2;
//            this.end = pageNum + 2;
//
//            if(start <=0){
//                //比如当前页是第1页，或者第2页，那么就不如和这个规则，
//                this.start = 1;
//                this.end = 5;
//            }
//            if(end > this.totalPage){
//                //比如当前页是倒数第2页或者最后一页，也同样不符合上面这个规则
//                this.end = totalPage;
//                this.start = end - 5;
//            }
//        }
    }

    //通过pageNum，pageSize，totalRecord计算得来tatalPage和startIndex
    //构造方法中将pageNum,totalRecord获得
    public PageResponse(int pageNum, int totalRecord) {
        this.pageNum = pageNum;
        this.pageSize = 10;
        this.totalRecord = totalRecord;

        //totalPage 总页数
        if(totalRecord%pageSize==0){
            this.totalPage = totalRecord / pageSize;
        }else{
            this.totalPage = (totalRecord + pageSize) / pageSize;
        }
//        //开始索引
//        this.startIndex = (pageNum-1)*pageSize ;
//        //显示5页，这里自己可以设置，想显示几页就自己通过下面算法修改
//        this.start = 1;
//        this.end = 5;
//        //显示页数的算法
//        if(totalPage <=5){
//            //总页数都小于5，那么end就为总页数的值了。
//            this.end = this.totalPage;
//        }else{
//            //总页数大于5，那么就要根据当前是第几页，来判断start和end为多少了，
//            this.start = pageNum - 2;
//            this.end = pageNum + 2;
//
//            if(start <=0){
//                //比如当前页是第1页，或者第2页，那么就不如和这个规则，
//                this.start = 1;
//                this.end = 5;
//            }
//            if(end > this.totalPage){
//                //比如当前页是倒数第2页或者最后一页，也同样不符合上面这个规则
//                this.end = totalPage;
//                this.start = end - 5;
//            }
//        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        if(pageSize <= 0){
            throw new IllegalStateException("先设置每页记录数");
        }
        this.totalRecord = totalRecord;

        int totalPageTemporary = 0;
        if(this.totalRecord%this.pageSize == 0){
            totalPageTemporary = this.totalRecord/this.pageSize;
        }else {
            totalPageTemporary = (this.totalRecord+this.pageSize)/this.pageSize;
        }
        this.totalPage = totalPageTemporary;
    }

    public int getTotalPage() {
        return totalPage;
    }

//    public void setTotalPage(int totalPage) {
//        this.totalPage = totalPage;
//    }
//
//    public int getStartIndex() {
//        return startIndex;
//    }
//
//    public void setStartIndex(int startIndex) {
//        this.startIndex = startIndex;
//    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

//    public int getStart() {
//        return start;
//    }
//
//    public void setStart(int start) {
//        this.start = start;
//    }
//
//    public int getEnd() {
//        return end;
//    }
//
//    public void setEnd(int end) {
//        this.end = end;
//    }
}
