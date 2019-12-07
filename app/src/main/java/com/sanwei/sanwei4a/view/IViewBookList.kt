package com.sanwei.sanwei4a.view

import com.sanwei.sanwei4a.entity.po.BookSimpleInfo

/**
 * Created by VincentLaf on 2018/4/22.
 *
 */
interface IViewBookList : IBaseView {

    //刷新
    fun setIsRefreshing(b: Boolean)

    //本次加载结束
    fun loadMoreComplete()

    //加载更多时失败
    fun loadMoreFail()

    //全部加载完
    fun loadMoreEnd()

    fun addItemList(list: ArrayList<BookSimpleInfo>)

    fun clearItems()
}