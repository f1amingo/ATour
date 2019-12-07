package com.sanwei.sanwei4a.util

import android.annotation.SuppressLint
import java.io.*
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object StringUtils {

    val timeFormator = object : ThreadLocal<SimpleDateFormat>(){
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault())
        }
    }

    fun isEmpty(text: String?): Boolean {
        return text.isNullOrEmpty() || text?.trim() == ""
    }

    /**
     * 电话号码 11位
     */
    fun isLegalPhoneNum(text: String?): Boolean {
        return if (!isEmpty(text)) {
            val regexStr = "^1[3|4|5|7|8][0-9]{9}\$"
            val patten = Pattern.compile(regexStr)
            patten.matcher(text).find()
        } else
            false
    }

    /**
     * 包含只能是数字大小写字母 8-16位
     */
    fun checkPassword(password: String): Boolean {
        return if (!isEmpty(password)) {
            val patten = Pattern.compile("[0-9a-zA-Z]{8,16}")
            patten.matcher(password).find()
        } else {
            false
        }
    }

    /**
     * 包含数字大小写字母汉字 4-16位
     */
    fun checkUsername(username: String): Boolean {
        return if (!isEmpty(username)) {
            val patten = Pattern.compile("^[a-zA-Z0-9\\\u4E00-\\\u9FA5_-]{4,16}\$")
            patten.matcher(username).find()
        } else {
            false
        }
    }

    fun img2String(file: File): String {
        val bufferReader = BufferedReader(InputStreamReader(FileInputStream(file), Charset.forName("ISO-8859-1")))
        val builder = StringBuilder()
        val chars = CharArray(100)
        var len = bufferReader.read(chars)
        while (len != -1) {
            builder.append(chars)
            len = bufferReader.read()
        }
        return builder.toString()
    }
}