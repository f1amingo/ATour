package com.sanwei.sanwei4a.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.ChatListAdapter
import com.sanwei.sanwei4a.adapter.ItemChatMsg
import com.sanwei.sanwei4a.util.LogUtil
import kotlinx.android.synthetic.main.activity_chat_bot.*
import okhttp3.*
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChatBotActivity : BaseActivity() {
    private lateinit var mAdapter: ChatListAdapter
    private var mInput: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)
        toolbar_chat_bot.setNavigationOnClickListener { finish() }
        initEditText()
        initRecyclerView()
        initBtnSend()
        getHistory()
    }

    private fun getHistory() {
        try {
            val inputStream = openFileInput("chat_bot")
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line = bufferedReader.readLine()
            val stringBuilder = StringBuilder()
            while (line != null) {
                stringBuilder.append(line)
                line = bufferedReader.readLine()
            }
            val jsonArray = JSONArray.parseArray(stringBuilder.toString())
            mAdapter.mItems.clear()
            jsonArray.forEach {
                val item = it as JSONObject
                val direct = if (item.getString("direct") == "Out") {
                    MsgDirectionEnum.Out
                } else {
                    MsgDirectionEnum.In
                }
                mAdapter.addData(ItemChatMsg(
                        item.getString("content"),
                        item.getLong("time"),
                        direct,
                        item.getBoolean("bot")
                ))
            }
        } catch (e: Exception) {
            LogUtil.e(TAG, e.message.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val str = JSONObject.toJSONString(mAdapter.mItems)
        LogUtil.e(TAG, str)
        val outputStream = openFileOutput("chat_bot", 0)
        val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream))
        bufferedWriter.write(str)
        bufferedWriter.flush()
        bufferedWriter.close()
        val intent = Intent()
        intent.putExtra("content", mAdapter.mItems[mAdapter.mItems.size - 1].content)
        intent.putExtra("time", mAdapter.mItems[mAdapter.mItems.size - 1].time)
        setResult(1, intent)
    }

    private fun initBtnSend() {
        btn_send_chat_bot.isEnabled = false
        btn_send_chat_bot.setOnClickListener {
            if (mInput == "") return@setOnClickListener
            mAdapter.addData(ItemChatMsg(mInput, Date().time, MsgDirectionEnum.Out, true))
            showWaitingDlg()
            val okHttpClient = OkHttpClient()
            val mediaType = MediaType.parse("application/json; charset=utf-8")
            val param = HashMap<String, String>()
            param["spoken"] = mInput
            param["userid"] = "user"
            val jsonStr = JSONObject.toJSONString(param)
            Log.d("test", jsonStr)
            val request = Request.Builder()
                    .post(RequestBody.create(mediaType, jsonStr))
                    .url("https://api.ownthink.com/bot")
                    .build()

            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("fail", "failure")
                    runOnUiThread {
                        dismissWaitingDlg()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val strBody = response.body()!!.string()
                        LogUtil.e(TAG, strBody)
                        val jsonObj = JSONObject.parseObject(strBody)
                        val result = jsonObj.getJSONObject("data").getJSONObject("info").getString("text")
                        runOnUiThread {
                            mAdapter.addData(ItemChatMsg(result, Date().time, MsgDirectionEnum.In, true))
                            dismissWaitingDlg()
                        }
                    }
                }
            })
        }
    }

    private fun initRecyclerView() {
        mAdapter = ChatListAdapter(R.layout.z_item_chat_msg, ArrayList())
        recycler_chat_bot.layoutManager = LinearLayoutManager(this)
        recycler_chat_bot.adapter = mAdapter
    }

    private fun initEditText() {
        input_chat_bot.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mInput = text.toString()
                btn_send_chat_bot.isEnabled = !mInput.isBlank()
                Log.e(TAG, "onTextChanged $mInput")
            }
        })
    }
}
