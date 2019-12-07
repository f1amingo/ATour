package com.sanwei.sanwei4a.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.room.SystemNotiEntity
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by VincentLaf on 2018/4/20.
 *
 */
class AdapterSystemNotification(mResId: Int, val mItems: MutableList<SystemNotiEntity>) :
        BaseQuickAdapter<SystemNotiEntity, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder, item: SystemNotiEntity) {
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(item.timestamp)
        helper.setText(R.id.z_txt_time_system_noti, time)
                .setText(R.id.z_txt_title_system_noti, item.title)
                .setText(R.id.z_txt_content_system_noti, item.content)
    }
}

