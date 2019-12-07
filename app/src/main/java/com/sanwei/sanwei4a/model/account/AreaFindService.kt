package com.sanwei.sanwei4a.model.account

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.doAsync
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.AccountParam
import com.sanwei.sanwei4a.entity.parameter.account.AreaParam
import com.sanwei.sanwei4a.entity.po.Account
import com.sanwei.sanwei4a.entity.po.AccountAddress
import com.sanwei.sanwei4a.entity.po.Area
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.util.Functions
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import java.io.IOException

/**
 * @author Johnson
 *  用户填写具体地址时，
 */
class AreaFindService {

    /**
     * 请求省、市、区县信息
     * @param code 地区标识码
     * @param level 地区级别，具体查看  Area
     */
    fun request(code: Int, level: Int, areaModel: OnRequestListener<List<Area>>) {
        doAsync {
            try {
                val area = Area()
                area.code = code
                area.level = level
                RequestUtil.doPost(Functions.CITY_QUERY_AREA, null, AreaParam(area), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        areaModel.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            if (response.code() == 401
                            /** 无权限*/
                            ) {
                                areaModel.onFail("无权访问", Constant.NOT_FOUND)
                            } else {
                                val reference = object : TypeReference<Result<List<Area>>>() {}
                                val str = response.body()?.string()
                                val result = JSONObject.parseObject(str, reference)
                                if (result.code == Constant.OK_CODE) {
                                    areaModel.onSuccess(result.data)
                                } else {
                                    areaModel.onFail(result.msg, result.code)
                                }
                            }
                        } else {
                            areaModel.onFail("请求错误", Constant.NOT_FOUND)
                        }
                        response?.close()
                    }
                })
            } catch (e: Exception) {
                areaModel.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }

    fun getAllAddress(accountId: Int, onGetAllAddressListener: OnRequestListener<List<AccountAddress>>) {
        doAsync {
            try {
                val account = Account()
                account.accId = accountId
                val param = AccountParam(null, account, null)
                RequestUtil.doPost(Functions.GET_ALL_ADDRESS, null, param, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        onGetAllAddressListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        try {
                            if (response?.isSuccessful == true) {
                                val str = response.body()!!.string()
                                LogUtil.d("getAllAddress", str)
                                val reference = object : TypeReference<Result<List<AccountAddress>>>() {}
                                val result = JSONObject.parseObject(str, reference)
                                if (result.code == Constant.OK_CODE) {
                                    onGetAllAddressListener.onSuccess(result.data)
                                } else {
                                    onGetAllAddressListener.onFail(result.msg, result.code)
                                }
                            } else {
                                onGetAllAddressListener.onFail("网络错误", Constant.NOT_FOUND)
                            }
                            response?.close()
                        } catch (e: Exception) {
                            onGetAllAddressListener.onFail(e.message, Constant.EXCEPTION)
                        }
                    }

                })
            } catch (e: Exception) {
                onGetAllAddressListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }
}