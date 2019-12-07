package com.sanwei.sanwei4a.view

import com.sanwei.sanwei4a.entity.parameter.account.User
import com.sanwei.sanwei4a.entity.po.BookDetailInfo
import com.sanwei.sanwei4a.presenter.PresenterMineDetails

/**
 * Created by VincentLaf on 2018/4/22.
 *
 */
interface IViewMineDetails : IBaseView {

    fun showLoadingFirstTime()
    fun loadSuccess(user: User)
    fun loadFail()
    fun updateUserInfo(newVal: String, which: PresenterMineDetails.ModifiedItem)
}