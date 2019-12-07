package com.sanwei.sanwei4a.im.event

import android.util.Log
import com.google.protobuf.ByteString
import com.sanwei.sanwei4a.im.DEFAULT_ENCODING
import com.sanwei.sanwei4a.im.DEFAULT_IMAGE_CODING
import com.sanwei.sanwei4a.im.protocal.MainContent.Message
import com.sanwei.sanwei4a.util.LogUtil
import per.johnson.server.event.ChatTransDataEvent
import per.johnson.server.protocal.CharsetHelper

class ChatTransDataEventImpl(var onMessageReceivedListener: OnMessageReceivedListener): ChatTransDataEvent{
    private val TAG = javaClass.simpleName
    override fun onErrorResponse(code: Int, msg: String?) {
        Log.d(TAG,"消息发送失败 code =$code msg:$msg")
    }

    override fun onTransBuffer(fingerPrintOfProtocal: String?, accountId: String?, dataContent: String?, typeu: Int) {
        Log.d(TAG,"收到来自 $accountId 的消息: $dataContent  fp=$fingerPrintOfProtocal typeu = $typeu")
        val message = Message.parseFrom(ByteString.copyFrom(dataContent, DEFAULT_IMAGE_CODING))
        val from = accountId!!.toInt()
        when {
            typeu == 1 -> { //p2p消息
                when {
                    from > 0 -> onMessageReceivedListener.onP2PMessageReceived(message, from)
                    from == 0 -> onMessageReceivedListener.onSystemMessageReceived(message)
                    else -> LogUtil.e(TAG, "不支持的类型")
                }
            }
            typeu > 1 -> { //群组消息
                onMessageReceivedListener.onGroupMessageReceived(message, from, typeu - 1)
            }
            else -> {  //不支持的消息
                LogUtil.e(TAG,"不支持的消息类型")
            }
        }
    }
}