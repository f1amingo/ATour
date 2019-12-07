package com.sanwei.sanwei4a.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import okhttp3.*
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.entity.parameter.BaseParameter
import java.io.File
import kotlin.concurrent.timer

/**
 * 请求工具
 */
object RequestUtil {

    //    const val IP = "localhost"
//    const val IP = "192.168.43.14"
    const val IP = "47.93.198.51"
    // const val REQUEST_ADDR="10.0.0.2"
    private const val REQUEST_ADDR = "http://$IP/"

    private val MEDIA_TYPE_PNG = MediaType.parse("image/png")!!
    private fun getUrl(func: String): String {
        return "$REQUEST_ADDR$func"
    }

    fun getHeadPicAddr(userId: Int, timestamp: String): String {
        return "$REQUEST_ADDR/user/$userId/$timestamp.png"
    }

    /**
     * 获取书籍图片地址
     * @param picName 照片名
     */
    fun getBookPic(picName: String): String {
        return "$REQUEST_ADDR/images/$picName"
    }

    /**
     * post方法
     * @param func 方法名
     * @param headers 请求头
     * @param parameter 参数 继承于 BaseParameter
     * @param callback 回调函数
     */
    fun doPost(func: String, headers: Map<String, String>?, parameter: BaseParameter, callback: Callback) {
        val client = OkHttpClient.Builder().retryOnConnectionFailure(true).build()
        val paramterType = MediaType.parse(ParameterTypes.JSON_UTF_8)
        val jsonString = JSONObject.toJSONString(parameter)
        val requestBody = RequestBody.create(paramterType, jsonString)
        val request = Request.Builder()
                .headers(parseHeaders(headers, parameter))
                .post(requestBody)
                .url(getUrl(func))
                .build()
        client.newCall(request).enqueue(callback)

        LogUtil.e("RequestUtil", "请求的地址：${getUrl(func)}")
        LogUtil.e("RequestUtil", jsonString)
    }

    /**
     * post方法
     * @param func 方法名
     * @param headers 请求头
     * @param json json参数
     * @param callback 回调函数
     */
    fun doPost(func: String, headers: Map<String, String>?, json: JSONObject, callback: Callback) {
        val client = OkHttpClient.Builder().retryOnConnectionFailure(true).build()
        val paramterType = MediaType.parse(ParameterTypes.JSON_UTF_8)
        val requestBody = RequestBody.create(paramterType, json.toJSONString())
        val request = Request.Builder()
                .post(requestBody)
                .url(getUrl(func))
                .build()
        client.newCall(request).enqueue(callback)
        LogUtil.e("RequestUtil", "请求的地址：${getUrl(func)}")
        LogUtil.e("RequestUtil", json.toJSONString())
    }

    /**
     * 文件上传请求
     */
    fun doPost(func: String, front: File, inpic: File, end: File, parameter: BaseParameter, callback: Callback) {
        val client = OkHttpClient()
        val url = getUrl(func)
        val requestBuilder = MultipartBody.Builder().setType(MediaType.parse("multipart/form-data")!!)
        requestBuilder.addFormDataPart("frontPic", null, RequestBody.create(MEDIA_TYPE_PNG, front))
        requestBuilder.addFormDataPart("inPic", null, RequestBody.create(MEDIA_TYPE_PNG, inpic))
        requestBuilder.addFormDataPart("endPic", null, RequestBody.create(MEDIA_TYPE_PNG, end))
        requestBuilder.addFormDataPart("bookParam", JSONObject.toJSONString(parameter))
        val requestBody = requestBuilder.build()
        LogUtil.e("doPost", requestBody.toString())
        val request = Request.Builder().url(url).post(requestBody).headers(parseHeaders(null, parameter)).build()
        client.newCall(request).enqueue(callback)
    }

