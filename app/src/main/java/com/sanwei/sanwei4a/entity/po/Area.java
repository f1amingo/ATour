package com.sanwei.sanwei4a.entity.po;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class Area implements Serializable {

    /**
     * 国
     */
    public static final int COUNTRY = 1;
    /**
     * 省
     */
    public static final int PROVINCE = 2;
    /**
     * 市
     */
    public static final int CITY = 3;
    /**
     * 县
     */
    public static final int AREA = 4;

    private int level;
    private int code;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}