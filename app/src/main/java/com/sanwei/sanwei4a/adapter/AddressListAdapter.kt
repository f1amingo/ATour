package com.sanwei.sanwei4a.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.po.AccountAddress
import java.io.Serializable

/**
 * Created by VincentLaf on 2018/3/31.
 *
 */
class AddressListAdapter(mResId: Int, val mItems: MutableList<AccountAddress>) :
        BaseQuickAdapter<AccountAddress, BaseViewHolder>(mResId, mItems) {

    override fun convert(helper: BaseViewHolder?, item: AccountAddress?) {
        helper!!
        item!!
        helper.addOnClickListener(R.id.z_layout_address_item)
                .addOnClickListener(R.id.z_edit_address_item)
                .addOnClickListener(R.id.z_delete_address_item)
                .addOnClickListener(R.id.z_check_address_item)
                .setText(R.id.z_txt_name_address_item,item.addName)
                .setText(R.id.z_txt_phone_address_item,item.addPhone)
                .setText(R.id.z_txt_address_address_item,item.addDetail)
    }
}



