package com.sanwei.sanwei4a.entity.parameter.account;

public class User {

    private Integer uId;

    private Integer uAccid;

    private String uIdnum;

    private String uName;

    private String uSex;

    private String uSchool;

    private String uCollege;

    private String uSid;

    public Integer getuId() {
        return uId;
    }

    public User setuId(Integer uId) {
        this.uId = uId;
        return this;
    }

    public Integer getuAccid() {
        return uAccid;
    }

    public User setuAccid(Integer uAccid) {
        this.uAccid = uAccid;
        return this;
    }

    public String getuIdnum() {
        return uIdnum;
    }

    public void setuIdnum(String uIdnum) {
        this.uIdnum = uIdnum;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuSex() {
        return uSex;
    }

    public User setuSex(String uSex) {
        this.uSex = uSex;
        return this;
    }

    public String getuSchool() {
        return uSchool;
    }

    public void setuSchool(String uSchool) {
        this.uSchool = uSchool;
    }

    public String getuCollege() {
        return uCollege;
    }

    public void setuCollege(String uCollege) {
        this.uCollege = uCollege;
    }

    public String getuSid() {
        return uSid;
    }

    public void setuSid(String uSid) {
        this.uSid = uSid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", uAccid=" + uAccid +
                ", uIdnum='" + uIdnum + '\'' +
                ", uName='" + uName + '\'' +
                ", uSex='" + uSex + '\'' +
                ", uSchool='" + uSchool + '\'' +
                ", uCollege='" + uCollege + '\'' +
                ", uSid='" + uSid + '\'' +
                '}';
    }
}
