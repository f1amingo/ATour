package com.sanwei.sanwei4a.entity.po;

import java.io.Serializable;

/**
 * 在首页(Discovery Fragment)展示的图书的信息
 */
public class BookSimpleInfo implements Serializable{

    /**
     * 发布者的平台id
     */
    private int accId;

    /** 发布者头像*/
    private String accPic;

    /**
     * 用户发布的记录id
     */
    private int booId;
    /**
     * 系统此图书记录的id
     */
    private int bookId;
    /**
     * 用户对此图书的描述
     */
    private String describe;
    /**
     * 书籍成色
     */
    private String condition;

    /**
     * 书籍名称
     */
    private String bookName;

    /**
     * 发布用户名
     */
    private String nickName;
    /**
     * 此用户的综合评价
     */
    private double starts;

    /* 积分押金两者取其一*/
    /**
     * 用户需要的积分
     */
    private int points;



    /**
     * 用户提出的押金价格
     */
    private double price;

    /**
     * 书籍首页路径信息
     */
    private String booFrontpic;

    /** 书籍状态
     * @see com.sanwei.sanwei4a.entity.Constant
     */
    private int bookState;

    public int getBooId() {
        return booId;
    }

    public void setBooId(int booId) {
        this.booId = booId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getStarts() {
        return starts;
    }

    public void setStarts(double starts) {
        this.starts = starts;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getBooFrontpic() {
        return booFrontpic;
    }

    public void setBooFrontpic(String booFrontpic) {
        this.booFrontpic = booFrontpic;
    }

    public String getAccPic() {
        return accPic;
    }

    public void setAccPic(String accPic) {
        this.accPic = accPic;
    }

    public int getBookState() {
        return bookState;
    }

    public void setBookState(int bookState) {
        this.bookState = bookState;
    }
}
