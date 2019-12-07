package com.sanwei.sanwei4a.view

import com.sanwei.sanwei4a.entity.po.SysBook

/**
 * Created by VincentLaf on 2018/4/11.
 *
 */
interface IViewUploadBook :IBaseView{

    fun fillTheBookInfo(book: SysBook)

    fun readyToLoadBookInfo()

    fun loadBookInfoError(msg:String)

    fun noSuchBook()

    fun loadingBookInfo()

    fun finish()
}