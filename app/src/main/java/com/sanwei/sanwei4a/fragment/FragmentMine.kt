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
import org.jetbrains.anko.support.v4.toast


/**
 * Created by Johnson on 2018/3/31.
 *
 */
class FragmentMine : BaseFragment() {

    private lateinit var mBalance: TextView
    private lateinit var mProblem: TextView
    private val mRcLogin = 7
    private val mRcModify = 77
    private var result = "sorry,I can't get it!"
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.mine_fragment, container, false)!!
        mBalance = view.find(R.id.z_txt_points_balance)
        mProblem = view.find(R.id.z_txt_problem_manage_mine)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
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

        initProblem()

        bot_test.setOnClickListener {
            val message = query_message.getText().toString()
        }

        layout_mine_order.setOnClickListener {
            startActivity<MyOrderActivity>()
        }
    }

    private fun initProblem() {
        z_txt_problem_manage_mine.setOnClickListener {
            startActivity<ProblemActivity>()
        }
    }


}