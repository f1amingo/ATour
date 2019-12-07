package com.sanwei.sanwei4a.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.parameter.account.User
import com.sanwei.sanwei4a.presenter.PresenterMineDetails
import com.sanwei.sanwei4a.util.EmptyViewBuilder
import com.sanwei.sanwei4a.util.FileHelper
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import com.sanwei.sanwei4a.view.IViewMineDetails
import kotlinx.android.synthetic.main.activity_mine_details.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*

class MineDetailsActivity : BaseActivity(), IViewMineDetails {

    override fun updateUserInfo(newVal: String, which: PresenterMineDetails.ModifiedItem) {
        when (which) {
            PresenterMineDetails.ModifiedItem.NICKNAME -> {
                z_txt_nick_mine_details.text = newVal
                mIsImgOrNickChanged = true
                App.account!!.accNickname = newVal
                FileHelper.saveAccountInfo(App.account!!)
            }
            PresenterMineDetails.ModifiedItem.GENDER -> z_txt_gender_mine_details.text = newVal
            PresenterMineDetails.ModifiedItem.BIRTH -> {
                val formattedTime = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(newVal.toLong())
                z_txt_birth_mine_details.text = formattedTime
            }
            PresenterMineDetails.ModifiedItem.REAL_NAME -> z_txt_name_mine_details.text = newVal
            PresenterMineDetails.ModifiedItem.STUDENT_ID -> z_txt_studentId_mine_details.text = newVal
            PresenterMineDetails.ModifiedItem.SCHOOL -> z_txt_school_mine_details.text = newVal
            PresenterMineDetails.ModifiedItem.COLLEGE -> z_txt_college_mine_details.text = newVal
        }
    }

    override fun showLoadingFirstTime() {
        mEmptyView.visibility = View.VISIBLE
        mEmptyView.removeAllViews()
        mEmptyView.addView(EmptyViewBuilder.createEmptyForDetails(this,
                EmptyViewBuilder.EmptyType.TYPE_LOADING,
                View.OnClickListener {
                    mPresenter.request()
                }))
        z_btn_logoff_mine_details.visibility = View.GONE
    }

    override fun loadSuccess(user: User) {
        mEmptyView.visibility = View.GONE
        z_btn_logoff_mine_details.visibility = View.VISIBLE

        z_txt_nick_mine_details.text = App.account!!.accNickname.toString()
        if (user.getuSex() != null)
            z_txt_gender_mine_details.text = user.getuSex().toString()
        if (user.getuIdnum() != null)
        {
            val formattedTime = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(user.getuIdnum().toLong())
            z_txt_birth_mine_details.text = formattedTime
        }
        if (user.getuName() != null)
            z_txt_name_mine_details.text = user.getuName().toString()
        if (user.getuSid() != null)
            z_txt_studentId_mine_details.text = user.getuSid().toString()
        if (user.getuSchool() != null)
            z_txt_school_mine_details.text = user.getuSchool().toString()
        if (user.getuCollege() != null)
            z_txt_college_mine_details.text = user.getuCollege().toString()
    }

    override fun loadFail() {
        mEmptyView.visibility = View.VISIBLE
        mEmptyView.removeAllViews()
        mEmptyView.addView(EmptyViewBuilder.createEmptyForDetails(this,
                EmptyViewBuilder.EmptyType.TYPE_NETWORK_ERROR,
                View.OnClickListener {
                    mPresenter.request()
                }))
    }

