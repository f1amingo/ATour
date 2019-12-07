package com.sanwei.sanwei4a.entity.result;

import java.util.List;

/**
 * 作为Result泛型中的一种被包含在其中
 * @param <T> 返回结果列表包含的类型
 */
public class QueryResult<T> {
    private long total;
    private boolean hasNext;
    private List<T> data;


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
