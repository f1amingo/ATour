package com.sanwei.sanwei4a.activity

import android.os.Bundle
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.po.Problem
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.account.AccountService
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_problem.*

class ProblemActivity : BaseActivity() {

    private lateinit var mAccountService: AccountService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem)

        mAccountService = AccountService()

        initToolbar()
        initBtnSubmit()
    }

    private fun initBtnSubmit() {
        z_btn_submit_problem.setOnClickListener {
            if (!checkInput()) return@setOnClickListener
            showWaitingDlg()
            val problem = Problem()
            problem.problemAccid = App.account?.accId
            problem.problemDescribe = z_edit_content_problem.text.toString()
            problem.problemPhone = z_phone_content_problem.text.toString()
            problem.problemQq = z_edit_qq_problem.text.toString()
            mAccountService.pushProblem(problem, object : OnRequestListener<Problem> {
                override fun onSuccess(data: Problem) {
                    runOnUiThread {
                        toast("提交成功")
                        dismissWaitingDlg()
                    }
                }

                override fun onFail(msg: String?, code: Int) {
                    runOnUiThread {
                        toast(msg!!)
                        dismissWaitingDlg()
                    }
                }
            })
        }
    }

    private fun checkInput(): Boolean {
        if (z_edit_content_problem.text.isEmpty()) {
            toast("问题描述不能为空")
            return false
        }
        return true
    }

    private fun initToolbar() {
        z_toolbar_problem.setNavigationOnClickListener { finish() }
    }
}
