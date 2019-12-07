package com.sanwei.sanwei4a

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import com.facebook.drawee.backends.pipeline.Fresco
import com.sanwei.sanwei4a.activity.SystemNotificationActivity
import com.sanwei.sanwei4a.entity.po.Account
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import org.jetbrains.anko.defaultSharedPreferences

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        hasSelectPreferTags = mContext.defaultSharedPreferences.getBoolean("preferTags", false)
        Fresco.initialize(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityStarted(p0: Activity?) {
                if (count == 0) {
                    LogUtil.d("ActivityLifecycleCallbacks", "前台运行")
                }
                count++
                LogUtil.d("ActivityLifecycleCallbacks", "onActivityStarted count：$count")
            }

            override fun onActivityStopped(p0: Activity?) {
                count--
                if (count == 0) {
                    LogUtil.d("ActivityLifecycleCallbacks", "后台运行")
                }
                LogUtil.d("ActivityLifecycleCallbacks", "onActivityStopped count：$count")
            }

            override fun onActivityCreated(p0: Activity?, p1: Bundle?) = Unit

            override fun onActivityResumed(p0: Activity?) = Unit

            override fun onActivityPaused(p0: Activity?) = Unit

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) = Unit

            override fun onActivityDestroyed(p0: Activity?) = Unit

        })
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mContext: Context
        //前后台运行
        private var count = 0
        var account: Account? = null
        private var isChannelCreated: Boolean = false
        const val USER_AGENT = "AC"
        private const val channel_id = "cheese"
        private const val notificationId = 377

        const val intentActionSystem = "com.sanwei.sanwei4a.system.notification"

        fun getImgUrl(): String? {
            if (account == null) return null
            return RequestUtil.getHeadPicAddr(account!!.accId, account!!.accPic)
        }

        fun showSystemNotification(title: String, content: String) {
            val pendingIntent = PendingIntent.getActivity(mContext,
                    0,
                    Intent(mContext, SystemNotificationActivity::class.java),
                    0)
            val builder = NotificationCompat.Builder(App.mContext, channel_id)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_cheese)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
//                    .setWhen(System.currentTimeMillis())
//                        .setLargeIcon(BitmapFactory.decodeResource(App.mContext.resources, R.drawable.ic_cheese))
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                createNotificationChannel()

            mContext.getSystemService(NotificationManager::class.java).notify(notificationId, builder.build())
        }

        private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !isChannelCreated) {
                val chanel = NotificationChannel(channel_id,
                        "芝士外脑",
                        NotificationManager.IMPORTANCE_HIGH)
                chanel.description = "芝士外脑即时通信服务"
                val notificationManager = mContext.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(chanel)
                isChannelCreated = true
            }
        }

        //是否在前台运行
        fun isRunningForeground(): Boolean {
            if (count == 0) return false
            return true
        }

        var hasSelectPreferTags: Boolean = false

        fun changeHasSelectPreferTags() {
            hasSelectPreferTags = true
            mContext.defaultSharedPreferences.edit().putBoolean("preferTags", hasSelectPreferTags).apply()
        }
    }
}