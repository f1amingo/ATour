package com.sanwei.sanwei4a.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.AddressListAdapter
import com.sanwei.sanwei4a.entity.po.AccountAddress
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.account.AreaFindService
import com.sanwei.sanwei4a.util.EmptyViewBuilder
import org.jetbrains.anko.*

class AddressManageActivity : BaseActivity() {

    private var mShowType: Int = ShowType.addressManage_manage
    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBtnAdd: Button
    private val mAdapter: AddressListAdapter = AddressListAdapter(R.layout.z_item_address, ArrayList())
    private lateinit var mModel: AreaFindService
    private val mRcAddAddress = ShowType.addAddress_add
    private val mRcEditAddress = ShowType.addAddress_edit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_manage)

        mToolbar = find(R.id.z_toolbar_address_manage)
        mRecyclerView = find(R.id.z_recycler_address_manage)
        mBtnAdd = find(R.id.z_btn_add_address_manage)
        mModel = AreaFindService()

        mShowType = intent.getIntExtra("showType", ShowType.addressManage_manage)

        initToolbar()
        initRecyclerView()
        initBtnAdd()
        initShowStyle()

        fetchData()
    }

    private fun initShowStyle() {
        when (mShowType) {
            ShowType.addressManage_manage -> {
                mToolbar.title = "地址管理"
            }
            ShowType.addressManage_choose -> {
                mToolbar.title = "请选择收货地址"

            }
        }
    }

    private fun fetchData() {
        mAdapter.emptyView = EmptyViewBuilder.createEmptyForDetails(this@AddressManageActivity,
                EmptyViewBuilder.EmptyType.TYPE_LOADING,
                View.OnClickListener { })

        mModel.getAllAddress(App.account!!.accId, object : OnRequestListener<List<AccountAddress>> {
            override fun onSuccess(data: List<AccountAddress>) {
                runOnUiThread {
                    mAdapter.addData(data)
                    mAdapter.emptyView = EmptyViewBuilder.createEmptyForDetails(this@AddressManageActivity,
                            EmptyViewBuilder.EmptyType.TYPE_NO_DATA,
                            View.OnClickListener { fetchData() })
                }
            }

            override fun onFail(msg: String?, code: Int) {
                runOnUiThread {
                    toast(msg!!)
                    mAdapter.emptyView = EmptyViewBuilder.createEmptyForDetails(this@AddressManageActivity,
                            EmptyViewBuilder.EmptyType.TYPE_NETWORK_ERROR,
                            View.OnClickListener { fetchData() })
                }
            }
        })
    }

    private fun initBtnAdd() {
        mBtnAdd.setOnClickListener {
            startActivityForResult<AddAddressActivity>(mRcAddAddress, "showType" to ShowType.addAddress_add)
        }
    }

    private fun initRecyclerView() {
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        mAdapter.setOnItemChildClickListener { _, view, position ->
            val item = mAdapter.getItem(position)!!
            when (view.id) {
                R.id.z_layout_address_item -> {
                    if (mShowType == ShowType.addressManage_choose) {
                        setResult(Activity.RESULT_OK, Intent().putExtra("address", item))
                        finish()
                    }
                }
                R.id.z_edit_address_item -> {
                    startActivityForResult<AddAddressActivity>(mRcEditAddress,
                            "showType" to ShowType.addAddress_edit,
                            "address" to item)
                }
                R.id.z_delete_address_item -> {
                    alert("确认要删除该地址吗？", "") {
                        positiveButton("确定") { toast("删除地址") }
                        negativeButton("取消") {}
                    }.show()
                }
                R.id.z_check_address_item -> {
//                    val thisCheckBox = mAdapter.getViewByPosition(mRecyclerView, position, R.id.z_check_address_item) as CheckBox
//                    if (!thisCheckBox.isChecked) {
//                        //必须有一个默认地址
//                        thisCheckBox.isChecked = true
//                    } else {
//                        for (i in 0 until mAdapter.itemCount) {
//                            if (i != position) {
//                                val view = mAdapter.getViewByPosition(mRecyclerView, i, R.id.z_check_address_item)
//                                if (view != null) {
//                                    (view as CheckBox).isChecked = false
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            mRcAddAddress -> {
                if (resultCode == Activity.RESULT_OK) {
                    mAdapter.mItems.clear()
                    fetchData()
                }
            }
            mRcEditAddress -> {
                if (resultCode == Activity.RESULT_OK) {
                    mAdapter.mItems.clear()
                    fetchData()
                }
            }
        }
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener { finish() }
    }
}
