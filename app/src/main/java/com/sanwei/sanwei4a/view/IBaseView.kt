package com.sanwei.sanwei4a.view

import android.content.Context

/**
 * Created by VincentLaf on 2018/4/11.
 *
 */
interface IBaseView {
    //    fun toast(msg: String)
    fun showWaitingDlg()

    fun dismissWaitingDlg()
    fun ctx(): Context
}