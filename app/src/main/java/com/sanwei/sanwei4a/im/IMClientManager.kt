package com.sanwei.sanwei4a.im

import android.content.Context
import android.net.wifi.hotspot2.ConfigParser
import android.util.Log
import com.sanwei.sanwei4a.im.event.ChatBaseEventImpl
import com.sanwei.sanwei4a.im.event.ChatTransDataEventImpl
import com.sanwei.sanwei4a.im.event.MessageQoSEventImpl
import com.sanwei.sanwei4a.im.event.OnMessageReceivedListener
import com.sanwei.sanwei4a.util.RequestUtil
import per.johnson.server.ClientCoreSDK
import per.johnson.server.conf.ConfigEntity

class IMClientManager private constructor(){

    private val TAG = javaClass.simpleName
    private var isInit = false
    fun init(context: Context,onMessageReceivedListener: OnMessageReceivedListener){
        if(!isInit) {
            ClientCoreSDK.DEBUG = false
            ConfigEntity.serverUDPPort = 1997
            ConfigEntity.serverIP = RequestUtil.IP
            val client = ClientCoreSDK.getInstance()
            client.init(context)
            client.chatBaseEvent = ChatBaseEventImpl()
            client.messageQoSEvent = MessageQoSEventImpl()
            client.chatTransDataEvent = ChatTransDataEventImpl(onMessageReceivedListener)
            isInit = true
        }else{
            Log.d(TAG,"初始化失败")
        }

    }
    fun release(){
        ClientCoreSDK.getInstance().release()
        isInit = false
    }


    companion object {
        private val instance by lazy { IMClientManager() }
        fun getIMClientManager():IMClientManager{
            return instance
        }
    }

}