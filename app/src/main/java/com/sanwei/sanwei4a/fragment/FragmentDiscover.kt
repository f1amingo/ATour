package com.sanwei.sanwei4a.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.activity.BarScanActivity
import com.sanwei.sanwei4a.activity.SearchActivity
import com.sanwei.sanwei4a.activity.ShowType
import com.sanwei.sanwei4a.activity.UploadBookActivity
import com.sanwei.sanwei4a.adapter.AdapterBookList
import com.sanwei.sanwei4a.entity.po.BookSimpleInfo
import com.sanwei.sanwei4a.presenter.PresenterBookList
import com.sanwei.sanwei4a.util.EmptyViewBuilder
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.Router
import com.sanwei.sanwei4a.view.IViewBookList
import com.stfalcon.frescoimageviewer.ImageViewer
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.discover_fragment.*
import kotlinx.coroutines.experimental.newSingleThreadContext
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import java.util.*


/**
 * Created by Johnson on 2018/3/31.
 * 管理界面
 */
class FragmentDiscover : BaseFragment(), IViewBookList {

    override fun setIsRefreshing(b: Boolean) {
        mSwipeRefreshLayout.isRefreshing = b
        //刷新同时不能加载更多
        mAdapterBookList.setEnableLoadMore(!b)
    }

    override fun loadMoreComplete() {
        mAdapterBookList.loadMoreComplete()
        mAdapterBookList.emptyView = EmptyViewBuilder.createEmptyView(context, EmptyViewBuilder.EmptyType.TYPE_NO_DATA)
    }

    override fun loadMoreFail() {
        mAdapterBookList.loadMoreFail()
        mAdapterBookList.emptyView = EmptyViewBuilder.createEmptyView(context, EmptyViewBuilder.EmptyType.TYPE_NETWORK_ERROR)
    }

    override fun loadMoreEnd() {
        mAdapterBookList.loadMoreEnd()
    }

    override fun addItemList(list: ArrayList<BookSimpleInfo>) {
        mAdapterBookList.addData(list)
    }

    override fun clearItems() {
        mAdapterBookList.mItems.clear()
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mAdapterBookList: AdapterBookList
    private val mPhone = "15071399733"
    private val mPresenter: PresenterBookList = PresenterBookList()
    private val mCodeBarScan: Int = 1237
    private var isHorizontal: Boolean = false
    private var firstVisiblePosition: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.discover_fragment, container, false)!!
        mRecyclerView = view.find(R.id.z_recycler_book)
        mSwipeRefreshLayout = view.find(R.id.z_refresh_book)
        mPresenter.attachView(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initRecyclerViewBook()
        initSwipeRefreshLayout()
        initScan()
        z_fab_upload_discover.setOnClickListener {
            if (App.account == null)
                toast("尚未登录")
            else
                startActivity<UploadBookActivity>()
        }

        z_cover_search_discover.setOnClickListener {
            startActivity<SearchActivity>()
        }

        z_img_list_layout_discover.setOnClickListener {
            try {
                mAdapterBookList.changeListLayout(isHorizontal)
                if (isHorizontal) {
                    val linearLayoutManager = mRecyclerView.layoutManager as LinearLayoutManager
                    firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition()
                    mRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    isHorizontal = false
                    z_img_list_layout_discover.setImageResource(R.drawable.ic_list_horizontal)
                } else {
                    val staggeredGridLayoutManager = mRecyclerView.layoutManager as StaggeredGridLayoutManager
                    val intArray = staggeredGridLayoutManager.findFirstVisibleItemPositions(null)
                    firstVisiblePosition = intArray[0]
                    mRecyclerView.layoutManager = LinearLayoutManager(context)
                    isHorizontal = true
                    z_img_list_layout_discover.setImageResource(R.drawable.ic_list_staggered)
                }
                mAdapterBookList.notifyDataSetChanged()
                mRecyclerView.scrollToPosition(firstVisiblePosition)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        mPresenter.refresh("mPhone")
    }

    private fun initRecyclerViewBook() {
        mAdapterBookList = AdapterBookList(R.layout.z_item_book_staggered, ArrayList())
        mRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerView.adapter = mAdapterBookList
        mAdapterBookList.setOnItemChildClickListener { _, view, position ->
            val item = mAdapterBookList.getItem(position)!!
            when (view.id) {
                R.id.z_layout_cover_book_item -> {
                    Router.toBookDetails(context, ShowType.bookDetails_default, item.booId)
                }
            }
        }
        mAdapterBookList.setOnItemClickListener { _, view, position ->
            val item = mAdapterBookList.getItem(position)!!
            Router.toBookDetails(context, ShowType.bookDetails_default, item.booId)
        }
        mAdapterBookList.setOnLoadMoreListener({
            mPresenter.loadMore(mPhone)
        }, mRecyclerView)
    }

    private fun initScan() {
        z_text_scan_discover.setOnClickListener {
            AndPermission.with(this)
                    .permission(Permission.CAMERA)
                    .onGranted {
                        if (it[0] == Permission.CAMERA) {
                            startActivityForResult(Intent(context, BarScanActivity::class.java), mCodeBarScan)
                        }
                    }.onDenied {
                        toast("授权失败，无法打开相机")
                    }
                    .start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            mCodeBarScan -> {
                if (resultCode == Activity.RESULT_OK) {
                    val isbn = data!!.getStringExtra("isbn")
                    LogUtil.d(TAG, "ISBN $isbn")
                    startActivity<SearchActivity>("isbn" to isbn)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    private fun initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener {
            mPresenter.refresh(mPhone)
        }
    }

}