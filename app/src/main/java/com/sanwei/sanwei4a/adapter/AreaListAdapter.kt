package com.sanwei.sanwei4a.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
import java.io.Serializable

/**
 * Created by VincentLaf on 2018/3/31.
 *
 */
class AreaListAdapter(val mResId: Int, val mItems: MutableList<DataArea>) :
        BaseQuickAdapter<DataArea, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: DataArea?) {
        helper!!.setText(R.id.z_txt_area_item, item!!.name)
    }
}

class DataArea(val name: String = "", val code: Int = 0)

