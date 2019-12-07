package com.sanwei.sanwei4a.entity.po;

import java.io.Serializable;

/**
 * 用户上传图书实体类
 */
public class BusinessBook implements Serializable {

    /**
     * 用户上传图书id
     */
    private Integer booId;

    private String booIsbn;

    /**
     * 系统对该图书官方信息id 在ISBN查询之后给出
     */
    private Integer booBookId;

    private Integer booAccId;
    /**
     * 书籍情况
     */
    private String booCon;

    private Integer booPoints;

    private Double booPrice;

    private Integer booWatched;

    private Integer booFava;

    private String booSign;

    private Integer booStat;

    /**
     * 图书第一张照片 转换成的String encoding = ISO-8859-1
     */
    private String booFrontpic;
    /**
     * 图书第二张照片 转换成的String encoding = ISO-8859-1
     */
    private String booInpic;
    /**
     * 图书第三张照片 转换成的String encoding = ISO-8859-1
     */
    private String booEndpic;

    private String booDes;

    /** 添加的用户地址ID*/
    //TODO 上传图书时添加该参数
    private Integer booAddrId;

    private static final long serialVersionUID = 1L;

    public Integer getBooId() {
        return booId;
    }

    public void setBooId(Integer booId) {
        this.booId = booId;
    }

    public String getBooIsbn() {
        return booIsbn;
    }

    public void setBooIsbn(String booIsbn) {
        this.booIsbn = booIsbn == null ? null : booIsbn.trim();
    }

    public Integer getBooBookId() {
        return booBookId;
    }

    public void setBooBookId(Integer booBookId) {
        this.booBookId = booBookId;
    }

    public Integer getBooAccId() {
        return booAccId;
    }

    public void setBooAccId(Integer booAccId) {
        this.booAccId = booAccId;
    }

    public String getBooCon() {
        return booCon;
    }

    public void setBooCon(String booCon) {
        this.booCon = booCon == null ? null : booCon.trim();
    }

    public Double getBooPrice() {
        return booPrice;
    }

    public void setBooPrice(Double booPrice) {
        this.booPrice = booPrice;
    }

    public Integer getBooWatched() {
        return booWatched;
    }

    public void setBooWatched(Integer booWatched) {
        this.booWatched = booWatched;
    }

    public Integer getBooFava() {
        return booFava;
    }

    public void setBooFava(Integer booFava) {
        this.booFava = booFava;
    }

    public String getBooSign() {
        return booSign;
    }

    public void setBooSign(String booSign) {
        this.booSign = booSign == null ? null : booSign.trim();
    }

    public Integer getBooStat() {
        return booStat;
    }

    public void setBooStat(Integer booStat) {
        this.booStat = booStat;
    }

    public String getBooFrontpic() {
        return booFrontpic;
    }

    public void setBooFrontpic(String booFrontpic) {
        this.booFrontpic = booFrontpic == null ? null : booFrontpic.trim();
    }

    public String getBooInpic() {
        return booInpic;
    }

    public void setBooInpic(String booInpic) {
        this.booInpic = booInpic == null ? null : booInpic.trim();
    }

    public String getBooEndpic() {
        return booEndpic;
    }

    public void setBooEndpic(String booEndpic) {
        this.booEndpic = booEndpic == null ? null : booEndpic.trim();
    }

    public String getBooDes() {
        return booDes;
    }

    public void setBooDes(String booDes) {
        this.booDes = booDes == null ? null : booDes.trim();
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
        BusinessBook other = (BusinessBook) that;
        return (this.getBooId() == null ? other.getBooId() == null : this.getBooId().equals(other.getBooId()))
                && (this.getBooIsbn() == null ? other.getBooIsbn() == null : this.getBooIsbn().equals(other.getBooIsbn()))
                && (this.getBooBookId() == null ? other.getBooBookId() == null : this.getBooBookId().equals(other.getBooBookId()))
                && (this.getBooAccId() == null ? other.getBooAccId() == null : this.getBooAccId().equals(other.getBooAccId()))
                && (this.getBooCon() == null ? other.getBooCon() == null : this.getBooCon().equals(other.getBooCon()))
                && (this.getBooPrice() == null ? other.getBooPrice() == null : this.getBooPrice().equals(other.getBooPrice()))
                && (this.getBooWatched() == null ? other.getBooWatched() == null : this.getBooWatched().equals(other.getBooWatched()))
                && (this.getBooFava() == null ? other.getBooFava() == null : this.getBooFava().equals(other.getBooFava()))
                && (this.getBooSign() == null ? other.getBooSign() == null : this.getBooSign().equals(other.getBooSign()))
                && (this.getBooStat() == null ? other.getBooStat() == null : this.getBooStat().equals(other.getBooStat()))
                && (this.getBooFrontpic() == null ? other.getBooFrontpic() == null : this.getBooFrontpic().equals(other.getBooFrontpic()))
                && (this.getBooInpic() == null ? other.getBooInpic() == null : this.getBooInpic().equals(other.getBooInpic()))
                && (this.getBooEndpic() == null ? other.getBooEndpic() == null : this.getBooEndpic().equals(other.getBooEndpic()))
                && (this.getBooDes() == null ? other.getBooDes() == null : this.getBooDes().equals(other.getBooDes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBooId() == null) ? 0 : getBooId().hashCode());
        result = prime * result + ((getBooIsbn() == null) ? 0 : getBooIsbn().hashCode());
        result = prime * result + ((getBooBookId() == null) ? 0 : getBooBookId().hashCode());
        result = prime * result + ((getBooAccId() == null) ? 0 : getBooAccId().hashCode());
        result = prime * result + ((getBooCon() == null) ? 0 : getBooCon().hashCode());
        result = prime * result + ((getBooPrice() == null) ? 0 : getBooPrice().hashCode());
        result = prime * result + ((getBooWatched() == null) ? 0 : getBooWatched().hashCode());
        result = prime * result + ((getBooFava() == null) ? 0 : getBooFava().hashCode());
        result = prime * result + ((getBooSign() == null) ? 0 : getBooSign().hashCode());
        result = prime * result + ((getBooStat() == null) ? 0 : getBooStat().hashCode());
        result = prime * result + ((getBooFrontpic() == null) ? 0 : getBooFrontpic().hashCode());
        result = prime * result + ((getBooInpic() == null) ? 0 : getBooInpic().hashCode());
        result = prime * result + ((getBooEndpic() == null) ? 0 : getBooEndpic().hashCode());
        result = prime * result + ((getBooDes() == null) ? 0 : getBooDes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", booId=").append(booId);
        sb.append(", booIsbn=").append(booIsbn);
        sb.append(", booBookId=").append(booBookId);
        sb.append(", booAccId=").append(booAccId);
        sb.append(", booCon=").append(booCon);
        sb.append(", booPrice=").append(booPrice);
        sb.append(", booWatched=").append(booWatched);
        sb.append(", booFava=").append(booFava);
        sb.append(", booSign=").append(booSign);
        sb.append(", booStat=").append(booStat);
        sb.append(", booFrontpic=").append(booFrontpic);
        sb.append(", booInpic=").append(booInpic);
        sb.append(", booEndpic=").append(booEndpic);
        sb.append(", booDes=").append(booDes);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Integer getBooAddrId() {
        return booAddrId;
    }

    public void setBooAddrId(Integer booAddrId) {
        this.booAddrId = booAddrId;
    }

    public Integer getBooPoints() {
        return booPoints;
    }

    public void setBooPoints(Integer booPoints) {
        this.booPoints = booPoints;
    }
}