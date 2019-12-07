package com.sanwei.sanwei4a.model

/**
 * 网络请求统一回调接口
 */
interface OnRequestListener<T> {
    fun onSuccess(data:T)
    fun onFail(msg:String?, code:Int)
}