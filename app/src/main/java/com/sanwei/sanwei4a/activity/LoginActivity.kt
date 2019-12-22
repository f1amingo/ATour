package com.sanwei.sanwei4a.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.AccountParam
import com.sanwei.sanwei4a.entity.parameter.BaseParameter
import com.sanwei.sanwei4a.entity.po.Account
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.util.*
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.*
import per.johnson.server.core.LocalUDPDataSender
import java.io.IOException

class LoginActivity : BaseActivity() {

    private var phone = ""
    private var password = ""
    private lateinit var mToolbar: Toolbar
    private lateinit var mEditPhone: EditText
    private lateinit var mEditPsw: EditText

    private val mRcRegister: Int = 137
    private val mRcFindPsw: Int = 138

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.WHITE
        setContentView(R.layout.activity_login)
        mToolbar = find(R.id.z_toolbar_login)
        mEditPhone = find(R.id.z_edit_phone_login)
        mEditPsw = find(R.id.z_edit_password_login)
        initToolbar()
        initEditText()
        initBtnLogin()
        initFindPsw()
    }

    private fun initFindPsw() {
        z_txt_findPsw_login.setOnClickListener {
            startActivityForResult<FindPswActivity>(mRcFindPsw)
        }
    }

    private fun initBtnLogin() {
        find<View>(R.id.z_btn_login_login).setOnClickListener {
            if (checkInput()) {
                showWaitingDlg()
                doAsync {
                    val account = Account()
                    account.accPhone = phone
                    account.accPassword = EncryptUtil.md5(password)
                    val accountParam = AccountParam(null, account, null)
                    accountParam.time = BaseParameter.parseCurrentTime()
                    try {
                        RequestUtil.doPost(Functions.LOGIN, null, accountParam, object : Callback {
                            override fun onFailure(call: Call?, e: IOException?) {
                                runOnUiThread {
                                    toast(getString(R.string.internet_error))
                                    dismissWaitingDlg()
                                }
                            }

                            override fun onResponse(call: Call?, response: Response?) {
                                try {
                                    if (response?.isSuccessful == true) {
                                        val str = response.body()?.string()!!
                                        Log.d(TAG, str)
                                        val reference = object : TypeReference<Result<Account>>() {}
                                        val result = JSONObject.parseObject(str, reference)
                                        if (result.code == Constant.OK_CODE) {
                                            App.account = result.data
                                            FileHelper.saveAccountInfo(App.account!!)
                                            Log.e(TAG, App.account.toString())
                                            object : LocalUDPDataSender.SendLoginDataAsync(applicationContext, App.account!!.accId.toString(), App.account!!.token) {
                                                override fun fireAfterSendLogin(code: Int) {
                                                    Log.d(TAG, "loginCode: $code ")
                                                }
                                            }.execute()
                                            runOnUiThread {
                                                dismissWaitingDlg()
                                                setResult(Activity.RESULT_OK)
                                                finish()
                                            }
                                        } else {
                                            runOnUiThread {
                                                toast("用户名或密码错误")
                                                dismissWaitingDlg()
                                            }
                                        }
                                    } else {
                                        runOnUiThread {
                                            toast("请求错误")
                                            dismissWaitingDlg()
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.e(TAG, e.message)
                                    e.printStackTrace()
                                    runOnUiThread {
                                        toast(e.message!!)
                                        dismissWaitingDlg()
                                    }
                                }
                            }
                        })
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            toast(e.message!!)
                            dismissWaitingDlg()
                        }
                    }
                }
            }
        }
    }

    private fun checkInput(): Boolean {
        if (!StringUtils.isLegalPhoneNum(phone)) {
            toast("手机号格式错误")
            return false
        }
        if (password.length < 8) {
            toast("密码格式错误")
            return false
        }
        return true
    }

    private fun initEditText() {
        mEditPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phone = input.toString()
            }
        })

        mEditPsw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                password = input.toString()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            mRcRegister -> {
                if (resultCode == Activity.RESULT_OK) {
                    data!!
                    mEditPhone.setText(data.getStringExtra("phone"))
                    mEditPsw.setText(data.getStringExtra("password"))
                    z_btn_login_login.performClick()
                }
            }
        }
    }

    private fun initToolbar() {
        mToolbar.inflateMenu(R.menu.menu_login)
        mToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.z_action_register -> {
                    startActivityForResult<RegisterActivity>(mRcRegister)
                }
            }
            return@setOnMenuItemClickListener true
        }
        mToolbar.setNavigationOnClickListener {
            finish()
        }
    }
}

