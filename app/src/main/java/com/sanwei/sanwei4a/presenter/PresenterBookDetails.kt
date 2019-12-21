package com.sanwei.sanwei4a.presenter

import com.sanwei.sanwei4a.entity.po.BookDetailInfo
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.book.BookUploadService
import com.sanwei.sanwei4a.model.discovery.BookDetailService
import com.sanwei.sanwei4a.view.IViewBookDetails

/**
 * Created by VincentLaf on 2018/4/22.
 *
 */
class PresenterBookDetails : BasePresenter<IViewBookDetails>() {

    val modelDetails: BookDetailService = BookDetailService()
    val modelRemove: BookUploadService = BookUploadService()

    fun request(booId: Int) {
        view?.setIsLoading()
        modelDetails.request(booId, object : OnRequestListener<BookDetailInfo> {
            override fun onSuccess(info: BookDetailInfo) {
                handler.post {
                    view?.loadSuccess(info)
                }
            }

            override fun onFail(msg: String?, code: Int) {
                handler.post {
                    view?.loadFail()

                }
            }
        })
    }

    fun remove(booId: Int) {
        view?.showWaitingDlg()
        modelRemove.removeBook(booId, object : OnRequestListener<Boolean> {
            override fun onSuccess(data: Boolean) {
                handler.post {
                    view?.dismissWaitingDlg()
                }
            }

            override fun onFail(msg: String?, code: Int) {
                handler.post {
                    view?.dismissWaitingDlg()
                }
            }
        })
    }

}