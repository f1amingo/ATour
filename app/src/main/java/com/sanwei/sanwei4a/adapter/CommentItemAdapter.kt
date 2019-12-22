package com.sanwei.sanwei4a.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by VincentLaf on 2019/12/20.
 */
class CommentItemAdapter(private val mResId: Int, val mItems: ArrayList<CommentItem>) :
        BaseQuickAdapter<CommentItem, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: CommentItem?) {
        item!!
        helper!!
        Glide.with(mContext)
                .load(item.avatar)
                .apply(RequestOptions().error(R.drawable.test).placeholder(R.drawable.test))
                .into(helper.getView(R.id.img_tour_comment))
        helper.setText(R.id.txt_tour_comment_name, item.name)
        helper.setText(R.id.txt_tour_comment_time, SimpleDateFormat("MM-dd hh:mm:ss", Locale.CHINA).format(item.date.toLong()))
        helper.setText(R.id.txt_tour_comment_content, item.content)
    }
}

class CommentItem(
        val id: String,
        val stars: String,
        val content: String,
        val avatar: String,
        val name: String,
        val date: String,
        val tourid: String
)