package com.sanwei.sanwei4a.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.activity.BookDetailsActivity
import com.sanwei.sanwei4a.activity.ShowType
import com.sanwei.sanwei4a.adapter.AdapterBookList
import com.sanwei.sanwei4a.adapter.AdapterBookListSingleLine
import com.sanwei.sanwei4a.entity.po.BookSimpleInfo
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_BORROWED
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_FAVORITE
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_PUBLISHED
import com.sanwei.sanwei4a.model.book.BookQueryService.Companion.TYPE_SENT
import com.sanwei.sanwei4a.presenter.PresenterBookList
import com.sanwei.sanwei4a.presenter.PresenterBookListSingleLine
import com.sanwei.sanwei4a.util.EmptyViewBuilder
import com.sanwei.sanwei4a.util.RequestUtil
import com.sanwei.sanwei4a.util.Router
import com.sanwei.sanwei4a.view.IViewBookList
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity

class FragmentBookList : BaseFragment(), IViewBookList {

    override fun setIsRefreshing(b: Boolean) {
        mSwipeRefreshLayout.isRefreshing = b
        //刷新同时不能加载更多
        mAdapter.setEnableLoadMore(!b)
    }

    override fun loadMoreComplete() {
        mAdapter.loadMoreComplete()
        mAdapter.emptyView = EmptyViewBuilder.createEmptyView(context, EmptyViewBuilder.EmptyType.TYPE_NO_DATA)
    }

    override fun loadMoreFail() {
        mAdapter.loadMoreFail()
        mAdapter.emptyView = EmptyViewBuilder.createEmptyView(context, EmptyViewBuilder.EmptyType.TYPE_NETWORK_ERROR)
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

    private var isViewCreated = false
    private var isLazyLoaded = false
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private val mAdapter: AdapterBookListSingleLine = AdapterBookListSingleLine(R.layout.z_item_book_single_line, ArrayList())
    private var mType: Int = 0
    private lateinit var mPresenter: PresenterBookListSingleLine

    fun setType(type: Int): FragmentBookList {
        mType = type
        return this
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_book_list, container, false)
        mRecyclerView = view.find(R.id.z_recycler_book_list_frag)
        mSwipeRefreshLayout = view.find(R.id.z_refresh_book_list_frag)
        mPresenter = PresenterBookListSingleLine()
        mPresenter.attachView(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        isViewCreated = true
        initRecyclerView()
        initSwipeRefreshLayout()

        lazyLoad()
    }

    private fun initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener { mPresenter.refresh(mType) }
    }

    private fun initRecyclerView() {
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter
        mAdapter.setOnLoadMoreListener({
            mPresenter.loadMore(mType)
        },
                mRecyclerView)
        mAdapter.setOnItemChildLongClickListener { _, _, position ->
            val item = mAdapter.getItem(position)

            true
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)!!
            val showType = when (mType) {
                TYPE_PUBLISHED -> ShowType.bookDetails_mine
                TYPE_SENT -> ShowType.bookDetails_out
                TYPE_BORROWED -> ShowType.bookDetails_in
                TYPE_FAVORITE -> ShowType.bookDetails_default
                else -> ShowType.bookDetails_default
            }
            Router.toBookDetails(context, showType, item.booId)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        lazyLoad()
    }

    private fun lazyLoad() {
        if (isViewCreated && userVisibleHint && !isLazyLoaded) {
            mPresenter.refresh(mType)
            isLazyLoaded = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}