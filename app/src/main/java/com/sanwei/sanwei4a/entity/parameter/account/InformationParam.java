package com.sanwei.sanwei4a.entity.parameter.account;

import com.sanwei.sanwei4a.entity.parameter.BaseParameter;

public class InformationParam extends BaseParameter {

    private String accNickname;
    private String accSex;
    private User user;

    public String getAccNickname() {
        return accNickname;
    }

    public void setAccNickname(String accNickname) {
        this.accNickname = accNickname;
    }

    public String getAccSex() {
        return accSex;
    }

    public void setAccSex(String accSex) {
        this.accSex = accSex;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
