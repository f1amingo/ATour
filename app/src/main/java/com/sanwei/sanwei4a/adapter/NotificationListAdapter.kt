package com.sanwei.sanwei4a.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by VincentLaf on 2018/3/31.
 *
 */
class NotificationListAdapter(val mResId: Int, val mItems: MutableList<ItemNotification>) :
        BaseQuickAdapter<ItemNotification, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: ItemNotification?) {

    }
}

class ItemNotification(val imgUrl: String = "",
                       val title: String = "",
                       val digest: String = "",
                       val time: String = "",
                       val type: NotificationType = NotificationType.CHAT_MSG,
                       var newCount: Int = 0) {

    enum class NotificationType {
        CHAT_MSG,
        PLATFORM_MSG
    }
}


