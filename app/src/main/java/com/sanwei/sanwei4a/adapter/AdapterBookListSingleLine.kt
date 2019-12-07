package com.sanwei.sanwei4a.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.po.BookSimpleInfo
import com.sanwei.sanwei4a.util.RequestUtil
import com.willy.ratingbar.ScaleRatingBar

/**
 * Created by VincentLaf on 2018/4/24.
 *
 */
class AdapterBookListSingleLine(mResId: Int, val mItems: MutableList<BookSimpleInfo>) :
        BaseQuickAdapter<BookSimpleInfo, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: BookSimpleInfo?) {
        helper!!
        item!!
        helper.setText(R.id.z_txt_title_book_item_single, item.bookName)
                .setText(R.id.z_txt_author_book_item_single, item.nickName)
                .setText(R.id.z_txt_content_book_item_single, item.describe)

        Glide.with(mContext)
                .load(RequestUtil.getBookPic(item.booFrontpic))
                .into(helper.getView(R.id.z_img_book_item_single))
    }
}

