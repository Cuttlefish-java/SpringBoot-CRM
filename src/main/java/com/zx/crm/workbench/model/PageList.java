package com.zx.crm.workbench.model;

public class PageList {
    private Integer pageNo;
    private Integer pageSize;
    private String name;
    private String owner;
    private String startDate;
    private String endDate;
    public PageList(Integer pageNo, Integer pageSize, String name, String owner, String startDate, String endDate) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.name = name;
        this.owner = owner;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "PageList{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public PageList() {
    }
}
