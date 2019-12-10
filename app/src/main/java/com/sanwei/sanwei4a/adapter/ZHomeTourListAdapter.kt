package com.sanwei.sanwei4a.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R

class ZHomeTourListAdapter(val mResId: Int, val mItems: ArrayList<ItemHomeTour>) :
        BaseQuickAdapter<ItemHomeTour, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: ItemHomeTour?) {
        item!!
        helper!!
//        helper.setText(R.id.z_img_tour_item, item.img)
        helper.setText(R.id.z_txt_tour_item_title, item.title)
        helper.setText(R.id.z_txt_tour_item_merchant, item.merchant)
        helper.setText(R.id.z_txt_tour_item_sale, item.sale)
    }
}

class ItemHomeTour(
        val img: String,
        val title: String,
        val merchant: String,
        val sale: String
)