    private val mRcPhoto = 37
    private var mIsImgOrNickChanged: Boolean = false
    private lateinit var mEmptyView: ViewGroup
    private lateinit var mPresenter: PresenterMineDetails
    private var mCurrentDlg: DialogInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_details)

        mEmptyView = find(R.id.z_empty_mine_details)
        mPresenter = PresenterMineDetails()
        mPresenter.attachView(this)

        initHeadImg()
        initToolbar()
        initNickname()
        initGender()
        initBirth()
        initRealName()
        initStudentId()
        initSchool()
        initCollege()
        initBtnLogoff()

        mPresenter.request()
    }

    private fun initBtnLogoff() {
        z_btn_logoff_mine_details.setOnClickListener {
            alert("确定退出登录？", "") {
                positiveButton("确定") {
                    App.account = null
                    FileHelper.clearAccountInfo()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                negativeButton("取消") {}
            }.show()
        }
    }

    private fun initNickname() {
        z_cover_nick_mine_details.setOnClickListener {
            mCurrentDlg = alert {
                title = "修改昵称"
                customView {
                    verticalLayout {
                        val input = editText {
                            setText(z_txt_nick_mine_details.text)
                        }
                        button("确定") {
                            onClick {
                                try {
                                    mPresenter.modify(input.text.toString(), PresenterMineDetails.ModifiedItem.NICKNAME)
                                    mCurrentDlg?.dismiss()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }.padding = dip(16)
                }
            }.show()
        }
    }

    private fun initGender() {
        z_cover_gender_mine_details.setOnClickListener {
            val genders = listOf("男", "女")
            selector("修改性别", genders, { _, i ->
                mPresenter.modify(genders[i], PresenterMineDetails.ModifiedItem.GENDER)
            })
        }
    }

    private fun initBirth() {
        z_cover_birth_mine_details.setOnClickListener {
            val datePicker = object : DatePickerDialog(this,
                    OnDateSetListener { _, year, month, date ->
                        val calendar = Calendar.getInstance()
                        calendar.set(year, month, date)
                        val formattedTime = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(calendar.time)
                        LogUtil.d("选择的生日", formattedTime)
                        mPresenter.modify(calendar.timeInMillis.toString(), PresenterMineDetails.ModifiedItem.BIRTH)
                    },
                    1995,
                    0,
                    1) {}
            datePicker.setCancelable(true)
            datePicker.show()
        }
    }

    private fun initRealName() {
        z_cover_name_mine_details.setOnClickListener {
            mCurrentDlg = alert {
                title = "真实姓名"
                customView {
                    verticalLayout {
                        val input = editText {
                            setText(z_txt_name_mine_details.text)
                        }
                        button("确定") {
                            onClick {
                                try {
                                    mPresenter.modify(input.text.toString(), PresenterMineDetails.ModifiedItem.REAL_NAME)
                                    mCurrentDlg?.dismiss()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }.padding = dip(16)
                }
            }.show()
        }
    }

    private fun initStudentId() {
        z_cover_studentId_mine_details.setOnClickListener {
            mCurrentDlg = alert {
                title = "修改学号"
                customView {
                    verticalLayout {
                        val input = editText {
                            setText(z_txt_studentId_mine_details.text)
                        }
                        button("确定") {
                            onClick {
                                try {
                                    mPresenter.modify(input.text.toString(), PresenterMineDetails.ModifiedItem.STUDENT_ID)
                                    mCurrentDlg?.dismiss()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }.padding = dip(16)
                }
            }.show()
        }
    }

    private fun initSchool() {
        z_cover_school_mine_details.setOnClickListener {
            mCurrentDlg = alert {
                title = "修改学校"
                customView {
                    verticalLayout {
                        val input = editText {
                            setText(z_txt_school_mine_details.text)
                        }
                        button("确定") {
                            onClick {
                                try {
                                    mPresenter.modify(input.text.toString(), PresenterMineDetails.ModifiedItem.SCHOOL)
                                    mCurrentDlg?.dismiss()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }.padding = dip(16)
                }
            }.show()
        }
    }

    private fun initCollege() {
        z_cover_college_mine_details.setOnClickListener {
            mCurrentDlg = alert {
                title = "修改学院"
                customView {
                    verticalLayout {
                        val input = editText {
                            setText(z_txt_college_mine_details.text)
                        }
                        button("确定") {
                            onClick {
                                try {
                                    mPresenter.modify(input.text.toString(), PresenterMineDetails.ModifiedItem.COLLEGE)
                                    mCurrentDlg?.dismiss()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }.padding = dip(16)
                }
            }.show()
        }
    }

    private fun loadUserHeadImg() {
        Glide.with(this)
                .load(App.getImgUrl())
                .apply(RequestOptions().error(R.drawable.ic_cheese).placeholder(R.drawable.ic_cheese))
                .into(z_img_head_mine_details)
    }

    private fun initHeadImg() {
        z_cover_header_mine_details.setOnClickListener {
            startActivityForResult<PhotoActivity>(mRcPhoto)
        }
        loadUserHeadImg()
    }

    private fun initToolbar() {
        z_toolbar_mine_details.setNavigationOnClickListener {
            if (mIsImgOrNickChanged)
                setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            mRcPhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    loadUserHeadImg()
                    mIsImgOrNickChanged = true
                }
            }
        }
    }

    override fun onBackPressed() {
        if (mIsImgOrNickChanged) setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }
}
