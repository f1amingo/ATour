package com.sanwei.sanwei4a.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.sanwei.sanwei4a.R
import kotlinx.android.synthetic.main.activity_find_psw.*

class FindPswActivity : AppCompatActivity() {

    private var mPhone: String = ""
    private var mPsw: String = ""
    private var mCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.WHITE
        setContentView(R.layout.activity_find_psw)

        initToolbar()
        initEditText()
    }

    private fun initToolbar() {
        z_toolbar_findPsw.setNavigationOnClickListener { finish() }
    }

    private fun initEditText() {
        z_edit_phone_findPsw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mPhone = input.toString()
            }
        })
        z_edit_code_findPsw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mCode = input.toString()
            }
        })
        z_edit_password_findPsw.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(input: Editable?) = Unit
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mPsw = input.toString()
            }
        })
    }
}
