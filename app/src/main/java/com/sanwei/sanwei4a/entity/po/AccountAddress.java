package com.sanwei.sanwei4a.entity.po;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 收货地址
 */
public class AccountAddress implements Serializable{

    private Integer addId;
    /** 账户id*/
    private Integer addAccid;
    /** 省code*/
    private Integer addProid;
    /** 市code*/
    private Integer addCityid;
    /** 区县code*/
    private Integer addAreaid;
    /** 详细地址*/
    private String addDetail;
    /** 收件人名字*/
    private String addName;
    /** 收件人联系方式*/
    private String addPhone;
    /** 收货地址总结  **省**市**区+detail */
    private String addSummarize;

    public Integer getAddId() {
        return addId;
    }

    public void setAddId(Integer addId) {
        this.addId = addId;
    }

    public Integer getAddAccid() {
        return addAccid;
    }

    public void setAddAccid(Integer addAccid) {
        this.addAccid = addAccid;
    }

    public Integer getAddProid() {
        return addProid;
    }

    public void setAddProid(Integer addProid) {
        this.addProid = addProid;
    }

    public Integer getAddCityid() {
        return addCityid;
    }

    public void setAddCityid(Integer addCityid) {
        this.addCityid = addCityid;
    }

    public Integer getAddAreaid() {
        return addAreaid;
    }

    public void setAddAreaid(Integer addAreaid) {
        this.addAreaid = addAreaid;
    }

    public String getAddDetail() {
        return addDetail;
    }

    public void setAddDetail(String addDetail) {
        this.addDetail = addDetail;
    }

    public boolean isIlegel(){
        return addAreaid>0&&addAccid>0&&addCityid>0&&addProid>0&&addDetail!=null&&!addDetail.trim().equals("");
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getAddPhone() {
        return addPhone;
    }

    public void setAddPhone(String addPhone) {
        this.addPhone = addPhone;
    }

    public String getAddSummarize() {
        return addSummarize;
    }

    public void setAddSummarize(String addSummarize) {
        this.addSummarize = addSummarize;
    }
}
