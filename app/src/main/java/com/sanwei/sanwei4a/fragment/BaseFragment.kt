package com.sanwei.sanwei4a.fragment

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.widget.ProgressBar
import android.widget.Toast
import com.sanwei.sanwei4a.view.IBaseView

abstract class BaseFragment : Fragment(), IBaseView {

    override fun ctx(): Context = context

    val TAG: String = this.javaClass.simpleName

    override fun toast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    var mWaitingDlg: AlertDialog? = null

    override fun showWaitingDlg() {
        if (mWaitingDlg == null) {
            mWaitingDlg = AlertDialog.Builder(context!!)
                    .setView(ProgressBar(context))
                    .setCancelable(false)
                    .show()
            //设置背景透明
            mWaitingDlg?.window?.setBackgroundDrawable(ColorDrawable())
        } else {
            mWaitingDlg?.show()
        }
    }

    override fun dismissWaitingDlg() {
        mWaitingDlg?.dismiss()
    }
}