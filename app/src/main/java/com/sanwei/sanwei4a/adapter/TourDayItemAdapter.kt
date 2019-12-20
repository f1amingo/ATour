package com.sanwei.sanwei4a.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R

/**
 * Created by VincentLaf on 2019/12/20.
 */
class TourDayItemAdapter(private val mResId: Int, val mItems: ArrayList<TourDayItem>) :
        BaseQuickAdapter<TourDayItem, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: TourDayItem?) {
        item!!
        helper!!
        Glide.with(mContext)
                .load(item.img)
                .apply(RequestOptions().error(R.drawable.test).placeholder(R.drawable.test))
                .into(helper.getView(R.id.z_img_tour_day_item))
        helper.setText(R.id.z_txt_tour_day_item_activity, "Day ${item.index + 1} ${item.activity}")
        helper.setText(R.id.z_txt_tour_day_item_description, item.description)
    }
}

class TourDayItem(
        val index: Int,
        val activity: String,
        val description: String,
        val img: String
)