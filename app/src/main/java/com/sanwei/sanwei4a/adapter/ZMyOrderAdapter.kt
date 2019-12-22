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
        helper.setText(R.id.z_txt_my_order_price, "￥ ${item.order_price}")
        helper.setText(R.id.z_txt_order_section, "套餐类型：${item.section_name}")
    }
}




class ItemMyOrder(

        val product_name: String,
        val merchant_name: String,
        val order_price: String,
        var section_name:String
)