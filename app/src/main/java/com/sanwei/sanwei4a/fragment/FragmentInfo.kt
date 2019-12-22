package com.sanwei.sanwei4a.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSONObject
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.Observer
import com.netease.nimlib.sdk.RequestCallbackWrapper
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.MsgServiceObserve
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.netease.nimlib.sdk.msg.model.RecentContact
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.activity.ChatActivity
import com.sanwei.sanwei4a.activity.ChatBotActivity
import com.sanwei.sanwei4a.adapter.ItemNotification
import com.sanwei.sanwei4a.adapter.NotificationListAdapter
import kotlinx.android.synthetic.main.fragment_info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Flamingo on 2019/12/14.
 */
class FragmentInfo : BaseFragment() {

    private lateinit var mAdapter: NotificationListAdapter
    private val onMsgReceiveListener = Observer<List<IMMessage>> {
        toast("收到一条新消息")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_info, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        cover_notification_chat_bot.setOnClickListener {
            startActivityForResult<ChatBotActivity>(1)
        }

        initRecyclerView()
        initSwipeRefreshLayout()
        getRecentSessions()
        initRecentContactUpdateListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == 1) {
            val content = data!!.getStringExtra("content")
            val time = data.getLongExtra("time", Date().time)
            txt_chat_bot_notification.text = content
            txt_chat_bot_notification_time.text = SimpleDateFormat("YYYY-MM-dd hh:mm:ss", Locale.CHINA).format(time)
        }
    }

    override fun onResume() {
        super.onResume()
        initOnReceiveMessage(true)
    }

    override fun onPause() {
        super.onPause()
        initOnReceiveMessage(false)
    }

    private fun initRecentContactUpdateListener() {
        NIMClient.getService(MsgServiceObserve::class.java)
                .observeRecentContact({
                    getRecentSessions()
                }, true)
    }

    private fun getRecentSessions() {
        Log.e(TAG, "获取最近会话列表")
        NIMClient.getService(MsgService::class.java).queryRecentContacts()
                .setCallback(object : RequestCallbackWrapper<List<RecentContact?>?>() {
                    override fun onResult(code: Int, recents: List<RecentContact?>?, e: Throwable?) {
                        if (e != null) {
                            Log.e(TAG, "IM获取最近会话列表异常 ${e.message}")
                            toast("获取最近会话异常")
                        }
                        Log.e(TAG, "获取最近会话列表成功")
                        mAdapter.mItems.clear()
                        recents?.forEach {
                            Log.e(TAG, JSONObject.toJSONString(it))
                            it!!
                            mAdapter.addData(ItemNotification(
                                    contactId = it.contactId,
                                    content = it.content,
                                    fromAccount = it.fromAccount,
                                    fromNick = it.fromNick,
                                    recentMessageId = it.recentMessageId,
                                    time = it.time,
                                    unreadCount = it.unreadCount
                            ))
                        }
                    }
                })
    }

    private fun initOnReceiveMessage(register: Boolean) {
        NIMClient.getService(MsgServiceObserve::class.java)
                .observeReceiveMessage(onMsgReceiveListener, register)
    }

    private fun initSwipeRefreshLayout() {
        z_refresh_info.setOnRefreshListener {
            doAsync {
                Thread.sleep(500)
                uiThread {
                    z_refresh_info.isRefreshing = false
                }
            }
        }
    }

    private fun initRecyclerView() {
        mAdapter = NotificationListAdapter(R.layout.z_item_notification, ArrayList())
        z_recycler_info.layoutManager = LinearLayoutManager(context)
        z_recycler_info.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            startActivity<ChatActivity>("accId" to item!!.contactId)
            NIMClient.getService(MsgService::class.java)
                    .clearUnreadCount(item.contactId, SessionTypeEnum.P2P)
        }
    }
}