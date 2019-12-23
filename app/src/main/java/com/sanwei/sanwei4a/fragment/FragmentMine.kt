package com.sanwei.sanwei4a.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.activity.LoginActivity
import com.sanwei.sanwei4a.activity.MineDetailsActivity
import com.sanwei.sanwei4a.activity.MyOrderActivity
import com.sanwei.sanwei4a.activity.ProblemActivity
import kotlinx.android.synthetic.main.mine_fragment.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult

class FragmentMine : BaseFragment() {

    private lateinit var mProblem: TextView
    private val mRcLogin = 7
    private val mRcModify = 77
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.mine_fragment, container, false)!!
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