package com.sanwei.sanwei4a.adapter.newfind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanwei.sanwei4a.R;
import com.sanwei.sanwei4a.entity.newfind.JingPianItem;

import java.util.List;

public class JingPianListAdapter extends BaseQuickAdapter<JingPianItem,BaseViewHolder>{
    private Context context;
    public JingPianListAdapter(int layoutResId, @Nullable List<JingPianItem> data) {
        super(layoutResId, data);
    }
    public JingPianListAdapter(int layoutResId, @Nullable List<JingPianItem> data,Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, JingPianItem item) {
        helper.setText(R.id.price,item.getPrice());
        helper.setText(R.id.title_1,item.getTitle_1());
        helper.setText(R.id.title_2,item.getTitle_2());
        helper.setText(R.id.description,item.getDescription());
        helper.setText(R.id.introduction,item.getIntroduction());
        helper.setText(R.id.name,item.getName());
        helper.setText(R.id.label,item.getLabel());
        Glide.with(context).load(R.drawable.test).into((ImageView) helper.getView(R.id.img));
        Glide.with(context).load(R.mipmap.icon).into((ImageView) helper.getView(R.id.avatar));

    }
}
