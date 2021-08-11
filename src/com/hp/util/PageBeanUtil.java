package com.hp.util;
//分页的工具类
public class PageBeanUtil {
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public PageBeanUtil(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
//在这里需要计算一下
    //索引=(页数-1）* 条数
    public int getStart() {
        return (page-1)*pageSize;
    }

    public void setStart(int start) {
        this.start = start;
    }

    private int page;//第几页---前端传过来的
    private int pageSize;//第一页的条数也叫 limit
    private int start; //索引
}
