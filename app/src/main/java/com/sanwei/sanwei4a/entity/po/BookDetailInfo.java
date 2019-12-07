package com.sanwei.sanwei4a.entity.po;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class BookDetailInfo implements Serializable {

    /**
     * 系统保存的豆瓣图书id
     */
    private Integer bookId;
    /**
     * 系统保存的isbn
     */
    private String bookIsbn;
    /**
     * 书籍作者
     */
    private String bookAuthor;

    /**
     * 书籍名
     */
    private String bookName;

    /**
     * 页数
     */
    private Integer bookPage;
    /**
     * 书籍type
     */
    private Integer bookType;

    /**
     * 书籍售卖价格
     */
    private Double bookPrice;

    /**
     * 出版社
     */
    private String bookPublisher;

    /**
     * 官方书籍描述
     */
    private String bookDetail;

    /**
     * 用户上传书籍id
     */
    private Integer booId;

    /**
     * 用户上传isbn 一般与bookIsbn相同
     */
    private String booIsbn;


    /**
     * 书籍新旧状态
     */
    private String booCon;


    /**
     * 需要缴纳押金数 积分与押金二者选其一
     */
    private Double booPrice;
    /**
     * 需要缴纳积分数 积分与押金二者选其一
     */
    private int points;

    /**
     * 该书被查看数
     */
    private Integer booWatched;

    /**
     * 被收藏数
     */
    private Integer booFava;

    /**
     * 书籍主人对此的描述
     */
    private String booDes;

    /**
     * 书籍平台id
     */
    private Integer accId;

    /**
     * 书籍主人昵称
     */
    private String accNickname;

    /**
     * 主人电话
     */
    private String accPhone;

    /**
     * 主人被评价  其值=0 意味着未被评价
     */
    private Double accStars;

    /**
     * 是否被收藏
     */
    private boolean isFavo;

    /** 当前持有者Id*/
    private int curAccId;

    private String accPic;

    private String booFrontpic;
    private String booInpic;
    private String booEndpic;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getBookPage() {
        return bookPage;
    }

    public void setBookPage(Integer bookPage) {
        this.bookPage = bookPage;
    }

    public Integer getBookType() {
        return bookType;
    }

    public void setBookType(Integer bookType) {
        this.bookType = bookType;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail;
    }

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
        this.booIsbn = booIsbn;
    }

    public String getBooCon() {
        return booCon;
    }

    public void setBooCon(String booCon) {
        this.booCon = booCon;
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

    public String getBooDes() {
        return booDes;
    }

    public void setBooDes(String booDes) {
        this.booDes = booDes;
    }

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
        this.accNickname = accNickname;
    }

    public String getAccPhone() {
        return accPhone;
    }

    public void setAccPhone(String accPhone) {
        this.accPhone = accPhone;
    }

    public Double getAccStars() {
        return accStars;
    }

    public void setAccStars(Double accStars) {
        this.accStars = accStars;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public boolean isFavo() {
        return isFavo;
    }

    public void setFavo(boolean favo) {
        isFavo = favo;
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

    public String getBooInpic() {
        return booInpic;
    }

    public void setBooInpic(String booInpic) {
        this.booInpic = booInpic;
    }

    public String getBooEndpic() {
        return booEndpic;
    }

    public void setBooEndpic(String booEndpic) {
        this.booEndpic = booEndpic;
    }

    public int getCurAccId() {
        return curAccId;
    }

    public void setCurAccId(int curAccId) {
        this.curAccId = curAccId;
    }

    public String getAccPic() {
        return accPic;
    }

    public void setAccPic(String accPic) {
        this.accPic = accPic;
    }
}
