package com.sanwei.sanwei4a.util

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.entity.po.Account
import java.io.*
import java.lang.StringBuilder

object JsonUtil {

    private val TAG = this.javaClass.simpleName

    fun loadJson(context: Context, filename: String): JSONArray {
        val inputStream = context.assets.open(filename)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line = bufferedReader.readLine()
        val strBuilder = StringBuilder()
        while (line != null) {
            strBuilder.append(line)
            line = bufferedReader.readLine()
        }
        val jsonArray = JSON.parseArray(strBuilder.toString())
        if (jsonArray.size != 6) {
            LogUtil.e(TAG, "${filename}文件应该有6条数据")
        }
        return jsonArray
    }

}