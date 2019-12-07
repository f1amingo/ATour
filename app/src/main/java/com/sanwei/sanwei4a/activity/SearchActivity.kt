package com.sanwei.sanwei4a.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.inputmethod.EditorInfo
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.AdapterBookList
import com.sanwei.sanwei4a.entity.po.BookSimpleInfo
import com.sanwei.sanwei4a.presenter.PresenterSearch
import com.sanwei.sanwei4a.util.EmptyViewBuilder
import com.sanwei.sanwei4a.util.Router
import com.sanwei.sanwei4a.view.IViewSearch
import kotlinx.android.synthetic.main.activity_search.*
import android.view.inputmethod.InputMethodManager


class SearchActivity : BaseActivity(), IViewSearch {
    override fun setIsRefreshing(b: Boolean) {
        z_refresh_search.isRefreshing = b
        mAdapter.setEnableLoadMore(!b)
    }

    override fun loadMoreComplete() {
        mAdapter.loadMoreComplete()
        mAdapter.emptyView = EmptyViewBuilder.createEmptyView(this, EmptyViewBuilder.EmptyType.TYPE_NO_DATA)
    }

    override fun loadMoreFail() {
        mAdapter.loadMoreFail()
        mAdapter.emptyView = EmptyViewBuilder.createEmptyView(this, EmptyViewBuilder.EmptyType.TYPE_NETWORK_ERROR)
    }

    override fun loadMoreEnd() {
        mAdapter.loadMoreEnd()
    }

    override fun addItemList(list: ArrayList<BookSimpleInfo>) {
        mAdapter.addData(list)
    }

    override fun clearItems() {
        mAdapter.mItems.clear()
    }

    private var mIsbn: String? = null
    private var mKeyword: String = ""
    private val mAdapter: AdapterBookList = AdapterBookList(R.layout.z_item_book_staggered, ArrayList())
    private val mPresenter: PresenterSearch = PresenterSearch()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        mIsbn = intent.getStringExtra("isbn")

        initBack()
        initGoBtn()
        initRecyclerView()
        initEdit()
        z_refresh_search.isEnabled = false

        mPresenter.attachView(this)

        if (mIsbn != null) {
            z_edit_search.setText(mIsbn)
            mPresenter.refreshByIsbn(mIsbn!!)
        }
    }

    private fun initEdit() {
        z_edit_search.setOnEditorActionListener { _, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    search()
                }
            }
            return@setOnEditorActionListener false
        }
    }

    private fun hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(z_edit_search.windowToken, 0)
    }

    private fun search() {
        mKeyword = z_edit_search.text.toString()
        if (mKeyword == "") return
        hideSoftKeyboard()
        mIsbn = null
        mPresenter.refreshByKeyword(mKeyword)
    }

    private fun initGoBtn() {
        z_btn_go_search.setOnClickListener {
            search()
        }
    }

    private fun initRecyclerView() {
        z_recycler_search.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        z_recycler_search.adapter = mAdapter
        mAdapter.setOnItemChildClickListener { _, view, position ->
            val item = mAdapter.getItem(position)!!
            when (view.id) {
                R.id.z_layout_cover_book_item -> {
                    Router.toBookDetails(this, ShowType.bookDetails_default, item.booId)
                }
            }
        }
        mAdapter.setOnLoadMoreListener({
            if (mKeyword != "") mPresenter.loadMoreByKeyword(mKeyword)
            else if (mIsbn != null) mPresenter.loadMoreByIsbn(mIsbn!!)
        }, z_recycler_search)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    private fun initBack() {
        z_img_back_search.setOnClickListener { finish() }
    }
}
