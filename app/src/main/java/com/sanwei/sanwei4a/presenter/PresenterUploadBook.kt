package com.sanwei.sanwei4a.presenter

import android.util.Log
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.activity.ShowType
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.po.BusinessBook
import com.sanwei.sanwei4a.entity.po.SysBook
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.account.AccountService
import com.sanwei.sanwei4a.model.book.BookQueryService
import com.sanwei.sanwei4a.model.book.BookUploadService
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.Router
import com.sanwei.sanwei4a.view.IViewUploadBook
import java.io.File

/**
 * Created by VincentLaf on 2018/4/11.
 *
 */
class PresenterUploadBook : BasePresenter<IViewUploadBook>() {

    private val modelQuery = BookQueryService()
    private val modelUpload = BookUploadService()

    fun getBookInfo(isbn: String) {
        view?.loadingBookInfo()
        modelQuery.requestBookOfficialInfo(isbn, object : OnRequestListener<SysBook> {
            override fun onSuccess(books: SysBook) {
                handler.post({
                    try {
                        view?.fillTheBookInfo(books)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                })

            }

            override fun onFail(msg: String?, code: Int) {
                handler.post {
                    try {
                        when (code) {
                            Constant.NOT_FOUND -> {
                                view?.noSuchBook()
                            }
                            Constant.EXCEPTION -> {
                                view?.loadBookInfoError(msg!!)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }//onFail
        })//requestBookOfficialInfo
    }

    fun uploadBook(book: BusinessBook, frontFile: File, inPic: File, endPic: File) {
        view?.showWaitingDlg()
        modelUpload.request(book, frontFile, inPic, endPic, object : OnRequestListener<Int> {
            override fun onSuccess(data: Int) {
                handler.post {
                    view?.dismissWaitingDlg()
                    view?.finish()
                    Router.toBookDetails(view?.ctx(), ShowType.bookDetails_mine, data)
                    AccountService().getBalance(object : OnRequestListener<Int> {
                        override fun onSuccess(data: Int) {
                            App.account!!.accBanlance = data
                        }

                        override fun onFail(msg: String?, code: Int) {
                            LogUtil.e("getBalance", "获取余额失败 msg：$msg")
                        }
                    })
                }
            }

            override fun onFail(msg: String?, code: Int) {
                msg!!
                LogUtil.e(TAG, msg)
                handler.post {
                    view?.dismissWaitingDlg()
                }
            }
        })
    }
}