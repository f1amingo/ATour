package com.sanwei.sanwei4a.im.util

import android.content.Context
import android.util.Log
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.im.event.OnDataSendListener
import per.johnson.server.core.LocalUDPDataSender
import per.johnson.server.protocal.ProtocalOuterClass
import per.johnson.server.protocal.ProtocalType
import com.sanwei.sanwei4a.im.protocal.MainContent.Message

object DataSender {
    private val TAG = javaClass.simpleName

    /**
     * 发送 P2P 消息
     * @param message
     *      @see MessageFactory
     */
    fun sendP2PMessage(context: Context, message: Message, toId: Int, onDataSendListener: OnDataSendListener) {
        if (App.account==null||App.account!!.accId <=0) {
            Log.e(TAG, "accountId 为空 此次发送取消")
            onDataSendListener.onFail()
            return
        }
        val accountId = App.account!!.accId.toString()
        val protocal = ProtocalOuterClass.Protocal.newBuilder()
                .setTo(toId.toString())
                .setFrom(accountId)
                .setDataContentBytes(message.toByteString())
                .setQoS(true)
                .setType(ProtocalType.C.`FROM_CLIENT_TYPE_OF_COMMON$DATA`)
                .setTypeu(ProtocalType.C_S.P2P_CHAT)
                .build()
        object : LocalUDPDataSender.SendCommonDataAsync(context, protocal) {
            override fun onPostExecute(code: Int?) {
                if (code == 0) {
                    onDataSendListener.onSuccess()
                } else {
                    onDataSendListener.onFail()
                }
            }

        }.execute()
    }

    /**
     * 发送群组消息
     * @param message
     *      @see MessageFactory
     */
    fun sendP2GMessage(context: Context, message: Message, groupId: Int, onDataSendListener: OnDataSendListener){
        if (App.account==null||App.account!!.accId <=0) {
            Log.e(TAG, "尚未登陆 此次发送取消")
            onDataSendListener.onFail()
            return
        }
        val accountId = App.account!!.accId.toString()
        val protocal = ProtocalOuterClass.Protocal.newBuilder()
                .setTo(groupId.toString())
                .setFrom(accountId)
                .setDataContentBytes(message.toByteString())
                .setQoS(true)
                .setType(ProtocalType.C.`FROM_CLIENT_TYPE_OF_COMMON$DATA`)
                .setTypeu(1+groupId)
                .build()
        object : LocalUDPDataSender.SendCommonDataAsync(context, protocal) {
            override fun onPostExecute(code: Int?) {
                if (code == 0) {
                    onDataSendListener.onSuccess()
                } else {
                    onDataSendListener.onFail()
                }
            }

        }.execute()
    }
}