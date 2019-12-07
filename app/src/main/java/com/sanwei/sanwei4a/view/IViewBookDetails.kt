package com.sanwei.sanwei4a.view

import com.sanwei.sanwei4a.entity.po.BookDetailInfo

/**
 * Created by VincentLaf on 2018/4/22.
 *
 */
interface IViewBookDetails : IBaseView {

    fun setIsLoading()

    fun loadSuccess(info: BookDetailInfo)

    fun loadFail()
}