package com.sanwei.sanwei4a.util

import android.util.Log

/**
 * Created by VincentLaf on 2018/1/20.
 * Log工具类
 */
class LogUtil {

    companion object {

        val VERBOSE = 1
        val DEBUG = 2
        val INFO = 3
        val WARN = 4
        val ERROR = 5
        //什么也不显示
        val NOTHING = 6
        //当前级别
        val level = VERBOSE

        fun v(tag: String, msg: String) {
            if (level <= VERBOSE) {
                Log.v(tag, msg)
            }
        }

        fun d(tag: String, msg: String) {
            if (level <= DEBUG) {
                Log.d(tag, msg)
            }
        }

        fun i(tag: String, msg: String) {
            if (level <= INFO) {
                Log.i(tag, msg)
            }
        }

        fun w(tag: String, msg: String) {
            if (level <= WARN) {
                Log.w(tag, msg)
            }
        }

        fun e(tag: String, msg: String) {
            if (level <= ERROR) {
                Log.e(tag, msg)
            }
        }
    }
}