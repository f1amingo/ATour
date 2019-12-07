package com.sanwei.sanwei4a.adapter.newfind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanwei.sanwei4a.R;
import com.sanwei.sanwei4a.entity.newfind.VideoItem;

import java.util.List;

public class VideoListAdapter extends BaseQuickAdapter<VideoItem,BaseViewHolder> {
    private Context context;
    public VideoListAdapter(int layoutResId, @Nullable List<VideoItem> data) {
        super(layoutResId, data);
    }
    public VideoListAdapter(int layoutResId, @Nullable List<VideoItem> data, Context context) {
        super(layoutResId, data);
        this.context=context;
    }


    @Override
    protected void convert(BaseViewHolder helper, VideoItem item) {
        helper.setText(R.id.name,item.getName());
        helper.setText(R.id.title,item.getTitle());
        Glide.with(context).load(item.getImg()).into((ImageView) helper.getView(R.id.img));
    }
}
