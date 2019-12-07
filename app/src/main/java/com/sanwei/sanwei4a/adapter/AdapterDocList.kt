package com.sanwei.sanwei4a.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by VincentLaf on 2018/8/8.
 *
 */
class AdapterDocList(mResId: Int, val mItems: MutableList<DocItem>) :
        BaseQuickAdapter<DocItem, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: DocItem?) {
        helper!!
        item!!
    }
}

data class DocItem(var name: String = "")


