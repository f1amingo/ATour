package com.sanwei.sanwei4a.model.chat

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.BaseParameter
import com.sanwei.sanwei4a.entity.po.Message
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.im.event.OnMessageReceivedListener
import com.sanwei.sanwei4a.im.protocal.MainContent
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.util.Functions
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.doAsync
import java.io.IOException

class ChatService {

    // 获取离线的未读消息
    fun getOffLineMessages(onMessageReceivedListener: OnMessageReceivedListener) {
        val listener = OnMessageRequestListener(onMessageReceivedListener)
        doAsync {
            try {
                if (App.account == null) {
                    throw Exception("尚未登陆")
                }
                RequestUtil.doPost(Functions.QUERY_OFFLINE_MESSAGES, null, BaseParameter(), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        listener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            val str = response.body()?.string()
                            if (!str.isNullOrEmpty()) {
                                val reference = object : TypeReference<Result<List<Message>>>() {}
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
    }

    // 获取已读消息
    fun getReadMessages(onMessageReceivedListener: OnMessageReceivedListener) {
        val listener = OnMessageRequestListener(onMessageReceivedListener)
        doAsync {
            try {
                if (App.account == null) {
                    throw Exception("尚未登陆")
                }
                RequestUtil.doPost(Functions.QUERY_READ_MESSAGES, null, BaseParameter(), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        listener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            val str = response.body()?.string()
                            if (!str.isNullOrEmpty()) {
                                val reference = object : TypeReference<Result<List<Message>>>() {}
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
    }

    class OnMessageRequestListener(var onMessageReceivedListener: OnMessageReceivedListener) : OnRequestListener<List<Message>> {
        private val TAG = javaClass.simpleName
        override fun onSuccess(data: List<Message>) {
            for (m in data) {
                val message = MainContent.Message.parseFrom(m.messageContent)
                val from = m.messageFrom.toInt()
                when {
                    m.messageTypeu == 1 -> {
                        when {
                            from > 0 -> {
                                onMessageReceivedListener.onP2PMessageReceived(message, from)
                            }
                            from == 0 -> onMessageReceivedListener.onSystemMessageReceived(message)
                            else -> LogUtil.e(TAG, "不支持的类型")
                        }
                    }
                    m.messageTypeu > 1 -> {
                        onMessageReceivedListener.onGroupMessageReceived(message, from, m.messageTypeu - 1)
                    }
                    else -> {
                        LogUtil.e(TAG, "不支持的消息类型")
                    }
                }
            }
        }

        override fun onFail(msg: String?, code: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}