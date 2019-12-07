package com.sanwei.sanwei4a.model.discovery

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.discovery.PhonePageParam
import com.sanwei.sanwei4a.entity.po.BookSimpleInfo
import com.sanwei.sanwei4a.entity.result.QueryResult
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.util.Functions
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.doAsync
import java.io.IOException

/**
 * 首页数据服务
 */
class DiscoveryService {

    fun request(userPhone: String, page: Int, discoveryListener: OnRequestListener<QueryResult<BookSimpleInfo>>) {
        doAsync {
            RequestUtil.doPost(Functions.DISCOVERY, null, PhonePageParam(userPhone, page), object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    discoveryListener.onFail(e?.message,Constant.EXCEPTION)
                }

                override fun onResponse(call: Call?, response: Response?) {
                    if (response?.isSuccessful == true) {
                        try {
                            val str = response.body()?.string()
                            LogUtil.e("DiscoveryService", str!!)
                            val reference = object : TypeReference<Result<QueryResult<BookSimpleInfo>>>() {}
                            val result = JSONObject.parseObject(str, reference)
                            if (result.code == Constant.OK_CODE) {
                                discoveryListener.onSuccess(result.data)
                            } else {
                                discoveryListener.onFail(result.msg,result.code)
                            }
                        } catch (e: Exception) {
                            discoveryListener.onFail("网络错误",Constant.EXCEPTION)
                        }
                    } else {
                        discoveryListener.onFail("网络错误",Constant.NOT_FOUND)
                    }
                    response?.close()
                }
            })
        }
    }
}