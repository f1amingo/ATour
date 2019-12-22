package com.sanwei.sanwei4a.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.bumptech.glide.Glide
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.Constant.ORDER_STAT_UNPAY
import com.sanwei.sanwei4a.entity.Constant.PAYTYPE_POINTS
import com.sanwei.sanwei4a.entity.po.AccountAddress
import com.sanwei.sanwei4a.entity.po.BookDetailInfo
import com.sanwei.sanwei4a.entity.po.Order
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.account.AccountService
import com.sanwei.sanwei4a.model.account.AreaFindService
import com.sanwei.sanwei4a.model.order.OrderService
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import kotlinx.android.synthetic.main.activity_borrow.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import java.util.*

class BorrowActivity : BaseActivity() {

    private lateinit var mModelAddress: AreaFindService
    private lateinit var mModelBorrow: OrderService

    private lateinit var mToolbar: Toolbar
    private lateinit var mBookDetails: BookDetailInfo

    private val mRcAddressChoose = ShowType.addressManage_choose

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_borrow)

        mToolbar = find(R.id.z_toolbar_borrow)
        z_layout_address_borrow.visibility = View.GONE

        mBookDetails = intent.getSerializableExtra("bookDetails") as BookDetailInfo

        mModelAddress = AreaFindService()
        mModelBorrow = OrderService()

        initToolbar()
        initLayoutAddress()

        loadAddress()
        fillTheBlanks()

        z_txt_btn_borrow.setOnClickListener {
            showWaitingDlg()
            val order = Order()
            order.orderFromAid = App.account!!.accId
            order.orderToAid = mBookDetails.accId
            order.orderBooId = mBookDetails.booId
            order.orderTime = Date()
            order.orderExtra = z_edit_words_borrow.text.toString()
            order.orderPaytype = PAYTYPE_POINTS
            order.orderStat = ORDER_STAT_UNPAY

            mModelBorrow.createOrder(order, object : OnRequestListener<Order> {
                override fun onSuccess(data: Order) {
                    runOnUiThread {
                        mModelBorrow.pay(data, object : OnRequestListener<String?> {
                            override fun onSuccess(data: String?) {
                                runOnUiThread {
                                    toast("借书成功")
                                    dismissWaitingDlg()
                                    AccountService().getBalance(object : OnRequestListener<Int> {
                                        override fun onSuccess(data: Int) {
                                            App.account!!.accBanlance = data
                                        }

                                        override fun onFail(msg: String?, code: Int) {
                                            LogUtil.e("getBalance", "获取余额失败 msg：$msg")
                                        }
                                    })
                                    setResult(Activity.RESULT_OK)
                                    finish()
                                }
                            }

                            override fun onFail(msg: String?, code: Int) {
                                runOnUiThread {
                                    try {
                                        toast(msg!!)
                                        dismissWaitingDlg()
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        })
                    }
                }

                override fun onFail(msg: String?, code: Int) {
                    LogUtil.e(TAG, msg!!)
                    runOnUiThread {
                        toast(msg)
                        dismissWaitingDlg()
                    }
                }
            })
        }
        z_layout_delivery_borrow.setOnClickListener { toast("暂不支持其他方式") }
    }

    private fun fillTheBlanks() {
        Glide.with(this).load(RequestUtil.getBookPic(mBookDetails.booFrontpic)).into(z_img_book_borrow)
        z_txt_book_tittle_borrow.text = mBookDetails.bookName
        z_txt_author_borrow.text = mBookDetails.bookAuthor
        z_txt_username_borrow.text = mBookDetails.accNickname
        z_txt_content_borrow.text = mBookDetails.booDes
        z_txt_btn_borrow.text = StringBuffer("需要积分(${mBookDetails.points})")
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
        z_layout_address_borrow.visibility = View.VISIBLE
        if (address == null) {
            z_icon_address.visibility = View.GONE
            z_layout_address_first.visibility = View.GONE
            z_txt_address_borrow.visibility = View.GONE
            z_txt_address_manage_borrow.visibility = View.VISIBLE
        } else {
            z_txt_name_borrow.text = StringBuffer("收货人：${address.addName}")
            z_txt_phone_borrow.text = address.addPhone
            z_txt_address_borrow.text = StringBuffer("收获地址：${address.addDetail}")
            z_txt_address_manage_borrow.visibility = View.GONE
        }
    }

    private fun initLayoutAddress() {
        z_layout_address_borrow.setOnClickListener {
            startActivityForResult<AddressManageActivity>(mRcAddressChoose, "showType" to ShowType.addressManage_choose)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            mRcAddressChoose -> {
                if (resultCode == Activity.RESULT_OK) {
                    val address = data!!.getSerializableExtra("address") as AccountAddress
                    fillAddress(address)
                }
            }
        }
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener { finish() }
    }
}
