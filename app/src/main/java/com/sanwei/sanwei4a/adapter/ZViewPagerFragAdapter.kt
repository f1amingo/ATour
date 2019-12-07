package com.sanwei.sanwei4a.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by VincentLaf on 2018/1/18.
 * 配合Fragment+ViewPager+TabLayout组合使用的FragmentAdapter
 */
class ZViewPagerFragAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val mFragmentList = ArrayList<Fragment>()
    //存放tab的title,会显示到界面上去
    private val mTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int = mFragmentList.size

    fun addFragment(fragment: Fragment, title: String): ZViewPagerFragAdapter {
        mFragmentList.add(fragment)
        mTitleList.add(title)
        return this
    }

    //当viewpager与tablayout绑定时会自动获取title
    override fun getPageTitle(position: Int): CharSequence = mTitleList[position]
}