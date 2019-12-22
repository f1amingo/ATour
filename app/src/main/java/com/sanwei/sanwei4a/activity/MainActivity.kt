package com.sanwei.sanwei4a.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.CustomPagerAdapter
import org.jetbrains.anko.find

import com.sanwei.sanwei4a.fragment.FragmentHome
import com.sanwei.sanwei4a.fragment.FragmentInfo
import com.sanwei.sanwei4a.fragment.FragmentMine

import com.sanwei.sanwei4a.custom.PagingViewPager
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

class MainActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    private lateinit var mBottomNavigationView: BottomNavigationView
    private lateinit var mViewPager: PagingViewPager
    //viewpager与bottomNavigation button联动
    private var mSelectedMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomNavigationView = find(R.id.bottom_navigation_view)
        initViewPager()
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        mBottomNavigationView.disableShiftMode()
        mBottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> mViewPager.currentItem = 0
                R.id.navigation_friends -> mViewPager.currentItem = 1
                R.id.navigation_mine -> mViewPager.currentItem = 2
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initViewPager() {
        mViewPager = findViewById(R.id.viewPager)
        mViewPager.addOnPageChangeListener(this)
        val fragments = ArrayList<Fragment>(4)
        fragments.add(FragmentHome())
        fragments.add(FragmentInfo())
        fragments.add(FragmentMine())
        mViewPager.adapter = CustomPagerAdapter(supportFragmentManager, fragments)
        mViewPager.setPagingEnabled(true)
        mViewPager.offscreenPageLimit = 3
    }

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    override fun onPageSelected(position: Int) {
        mSelectedMenuItem?.isChecked = false
        mSelectedMenuItem = mBottomNavigationView.menu.getItem(position)
        mSelectedMenuItem?.isChecked = true
        if (position == 0 && !App.hasSelectPreferTags && App.account != null) {
            showPreferDlg()
        }
    }

    private lateinit var mPreferDlg: AlertDialog

    private fun showPreferDlg() {
        val builder = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.layout_dlg_prefer_tag, null)
        builder.setView(view)
        builder.setCancelable(false)
        view.find<Button>(R.id.z_btn_ok_prefer_tag).setOnClickListener {
            mPreferDlg.dismiss()

        }
        val flowLayout = view.findViewById<TagFlowLayout>(R.id.z_flow_prefer_tag)
        val tags = listOf("人文社科", "小说", "悬疑科幻", "计算机", "哲学宗教", "政治军事", "传记", "考研保研")
        flowLayout.adapter = object : TagAdapter<String>(tags) {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val textView = LayoutInflater.from(this@MainActivity).inflate(R.layout.layout_prefer_tag, parent, false) as TextView
                textView.text = t
                return textView
            }
        }
        mPreferDlg = builder.create()
        mPreferDlg.show()
        App.changeHasSelectPreferTags()
    }
}

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = this.getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView

            item.setShiftingMode(false)
            // set once again checked value, so view will be updated

            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e("exts", "Unable to get shift mode field", e)
    } catch (e: IllegalAccessException) {
        Log.e("exts", "Unable to change value of shift mode", e)
    }
}