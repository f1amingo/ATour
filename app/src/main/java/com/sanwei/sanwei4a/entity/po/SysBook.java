package com.sanwei.sanwei4a.entity.po;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class SysBook implements Serializable {

    private Integer bookId;

    private String bookIsbn;

    private String bookAuthor;

    private String bookName;

    private Integer bookPage;

    private Integer bookType;

    private Double bookPrice;

    private String bookPublisher;

    private String booBinding;

    private String bookDetail;

    private static final long serialVersionUID = 1L;

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
        this.bookIsbn = bookIsbn == null ? null : bookIsbn.trim();
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor == null ? null : bookAuthor.trim();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
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
        this.bookPublisher = bookPublisher == null ? null : bookPublisher.trim();
    }

    public String getBooBinding() {
        return booBinding;
    }

    public void setBooBinding(String booBinding) {
        this.booBinding = booBinding == null ? null : booBinding.trim();
    }

    public String getBookDetail() {
        return bookDetail;
    }

    public void setBookDetail(String bookDetail) {
        this.bookDetail = bookDetail == null ? null : bookDetail.trim();
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
        SysBook other = (SysBook) that;
        return (this.getBookId() == null ? other.getBookId() == null : this.getBookId().equals(other.getBookId()))
                && (this.getBookIsbn() == null ? other.getBookIsbn() == null : this.getBookIsbn().equals(other.getBookIsbn()))
                && (this.getBookAuthor() == null ? other.getBookAuthor() == null : this.getBookAuthor().equals(other.getBookAuthor()))
                && (this.getBookName() == null ? other.getBookName() == null : this.getBookName().equals(other.getBookName()))
                && (this.getBookPage() == null ? other.getBookPage() == null : this.getBookPage().equals(other.getBookPage()))
                && (this.getBookType() == null ? other.getBookType() == null : this.getBookType().equals(other.getBookType()))
                && (this.getBookPrice() == null ? other.getBookPrice() == null : this.getBookPrice().equals(other.getBookPrice()))
                && (this.getBookPublisher() == null ? other.getBookPublisher() == null : this.getBookPublisher().equals(other.getBookPublisher()))
                && (this.getBooBinding() == null ? other.getBooBinding() == null : this.getBooBinding().equals(other.getBooBinding()))
                && (this.getBookDetail() == null ? other.getBookDetail() == null : this.getBookDetail().equals(other.getBookDetail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBookId() == null) ? 0 : getBookId().hashCode());
        result = prime * result + ((getBookIsbn() == null) ? 0 : getBookIsbn().hashCode());
        result = prime * result + ((getBookAuthor() == null) ? 0 : getBookAuthor().hashCode());
        result = prime * result + ((getBookName() == null) ? 0 : getBookName().hashCode());
        result = prime * result + ((getBookPage() == null) ? 0 : getBookPage().hashCode());
        result = prime * result + ((getBookType() == null) ? 0 : getBookType().hashCode());
        result = prime * result + ((getBookPrice() == null) ? 0 : getBookPrice().hashCode());
        result = prime * result + ((getBookPublisher() == null) ? 0 : getBookPublisher().hashCode());
        result = prime * result + ((getBooBinding() == null) ? 0 : getBooBinding().hashCode());
        result = prime * result + ((getBookDetail() == null) ? 0 : getBookDetail().hashCode());
        return result;
    }

    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}