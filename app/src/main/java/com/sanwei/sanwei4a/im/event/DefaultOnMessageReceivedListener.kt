package com.sanwei.sanwei4a.im.event

import android.content.Intent
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.im.PIC_TYPE
import com.sanwei.sanwei4a.im.STRING_TYPE
import com.sanwei.sanwei4a.im.VIDEO_TYPE
import com.sanwei.sanwei4a.im.VOICE_TYPE
import com.sanwei.sanwei4a.im.protocal.MainContent.Message
import com.sanwei.sanwei4a.room.AppDatabase
import com.sanwei.sanwei4a.room.SystemNotiEntity
import com.sanwei.sanwei4a.util.LogUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.json.JSONObject


class DefaultOnMessageReceivedListener : OnMessageReceivedListener {
    val TAG = javaClass.simpleName

    override fun onGroupMessageReceived(message: Message, fromId: Int, groupId: Int) {
        val contentType = message.type
        when (contentType) {
            STRING_TYPE -> {
                LogUtil.d(TAG, "收到 $fromId 发送的 群(id = $groupId)文字消息 : ${message.content.toStringUtf8()}")

            }
            PIC_TYPE -> {
                LogUtil.d(TAG, "收到 $fromId 发送的 群(id = $groupId)图片消息")
            }
            VOICE_TYPE -> {
                LogUtil.d(TAG, "收到 $fromId 发送的 群(id = $groupId)声音消息")
            }
            VIDEO_TYPE -> {
                LogUtil.d(TAG, "收到 $fromId 发送的 群(id = $groupId)视频消息")
            }
            else -> {
                LogUtil.d(TAG, "不支持此类型消息")
            }
        }
    }

    override fun onP2PMessageReceived(message: Message, fromId: Int) {
        val contentType = message.type
        when (contentType) {
            STRING_TYPE -> {
                LogUtil.d(TAG, "收到 $fromId 发送的P2P 文字消息 : ${message.content.toStringUtf8()}")
                App.mContext.toast(message.content.toStringUtf8())
            }
            PIC_TYPE -> {
                LogUtil.d(TAG, "收到 $fromId 发送的P2P 图片消息")
            }
            VOICE_TYPE -> {
                LogUtil.d(TAG, "收到 $fromId 发送的P2P 声音消息")
            }
            VIDEO_TYPE -> {
                LogUtil.d(TAG, "收到 $fromId 发送的P2P 视频消息")
            }
            else -> {
                LogUtil.d(TAG, "不支持此类型消息")
            }
        }
    }

    override fun onSystemMessageReceived(message: Message) {
        val contentType = message.type
        when (contentType) {
            STRING_TYPE -> {
                val contentJson = JSONObject(message.content.toStringUtf8())
                val title = contentJson.get("title").toString()
                val content = contentJson.get("content").toString()
                val timestamp = contentJson.getLong("timestamp")
                LogUtil.d(TAG, "收到系统文字消息 : $contentJson")
                if (App.isRunningForeground()) {
                    App.mContext.sendBroadcast(Intent(App.intentActionSystem).putExtra("title", title).putExtra("content", content))
                } else {
                    App.showSystemNotification(title, content)
                }
                doAsync {
                    AppDatabase.get().getSystemNotiDao().insertOne(SystemNotiEntity(title, content, timestamp))
                }
            }
            PIC_TYPE -> {
                LogUtil.d(TAG, "收到系统图片消息")
            }
            VOICE_TYPE -> {
                LogUtil.d(TAG, "收到系统声音消息")
            }
            VIDEO_TYPE -> {
                LogUtil.d(TAG, "收到系统视频消息")
            }
            else -> {
                LogUtil.d(TAG, "不支持此类型消息")
            }
        }
    }
}