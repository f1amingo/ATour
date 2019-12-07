package com.sanwei.sanwei4a.presenter

import android.os.Handler
import android.os.Looper
import com.sanwei.sanwei4a.view.IBaseView


/**
 * Created by VincentLaf on 2018/4/11.
 *
 */
open class BasePresenter<V : IBaseView> {

    val TAG: String = this.javaClass.simpleName

    val handler = Handler(Looper.getMainLooper())

    var view: V? = null

    fun attachView(mvpView: V) {
        this.view = mvpView
    }

    fun detachView() {
        this.view = null
    }
}