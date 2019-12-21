package com.sanwei.sanwei4a.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import com.alibaba.fastjson.JSONObject
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.RequestCallback
import com.netease.nimlib.sdk.RequestCallbackWrapper
import com.netease.nimlib.sdk.msg.MessageBuilder
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.netease.nimlib.sdk.msg.model.QueryDirectionEnum
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.ChatListAdapter
import com.sanwei.sanwei4a.adapter.ItemChatMsg
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class ChatActivity : BaseActivity() {

    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBtnSend: View
    private lateinit var mAdapter: ChatListAdapter
    private var mInput: String = ""
    private var accId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        accId = intent.getStringExtra("accId")

        mToolbar = find(R.id.z_toolbar_chat)
        mRecyclerView = find(R.id.z_recycler_chat)
        mBtnSend = find(R.id.z_btn_send_chat)
        mBtnSend.isEnabled = false

        initEditText()
        initToolbar()
        initRecyclerView()
        initBtnSend()

        fetchChatRecord()
    }

    private fun initEditText() {
        z_input_chat.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mInput = text.toString()
                mBtnSend.isEnabled = !mInput.isBlank()
                Log.e(TAG, "onTextChanged $mInput")
            }
        })
    }

    private fun fetchChatRecord() {
        val anchor = MessageBuilder.createEmptyMessage("test1", SessionTypeEnum.P2P, 0)
        NIMClient.getService(MsgService::class.java).queryMessageListEx(anchor, QueryDirectionEnum.QUERY_NEW,
                20, true).setCallback(object : RequestCallbackWrapper<List<IMMessage>>() {
            override fun onResult(code: Int, result: List<IMMessage>?, exception: Throwable?) {
                Log.e(TAG, "获取聊天记录成功")
                result!!.forEach {
                    Log.e(TAG, JSONObject.toJSONString(it))
                    mAdapter.addData(ItemChatMsg(it.content, it.time, it.direct))
                    mRecyclerView.smoothScrollToPosition(mAdapter.itemCount - 1)
                }
            }
        })
    }

    private fun initBtnSend() {
        mBtnSend.setOnClickListener {
            if (mInput == "") return@setOnClickListener
            // 发送
            val textMessage = MessageBuilder.createTextMessage(accId, SessionTypeEnum.P2P, mInput)
            NIMClient.getService(MsgService::class.java)
                    .sendMessage(textMessage, false)
                    .setCallback(object : RequestCallback<Void> {
                        override fun onSuccess(param: Void?) {
                            Log.e(TAG, "消息发送成功")
                            //更新UI
                            val newItem = ItemChatMsg(textMessage.content, System.currentTimeMillis(), MsgDirectionEnum.Out)
                            mAdapter.addData(newItem)
                            //向下滚动
                            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount - 1)
                            z_input_chat.setText("")
                        }

                        override fun onFailed(code: Int) {
                            Log.e(TAG, "消息发送失败")
                            toast("消息发送失败")
                        }

                        override fun onException(exception: Throwable?) {
                            Log.e(TAG, "消息发送异常")
                            toast("消息发送异常")
                        }
                    })
        }
    }

    private fun initRecyclerView() {
        mAdapter = ChatListAdapter(R.layout.z_item_chat_msg, ArrayList())
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener { finish() }
    }
}
