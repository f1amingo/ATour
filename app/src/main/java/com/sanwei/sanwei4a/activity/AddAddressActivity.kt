package com.sanwei.sanwei4a.activity

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.AreaListAdapter
import com.sanwei.sanwei4a.adapter.DataArea
import com.sanwei.sanwei4a.entity.po.AccountAddress
import com.sanwei.sanwei4a.entity.po.Area
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.account.AccountService
import com.sanwei.sanwei4a.model.account.AreaFindService
import com.sanwei.sanwei4a.util.LogUtil
import org.jetbrains.anko.find

class AddAddressActivity : BaseActivity() {

    private lateinit var mToolbar: Toolbar
    private lateinit var mLayoutArea: View
    private var mItemProvince: ArrayList<DataArea> = ArrayList()
    private var mItemCity: ArrayList<DataArea> = ArrayList()
    private var mItemDistrict: ArrayList<DataArea> = ArrayList()
    private var mRequestedLevel = Area.COUNTRY
    private val mAdapter: AreaListAdapter = AreaListAdapter(R.layout.z_item_area, ArrayList())

    private lateinit var mBottomView: View
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTabLayout: TabLayout
    private lateinit var mBottomDlg: BottomSheetDialog
    private lateinit var mTxtArea: TextView
    private lateinit var mEditName: EditText
    private lateinit var mEditPhone: EditText
    private lateinit var mEditAddress: EditText

    private val mArrayName = arrayOf("", "", "")
    private val mArrayCode = arrayOf(0, 0, 0)

