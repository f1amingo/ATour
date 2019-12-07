package com.sanwei.sanwei4a.entity.po;

import java.io.Serializable;

public class Account implements Serializable {

    public Account() {
    }

    public Account(Integer accId, String accNickname, String accPassword, String accPhone, String accSex, String accPic, String accInvitecode, Integer accBanlance) {
        this.accId = accId;
        this.accNickname = accNickname;
        this.accPassword = accPassword;
        this.accPhone = accPhone;
        this.accSex = accSex;
        this.accPic = accPic;
        this.accInvitecode = accInvitecode;
        this.accBanlance = accBanlance;
    }

    private String token;

    private Integer accId;

    private String accNickname;

    private String accPassword;

    private String accPhone;

    private String accSex;

    private String accPic;
    /** 邀请码*/
    private String accInvitecode;
    /** 积分余额*/
    private Integer accBanlance;

    private static final long serialVersionUID = 1L;

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    public String getAccNickname() {
        return accNickname;
    }

    public void setAccNickname(String accNickname) {
        this.accNickname = accNickname == null ? null : accNickname.trim();
    }

    public String getAccPassword() {
        return accPassword;
    }

    public void setAccPassword(String accPassword) {
        this.accPassword = accPassword == null ? null : accPassword.trim();
    }

    public String getAccPhone() {
        return accPhone;
    }

    public void setAccPhone(String accPhone) {
        this.accPhone = accPhone == null ? null : accPhone.trim();
    }

    public String getAccSex() {
        return accSex;
    }

    public void setAccSex(String accSex) {
        this.accSex = accSex == null ? null : accSex.trim();
    }

    public String getAccPic() {
        return accPic;
    }

    public void setAccPic(String accPic) {
        this.accPic = accPic == null ? null : accPic.trim();
    }

    public String getAccInvitecode() {
        return accInvitecode;
    }

    public void setAccInvitecode(String accInvitecode) {
        this.accInvitecode = accInvitecode == null ? null : accInvitecode.trim();
    }

    public Integer getAccBanlance() {
        return accBanlance;
    }

    public void setAccBanlance(Integer accBanlance) {
        this.accBanlance = accBanlance;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Account other = (Account) that;
        return (this.getAccId() == null ? other.getAccId() == null : this.getAccId().equals(other.getAccId()))
                && (this.getAccNickname() == null ? other.getAccNickname() == null : this.getAccNickname().equals(other.getAccNickname()))
                && (this.getAccPassword() == null ? other.getAccPassword() == null : this.getAccPassword().equals(other.getAccPassword()))
                && (this.getAccPhone() == null ? other.getAccPhone() == null : this.getAccPhone().equals(other.getAccPhone()))
                && (this.getAccSex() == null ? other.getAccSex() == null : this.getAccSex().equals(other.getAccSex()))
                && (this.getAccPic() == null ? other.getAccPic() == null : this.getAccPic().equals(other.getAccPic()))
                && (this.getAccInvitecode() == null ? other.getAccInvitecode() == null : this.getAccInvitecode().equals(other.getAccInvitecode()))
                && (this.getAccBanlance() == null ? other.getAccBanlance() == null : this.getAccBanlance().equals(other.getAccBanlance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAccId() == null) ? 0 : getAccId().hashCode());
        result = prime * result + ((getAccNickname() == null) ? 0 : getAccNickname().hashCode());
        result = prime * result + ((getAccPassword() == null) ? 0 : getAccPassword().hashCode());
        result = prime * result + ((getAccPhone() == null) ? 0 : getAccPhone().hashCode());
        result = prime * result + ((getAccSex() == null) ? 0 : getAccSex().hashCode());
        result = prime * result + ((getAccPic() == null) ? 0 : getAccPic().hashCode());
        result = prime * result + ((getAccInvitecode() == null) ? 0 : getAccInvitecode().hashCode());
        result = prime * result + ((getAccBanlance() == null) ? 0 : getAccBanlance().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accId=").append(accId);
        sb.append(", accNickname=").append(accNickname);
        sb.append(", accPassword=").append(accPassword);
        sb.append(", accPhone=").append(accPhone);
        sb.append(", accSex=").append(accSex);
        sb.append(", accPic=").append(accPic);
        sb.append(", accInvitecode=").append(accInvitecode);
        sb.append(", accBanlance=").append(accBanlance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}