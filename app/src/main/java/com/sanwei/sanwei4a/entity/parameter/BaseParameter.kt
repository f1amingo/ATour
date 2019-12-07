package com.sanwei.sanwei4a.entity.parameter

import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.util.StringUtils
import java.text.SimpleDateFormat
import java.util.*


open class BaseParameter {
    var time :String = parseCurrentTime()
    var accId:Int? = App.account?.accId


    companion object {
        fun parseCurrentTime():String{
            val simpleDateFormat = StringUtils.timeFormator.get()
            return simpleDateFormat.format(Date())
        }
    }


    /*var IMEI :String?=null
    companion object {
        class Build(context: Context) {
            private val context = context
            @SuppressLint("MissingPermission", "SimpleDateFormat")
            @RequiresApi(android.os.Build.VERSION_CODES.O)
            fun build():BaseParameter{
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                var base = BaseParameter()
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                base.time = simpleDateFormat.format(Date())
                base.IMEI = telephonyManager.imei
                return base
            }
        }
    }
    init {
        this.time = parseCurrentTime()
    }*/
}