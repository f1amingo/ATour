package com.sanwei.sanwei4a.presenter

import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.entity.po.BookSimpleInfo
import com.sanwei.sanwei4a.entity.result.QueryResult
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.book.BookQueryService
import com.sanwei.sanwei4a.model.discovery.DiscoveryService
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.view.IViewBookList

/**
 * Created by VincentLaf on 2018/4/22.
 *
 */
class PresenterBookListSingleLine : BasePresenter<IViewBookList>() {

    val model: BookQueryService = BookQueryService()

    private var mNextPage: Int = 1

    fun refresh(type: Int) {
        view?.setIsRefreshing(true)
        mNextPage = 1
        model.requestMineBooks(type, mNextPage, object : OnRequestListener<QueryResult<BookSimpleInfo>> {
            override fun onSuccess(queryResult: QueryResult<BookSimpleInfo>) {
                handler.post {
                    view?.clearItems()
                    view?.addItemList(ArrayList(queryResult.data))
                    view?.loadMoreComplete()
                    view?.setIsRefreshing(false)
                    if (!queryResult.isHasNext) view?.loadMoreEnd()
                    mNextPage++
                }
            }

            override fun onFail(msg: String?, code: Int) {
                msg!!
                LogUtil.e(TAG, msg)
                handler.post {
                    view?.loadMoreFail()
                    view?.setIsRefreshing(false)
                }
            }
        })
    }

    fun loadMore(type: Int) {
        view?.setIsRefreshing(false)
        model.requestMineBooks(type, mNextPage, object : OnRequestListener<QueryResult<BookSimpleInfo>> {
            override fun onSuccess(queryResult: QueryResult<BookSimpleInfo>) {
                handler.post {
                    view?.addItemList(ArrayList(queryResult.data))
                    view?.loadMoreComplete()
                    if (!queryResult.isHasNext) view?.loadMoreEnd()
                    mNextPage++
                }
            }

            override fun onFail(msg: String?, code: Int) {
                msg!!
                LogUtil.e(TAG, msg)
                handler.post {
                    view?.loadMoreFail()
                }
            }
        })
    }
}