    private val mIAreaModel = object : OnRequestListener<List<Area>> {
        override fun onSuccess(data: List<Area>) {
            when (mRequestedLevel) {
                Area.COUNTRY -> {
                    mItemProvince = ArrayList()
                    for (i in data) {
                        mItemProvince.add(DataArea(i.name, i.code))
                    }
                    runOnUiThread { mAdapter.setNewData(mItemProvince) }
                    if (mShowType == ShowType.addAddress_edit) {
                        if (mProvinceName == "")
                            for (i in mItemProvince)
                                if (i.code == mAddress.addProid) {
                                    mProvinceName = i.name
                                    mRequestedLevel = Area.PROVINCE
                                    mAreaFindModel.request(mAddress.addProid, mRequestedLevel, this)
                                    break
                                }
                    }
                }
                Area.PROVINCE -> {
                    mItemCity = ArrayList()
                    for (i in data) {
                        mItemCity.add(DataArea(i.name, i.code))
                    }
                    runOnUiThread { mAdapter.setNewData(mItemCity) }
                    if (mShowType == ShowType.addAddress_edit) {
                        if (mCityName == "")
                            for (i in mItemCity)
                                if (i.code == mAddress.addCityid) {
                                    mCityName = i.name
                                    mRequestedLevel = Area.CITY
                                    mAreaFindModel.request(mAddress.addCityid, mRequestedLevel, this)
                                    break
                                }
                    }
                }
                Area.CITY -> {
                    mItemDistrict = ArrayList()
                    for (i in data) {
                        mItemDistrict.add(DataArea(i.name, i.code))
                    }
                    runOnUiThread { mAdapter.setNewData(mItemDistrict) }
                    if (mShowType == ShowType.addAddress_edit) {
                        if (mAreaName == "") {
                            for (i in mItemDistrict)
                                if (i.code == mAddress.addAreaid) {
                                    mAreaName = i.name
                                    runOnUiThread {
                                        mTxtArea.text = StringBuffer("$mProvinceName-$mCityName-$mAreaName")
                                    }
                                    break
                                }
                            if (!mIsBottomViewInited) {
                                runOnUiThread {
                                    initBottomView()
                                }
                                mIsBottomViewInited = true
                            }
                        }
                    }
                }
            }
        }

        override fun onFail(msg: String?, code: Int) {
            try {
                msg!!
                runOnUiThread { toast(msg) }
                LogUtil.e("mAreaFindModel", msg)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private val mAreaFindModel = AreaFindService()
    private val mAccountService = AccountService()

    private var mShowType = ShowType.addAddress_add
    private lateinit var mAddress: AccountAddress
    private var mProvinceName = ""
    private var mCityName = ""
    private var mAreaName = ""
    private var mIsBottomViewInited = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        mToolbar = findViewById(R.id.z_toolbar_add_address)
        mLayoutArea = findViewById(R.id.z_layout_area_add_address)
        mTxtArea = find(R.id.z_txt_area_add_address)
        mEditName = find(R.id.z_edit_name_add_address)
        mEditPhone = find(R.id.z_edit_phone_add_address)
        mEditAddress = find(R.id.z_edit_address_add_address)

        mShowType = intent.getIntExtra("showType", ShowType.addAddress_add)
        if (mShowType == ShowType.addAddress_edit) {
            mAddress = intent.getSerializableExtra("address") as AccountAddress
            LogUtil.d(TAG, "来自上一页的address:$mAddress")
        }

        initToolbar()
        initProvince()
        if (mShowType == ShowType.addAddress_add)
            initBottomView()
        initAreaPicker()

        initShowStyle()
    }

    private fun initShowStyle() {
        when (mShowType) {
            ShowType.addAddress_add -> {
                mToolbar.title = "添加新地址"
            }
            ShowType.addAddress_edit -> {
                mToolbar.title = "编辑地址"
                mEditName.setText(mAddress.addName)
                mEditPhone.setText(mAddress.addPhone)
                mEditAddress.setText(mAddress.addDetail)
            }
        }
    }

    private fun initBottomView() {
        mBottomView = LayoutInflater.from(this).inflate(R.layout.z_bottom_address_picker, null)
        mRecyclerView = mBottomView.find(R.id.z_recycler_address_picker)
        mTabLayout = mBottomView.find(R.id.z_tab_address_picker)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        mBottomDlg = BottomSheetDialog(this)
        mBottomDlg.window.setBackgroundDrawable(ColorDrawable())
        mBottomDlg.setContentView(mBottomView)

        if (mShowType == ShowType.addAddress_add) {
            mTabLayout.addTab(mTabLayout.newTab().setText("请选择"))
        } else {
            mTabLayout.addTab(mTabLayout.newTab().setText(mProvinceName))
            mTabLayout.addTab(mTabLayout.newTab().setText(mCityName))
            mTabLayout.addTab(mTabLayout.newTab().setText(mAreaName))
            mTabLayout.getTabAt(2)!!.select()
            mAdapter.setNewData(mItemDistrict)
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            try {
                val item = mAdapter.getItem(position)!!
                while (mTabLayout.tabCount != mTabLayout.selectedTabPosition + 1) {
                    mTabLayout.removeTabAt(mTabLayout.tabCount - 1)
                }
                mTabLayout.getTabAt(mTabLayout.selectedTabPosition)!!.text = item.name
                mArrayCode[mTabLayout.selectedTabPosition] = item.code
                mArrayName[mTabLayout.selectedTabPosition] = item.name
                if (mTabLayout.tabCount == 3) {
                    val province = (mTabLayout.getTabAt(0) as TabLayout.Tab).text.toString()
                    val city = (mTabLayout.getTabAt(1) as TabLayout.Tab).text.toString()
                    val district = (mTabLayout.getTabAt(2) as TabLayout.Tab).text.toString()
                    mTxtArea.text = StringBuffer("$province-$city-$district")
                    mBottomDlg.dismiss()
                    return@setOnItemClickListener
                }
                val tab = mTabLayout.newTab()
                tab.text = "请选择"
                mTabLayout.addTab(tab)
                tab.select()
                mRequestedLevel = mTabLayout.selectedTabPosition + 1
                mAreaFindModel.request(item.code, mRequestedLevel, mIAreaModel)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (mTabLayout.selectedTabPosition) {
                    0 -> {
                        mAdapter.setNewData(mItemProvince)
                    }
                    1 -> {
                        mAdapter.setNewData(mItemCity)
                    }
                    2 -> {
                        mAdapter.setNewData(mItemDistrict)
                    }
                }
            }
        })
    }

    private fun initProvince() {
        mRequestedLevel = Area.COUNTRY
        mAreaFindModel.request(0, mRequestedLevel, mIAreaModel)
    }

    private fun initAreaPicker() {
        mLayoutArea.setOnClickListener {
            if (mShowType == ShowType.addAddress_edit)
                if (mProvinceName == "" || mCityName == "" || mAreaName == "")
                    return@setOnClickListener
            mBottomDlg.show()
        }
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener { finish() }
        mToolbar.inflateMenu(R.menu.menu_add_address)
//        val menuItem = mToolbar.menu.getItem(0).title
        mToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.z_action_add_address -> {
                    LogUtil.d("添加地址", "${mArrayCode[0]}-${mArrayCode[1]}-${mArrayCode[2]}")
                    LogUtil.d("添加地址", "${mArrayName[0]}-${mArrayName[1]}-${mArrayName[2]}")
                    if (checkInput()) {
                        showWaitingDlg()
                        val accountAddress = AccountAddress()
                        accountAddress.addName = mEditName.text.toString()
                        accountAddress.addAccid = App.account!!.accId
                        accountAddress.addPhone = mEditPhone.text.toString()
                        accountAddress.addDetail = mEditAddress.text.toString()
                        accountAddress.addProid = mArrayCode[0]
                        accountAddress.addCityid = mArrayCode[1]
                        accountAddress.addAreaid = mArrayCode[2]
                        mAccountService.addAddress(accountAddress, object : OnRequestListener<List<AccountAddress>> {
                            override fun onSuccess(address: List<AccountAddress>) {
                                runOnUiThread {
                                    this@AddAddressActivity.dismissWaitingDlg()
                                    toast("添加成功")
                                    setResult(Activity.RESULT_OK)
                                    finish()
                                }
                            }

                            override fun onFail(msg: String?, code: Int) {
                                runOnUiThread {
                                    this@AddAddressActivity.dismissWaitingDlg()
                                    toast(msg!!)
                                }
                            }
                        })
                    }
                }
            }
            return@setOnMenuItemClickListener false
        }
    }

    private fun checkInput(): Boolean {
        if (mEditName.text.isEmpty()) {
            toast("请填写联系人")
            return false
        }
        if (mEditPhone.text.length != 11) {
            toast("手机号格式错误")
            return false
        }
        if (mEditAddress.text.isEmpty()) {
            toast("请填写详细地址")
            return false
        }
        return true
    }
}
