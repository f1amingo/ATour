package com.sanwei.sanwei4a.model.account

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.parameter.AccountParam
import com.sanwei.sanwei4a.entity.parameter.BaseParameter
import com.sanwei.sanwei4a.entity.parameter.account.*
import com.sanwei.sanwei4a.entity.po.Account
import com.sanwei.sanwei4a.entity.po.AccountAddress
import com.sanwei.sanwei4a.entity.po.Area
import com.sanwei.sanwei4a.entity.po.Problem
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.model.DefaultCallbackImpl
import com.sanwei.sanwei4a.model.OnRequestListener
import com.sanwei.sanwei4a.util.Functions
import com.sanwei.sanwei4a.util.LogUtil
import com.sanwei.sanwei4a.util.RequestUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.jetbrains.anko.doAsync
import java.io.File
import java.io.IOException

/**
 * 提供用户服务
 */
class AccountService {

    /**
     * 添加用户地址
     * @param address 除了addId,每个参数都要填满
     */
    fun addAddress(address: AccountAddress, addressModel: OnRequestListener<List<AccountAddress>>) {
        doAsync {
            if (address.isIlegel) {
                try {
                    val addressParam = AddressParam(address)
                    RequestUtil.doPost(Functions.ADD_ADDRESS, null, addressParam, object : Callback {
                        override fun onFailure(call: Call?, e: IOException?) {
                            addressModel.onFail(e?.message, Constant.EXCEPTION)
                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            if (response?.isSuccessful == true) {
                                val str = response.body()?.string()
                                val reference = object : TypeReference<Result<List<AccountAddress>>>() {}
                                val result = JSONObject.parseObject(str, reference)
                                if (result.code == Constant.FAIL_CODE) {
                                    addressModel.onFail(result.msg, result.code)
                                } else {
                                    addressModel.onSuccess(result.data)
                                }
                            } else {
                                addressModel.onFail("请求失败", Constant.NOT_FOUND)
                            }
                        }
                    })
                } catch (e: Exception) {
                    addressModel.onFail(e.message, Constant.EXCEPTION)
                }
            } else {
                addressModel.onFail("参数错误+ " + JSONObject.toJSONString(address), Constant.EXCEPTION)
            }
        }
    }

