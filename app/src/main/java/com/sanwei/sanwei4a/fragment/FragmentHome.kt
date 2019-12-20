package com.sanwei.sanwei4a.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.facebook.common.logging.LoggingDelegate
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.activity.TourDetailsActivity
import com.sanwei.sanwei4a.adapter.CustomPagerAdapter
import com.sanwei.sanwei4a.adapter.ItemHomeTour
import com.sanwei.sanwei4a.adapter.ZHomeTourListAdapter
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.home_fragment.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity


class FragmentHome : BaseFragment() {

    private lateinit var mAdapter: ZHomeTourListAdapter
    private val mTabTextList = listOf("吃什么", "住哪里", "低价机票")
    private val mTabFragments = listOf<Fragment>(FragmentRestaurant(), FragmentHotel(), FragmentFlight())

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.home_fragment, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initBanner()
        initTab()
        initRecyclerView()
//        initViewPager()
        replaceFragment(mTabFragments[0])
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_home, fragment)
        transaction.commit()
    }

    private fun initTab() {
        mTabTextList.forEach {
            z_tab_home.addTab(z_tab_home.newTab().setText(it))
        }

        z_tab_home.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) = Unit

            override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("onTabSelected", tab?.text.toString())
                Log.d(TAG, mTabTextList.indexOf(tab?.text).toString())
//                z_pager_home.currentItem = mTabTextList.indexOf(tab?.text)
                replaceFragment(mTabFragments[mTabTextList.indexOf(tab?.text)])
            }
        })
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
        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            startActivity<TourDetailsActivity>()
        }
    }

//    private fun initViewPager() {
//        z_pager_home.adapter =
//                CustomPagerAdapter(childFragmentManager, mTabFragments)
//        z_pager_home.setPagingEnabled(true)
//    }

}