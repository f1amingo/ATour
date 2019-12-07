package com.sanwei.sanwei4a.presenter

import com.sanwei.sanwei4a.entity.po.BookSimpleInfo
import com.sanwei.sanwei4a.entity.result.QueryResult
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.book.BookQueryService
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.view.IViewSearch

/**
 * Created by VincentLaf on 2018/4/22.
 *
 */
class PresenterSearch : BasePresenter<IViewSearch>() {

    val model: BookQueryService = BookQueryService()

    private var mNextPage: Int = 1

    fun refreshByKeyword(key: String) {
        view?.setIsRefreshing(true)
        mNextPage = 1
        model.queryBooksByKeyWords(key, mNextPage, object : OnRequestListener<QueryResult<BookSimpleInfo>> {
            override fun onSuccess(data: QueryResult<BookSimpleInfo>) {
                handler.post {
                    view?.clearItems()
                    view?.addItemList(ArrayList(data.data))
                    view?.loadMoreComplete()
                    view?.setIsRefreshing(false)
                    if (!data.isHasNext) view?.loadMoreEnd()
                    mNextPage++
                }
            }

            override fun onFail(msg: String?, code: Int) {
                msg!!
                LogUtil.e(TAG, msg)
                handler.post {
                    view?.toast(msg)
                    view?.loadMoreFail()
                    view?.setIsRefreshing(false)
                }
            }
        })
    }

    fun loadMoreByKeyword(key: String) {
        view?.setIsRefreshing(false)
        model.queryBooksByKeyWords(key, mNextPage, object : OnRequestListener<QueryResult<BookSimpleInfo>> {
            override fun onSuccess(data: QueryResult<BookSimpleInfo>) {
                handler.post {
                    view?.addItemList(ArrayList(data.data))
                    view?.loadMoreComplete()
                    if (!data.isHasNext) view?.loadMoreEnd()
                    mNextPage++
                }
            }

            override fun onFail(msg: String?, code: Int) {
                msg!!
                LogUtil.e(TAG, msg)
                handler.post {
                    view?.toast(msg)
                    view?.loadMoreFail()
                }
            }
        })
    }

    fun refreshByIsbn(isbn:String){
        view?.setIsRefreshing(true)
        mNextPage = 1
        model.queryBooksByISBN(isbn,mNextPage,object :OnRequestListener<QueryResult<BookSimpleInfo>>{
            override fun onSuccess(data: QueryResult<BookSimpleInfo>) {
                handler.post {
                    view?.clearItems()
                    view?.addItemList(ArrayList(data.data))
                    view?.loadMoreComplete()
                    view?.setIsRefreshing(false)
                    if (!data.isHasNext) view?.loadMoreEnd()
                    mNextPage++
                }
            }

            override fun onFail(msg: String?, code: Int) {
                msg!!
                LogUtil.e(TAG, msg)
                handler.post {
                    view?.toast(msg)
                    view?.loadMoreFail()
                    view?.setIsRefreshing(false)
                }
            }
        })
    }

    fun loadMoreByIsbn(isbn:String){
        view?.setIsRefreshing(false)
        model.queryBooksByISBN(isbn,mNextPage,object : OnRequestListener<QueryResult<BookSimpleInfo>> {
            override fun onSuccess(data: QueryResult<BookSimpleInfo>) {
                handler.post {
                    view?.addItemList(ArrayList(data.data))
                    view?.loadMoreComplete()
                    if (!data.isHasNext) view?.loadMoreEnd()
                    mNextPage++
                }
            }

            override fun onFail(msg: String?, code: Int) {
                msg!!
                LogUtil.e(TAG, msg)
                handler.post {
                    view?.toast(msg)
                    view?.loadMoreFail()
                }
            }
        })
    }
}