package com.sanwei.sanwei4a.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.doAsync
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.AccountParam
import com.sanwei.sanwei4a.entity.parameter.BaseParameter
import com.sanwei.sanwei4a.entity.parameter.CodeParam
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.po.Account
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.util.EncryptUtil
import com.sanwei.sanwei4a.util.Functions
import com.sanwei.sanwei4a.util.RequestUtil
import com.sanwei.sanwei4a.util.StringUtils
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.appcompat.v7.themedActionBarOverlayLayout
import org.jetbrains.anko.find
import java.io.IOException

class RegisterActivity : BaseActivity() {
    /** 用户手机*/
    private var mPhone = ""
    /** 密码*/
    private var mPsw = ""
    /** 验证码*/
    private var mCode = ""
    /** 邀请码*/
    private var mInviteCode = ""

    private var smsId: Int? = null

    private lateinit var mToolbar: Toolbar
    private lateinit var mBtnSendCode: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.WHITE
        setContentView(R.layout.activity_register)

        mToolbar = find(R.id.z_toolbar_register)
        mBtnSendCode = find(R.id.z_btn_sendCode_register)

        initToolbar()
        initEditText()
        initBtnSendCode()
        initBtnRegister()
    }

    private fun initBtnRegister() {
        z_btn_login_register.setOnClickListener {
            if (!checkInput())
                return@setOnClickListener
            showWaitingDlg()
            doAsync {
                val account = Account(0, null, EncryptUtil.md5(mPsw), mPhone, null, null, mInviteCode, 0)
                val accountParam = AccountParam(smsId, account, mInviteCode)
                RequestUtil.doPost(Functions.REGISTER, null, accountParam, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        runOnUiThread {
                            toast("网络错误")
                            dismissWaitingDlg()
                        }
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        //验证返回值
                        if (response?.isSuccessful == true) {
                            val str = response.body()?.string()
                            Log.d(TAG, str)
                            val reference = object : TypeReference<Result<Account>>() {}
                            val result = JSONObject.parseObject(str, reference)
                            if (result.code == Constant.OK_CODE) {
                                App.account = result.data
                                runOnUiThread {
                                    toast("注册成功")
                                    dismissWaitingDlg()
                                    val intent = Intent()
                                    intent.putExtra("phone", mPhone)
                                    intent.putExtra("password", mPsw)
                                    setResult(Activity.RESULT_OK, intent)
                                    finish()
                                }
                            } else {
                                runOnUiThread {
                                    toast(result.msg)
                                    dismissWaitingDlg()
                                }
                            }
                        } else {
                            runOnUiThread {
                                toast("网络错误")
                                dismissWaitingDlg()
                            }
                        }
                    }
                })
            }
        }
    }

    private fun checkInput(): Boolean {
        if (!StringUtils.isLegalPhoneNum(mPhone)) {
            toast("手机号错误")
            return false
        }
        if (mCode.length < 6) {
            toast("验证码格式错误")
            return false
        }
        if (mPsw.length < 8) {
            toast("密码格式错误")
            return false
        }
        return true
    }

    private fun initEditText() {
        z_edit_phone_register.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mPhone = input.toString()
            }
        })
        z_edit_password_register.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mPsw = input.toString()
            }
        })
        z_edit_code_register.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mCode = input.toString()
            }
        })
        z_edit_InviteCode_register.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mInviteCode = input.toString()
            }
        })
    }

    private fun initBtnSendCode() {
        mBtnSendCode.setOnClickListener {
            if (!StringUtils.isLegalPhoneNum(mPhone)) {
                toast("手机号格式错误")
                return@setOnClickListener
            }
            val timer = TimeCount(mBtnSendCode, 60000, 1000)
            timer.start()
            doAsync {
                val param = CodeParam()
                param.time = BaseParameter.parseCurrentTime()
                param.type = CodeParam.CodeType.REGISTER
                param.userPhone = mPhone
                runOnUiThread { showWaitingDlg() }
                RequestUtil.doPost(Functions.SENDCODE, null, param, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        runOnUiThread {
                            toast("网络错误")
                            dismissWaitingDlg()
                        }
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        try {
                            if (response?.isSuccessful == true) {
                                val str = response.body()?.string()!!
                                Log.d(TAG, str)
                                val reference = object : TypeReference<Result<Int>>() {}
                                val result = JSONObject.parseObject(str, reference)
                                if (result.code == Constant.OK_CODE) {
                                    smsId = result.data
                                    runOnUiThread {
                                        toast(result.msg)
                                    }
                                } else {
                                    runOnUiThread { toast("发送失败，在我的界面反馈错误!") }
                                }
                                runOnUiThread { dismissWaitingDlg() }
                            } else {
                                runOnUiThread {
                                    toast("请求错误")
                                    dismissWaitingDlg()
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            runOnUiThread {
                                toast(e.message!!)
                                dismissWaitingDlg()
                            }
                        }
                    }
                })
            }
        }
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener { finish() }
    }

    private class TimeCount(val bt: Button, millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {
            bt.text = "获取验证码"
            bt.isClickable = true
        }

        override fun onTick(millisUntilFinished: Long) {
            val text = "${millisUntilFinished / 1000}秒后重新发送"
            bt.text = text
            bt.isClickable = false
        }
    }
}
