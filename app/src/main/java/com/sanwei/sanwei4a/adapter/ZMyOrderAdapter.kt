package com.sanwei.sanwei4a.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R

class ZMyOrderAdapter(val mResId: Int, val mItems: ArrayList<ItemMyOrder>) :
        BaseQuickAdapter<ItemMyOrder, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: ItemMyOrder?) {
        item!!
        helper!!
        helper.setText(R.id.z_txt_order_item_title, item.product_name)
        helper.setText(R.id.z_txt_order_merchant, "店铺：${item.merchant_name}")
        helper.setText(R.id.z_txt_my_order_price, "￥ ${item.price}")
        helper.setText(R.id.z_txt_order_section, "套餐类型：${item.section_name}")
        var strState = item.state
        when (item.state) {
            "topay" -> {
                strState = "待支付"
            }
            "review" -> {
                strState = "待商家处理"
            }
            "pass" -> {
                strState = "待评价"
            }
            "comment" -> {
                strState = "已评价"
            }
        }
        helper.setText(R.id.z_txt_order_state, strState)
    }
}

class ItemMyOrder(
        var id: String = "",
        var product_category: String = "",
        var price: String = "",
        var comment: String = "",
        var state: String = "",
        var merchant_name: String = "",
        var user_id: String = "",
        var product_name: String = "",
        var product_id: String = "",
        var section_name: String = ""
)