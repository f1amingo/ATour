package com.sanwei.sanwei4a.adapter.newfind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanwei.sanwei4a.R;
import com.sanwei.sanwei4a.entity.newfind.ReDianItem;
import com.sanwei.sanwei4a.entity.newfind.ShopItem;

import java.util.List;

public class ReDianListAdapter extends BaseQuickAdapter <ReDianItem,BaseViewHolder>{
    private Context context;
    public ReDianListAdapter(int layoutResId, @Nullable List<ReDianItem> data) {
        super(layoutResId, data);
    }
    public ReDianListAdapter(int layoutResId, @Nullable List<ReDianItem> data, Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReDianItem item) {
        Glide.with(context).load(item.getImg()).into((ImageView) helper.getView(R.id.img));
    }
}
