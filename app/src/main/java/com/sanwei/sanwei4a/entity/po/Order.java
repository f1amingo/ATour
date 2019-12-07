package com.sanwei.sanwei4a.entity.po;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    /**
     * 订单自增id 可不传
     */
    private Integer orderId;
    /**
     * 订单唯一标识 可不传
     */
    private String orderIdx;
    /**
     * 订单发起者平台id 即借书人
     */
    private Integer orderFromAid;
    /**
     * 图书提供者平台id
     */
    private Integer orderToAid;
    /**
     * 创建时间
     */
    private Date orderTime;
    /**
     * 用户发布图书id
     */
    private Integer orderBooId;
    /**
     * 订单备注
     */
    private String orderExtra;
    /**
     * 结束时间
     */
    private Date orderEndtime;
    /**
     * 支付类型 {@link com.sanwei.sanwei4a.entity.Constant}
     */
    private Integer orderPaytype;
    /**
     * 订单状态 {@link com.sanwei.sanwei4a.entity.Constant}
     */
    private Integer orderStat;

    private static final long serialVersionUID = 1L;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderIdx() {
        return orderIdx;
    }

    public void setOrderIdx(String orderIdx) {
        this.orderIdx = orderIdx == null ? null : orderIdx.trim();
    }

    public Integer getOrderFromAid() {
        return orderFromAid;
    }

    public void setOrderFromAid(Integer orderFromAid) {
        this.orderFromAid = orderFromAid;
    }

    public Integer getOrderToAid() {
        return orderToAid;
    }

    public void setOrderToAid(Integer orderToAid) {
        this.orderToAid = orderToAid;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderBooId() {
        return orderBooId;
    }

    public void setOrderBooId(Integer orderBooId) {
        this.orderBooId = orderBooId;
    }

    public String getOrderExtra() {
        return orderExtra;
    }

    public void setOrderExtra(String orderExtra) {
        this.orderExtra = orderExtra == null ? null : orderExtra.trim();
    }

    public Date getOrderEndtime() {
        return orderEndtime;
    }

    public void setOrderEndtime(Date orderEndtime) {
        this.orderEndtime = orderEndtime;
    }

    public Integer getOrderPaytype() {
        return orderPaytype;
    }

    public void setOrderPaytype(Integer orderPaytype) {
        this.orderPaytype = orderPaytype;
    }

    public Integer getOrderStat() {
        return orderStat;
    }

    public void setOrderStat(Integer orderStat) {
        this.orderStat = orderStat;
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
        Order other = (Order) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
                && (this.getOrderIdx() == null ? other.getOrderIdx() == null : this.getOrderIdx().equals(other.getOrderIdx()))
                && (this.getOrderFromAid() == null ? other.getOrderFromAid() == null : this.getOrderFromAid().equals(other.getOrderFromAid()))
                && (this.getOrderToAid() == null ? other.getOrderToAid() == null : this.getOrderToAid().equals(other.getOrderToAid()))
                && (this.getOrderTime() == null ? other.getOrderTime() == null : this.getOrderTime().equals(other.getOrderTime()))
                && (this.getOrderBooId() == null ? other.getOrderBooId() == null : this.getOrderBooId().equals(other.getOrderBooId()))
                && (this.getOrderExtra() == null ? other.getOrderExtra() == null : this.getOrderExtra().equals(other.getOrderExtra()))
                && (this.getOrderEndtime() == null ? other.getOrderEndtime() == null : this.getOrderEndtime().equals(other.getOrderEndtime()))
                && (this.getOrderPaytype() == null ? other.getOrderPaytype() == null : this.getOrderPaytype().equals(other.getOrderPaytype()))
                && (this.getOrderStat() == null ? other.getOrderStat() == null : this.getOrderStat().equals(other.getOrderStat()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderIdx() == null) ? 0 : getOrderIdx().hashCode());
        result = prime * result + ((getOrderFromAid() == null) ? 0 : getOrderFromAid().hashCode());
        result = prime * result + ((getOrderToAid() == null) ? 0 : getOrderToAid().hashCode());
        result = prime * result + ((getOrderTime() == null) ? 0 : getOrderTime().hashCode());
        result = prime * result + ((getOrderBooId() == null) ? 0 : getOrderBooId().hashCode());
        result = prime * result + ((getOrderExtra() == null) ? 0 : getOrderExtra().hashCode());
        result = prime * result + ((getOrderEndtime() == null) ? 0 : getOrderEndtime().hashCode());
        result = prime * result + ((getOrderPaytype() == null) ? 0 : getOrderPaytype().hashCode());
        result = prime * result + ((getOrderStat() == null) ? 0 : getOrderStat().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}