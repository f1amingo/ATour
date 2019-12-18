package com.sanwei.sanwei4a

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.facebook.drawee.backends.pipeline.Fresco
import com.netease.nimlib.sdk.*
import com.netease.nimlib.sdk.auth.AuthService
import com.netease.nimlib.sdk.auth.AuthServiceObserver
import com.netease.nimlib.sdk.auth.LoginInfo
import com.netease.nimlib.sdk.msg.MessageBuilder
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.MsgServiceObserve
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.netease.nimlib.sdk.uinfo.UserInfoProvider
import com.netease.nimlib.sdk.uinfo.model.UserInfo
import com.netease.nimlib.sdk.util.NIMUtil
import com.sanwei.sanwei4a.activity.SystemNotificationActivity
import com.sanwei.sanwei4a.entity.po.Account
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.toast


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

        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), options())
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
            NIMClient.getService(AuthServiceObserver::class.java).observeOnlineStatus(
                    { status ->
                        Log.d("App", status.name)
                        if (status.name == "UNLOGIN") {
                            NIMClient.getService(AuthService::class.java).login(loginInfo())
                                    .setCallback(object : RequestCallback<LoginInfo> {
                                        override fun onSuccess(param: LoginInfo?) {
                                            Log.e("APP", "IM登录成功")
                                        }

                                        override fun onFailed(code: Int) {
                                            Log.e("APP", "IM登录失败")
                                        }

                                        override fun onException(exception: Throwable?) {
                                            Log.e("APP", "IM登录异常")
                                        }
                                    })
                        }
                    }, true)



        }
    }

    // 如果返回值为 null，则全部使用默认参数。
    private fun options(): SDKOptions {
        val options = SDKOptions()
        options.appKey = "afcf5899f1d81c39a6780a7cbbf4b322"
        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
//        val config = StatusBarNotificationConfig()
//        config.notificationEntrance = WelcomeActivity; // 点击通知栏跳转到该Activity
//        config.notificationSmallIconId = R.drawable.ic_stat_notify_msg;
        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true
        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
//        options.thumbnailSize = ${ Screen.width } / 2;
        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = object : UserInfoProvider {
            override fun getUserInfo(account: String?): UserInfo? {
                return null
            }

            override fun getAvatarForMessageNotifier(sessionType: SessionTypeEnum?, sessionId: String?): Bitmap? {
                return null
            }

            override fun getDisplayNameForMessageNotifier(account: String?, sessionId: String?, sessionType: SessionTypeEnum?): String? {
                return null
            }
        }
        return options
    }

    private fun loginInfo(): LoginInfo? {
        return LoginInfo("test2", "a6156a446a50c1f5d014ca3d910092e8")
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

            mContext.getSystemService(NotificationManager::class.java)!!.notify(notificationId, builder.build())
        }

        private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !isChannelCreated) {
                val chanel = NotificationChannel(channel_id,
                        "芝士外脑",
                        NotificationManager.IMPORTANCE_HIGH)
                chanel.description = "芝士外脑即时通信服务"
                val notificationManager = mContext.getSystemService(NotificationManager::class.java)
                notificationManager!!.createNotificationChannel(chanel)
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