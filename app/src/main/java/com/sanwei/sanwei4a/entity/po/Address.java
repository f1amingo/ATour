package com.sanwei.sanwei4a.entity.po;

/**
 * 查询结果
 * 替代类{@link AccountAddress}
 */
@Deprecated
public class Address {
    /** 省code*/
    private String province;
    /** 市code*/
    private String area;
    /** 区/县 code*/
    private String city;
    /** 详细地址*/
    private String detail;
    /** **省**市**区+detail */
    private String addSummarize;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddSummarize() {
        return addSummarize;
    }

    public void setAddSummarize(String addSummarize) {
        this.addSummarize = addSummarize;
    }
}
