package com.sanwei.sanwei4a.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.netease.nimlib.sdk.msg.constant.MsgDirectionEnum
import com.sanwei.sanwei4a.R
import kotlinx.android.synthetic.main.z_item_chat_msg.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by VincentLaf on 2017/11/24.
 *
 */
class ChatListAdapter(val mResId: Int, val mItems: ArrayList<ItemChatMsg>) :
        BaseQuickAdapter<ItemChatMsg, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: ItemChatMsg?) {
        item!!
        helper!!
        val formatTime = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA).format(item.time)
        if (item.direct == MsgDirectionEnum.Out) {
            helper.setText(R.id.z_txt_chat_right, item.content)
                    .setText(R.id.z_txt_chat_time_right, formatTime)
                    .setGone(R.id.z_layout_chat_left, false)
                    .setGone(R.id.z_layout_chat_right, true)
                    .setGone(R.id.z_img_chat_left, false)
                    .setGone(R.id.z_img_chat_right, true)
                    .setGone(R.id.z_txt_chat_time_left, false)
                    .setGone(R.id.z_txt_chat_time_right, true)

        } else {
            helper.setText(R.id.z_txt_chat_left, item.content)
                    .setText(R.id.z_txt_chat_time_left, formatTime)
                    .setGone(R.id.z_layout_chat_left, true)
                    .setGone(R.id.z_layout_chat_right, false)
                    .setGone(R.id.z_img_chat_left, true)
                    .setGone(R.id.z_img_chat_right, false)
                    .setGone(R.id.z_txt_chat_time_left, true)
                    .setGone(R.id.z_txt_chat_time_right, false)
        }
    }
}

class ItemChatMsg(
        val content: String,
        val time: Long,
        val direct: MsgDirectionEnum) {
}