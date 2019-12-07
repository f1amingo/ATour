package com.sanwei.sanwei4a.util

import com.alibaba.fastjson.JSON
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.entity.po.Account
import java.io.*

object FileHelper {

    private val TAG = this.javaClass.simpleName
    private val mAccountInfoPath: String = App.mContext.filesDir.absolutePath.toString() + "/account.json"

    fun saveAccountInfo(acc: Account) {
        val jsonString = JSON.toJSONString(acc)
        val bufferWriter = BufferedWriter(FileWriter(mAccountInfoPath))
        bufferWriter.write(jsonString)
        bufferWriter.flush()
        bufferWriter.close()
        LogUtil.e(TAG, "用户信息保存成功：$mAccountInfoPath")
    }

    fun fetchAccountInfo(): Account? {
        val file = File(mAccountInfoPath)
        if (!file.exists()) return null
        val bufferedReader = BufferedReader(FileReader(file))
        val builder = StringBuilder()
        var line: String? = bufferedReader.readLine()
        while (line != null) {
            builder.append(line)
            line = bufferedReader.readLine()
        }
        LogUtil.e(TAG, "取用户信息保存成功：\n$builder")
        return JSON.parseObject(builder.toString(), Account::class.java)
    }

    fun clearAccountInfo() {
        val file = File(mAccountInfoPath)
        if (file.exists()) file.delete()
    }

}