package com.sanwei.sanwei4a.adapter.newfind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanwei.sanwei4a.R;
import com.sanwei.sanwei4a.entity.newfind.ShopItem;

import java.util.List;

import per.johnson.server.protocal.ProtocalType;

public class ShopListAdapter extends BaseQuickAdapter<ShopItem,BaseViewHolder> {
    private Context context;
    public ShopListAdapter(int layoutResId, @Nullable List<ShopItem> data) {
        super(layoutResId, data);
    }
    public ShopListAdapter(int layoutResId, @Nullable List<ShopItem> data, Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopItem item) {
        helper.setText(R.id.price,item.getPrice());
        helper.setText(R.id.title,item.getTitle());
        Glide.with(context).load(item.getImg()).into((ImageView) helper.getView(R.id.img));
    }
}
