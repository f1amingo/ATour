package com.sanwei.sanwei4a.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.ZViewPagerFragAdapter
import com.sanwei.sanwei4a.fragment.FragmentBookList
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_BORROWED
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_FAVORITE
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_PUBLISHED
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_SENT
import kotlinx.android.synthetic.main.activity_book_list.*

class BookListActivity : BaseActivity() {

    private lateinit var mAdapter: ZViewPagerFragAdapter
    private var mTypeFromParent: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        mAdapter = ZViewPagerFragAdapter(supportFragmentManager)

        mTypeFromParent = intent.getIntExtra("type", 0)

        initToolbar()
        initViewPager()

        z_pager_book_list.setCurrentItem(mTypeFromParent - 1, false)
    }

    private fun initViewPager() {
        mAdapter.addFragment(FragmentBookList().setType(TYPE_PUBLISHED), "我发布的")
                .addFragment(FragmentBookList().setType(TYPE_SENT), "我借出的")
                .addFragment(FragmentBookList().setType(TYPE_BORROWED), "我借入的")
                .addFragment(FragmentBookList().setType(TYPE_FAVORITE), "我想要的")
        z_pager_book_list.adapter = mAdapter
        z_pager_book_list.offscreenPageLimit = 3
        z_tab_book_list.setupWithViewPager(z_pager_book_list)

        z_pager_book_list.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
            override fun onPageSelected(position: Int) {
                z_toolbar_book_list.title = mAdapter.getPageTitle(position)
            }
        })
    }

    private fun initToolbar() {
        z_toolbar_book_list.setNavigationOnClickListener { finish() }
    }
}
