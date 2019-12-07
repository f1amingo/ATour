package com.sanwei.sanwei4a.im.event

import android.util.Log
import per.johnson.server.event.ChatBaseEvent

class ChatBaseEventImpl:ChatBaseEvent{

    private val TAG = javaClass.simpleName
    override fun onLoginMessage(code: Int) {
        if(code == 0){
            Log.d(TAG,"登陆成功")
        }else{
            Log.d(TAG,"登陆失败 errorCode = $code")
        }
    }

    override fun onLinkCloseMessage(code: Int) {
        Log.d(TAG,"连接断开 errorCode = $code")
    }
}