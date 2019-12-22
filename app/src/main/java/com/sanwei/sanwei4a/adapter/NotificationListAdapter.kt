package com.sanwei.sanwei4a.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
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
        helper.setText(R.id.z_txt_nick_session, "官方直营店")
        helper.setText(R.id.z_txt_time_session, SimpleDateFormat("MM-dd hh:mm:ss", Locale.CHINA).format(item.time))
        helper.setText(R.id.z_txt_content_session, item.content)
        helper.setText(R.id.txt_badge_notification_item, item.unreadCount.toString())
        if (item.unreadCount > 0) {
            helper.setVisible(R.id.txt_badge_notification_item, true)
        } else {
            helper.setVisible(R.id.txt_badge_notification_item, false)
        }
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


