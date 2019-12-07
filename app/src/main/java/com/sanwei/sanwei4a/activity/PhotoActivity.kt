package com.sanwei.sanwei4a.activity

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.facebook.drawee.backends.pipeline.Fresco
import com.jph.takephoto.app.TakePhotoActivity
import com.jph.takephoto.model.TResult
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.po.Account
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.account.AccountService
import com.sanwei.sanwei4a.util.FileHelper
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import com.stfalcon.frescoimageviewer.ImageViewer
import kotlinx.android.synthetic.main.activity_mine_details.*
import kotlinx.android.synthetic.main.activity_photo.*
import org.jetbrains.anko.toast
import top.zibin.luban.Luban
import java.io.File

class PhotoActivity : TakePhotoActivity() {

    var mWaitingDlg: AlertDialog? = null

    private val TAG = "PhotoActivity"

    private var mCompressedFile: File? = null

    private lateinit var mModel: AccountService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        mModel = AccountService()

        Fresco.initialize(this)

        initToolbar()
        initHeadImg()
        initBtn()
    }

    private fun initBtn() {
        z_btn_take_photo.setOnClickListener {
            takePhoto.onPickFromCapture(Uri.fromFile(getImgFile()))
        }

        z_btn_choose_photo.setOnClickListener {
            takePhoto.onPickFromGallery()
        }

        z_btn_confirm_photo.setOnClickListener {
            if (mCompressedFile == null) {
                toast("未做任何修改")
                return@setOnClickListener
            }
            showWaitingDlg()
            try {
                mModel.replaceHeader(mCompressedFile!!, App.account!!.accId, object : OnRequestListener<Account> {
                    override fun onSuccess(data: Account) {
                        runOnUiThread {
                            toast("操作成功")
                            App.account!!.accPic = data.accPic
                            FileHelper.saveAccountInfo(App.account!!)
                            dismissWaitingDlg()
                            LogUtil.e(TAG,data.toString())
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    }

                    override fun onFail(msg: String?, code: Int) {
                        runOnUiThread {
                            toast("操作失败")
                            dismissWaitingDlg()
                        }
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initHeadImg() {
        val headImgUrl = App.getImgUrl()
        Glide.with(this)
                .load(headImgUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_cheese).error(R.drawable.ic_cheese))
                .into(z_img_head_photo)
        z_img_head_photo.setOnClickListener {
            if (mCompressedFile == null) {
                ImageViewer.Builder(this, listOf(headImgUrl))
                        .setStartPosition(0)
                        .show()
            } else {
                ImageViewer.Builder(this, listOf(Uri.fromFile(mCompressedFile)))
                        .setStartPosition(0)
                        .show()
            }
        }
    }

    private fun initToolbar() {
        z_toolbar_photo.setNavigationOnClickListener { finish() }
    }

    private fun getImgFile(): File {
        val outputImg =
                File(externalCacheDir, "${System.currentTimeMillis()}.jpg")
        try {
            if (outputImg.exists()) {
                outputImg.delete()
            }
            outputImg.createNewFile()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return outputImg
    }//getImageUri

    override fun takeSuccess(result: TResult) {
        try {
            LogUtil.d(TAG, "源路径：${result.image.originalPath}")
            LogUtil.d(TAG, "源文件大小：${File(result.image.originalPath).length()}")
            Luban.with(this)
                    .load(result.image.originalPath)                      // 传人要压缩的图片列表
                    .ignoreBy(100)                                  // 忽略不压缩图片的大小
                    .setTargetDir(externalCacheDir.path)     // 设置压缩后文件存储位置
                    .setCompressListener(object : top.zibin.luban.OnCompressListener {
                        override fun onSuccess(file: File?) {
                            LogUtil.d(TAG, "压缩成功")
                            LogUtil.d(TAG, "压缩路径：${file!!.path}")
                            LogUtil.d(TAG, "压缩后大小：${file.length()}")
                            mCompressedFile = file
                            Glide.with(this@PhotoActivity).load(file).into(z_img_head_photo)
                        }

                        override fun onError(e: Throwable?) {
                            LogUtil.d("PhotoActivity", "压缩失败")
                            e?.printStackTrace()
                        }

                        override fun onStart() = Unit
                    }).launch()    //启动压缩
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //显示等待对话框
    private fun showWaitingDlg() {
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
    private fun dismissWaitingDlg() {
        mWaitingDlg?.dismiss()
    }
}
