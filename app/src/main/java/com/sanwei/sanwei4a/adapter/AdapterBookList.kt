package com.sanwei.sanwei4a.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.po.BookSimpleInfo
import com.sanwei.sanwei4a.util.RequestUtil
import com.willy.ratingbar.ScaleRatingBar
import com.chad.library.adapter.base.util.MultiTypeDelegate


/**
 * Created by VincentLaf on 2018/3/31.
 *
 */
class AdapterBookList(mResId: Int, val mItems: MutableList<BookSimpleInfo>) :
        BaseQuickAdapter<BookSimpleInfo, BaseViewHolder>(mResId, mItems) {

    private val listStaggered = 0
    private val listHorizontal = 1
    private var currentShowType = listStaggered

    init {
        multiTypeDelegate = object : MultiTypeDelegate<BookSimpleInfo>() {
            override fun getItemType(t: BookSimpleInfo?): Int = currentShowType
        }
        multiTypeDelegate.registerItemType(listStaggered, R.layout.z_item_book_staggered)
                .registerItemType(listHorizontal, R.layout.z_item_book_horizontal)
    }

    fun changeListLayout(isHorizontal: Boolean) {
        currentShowType = if (isHorizontal) listStaggered else listHorizontal
    }

    override fun convert(helper: BaseViewHolder?, item: BookSimpleInfo?) {
        helper!!
        item!!
        if (helper.itemViewType == listStaggered) {
            helper.setText(R.id.z_txt_title_book_item, item.bookName)
                    .setText(R.id.z_txt_description_book_item, item.describe)
                    .setText(R.id.z_txt_price_book_item, "积分：${item.points}")
                    .setText(R.id.z_txt_username_book_item, item.nickName)
                    .addOnClickListener(R.id.z_layout_cover_book_item)
            helper.getView<ScaleRatingBar>(R.id.z_rating_book_item).rating = item.starts.toFloat()

            Glide.with(mContext)
                    .load(RequestUtil.getHeadPicAddr(item.accId, item.accPic))
                    .apply(RequestOptions().error(R.drawable.ic_cheese).placeholder(R.drawable.ic_cheese))
                    .into(helper.getView(R.id.z_img_user_book_item))

            Glide.with(mContext)
                    .load(RequestUtil.getBookPic(item.booFrontpic))
                    .apply(RequestOptions().placeholder(R.drawable.ic_book_placeholder).error(R.drawable.ic_book_placeholder))
                    .into(helper.getView(R.id.z_img_book_book_item))
        } else {
            helper.setText(R.id.z_txt_title_book_item_horizontal, item.bookName)
                    .setText(R.id.z_txt_description_book_item_horizontal, item.describe)
                    .setText(R.id.z_txt_points_book_item_horizontal, "积分：${item.points}")
                    .setText(R.id.z_txt_username_book_item_horizontal, item.nickName)
            helper.getView<ScaleRatingBar>(R.id.z_rating_book_item_horizontal).rating = item.starts.toFloat()

            Glide.with(mContext)
                    .load(RequestUtil.getHeadPicAddr(item.accId, item.accPic))
                    .apply(RequestOptions().error(R.drawable.ic_cheese).placeholder(R.drawable.ic_cheese))
                    .into(helper.getView(R.id.z_img_user_book_item_horizontal))

            Glide.with(mContext)
                    .load(RequestUtil.getBookPic(item.booFrontpic))
                    .apply(RequestOptions().placeholder(R.drawable.ic_book_placeholder).error(R.drawable.ic_book_placeholder))
                    .into(helper.getView(R.id.z_img_book_item_horizontal))
        }
    }
}

