package com.sanwei.sanwei4a.model.book

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.book.BookAccParam
import com.sanwei.sanwei4a.entity.parameter.book.KeyWordsParam
import com.sanwei.sanwei4a.entity.parameter.book.MineBookParam
import com.sanwei.sanwei4a.entity.parameter.discovery.BookParam
import com.sanwei.sanwei4a.entity.po.BookDetailInfo
import com.sanwei.sanwei4a.entity.po.BookSimpleInfo
import com.sanwei.sanwei4a.entity.po.SysBook
import com.sanwei.sanwei4a.entity.result.QueryResult
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.util.Functions
import com.sanwei.sanwei4a.util.RequestUtil
import org.jetbrains.anko.doAsync
import java.io.IOException

/**
 * 书籍信息查询服务
 */
class BookQueryService {
    /**
     * 请求书籍官方信息
     */
    fun requestBookOfficialInfo(ISBN: String, listener: OnRequestListener<SysBook>) {
        val bookParam = BookParam()
        bookParam.isbn = ISBN
        try {
            RequestUtil.doPost(Functions.SYSBOOK_QUERY, null, bookParam, object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    listener.onFail(e?.message, Constant.EXCEPTION)
                }

                override fun onResponse(call: Call?, response: Response?) {
                    if (response?.isSuccessful == true) {
                        val str = response.body()?.string()
                        if (!str.isNullOrEmpty()) {
                            val reference = object : TypeReference<Result<SysBook>>() {}
                            val result = JSONObject.parseObject(str, reference)
                            if (result.code == Constant.FAIL_CODE) {
                                listener.onFail("无法查询到该书信息，需要联系客服", Constant.NOT_FOUND)
                            } else {
                                listener.onSuccess(result.data)
                            }
                        } else {
                            listener.onFail("返回体为空【严重】", Constant.EXCEPTION)  //几乎不可能发生
                        }
                    } else {
                        listener.onFail("请求错误", Constant.EXCEPTION)
                    }
                    response?.close()
                }
            })
        } catch (e: Exception) {
            listener.onFail(e.message, Constant.EXCEPTION)
        }
    }

    /**
     * 收藏或取消收藏
     */
    fun doFavorite(booId:Int, onRequestListener: OnRequestListener<BookDetailInfo>){
        doAsync {
            try {
                RequestUtil.doPost(Functions.DO_FAVORITE,null,BookAccParam(booId),object :Callback{
                    override fun onFailure(call: Call?, e: IOException?) {
                        onRequestListener.onFail(e?.message,Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if(response?.isSuccessful == true){
                            val str = response.body()!!.string()
                            val reference = object : TypeReference<Result<BookDetailInfo>>() {}
                            val result = JSONObject.parseObject(str,reference)
                            if(result.code == Constant.OK_CODE){
                                onRequestListener.onSuccess(result.data)
                            }else{
                                onRequestListener.onFail(result.msg,result.code)
                            }

                        }else{
                            onRequestListener.onFail("网络错误",Constant.NOT_FOUND)
                        }
                        response?.close()
                    }

                })

            }catch (e:Exception){
                onRequestListener.onFail("网络错误",Constant.EXCEPTION)
            }
        }
    }

    /**
     * 请求与我相关的图书信息
     * @param type 类型参见 伴生对象中type类型
     */
    fun requestMineBooks(type:Int, page:Int,onRequestListener: OnRequestListener<QueryResult<BookSimpleInfo>>){
        doAsync {
            try{
                RequestUtil.doPost(Functions.QUERY_MINE_BOOKS,null,MineBookParam(type,page),object :Callback{
                    override fun onFailure(call: Call?, e: IOException?) {
                        onRequestListener.onFail(e?.message,Constant.EXCEPTION)
                    }
                    override fun onResponse(call: Call?, response: Response?) {
                        if(response?.isSuccessful == true){
                            val str = response.body()!!.string()
                            val rederence = object : TypeReference<Result<QueryResult<BookSimpleInfo>>>(){}
                            val result = JSONObject.parseObject(str,rederence)
                            if(result.code == Constant.OK_CODE){
                                onRequestListener.onSuccess(result.data)
                            }else{
                                onRequestListener.onFail(result.msg,result.code)
                            }
                        }else{
                            onRequestListener.onFail("网络错误",Constant.NOT_FOUND)
                        }
                        response?.close()
                    }
                })

            }catch (e:Exception){
                onRequestListener.onFail(e.message,Constant.EXCEPTION)
            }
        }
    }

    /**
     * 通过关键词搜索书籍
     */
    fun queryBooksByKeyWords(key:String, page:Int,onRequestListener: OnRequestListener<QueryResult<BookSimpleInfo>>){
        doAsync {
            try{
                val param = KeyWordsParam(key,page)
                RequestUtil.doPost(Functions.QUERY_BOOK_BY_KEY,null,param,object :Callback{
                    override fun onFailure(call: Call?, e: IOException?) {
                        onRequestListener.onFail(e?.message,Constant.EXCEPTION)
                    }
                    override fun onResponse(call: Call?, response: Response?) {
                        if(response?.isSuccessful == true){
                            val str = response.body()!!.string()
                            val rederence = object : TypeReference<Result<QueryResult<BookSimpleInfo>>>(){}
                            val result = JSONObject.parseObject(str,rederence)
                            if(result.code == Constant.OK_CODE){
                                onRequestListener.onSuccess(result.data)
                            }else{
                                onRequestListener.onFail(result.msg,result.code)
                            }
                        }else{
                            onRequestListener.onFail("网络错误",Constant.NOT_FOUND)
                        }
                        response?.close()
                    }
                })

            }catch (e:Exception){
                onRequestListener.onFail(e.message,Constant.EXCEPTION)
            }
        }
    }

    /**
     * 通过ISBN搜索书籍
     */
    fun queryBooksByISBN(ISBN:String, page:Int,onRequestListener: OnRequestListener<QueryResult<BookSimpleInfo>>){
        doAsync {
            try{
                RequestUtil.doPost(Functions.QUERY_BOOK_BY_ISBN,null,KeyWordsParam(ISBN,page),object :Callback{
                    override fun onFailure(call: Call?, e: IOException?) {
                        onRequestListener.onFail(e?.message,Constant.EXCEPTION)
                    }
                    override fun onResponse(call: Call?, response: Response?) {
                        if(response?.isSuccessful == true){
                            val str = response.body()!!.string()
                            val rederence = object : TypeReference<Result<QueryResult<BookSimpleInfo>>>(){}
                            val result = JSONObject.parseObject(str,rederence)
                            if(result.code == Constant.OK_CODE){
                                onRequestListener.onSuccess(result.data)
                            }else{
                                onRequestListener.onFail(result.msg,result.code)
                            }
                        }else{
                            onRequestListener.onFail("网络错误",Constant.NOT_FOUND)
                        }
                        response?.close()
                    }
                })

            }catch (e:Exception){
                onRequestListener.onFail(e.message,Constant.EXCEPTION)
            }
        }
    }
    companion object {
        /** 我发布的*/
        const val TYPE_PUBLISHED= 1
        /** 我借出的*/
        const val TYPE_SENT= 2
        /** 我借入的*/
        const val TYPE_BORROWED= 3
        /** 我收藏的*/
        const val TYPE_FAVORITE= 4
    }

}