package com.sanwei.sanwei4a.activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.EditText
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.ChatListAdapter
import com.sanwei.sanwei4a.adapter.ItemChatMsg
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.find

class ChatActivity : BaseActivity() {

    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBtnSend: View
    private lateinit var mAdapter: ChatListAdapter
    private var mInput: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

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
                if (mInput.isBlank()) {
                    mBtnSend.isEnabled = false
                } else {
                    mBtnSend.isEnabled = true
                }
            }
        })
    }

    private fun fetchChatRecord() {
        for (i in 1..5) {
            mAdapter.addData(ItemChatMsg("测试消息$i",
                    System.currentTimeMillis(),
                    ItemChatMsg.TYPE_RECEIVED))
        }
    }

    private fun initBtnSend() {
        mBtnSend.setOnClickListener {
            //            val input = mEdit.text.toString()
//            if (input == "") return@setOnClickListener
//            mEdit.setText("")
//            //添加新消息
//            val newItem = ItemChatMsg(input, System.currentTimeMillis(), ItemChatMsg.TYPE_SENT)
//            mAdapter.addData(newItem)
//            //向下滚动
//            mRecyclerView.smoothScrollToPosition(mAdapter.itemCount - 1)
            //发送
//            val contentBody = ContentBody(STRING_TYPE, input.toByteArray(Charset.forName(DEFAULT_ENCODING)))
//            val content = MessagePackager.mpackage(contentBody)
//            sendMsg(content, mThisAccountId)
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
