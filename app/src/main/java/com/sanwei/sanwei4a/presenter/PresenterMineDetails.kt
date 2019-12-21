package com.sanwei.sanwei4a.presenter

import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.entity.parameter.account.InformationParam
import com.sanwei.sanwei4a.entity.parameter.account.User
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.model.account.AccountService
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.view.IViewMineDetails

/**
 * Created by VincentLaf on 2018/4/26.
 *
 */
class PresenterMineDetails : BasePresenter<IViewMineDetails>() {

    enum class ModifiedItem {
        NICKNAME,
        GENDER,
        BIRTH,
        REAL_NAME,
        STUDENT_ID,
        SCHOOL,
        COLLEGE
    }

    val model: AccountService = AccountService()

    fun request() {
        view?.showLoadingFirstTime()
        model.queryUserInfo(object : OnRequestListener<User> {
            override fun onSuccess(data: User) {
                handler.post {
                    view?.loadSuccess(data)
                }
            }

            override fun onFail(msg: String?, code: Int) {
                handler.post {
                    view?.loadFail()
                }
            }
        })
    }

    fun modify(newVal: String, which: ModifiedItem) {
        view?.showWaitingDlg()
        val information = InformationParam()
        information.user = User().setuAccid(App.account!!.accId)
        when (which) {
            PresenterMineDetails.ModifiedItem.NICKNAME -> information.accNickname = newVal
            PresenterMineDetails.ModifiedItem.GENDER -> information.user.setuSex(newVal)
            PresenterMineDetails.ModifiedItem.BIRTH -> information.user.setuIdnum(newVal)
            PresenterMineDetails.ModifiedItem.REAL_NAME -> information.user.setuName(newVal)
            PresenterMineDetails.ModifiedItem.STUDENT_ID -> information.user.setuSid(newVal)
            PresenterMineDetails.ModifiedItem.SCHOOL -> information.user.setuSchool(newVal)
            PresenterMineDetails.ModifiedItem.COLLEGE -> information.user.setuCollege(newVal)
        }
        model.modifyInformation(information, object : OnRequestListener<InformationParam> {
            override fun onSuccess(data: InformationParam) {
                LogUtil.e(TAG, data.toString())
                handler.post {
                    view?.updateUserInfo(newVal, which)
                    view?.dismissWaitingDlg()
                }
            }

            override fun onFail(msg: String?, code: Int) {
                handler.post {
                    try {
                        LogUtil.e(TAG, msg!!)
                        println(msg)
                        view?.dismissWaitingDlg()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}