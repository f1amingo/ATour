package com.sanwei.sanwei4a.model.book

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.sanwei.sanwei4a.App
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.doAsync
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.book.BookAccParam
import com.sanwei.sanwei4a.entity.parameter.discovery.BookParam
import com.sanwei.sanwei4a.entity.po.BusinessBook
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.util.Functions
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import java.io.File
import java.io.IOException

/** 其中只有booWatched、booFava、booSign、booStat不需要*/
class BookUploadService {
    /** 其中只有booWatched、booFava、booSign、booStat booFrontpic ,booInpic,booEndpic不需要*/
    fun request(businessBook: BusinessBook, front: File, inpic: File, end: File, bookUploadListener: OnRequestListener<Int>) {
        doAsync {
            try {
                val bookParam = BookParam()
                bookParam.bBook = businessBook

                RequestUtil.doPost(Functions.UPLOAD_BOOK, front, inpic, end, bookParam, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        bookUploadListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            val body = response.body()?.string()
                            LogUtil.e("BookUploadService", body!!)
                            val reference = object : TypeReference<Result<Int>>() {}
                            val result = JSONObject.parseObject(body, reference)
                            if (result.code == Constant.OK_CODE) {
                                bookUploadListener.onSuccess(result.data)
                            } else {
                                bookUploadListener.onFail(result.msg, result.code)
                            }
                        } else {
                            bookUploadListener.onFail("请求失败", Constant.NOT_FOUND)
                        }
                        response?.close()
                    }
                })
            } catch (e: Exception) {
                bookUploadListener.onFail("网络错误", Constant.EXCEPTION)
            }
        }
    }

    fun removeBook(booId: Int, onRequestListener: OnRequestListener<Boolean>) {
        doAsync {
            try {
                if (App.account == null)
                    throw Exception("尚未登陆")
                if (booId <= 0)
                    throw Exception("该书籍不可用")
                val bookAccParam = BookAccParam(booId)
                RequestUtil.doPost(Functions.REMOVE_PUBLISHED_BOOK, null, bookAccParam, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        onRequestListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            val body = response.body()?.string()
                            val reference = object : TypeReference<Result<String>>() {}
                            val result = JSONObject.parseObject(body, reference)
                            if (result.code == Constant.OK_CODE) {
                                onRequestListener.onSuccess(true)
                            } else {
                                onRequestListener.onFail(result.msg, result.code)
                            }
                        } else {
                            onRequestListener.onFail("请求失败", Constant.NOT_FOUND)
                        }
                        response?.close()
                    }
                })
            } catch (e: Exception) {
                onRequestListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }
}