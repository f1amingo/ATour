package com.sanwei.sanwei4a.model

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.result.Result
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

@Deprecated(message = "fastjson无法解析嵌套泛型，等待答复")
class DefaultCallbackImpl<T>(private var onRequestListener: OnRequestListener<T>) :Callback{
    override fun onFailure(call: Call?, e: IOException?) {
        e?.printStackTrace()
        onRequestListener.onFail(e?.message,Constant.EXCEPTION)
    }
    override fun onResponse(call: Call?, response: Response?) {
        if (response?.isSuccessful == true) {
            try {
                val reference = object : TypeReference<Result<T>>() {}
                val resBody = response.body()?.string()!!
                val result = JSONObject.parseObject(resBody, reference)
                if (result.code == Constant.OK_CODE) {
                    onRequestListener.onSuccess(result.data)
                } else {
                    onRequestListener.onFail(result.msg,result.code)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onRequestListener.onFail(e.message,Constant.EXCEPTION)
            }
        } else {
            onRequestListener.onFail("网络请求错误",Constant.NOT_FOUND)
        }
    }
}