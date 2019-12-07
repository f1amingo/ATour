package com.sanwei.sanwei4a.adapter.newfind;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sanwei.sanwei4a.R;
import com.sanwei.sanwei4a.entity.newfind.CourseItem;

import java.util.List;

public class CourseListAdapter extends BaseQuickAdapter<CourseItem,BaseViewHolder> {
    private Context context;
    public CourseListAdapter(int layoutResId, @Nullable List<CourseItem> data) {
        super(layoutResId, data);
    }
    public CourseListAdapter(int layoutResId, @Nullable List<CourseItem> data, Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseItem item) {
        helper.setText(R.id.title,item.getTitle());
        helper.setText(R.id.description,item.getDescription());
        helper.setText(R.id.detail,item.getDetail());
        if(item.getName()==null||item.getName().equals("")){
            helper.getView(R.id.person).setVisibility(View.INVISIBLE);
        }else {
            helper.setText(R.id.name,item.getName());
            helper.setText(R.id.introduction,item.getIntroduction());
        }
        Glide.with(context).load(item.getImg()).into((ImageView) helper.getView(R.id.img));

    }
}
