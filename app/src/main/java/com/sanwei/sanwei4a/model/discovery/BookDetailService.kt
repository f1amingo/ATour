package com.sanwei.sanwei4a.model.discovery

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.doAsync
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.discovery.BookParam
import com.sanwei.sanwei4a.entity.po.BookDetailInfo
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.util.Functions
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import java.io.IOException

class BookDetailService {

    fun request(booId: Int, listener: OnRequestListener<BookDetailInfo>) {
        doAsync {
            if (booId <= 0) {
                listener.onFail("参数 booId=$booId 错误", Constant.EXCEPTION)
            } else {
                val bookParam = BookParam()
                bookParam.booId = booId
                RequestUtil.doPost(Functions.QUERY_BOOK_DETAIL, null, bookParam, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        listener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            try {
                                val reference = object : TypeReference<Result<BookDetailInfo>>() {}
                                val resBody = response.body()?.string()!!
                                LogUtil.e("BookDetailService", resBody)
                                val result = JSONObject.parseObject(resBody, reference)
                                if (result.code == Constant.OK_CODE) {
                                    listener.onSuccess(result.data)
                                } else {
                                    listener.onFail(result.msg, result.code)
                                }
                            } catch (e: Exception) {
                                listener.onFail("网络错误", Constant.EXCEPTION)
                            }
                        } else {
                            listener.onFail("网络请求错误", Constant.NOT_FOUND)
                        }
                        response?.close()
                    }
                })
            }
        }
    }
}