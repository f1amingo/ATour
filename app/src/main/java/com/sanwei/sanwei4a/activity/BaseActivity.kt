package com.sanwei.sanwei4a.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.Toast
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.view.IBaseView
import com.tapadoo.alerter.Alerter

abstract class BaseActivity : AppCompatActivity(), IBaseView {

    override fun ctx(): Context = this

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(App.intentActionSystem)
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent) {
            val title = p1.getStringExtra("title")
            val content = p1.getStringExtra("content")
            LogUtil.d("onReceive", "title: $title")
            LogUtil.d("onReceive", "content: $content")
            Alerter.create(this@BaseActivity)
                    .setTitle(title)
                    .setText(content)
                    .show()
        }
    }

    override fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    val TAG = javaClass.simpleName!!

    //等待对话框
    var mWaitingDlg: AlertDialog? = null

    //显示等待对话框
    override fun showWaitingDlg() {
        if (mWaitingDlg == null) {
            mWaitingDlg = AlertDialog.Builder(this)
                    .setView(ProgressBar(this))
                    .setCancelable(false)
                    .show()
            //设置背景透明
            mWaitingDlg?.window?.setBackgroundDrawable(ColorDrawable())
        } else {
            mWaitingDlg?.show()
        }
    }

    //显示等待对话框
    override fun dismissWaitingDlg() {
        mWaitingDlg?.dismiss()
    }
}