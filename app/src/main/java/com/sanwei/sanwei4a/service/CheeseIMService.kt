package com.sanwei.sanwei4a.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.im.IMClientManager
import com.sanwei.sanwei4a.im.event.DefaultOnMessageReceivedListener
import com.sanwei.sanwei4a.util.LogUtil
import per.johnson.server.core.LocalUDPDataSender

class CheeseIMService : Service() {

    private val TAG = javaClass.simpleName

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.d(TAG, "服务onStartCommand")


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG, "服务onDestroy")
        IMClientManager.getIMClientManager().release()
    }
}
