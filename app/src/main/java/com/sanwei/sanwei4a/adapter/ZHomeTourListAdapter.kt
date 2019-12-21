package com.sanwei.sanwei4a.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.util.RequestUtil

class ZHomeTourListAdapter(val mResId: Int, val mItems: ArrayList<ItemHomeTour>) :
        BaseQuickAdapter<ItemHomeTour, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: ItemHomeTour?) {
        item!!
        helper!!
        Glide.with(mContext)
                .load(item.img)
                .apply(RequestOptions().error(R.drawable.test).placeholder(R.drawable.test))
                .into(helper.getView(R.id.z_img_tour_item))
        helper.setText(R.id.z_txt_tour_item_title, item.title)
        helper.setText(R.id.z_txt_tour_item_merchant, "店铺：${item.merchant}")
        helper.setText(R.id.z_txt_tour_item_sale, "￥ ${item.price}")
    }
}

class ItemHomeTour(
        val id: String,
        val img: String,
        val title: String,
        val merchant: String,
        val price: String
)