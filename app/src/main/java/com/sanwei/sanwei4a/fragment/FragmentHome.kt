package com.sanwei.sanwei4a.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.CustomPagerAdapter
import com.sanwei.sanwei4a.adapter.ItemHomeTour
import com.sanwei.sanwei4a.adapter.ZHomeTourListAdapter
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.home_fragment.*


class FragmentHome : BaseFragment() {

    private lateinit var mAdapter: ZHomeTourListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.home_fragment, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initBanner()
        initTab()
        initRecyclerView()
        initViewPager()
    }

    private fun initTab() {
        z_tab_home.addTab(z_tab_home.newTab().setText("怎么玩"))
        z_tab_home.addTab(z_tab_home.newTab().setText("住哪里"))
        z_tab_home.addTab(z_tab_home.newTab().setText("吃什么"))
        z_tab_home.addTab(z_tab_home.newTab().setText("低价机票"))
    }

    private fun initBanner() {
        z_discover_banner.setImages(listOf("https://dimg04.c-ctrip.com/images/zg0c1b000001a6c4p3595.jpg"
                , "https://dimg04.c-ctrip.com/images/zg0k1b000001a5tueEAB8.jpg"))
                .setImageLoader(object : ImageLoader() {
                    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                        Glide.with(context).load(path as String).into(imageView)
                    }
                })
                .start()
    }

    private fun initRecyclerView() {
        mAdapter = ZHomeTourListAdapter(R.layout.z_item_home_tour, ArrayList())
        z_recycler_home_tour.adapter = mAdapter
        z_recycler_home_tour.layoutManager = LinearLayoutManager(context)

        mAdapter.addData(ItemHomeTour("", "", "", ""))
        mAdapter.addData(ItemHomeTour("", "", "", ""))
        mAdapter.addData(ItemHomeTour("", "", "", ""))

    }

    private fun initViewPager() {
        z_pager_home.adapter = CustomPagerAdapter(activity.supportFragmentManager, arrayListOf(FragmentHotel()))
//        mViewPager = findViewById(R.id.viewPager)
//        mViewPager.addOnPageChangeListener(this)
//        val fragments = ArrayList<Fragment>(4)
//        fragments.add(FragmentDiscover())
//
//        mViewPager.adapter = CustomPagerAdapter(supportFragmentManager, fragments)
//        mViewPager.setPagingEnabled(true)
//        mViewPager.offscreenPageLimit = 2
    }

}