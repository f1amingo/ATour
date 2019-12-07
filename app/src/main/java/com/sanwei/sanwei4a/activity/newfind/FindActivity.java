package com.sanwei.sanwei4a.activity.newfind;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sanwei.sanwei4a.R;
import com.sanwei.sanwei4a.activity.BaseActivity;
import com.sanwei.sanwei4a.adapter.newfind.CourseListAdapter;
import com.sanwei.sanwei4a.adapter.newfind.JingPianListAdapter;
import com.sanwei.sanwei4a.adapter.newfind.ReDianListAdapter;
import com.sanwei.sanwei4a.adapter.newfind.ShopListAdapter;
import com.sanwei.sanwei4a.adapter.newfind.VideoListAdapter;
import com.sanwei.sanwei4a.entity.newfind.CourseItem;
import com.sanwei.sanwei4a.entity.newfind.JingPianItem;
import com.sanwei.sanwei4a.entity.newfind.ReDianItem;
import com.sanwei.sanwei4a.entity.newfind.ShopItem;
import com.sanwei.sanwei4a.entity.newfind.VideoItem;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FindActivity extends BaseActivity {
    private Banner banner;
    private RecyclerView jingpianRecycler;
    private JingPianListAdapter jingPianListAdapter;
    private RecyclerView videoCourseRecycler;
    private CourseListAdapter videoCourseAdapter;
    private RecyclerView shopRecycler;
    private ShopListAdapter shopListAdapter;
    private  RecyclerView viCourseRecycler;
    private CourseListAdapter viCourseAdapter;
    private RecyclerView reDianRecycler;
    private ReDianListAdapter reDianListAdapter;
    private RecyclerView videoRecycler;
    private VideoListAdapter videoListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_find);
        initView();
        initData();
    }

    private void initData() {
        List list_path=new ArrayList();
        List list_title=new ArrayList();
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list_path.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list_title.add("好好学习");
        list_title.add("天天向上");
        list_title.add("热爱劳动");
        list_title.add("不搞对象");
        banner.setImages(list_path);
        banner.setBannerTitles(list_title);
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load((String) path).into(imageView);
            }
        });
        banner.start();
        List jingpianList=new ArrayList();
        for(int i=0;i<6;i++){
            JingPianItem jingPianItem=new JingPianItem();
            jingPianItem.setLabel("上新");
            jingPianItem.setDescription("帮你打通机器学习的任督二脉");
            jingPianItem.setTitle_1("21|函数扩张");
            jingPianItem.setTitle_2("机器学习40讲");
            jingPianItem.setPrice("$8/40期");
            jingPianItem.setName("老张");
            jingPianItem.setIntroduction("博士");
            jingPianItem.setAvatar("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
            jingPianItem.setImg("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
            jingpianList.add(jingPianItem);
        }
        jingPianListAdapter=new JingPianListAdapter(R.layout.z_item_jingpian,jingpianList,this);
        jingpianRecycler.setAdapter(jingPianListAdapter);
        List videoCourses=new ArrayList();
        for(int i=0;i<3;i++){
            CourseItem item=new CourseItem();
            item.setDescription("Java/Android开发者的实战指南");
            item.setDetail("53课时*约550分钟");
            item.setName("张涛");
            item.setIntroduction("资深安卓工程师");
            item.setTitle("快速上手Kotlin");
            item.setPrice("200元");
            item.setImg("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
            videoCourses.add(item);
        }
        videoCourseAdapter=new CourseListAdapter(R.layout.z_item_course,videoCourses,this);
        videoCourseRecycler.setAdapter(videoCourseAdapter);
        List shops=new ArrayList();
        for(int i=0;i<6;i++){
            ShopItem item=new ShopItem();
            item.setTitle("快速上手Kotlin");
            item.setPrice("200元");
            item.setImg("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
            shops.add(item);
        }
        shopListAdapter=new ShopListAdapter(R.layout.z_item_shop,shops,this);
        shopRecycler.setAdapter(shopListAdapter);

        List viCourses=new ArrayList();
        for(int i=0;i<3;i++){
            CourseItem item=new CourseItem();
            item.setDescription("好书，值得一看");
            item.setDetail("共6篇文章");
            item.setTitle("深入浅出XXX");
            item.setPrice("200元");
            item.setImg("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
            viCourses.add(item);
        }
        viCourseAdapter=new CourseListAdapter(R.layout.z_item_course,viCourses,this);
        viCourseRecycler.setAdapter(viCourseAdapter);
        List redians=new ArrayList();
        for(int i=0;i<7;i++){
            ReDianItem item=new ReDianItem();
            item.setImg("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
            redians.add(item);
        }
        List videos=new ArrayList();
        for(int i=0;i<6;i++){
            VideoItem item=new VideoItem();
            item.setTitle("快速上手Kotlin");
            item.setName("张涛");
            item.setImg("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
            videos.add(item);
        }
        reDianListAdapter=new ReDianListAdapter(R.layout.z_item_redian,redians,this);
        reDianRecycler.setAdapter(reDianListAdapter);
        videoListAdapter=new VideoListAdapter(R.layout.z_item_video,videos,this);
        videoRecycler.setAdapter(videoListAdapter);

    }

    private void initView() {
        banner=findViewById(R.id.bannerRollImg);
        banner.setDelayTime(2000);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        jingpianRecycler=findViewById(R.id.recycler_jingpian);
        jingpianRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        jingpianRecycler.setLayoutManager(linearLayoutManager);

        videoCourseRecycler=findViewById(R.id.recycler_videoCourse);
        videoCourseRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(OrientationHelper.VERTICAL);
        videoCourseRecycler.setLayoutManager(linearLayoutManager1);


        shopRecycler=findViewById(R.id.recycler_shop);
        shopRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManage2=new LinearLayoutManager(this);
        linearLayoutManage2.setOrientation(OrientationHelper.HORIZONTAL);
        shopRecycler.setLayoutManager(linearLayoutManage2);

        viCourseRecycler=findViewById(R.id.recycler_vi_course);
        LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(OrientationHelper.VERTICAL);
        viCourseRecycler.setLayoutManager(linearLayoutManager3);

        reDianRecycler=findViewById(R.id.recycler_redian);
        reDianRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager_re=new LinearLayoutManager(this);
        linearLayoutManager_re.setOrientation(OrientationHelper.HORIZONTAL);
        reDianRecycler.setLayoutManager(linearLayoutManager_re);
        LinearLayoutManager linearLayoutManager_video=new LinearLayoutManager(this);
        linearLayoutManager_video.setOrientation(OrientationHelper.HORIZONTAL);

        videoRecycler=findViewById(R.id.recycler_video);
        videoRecycler.setNestedScrollingEnabled(false);
        videoRecycler.setLayoutManager(linearLayoutManager_video);
    }
}