    fun doPost(func: String, header: File, parameter: BaseParameter, callback: Callback) {
        val client = OkHttpClient()
        val url = getUrl(func)
        val requestBuilder = MultipartBody.Builder().setType(MediaType.parse("multipart/form-data")!!)
        requestBuilder.addFormDataPart("header", null, RequestBody.create(MEDIA_TYPE_PNG, header))
        requestBuilder.addFormDataPart("user", JSONObject.toJSONString(parameter))
        val requestBody = requestBuilder.build()
        val request = Request.Builder().url(url).post(requestBody).headers(parseHeaders(null, parameter)).build()
        client.newCall(request).enqueue(callback)
    }

    private fun parseHeaders(headers: Map<String, String>?, param: BaseParameter): Headers {
        val builder = Headers.Builder()
        if (App.account != null) {
            builder.add("token", App.account!!.token!!)
        }
        builder.add("User-Agent", App.USER_AGENT)
        builder.add("time", param.time)
//        val time = param.time
//        val md5 = EncryptUtil.md5(time)!!
//        val tip = EncryptUtil.aesEncrypt(time, md5.substring(0, 16)).trim('\n')
//        //加密
//        builder.add("tip", tip)
        if (headers != null) {
            for ((k, v) in headers) {
                builder.add(k, v)
            }
        }
        return builder.build()
    }

    /**
     * 请求主体数据类型
     */
    private object ParameterTypes {
        const val JSON_UTF_8 = "application/json;charset=UTF-8"
    }
}

/**
 * 所有服务入口名
 */
object Functions {
    /** 登陆*/
    const val LOGIN = "account/login"
    /** 注册*/
    const val REGISTER = "account/register"
    /** 找回密码*/
    const val FIND_PSW = "account/findPasswordBack"
    /** 发送验证码*/
    const val SENDCODE = "account/sendCode"

    /** 查询余额*/
    const val GET_BALANCE = "account/getBalance"
    /** 验证电话号码唯一性*/
    const val VALIDATE_PHONE = "account/validatePhone"
    /** 更换头像*/
    const val REPLACE_HEADER = "account/replaceHeader"
    /** 修改用户信息*/
    const val UPDATE_USER_INFO = "account/update"
    /** 查询用户信息*/
    const val QUERY_USER_INFO = "account/queryUserInformation"
    /** 问题反馈*/
    const val PUSH_PROBLEM = "account/pushProblem"
    /** 拉取首页图书数据*/
    const val DISCOVERY = "book/discovery"
    /** 查看书籍详情*/
    const val QUERY_BOOK_DETAIL = "book/queryBookDetail"
    const val QUERY_BOOK_BY_KEY = "book/queryByKeyWord"
    const val QUERY_BOOK_BY_ISBN = "book/queryByISBN"
    const val QUERY_MINE_BOOKS = "book/queryMineBooks"
    /** 移除已发布书籍*/
    const val REMOVE_PUBLISHED_BOOK = "book/removePublishedBook"

    /** 查询地区*/
    const val CITY_QUERY_AREA = "city/queryArea"
    /** 跟据代码(code)查询 省/城市/区县名字*/
    const val QUERY_CITY_NAME = "city/queryProOrCityName"
    /** 查询书籍官方消息，即标准信息*/
    const val SYSBOOK_QUERY = "book/querySysBook"
    /** 上传图书*/
    const val UPLOAD_BOOK = "book/upload"

    /** 用户收藏或者取消收藏图书*/
    const val DO_FAVORITE = "book/doFavo"
    /** 添加用户收货地址*/
    const val ADD_ADDRESS = "account/addAddress"
    /** 查询所有收货地址*/
    const val GET_ALL_ADDRESS = "account/queryAllAddress"
    /** 提交并创建订单*/
    const val CREATE_ORDER = "order/create"
    /** 订单支付*/
    const val PAY = "order/pay"

    const val QUERY_OFFLINE_MESSAGES = "chat/queryOffLineMessages"
    const val QUERY_READ_MESSAGES = "chat/queryReadMessages"
}