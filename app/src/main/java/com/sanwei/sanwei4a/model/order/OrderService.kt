package com.sanwei.sanwei4a.model.order

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.order.OrderParam
import com.sanwei.sanwei4a.entity.po.Order
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.util.Functions
import com.sanwei.sanwei4a.util.RequestUtil.doPost
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.doAsync
import java.io.IOException

/**
 * 订单的一系列服务
 */
class OrderService {

    /** 创建订单请求*/
    fun createOrder(order: Order, onOrderCreateListener: OnRequestListener<Order>) {
        doAsync {
            try {
                doPost(Functions.CREATE_ORDER, null, OrderParam(order), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        onOrderCreateListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            val str = response.body()?.string()
                            val reference = object : TypeReference<Result<Order>>() {}
                            val result = JSONObject.parseObject(str, reference)
                            if (result.code == Constant.OK_CODE) {
                                onOrderCreateListener.onSuccess(result.data)
                            } else {
                                onOrderCreateListener.onFail(result.msg, Constant.FAIL_CODE)
                            }
                        } else {
                            onOrderCreateListener.onFail("请求失败", Constant.EXCEPTION)
                        }
                        response?.close()
                    }
                })
            } catch (e: Exception) {
                onOrderCreateListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }

    /** 支付 暂时不支持押金方式*/
    fun pay(order: Order, onPayFinishListener: OnRequestListener<String?>) {
        doAsync {
            try {
                doPost(Functions.PAY, null, OrderParam(order), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        onPayFinishListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            val str = response.body()?.string();
                            val reference = object : TypeReference<Result<Any>>() {}
                            val result = JSONObject.parseObject(str, reference)
                            if (result.code == Constant.OK_CODE) {
                                onPayFinishListener.onSuccess(null)
                            } else {
                                onPayFinishListener.onFail(result.msg, result.code)
                            }
                        } else {
                            onPayFinishListener.onFail("请求失败", Constant.FAIL_CODE)
                        }
                        response?.close()
                    }
                })

            } catch (e: Exception) {
                onPayFinishListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }

}