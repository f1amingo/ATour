package com.sanwei.sanwei4a.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.facebook.drawee.backends.pipeline.Fresco
import com.jph.takephoto.app.TakePhotoActivity
import com.jph.takephoto.model.TResult
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.po.AccountAddress
import com.sanwei.sanwei4a.entity.po.BusinessBook
import com.sanwei.sanwei4a.entity.po.SysBook
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.account.AreaFindService
import com.sanwei.sanwei4a.presenter.PresenterUploadBook
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.view.IViewUploadBook
import com.stfalcon.frescoimageviewer.ImageViewer
import com.tapadoo.alerter.Alerter
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_upload_book.*
import org.angmarch.views.NiceSpinner
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import top.zibin.luban.Luban
import java.io.File

class UploadBookActivity : TakePhotoActivity(), IViewUploadBook {

    override fun ctx(): Context = this

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            Alerter.create(this@UploadBookActivity)
                    .setTitle(intent.getStringExtra("title"))
                    .setText(intent.getStringExtra("content"))
                    .show()
        }
    }

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

    override fun dismissWaitingDlg() {
        mWaitingDlg?.dismiss()
    }

    private var mWaitingDlg: AlertDialog? = null

    private val mRcAddressChoose = ShowType.addressManage_choose

    override fun loadingBookInfo() {
        find<View>(R.id.z_layout_book_info_add_book).visibility = View.GONE
        find<View>(R.id.z_progress_add_book).visibility = View.VISIBLE
        find<View>(R.id.z_btn_error_loading_book_info).visibility = View.GONE
        find<TextView>(R.id.z_txt_loading_info_add_book).text = "图书信息加载中..."
        find<TextView>(R.id.z_txt_loading_info_add_book).visibility = View.VISIBLE
    }

    override fun readyToLoadBookInfo() {
        find<View>(R.id.z_layout_book_info_add_book).visibility = View.GONE
        find<View>(R.id.z_progress_add_book).visibility = View.GONE
        find<View>(R.id.z_btn_error_loading_book_info).visibility = View.GONE
        find<TextView>(R.id.z_txt_loading_info_add_book).text = StringBuffer("扫描图书ISBN自动填写")
        find<TextView>(R.id.z_txt_loading_info_add_book).visibility = View.VISIBLE
    }

    override fun loadBookInfoError(msg: String) {
        find<View>(R.id.z_layout_book_info_add_book).visibility = View.GONE
        find<View>(R.id.z_progress_add_book).visibility = View.GONE
        find<View>(R.id.z_btn_error_loading_book_info).visibility = View.VISIBLE
        find<TextView>(R.id.z_txt_loading_info_add_book).text = msg
        find<View>(R.id.z_btn_error_loading_book_info).setOnClickListener {
            mPresenter.getBookInfo(mEditISBN.text.toString())
        }
        find<TextView>(R.id.z_txt_loading_info_add_book).visibility = View.VISIBLE
    }

    override fun noSuchBook() {
        find<View>(R.id.z_layout_book_info_add_book).visibility = View.GONE
        find<View>(R.id.z_progress_add_book).visibility = View.GONE
        find<View>(R.id.z_btn_error_loading_book_info).visibility = View.GONE
        find<TextView>(R.id.z_txt_loading_info_add_book).visibility = View.VISIBLE
        find<TextView>(R.id.z_txt_loading_info_add_book).text = "没有查到该图书信息，请联系客服"
    }

    override fun fillTheBookInfo(book: SysBook) {
        find<View>(R.id.z_layout_book_info_add_book).visibility = View.VISIBLE
        find<View>(R.id.z_progress_add_book).visibility = View.GONE
        find<View>(R.id.z_btn_error_loading_book_info).visibility = View.GONE
        find<TextView>(R.id.z_txt_loading_info_add_book).visibility = View.GONE

        find<TextView>(R.id.z_txt_title_add_book).text = book.bookName
        find<TextView>(R.id.z_txt_page_add_book).text = book.bookPage.toString()
        find<TextView>(R.id.z_txt_binding_add_book).text = book.booBinding
        find<TextView>(R.id.z_txt_publisher_add_book).text = book.bookPublisher
        find<TextView>(R.id.z_txt_details_add_book).text = book.bookDetail

        mBookId = book.bookId
    }

    private val TAG = this.javaClass.simpleName!!
    private lateinit var mBtnScan: View
    private val mCodeBarScan = 27
    private lateinit var mEditISBN: EditText
    private lateinit var mSpinner: NiceSpinner
    private lateinit var mImgPic1: ImageView
    private lateinit var mImgPic2: ImageView
    private lateinit var mImgPic3: ImageView
    private lateinit var mPresenter: PresenterUploadBook
    private lateinit var mBtnUpload: Button
    private lateinit var mToolbar: Toolbar
    private lateinit var mEditPoints: EditText
    private lateinit var mEditDescription: EditText
    private lateinit var mTxtAddress: TextView
    private var mBookId: Int = -1
    private var mAddress: AccountAddress? = null

    private lateinit var mModelAddress: AreaFindService

    //点击第几个图片0、1、2
    private var mSelectedIndex = 0
    private val mCompressFiles: Array<File?> = arrayOf(null, null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_book)

        mToolbar = find(R.id.z_toolbar_upload_book)
        mBtnScan = find(R.id.z_img_scan_add_book)
        mEditISBN = find(R.id.z_edit_isbn_add_book)
        mSpinner = find(R.id.z_spinner_add_book)
        mImgPic1 = find(R.id.z_img_book_pic1_add_book)
        mImgPic2 = find(R.id.z_img_book_pic2_add_book)
        mImgPic3 = find(R.id.z_img_book_pic3_add_book)
        mBtnUpload = find(R.id.z_btn_upload_book)
        mEditPoints = find(R.id.z_edit_points_add_book)
        mEditDescription = find(R.id.z_edit_description_upload_book)
        mTxtAddress = find(R.id.z_txt_address_add_book)

        mModelAddress = AreaFindService()

        mPresenter = PresenterUploadBook()
        mPresenter.attachView(this)
        Fresco.initialize(this)

        initBtnScan()

        mSpinner.attachDataSource(arrayListOf("全新", "九成新", "八成新", "七成新", "六成新", "五成新"))

        mImgPic1.setOnClickListener {
            mSelectedIndex = 0
            showBottomDlg()
        }

        mImgPic2.setOnClickListener {
            mSelectedIndex = 1
            showBottomDlg()
        }

        mImgPic3.setOnClickListener {
            mSelectedIndex = 2
            showBottomDlg()
        }

        initToolbar()
        initBtnUpload()
        initAddress()
        readyToLoadBookInfo()
        initCoverBookInfo()

        loadAddress()
    }

    private fun initCoverBookInfo() {
        z_cover_book_info_upload.setOnClickListener {
            if (!z_txt_details_add_book.text.isEmpty()) {
                if (z_txt_details_add_book.maxLines == 5) {
                    z_txt_details_add_book.maxLines = 100
                } else {
                    z_txt_details_add_book.maxLines = 5
                }
            }
        }
    }

    private fun initAddress() {
        z_cover_address_add_book.setOnClickListener {
            startActivityForResult<AddressManageActivity>(mRcAddressChoose, "showType" to ShowType.addressManage_choose)
        }
    }

    private fun loadAddress() {
        mModelAddress.getAllAddress(App.account!!.accId, object : OnRequestListener<List<AccountAddress>> {
            override fun onSuccess(data: List<AccountAddress>) {
                runOnUiThread {
                    if (data.isEmpty())
                        fillAddress(null)
                    else
                        fillAddress(data[0])
                }
            }

            override fun onFail(msg: String?, code: Int) {
                runOnUiThread {
                    toast(msg!!)
                }
            }
        })
    }

    private fun fillAddress(address: AccountAddress?) {
        mAddress = address
        if (address == null) {
            mTxtAddress.text = "地址管理"
        } else {
            mTxtAddress.text = address.addSummarize
        }
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener { finish() }
    }

    private fun initBtnUpload() {
        mBtnUpload.setOnClickListener {
            if (!checkInput()) return@setOnClickListener
            val book = BusinessBook()
            book.booAccId = App.account?.accId
            book.booIsbn = mEditISBN.text.toString()
            book.booBookId = mBookId
            book.booCon = mSpinner.text.toString()
            book.booPoints = mEditPoints.text.toString().toInt()
            book.booDes = mEditDescription.text.toString()
            book.booAddrId = mAddress!!.addId
            book.booPrice = 0.0
            mPresenter.uploadBook(book, mCompressFiles[0]!!, mCompressFiles[1]!!, mCompressFiles[2]!!)
        }
    }

    private fun checkInput(): Boolean {
        if (mEditISBN.text.length < 5) {
            toast("请扫描ISBN")
            return false
        }

        if (mAddress == null) {
            toast("请选择取书地点")
            return false
        }

        if (mEditPoints.text.isEmpty()) {
            toast("请输入押金")
            return false
        }

        if (mEditDescription.text.isEmpty()) {
            toast("请输入书籍描述")
            return false
        }

        if (mCompressFiles[0] == null || mCompressFiles[1] == null || mCompressFiles[2] == null) {
            toast("请上传三张图上照片")
            return false
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    @SuppressLint("InflateParams")
    private fun showBottomDlg() {
        val dialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.z_bottom_dialog_picture, null)
        view.find<View>(R.id.z_btn_dlg_camera).setOnClickListener {
            takePhoto.onPickFromCapture(Uri.fromFile(getImgFile()))
            dialog.dismiss()
        }
        view.find<View>(R.id.z_btn_dlg_gallery).setOnClickListener {
            takePhoto.onPickFromGallery()
            dialog.dismiss()
        }
        view.find<View>(R.id.z_btn_dlg_look).setOnClickListener {
            if (mCompressFiles[mSelectedIndex] == null) return@setOnClickListener
            val list = ArrayList<Uri>()
            for (i in mCompressFiles) {
                if (i != null) list.add(Uri.fromFile(i))
            }
            ImageViewer.Builder(this, list)
                    .setStartPosition(mSelectedIndex)
                    .show()
            dialog.dismiss()
        }
        dialog.window.setBackgroundDrawable(ColorDrawable())
        dialog.setContentView(view)
        dialog.show()
    }

    override fun takeSuccess(result: TResult) {
        super.takeSuccess(result)
        Log.i(TAG, "we are at takeSuccess：")
        Log.i(TAG, "takeSuccess：" + result.image.originalPath)
        try {
            LogUtil.d(TAG, "源路径：${result.image.originalPath}")
            LogUtil.d(TAG, "源文件大小：${File(result.image.originalPath).length()}")
            Luban.with(this)
                    .load(result.image.originalPath)// 传入要压缩的图片列表
                    .ignoreBy(100)// 忽略不压缩图片的大小
                    .setTargetDir(externalCacheDir.path)// 设置压缩后文件存储位置
                    .setCompressListener(object : top.zibin.luban.OnCompressListener {
                        override fun onSuccess(file: File?) {
                            LogUtil.d(TAG, "压缩成功")
                            LogUtil.d(TAG, "压缩路径：${file!!.path}")
                            LogUtil.d(TAG, "压缩后大小：${file.length()}")
                            mCompressFiles[mSelectedIndex] = file
                            val imgView = when (mSelectedIndex) {
                                0 -> mImgPic1
                                1 -> mImgPic2
                                else -> mImgPic3
                            }
                            Glide.with(this@UploadBookActivity).load(file).into(imgView)
                        }

                        override fun onError(e: Throwable) {
                            LogUtil.d("PhotoActivity", "压缩失败")
                            e.printStackTrace()
                        }

                        override fun onStart() = Unit
                    }).launch()    //启动压缩
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
    }

    private fun initBtnScan() {
        mBtnScan.setOnClickListener {
            AndPermission.with(this)
                    .permission(Permission.CAMERA)
                    .onGranted {
                        if (it[0] == Permission.CAMERA) {
                            startActivityForResult(Intent(this@UploadBookActivity, BarScanActivity::class.java), mCodeBarScan)
                        }
                    }.onDenied {
                        toast("授权失败，无法打开相机")
                    }
                    .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            mCodeBarScan -> {
                if (resultCode == Activity.RESULT_OK) {
                    val isbn = data!!.getStringExtra("isbn")
                    LogUtil.d(TAG, "ISBN $isbn")
                    mEditISBN.setText(isbn)
                    mPresenter.getBookInfo(isbn)
                }
            }
            mRcAddressChoose -> {
                if (resultCode == Activity.RESULT_OK) {
                    fillAddress(data!!.getSerializableExtra("address") as AccountAddress)
                    LogUtil.d(TAG, mAddress.toString())
                }
            }
        }
    }
}
