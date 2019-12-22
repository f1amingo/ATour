package com.sanwei.sanwei4a.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.activity.*
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_BORROWED
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_FAVORITE
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_PUBLISHED
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_SENT
import com.sanwei.sanwei4a.util.FileHelper
import com.sanwei.sanwei4a.util.LogUtil
import kotlinx.android.synthetic.main.mine_fragment.*
import okhttp3.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult
import java.io.IOException


/**
 * Created by Johnson on 2018/3/31.
 *
 */
class FragmentMine : BaseFragment() {

    private lateinit var mBtnLogin: Button
    private lateinit var mTxtName: TextView
    private lateinit var mBalance: TextView
    private lateinit var mInviteCode: TextView
    private lateinit var mProblem: TextView
    private val mRcLogin = 7
    private val mRcModify = 77
    private var result = "sorry,I can't get it!"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.mine_fragment, container, false)!!

        mBtnLogin = view.find(R.id.z_btn_login_mine)
        mTxtName = view.find(R.id.z_txt_username_mine)
        mBalance = view.find(R.id.z_txt_points_balance)
        mProblem = view.find(R.id.z_txt_problem_manage_mine)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mTxtName.visibility = View.GONE

        mBtnLogin.setOnClickListener {
            startActivityForResult(Intent(context, LoginActivity::class.java), mRcLogin)
        }


        z_img_head_mine.setOnClickListener {
            if (App.account == null) {
                startActivityForResult(Intent(context, LoginActivity::class.java), mRcLogin)
            } else {
                startActivityForResult<MineDetailsActivity>(mRcModify)
            }
        }
        mProblem.setOnClickListener {
            startActivity(Intent(context, ProblemActivity::class.java))
        }
        initAddressManage()

        initProblem()

        if (App.account != null) {
            fillUserInfo()
        }

        initFourLayouts()
        initFourRows()

        bot_test.setOnClickListener(){

            var message = query_message.getText().toString()
            chatbot(message)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (App.account != null) {
            try {
                z_txt_points_balance.text = App.account!!.accBanlance.toString()
                FileHelper.saveAccountInfo(App.account!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initFourRows() {
        z_text_sanwei_mine.setOnClickListener { toast("敬请期待") }
        z_text_gold_mine.setOnClickListener { toast("敬请期待") }
        z_text_bridge_mine.setOnClickListener { toast("敬请期待") }
        //z_text_grass_mine.setOnClickListener { toast("敬请期待") }
    }

    private fun initProblem() {
        z_txt_problem_manage_mine.setOnClickListener {
            startActivity<ProblemActivity>()
        }
    }

    private fun chatbot(message: String){

        var okHttpClient = OkHttpClient()
        var JSON = MediaType.parse("application/json; charset=utf-8");
        var param = HashMap<String, String>()
        param.put("spoken", message)
        param.put("userid", "user")
        val jsonStr = JSONObject.toJSONString(param)
        Log.d("test",jsonStr)
        val request = Request.Builder()
                .post(RequestBody.create(JSON, jsonStr))
                .url("https://api.ownthink.com/bot")
                .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail","failure")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response?.isSuccessful == true) {
                    val strBody = response.body()!!.string()
                    LogUtil.e(TAG, strBody)
                    val jsonObj = JSONObject.parseObject(strBody)
                    result = jsonObj.getJSONObject("data").getJSONObject("info").getString("text")
                    getActivity().runOnUiThread{
                        show_result.setText(result)
                    }
                }
            }
        })

    }

    private fun initFourLayouts() {
        z_layout_one_release_mine.setOnClickListener {
            if (App.account == null) {
                toast("尚未登录")
            } else {
                startActivity<BookListActivity>("type" to TYPE_PUBLISHED)
            }
        }
        z_layout_two_out_mine.setOnClickListener {
            if (App.account == null) {
                toast("尚未登录")
            } else {
                startActivity<BookListActivity>("type" to TYPE_SENT)
            }
        }
        z_layout_three_in_mine.setOnClickListener {
            if (App.account == null) {
                toast("尚未登录")
            } else {
                startActivity<BookListActivity>("type" to TYPE_BORROWED)
            }
        }
        z_layout_four_wanted_mine.setOnClickListener {
            if (App.account == null) {
                toast("尚未登录")
            } else {
                startActivity<BookListActivity>("type" to TYPE_FAVORITE)
            }
        }
    }

    private fun initAddressManage() {
        find<TextView>(R.id.z_txt_address_manage_mine).setOnClickListener {
            if (App.account != null)
                startActivity<AddressManageActivity>()
            else
                toast("尚未登陆")
        }
    }

    private fun fillUserInfo() {
        loadUserHeadImg()
        mTxtName.visibility = View.VISIBLE
        mTxtName.text = App.account?.accNickname
        mBalance.text = App.account?.accBanlance.toString()
        mInviteCode.text = App.account?.accInvitecode
        mBtnLogin.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            mRcLogin -> {
                if (resultCode == Activity.RESULT_OK) {
                    fillUserInfo()
                }
            }
            mRcModify -> {
                if (resultCode == Activity.RESULT_OK) {
                    LogUtil.d(TAG, "用户头像或昵称已修改")
                    if (App.account == null) {
                        mBtnLogin.visibility = View.VISIBLE
                        mTxtName.visibility = View.GONE
                        z_img_head_mine.setImageResource(R.drawable.ic_cheese)
                    } else {
                        loadUserHeadImg()
                        mTxtName.text = App.account!!.accNickname
                    }
                }
            }
        }
    }

    private fun loadUserHeadImg() {
        Glide.with(this)
                .load(App.getImgUrl())
                .apply(RequestOptions().error(R.drawable.ic_cheese).placeholder(R.drawable.ic_cheese))
                .into(z_img_head_mine)
    }
}