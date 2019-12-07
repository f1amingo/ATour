package com.sanwei.sanwei4a.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
import kotlinx.android.synthetic.main.z_item_chat_msg.view.*


/**
 * Created by VincentLaf on 2017/11/24.
 *
 */
class ChatListAdapter(val mResId: Int, val mItems: ArrayList<ItemChatMsg>) :
        BaseQuickAdapter<ItemChatMsg, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: ItemChatMsg?) {
        item!!
        helper!!
        if (item.showType == ItemChatMsg.TYPE_SENT) {
            helper.setText(R.id.z_txt_chat_right, item.content)
                    .setText(R.id.z_txt_chat_time_right, item.time.toString())
                    .setGone(R.id.z_layout_chat_left, false)
                    .setGone(R.id.z_layout_chat_right, true)
                    .setGone(R.id.z_img_chat_left,false)
                    .setGone(R.id.z_img_chat_right,true)
                    .setGone(R.id.z_txt_chat_time_left,false)
                    .setGone(R.id.z_txt_chat_time_right,true)

        } else {
            helper.setText(R.id.z_txt_chat_left, item.content)
                    .setText(R.id.z_txt_chat_time_left, "Kian, ${item.time}")
                    .setGone(R.id.z_layout_chat_left, true)
                    .setGone(R.id.z_layout_chat_right, false)
                    .setGone(R.id.z_img_chat_left,true)
                    .setGone(R.id.z_img_chat_right,false)
                    .setGone(R.id.z_txt_chat_time_left,true)
                    .setGone(R.id.z_txt_chat_time_right,false)
        }
    }
}

class ItemChatMsg(
        val content: String,
        val time: Long,
        val showType: Int) {

    companion object {
        const val TYPE_RECEIVED = 0
        const val TYPE_SENT = 1
    }
}