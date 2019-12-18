package com.sanwei.sanwei4a.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
import kotlinx.android.synthetic.main.z_item_notification.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by VincentLaf on 2018/3/31.
 *
 */
class NotificationListAdapter(private val mResId: Int, val mItems: MutableList<ItemNotification>) :
        BaseQuickAdapter<ItemNotification, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: ItemNotification?) {
        helper!!
        item!!
        helper.setText(R.id.z_txt_nick_session, item.fromNick)
        helper.setText(R.id.z_txt_time_session, SimpleDateFormat("MM-dd hh:mm:ss", Locale.CHINA).format(item.time))
        helper.setText(R.id.z_txt_content_session, item.content)
    }
}

class ItemNotification(
        val imgUrl: String = "",
        val contactId: String = "",
        val content: String = "",
        val fromAccount: String = "",
        val fromNick: String = "",
        val recentMessageId: String = "",
        val time: Long = 0,
        val unreadCount: Int = 0)