    /** level代表要查询的级别(省/市/区)*/
    fun queryCityName(code: Int, level: Int
                      , queryCityName: OnRequestListener<String>) {
        doAsync {
            try {
                val area = Area()
                area.code = code
                area.level = level
                RequestUtil.doPost(Functions.QUERY_CITY_NAME, null, AreaParam(area), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        queryCityName.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            val str = response.body()?.string()
                            val reference = object : TypeReference<Result<String>>() {}
                            val result = JSONObject.parseObject(str, reference)
                            if (result.code == Constant.FAIL_CODE) {
                                queryCityName.onFail(result.msg, result.code)
                            } else {
                                queryCityName.onSuccess(result.data)
                            }
                        } else {
                            queryCityName.onFail("请求失败!", Constant.NOT_FOUND)
                        }
                    }
                })
            } catch (e: Exception) {
                queryCityName.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }

    /** 修改头像*/
    fun replaceHeader(header: File, accId: Int, onRequestListener: OnRequestListener<Account>) {
        doAsync {
            try {
                val account = Account()
                account.accId = accId
                RequestUtil.doPost(Functions.REPLACE_HEADER, header, AccountParam(null, account, null), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        onRequestListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            val str = response.body()!!.string()
                            LogUtil.e("replaceHeader", str)
                            val reference = object : TypeReference<Result<Account>>() {}
                            val result = JSONObject.parseObject(str, reference)
                            if (result.code == Constant.OK_CODE) {
                                onRequestListener.onSuccess(result.data)
                            } else {
                                onRequestListener.onFail(result.msg, result.code)
                            }
                        } else {
                            onRequestListener.onFail("网络错误", Constant.NOT_FOUND)
                        }
                    }

                })
            } catch (e: Exception) {
                onRequestListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }

    /** 修改信息*/
    fun modifyInformation(informationParam: InformationParam
            /** 要修改什么就填入什么*/
                          , onRequestListener: OnRequestListener<InformationParam>) {
        doAsync {
            try {
                RequestUtil.doPost(Functions.UPDATE_USER_INFO, null, informationParam, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        e?.printStackTrace()
                        onRequestListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            try {
                                val reference = object : TypeReference<Result<InformationParam>>() {}
                                val resBody = response.body()?.string()!!
                                val result = JSONObject.parseObject(resBody, reference)
                                if (result.code == Constant.OK_CODE) {
                                    onRequestListener.onSuccess(result.data)
                                } else {
                                    onRequestListener.onFail(result.msg, result.code)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                onRequestListener.onFail(e.message, Constant.EXCEPTION)
                            }
                        } else {
                            onRequestListener.onFail("网络请求错误", Constant.NOT_FOUND)
                        }
                    }

                })
            } catch (e: Exception) {
                onRequestListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }

    /** 查询信息*/
    fun queryUserInfo(onRequestListener: OnRequestListener<User>) {
        doAsync {
            try {
                RequestUtil.doPost(Functions.QUERY_USER_INFO, null, BaseParameter(), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        e?.printStackTrace()
                        onRequestListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            try {
                                val reference = object : TypeReference<Result<User>>() {}
                                val resBody = response.body()?.string()!!
                                val result = JSONObject.parseObject(resBody, reference)
                                if (result.code == Constant.OK_CODE) {
                                    onRequestListener.onSuccess(result.data)
                                } else {
                                    onRequestListener.onFail(result.msg, result.code)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                onRequestListener.onFail(e.message, Constant.EXCEPTION)
                            }
                        } else {
                            onRequestListener.onFail("网络请求错误", Constant.NOT_FOUND)
                        }
                        response?.close()
                    }
                })
            } catch (e: Exception) {
                onRequestListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }

    fun pushProblem(problem: Problem, onRequestListener: OnRequestListener<Problem>) {
        doAsync {
            try {
                RequestUtil.doPost(Functions.PUSH_PROBLEM, null, ProblemParam(problem), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        e?.printStackTrace()
                        onRequestListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            try {
                                val reference = object : TypeReference<Result<Problem>>() {}
                                val resBody = response.body()?.string()!!
                                val result = JSONObject.parseObject(resBody, reference)
                                if (result.code == Constant.OK_CODE) {
                                    onRequestListener.onSuccess(problem)
                                } else {
                                    onRequestListener.onFail(result.msg, result.code)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                onRequestListener.onFail(e.message, Constant.EXCEPTION)
                            }
                        } else {
                            onRequestListener.onFail("网络请求错误", Constant.NOT_FOUND)
                        }
                        response?.close()
                    }

                })

            } catch (e: Exception) {
                onRequestListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }

    /** 余额查询 */
    fun getBalance(onRequestListener: OnRequestListener<Int>) {
        doAsync {
            try {
                if (App.account == null) {
                    throw Exception("尚未登陆")
                }
                RequestUtil.doPost(Functions.GET_BALANCE, null, BaseParameter(), object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        e?.printStackTrace()
                        onRequestListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            try {
                                val reference = object : TypeReference<Result<Int>>() {}
                                val resBody = response.body()?.string()!!
                                val result = JSONObject.parseObject(resBody, reference)
                                if (result.code == Constant.OK_CODE) {
                                    onRequestListener.onSuccess(result.data)
                                } else {
                                    onRequestListener.onFail(result.msg, result.code)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                onRequestListener.onFail(e.message, Constant.EXCEPTION)
                            }
                        } else {
                            onRequestListener.onFail("网络请求错误", Constant.NOT_FOUND)
                        }
                        response?.close()
                    }

                })

            } catch (e: Exception) {
                onRequestListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }

    /**
     * 检查手机号是否已经注册过
     * */
    fun isPhoneRegistered(phone: String, onRequestListener: OnRequestListener<Boolean>) {
        doAsync {
            try {
                val json = JSONObject()
                json["phone"] = phone
                RequestUtil.doPost(Functions.VALIDATE_PHONE, null, json, object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        e?.printStackTrace()
                        onRequestListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            try {
                                val reference = object : TypeReference<Result<Boolean>>() {}
                                val resBody = response.body()?.string()!!
                                val result = JSONObject.parseObject(resBody, reference)
                                if (result.code == Constant.OK_CODE) {
                                    onRequestListener.onSuccess(result.data)
                                } else {
                                    onRequestListener.onFail(result.msg, result.code)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                onRequestListener.onFail(e.message, Constant.EXCEPTION)
                            }
                        } else {
                            onRequestListener.onFail("网络请求错误", Constant.NOT_FOUND)
                        }
                        response?.close()
                    }
                })
            } catch (e: Exception) {
                onRequestListener.onFail(e.message, Constant.EXCEPTION)
            }
        }
    }


    /**
     *
     * @param param phone - type
     * 不出错的话 返回 smsId
     */
    fun sendCode(param : CodeParam, onRequestListener: OnRequestListener<Int>){
        doAsync {
            try{
                RequestUtil.doPost(Functions.SENDCODE,null,param,object : Callback{
                    override fun onFailure(call: Call?, e: IOException?) {
                        onRequestListener.onFail(e?.message, Constant.EXCEPTION)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response?.isSuccessful == true) {
                            try {
                                val reference = object : TypeReference<Result<Int>>() {}
                                val resBody = response.body()?.string()!!
                                val result = JSONObject.parseObject(resBody, reference)
                                if (result.code == Constant.OK_CODE) {
                                    onRequestListener.onSuccess(result.data)
                                } else {
                                    onRequestListener.onFail(result.msg, result.code)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                onRequestListener.onFail(e.message, Constant.EXCEPTION)
                            }
                        } else {
                            onRequestListener.onFail("网络请求错误", Constant.NOT_FOUND)
                        }
                        response?.close()
                    }

                })
            }catch (e:Exception){
                onRequestListener.onFail(e.message,Constant.EXCEPTION)
            }
        }
    }

    /** smsId - phone - new password md5ed */
    fun modifyPassword(accountParam: AccountParam, onRequestListener: OnRequestListener<Account>){
        try{
            RequestUtil.doPost(Functions.FIND_PSW,null,accountParam,object:Callback{
                override fun onFailure(call: Call?, e: IOException?) {
                    onRequestListener.onFail(e?.message, Constant.EXCEPTION)
                }

                override fun onResponse(call: Call?, response: Response?) {
                    if (response?.isSuccessful == true) {
                        try {
                            val reference = object : TypeReference<Result<Account>>() {}
                            val resBody = response.body()?.string()!!
                            val result = JSONObject.parseObject(resBody, reference)
                            if (result.code == Constant.OK_CODE) {
                                onRequestListener.onSuccess(result.data)
                            } else {
                                onRequestListener.onFail(result.msg, result.code)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            onRequestListener.onFail(e.message, Constant.EXCEPTION)
                        }
                    } else {
                        onRequestListener.onFail("网络请求错误", Constant.NOT_FOUND)
                    }
                    response?.close()
                }

            })

        }catch (e:Exception){
            onRequestListener.onFail(e.message,Constant.EXCEPTION)
        }
    }
}
