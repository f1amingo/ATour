package com.sanwei.sanwei4a.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R

/**
 * Created by VincentLaf on 2019/12/20.
 */
class ImgListAdapter(private val mResId: Int, val mItems: ArrayList<ItemImg>) :
        BaseQuickAdapter<ItemImg, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: ItemImg?) {
        item!!
        helper!!
        Glide.with(mContext)
                .load(item.src)
                .apply(RequestOptions().error(R.drawable.test).placeholder(R.drawable.test))
                .into(helper.getView(R.id.z_img_tour_img_list_item))
    }
}

class ItemImg(val src: String)