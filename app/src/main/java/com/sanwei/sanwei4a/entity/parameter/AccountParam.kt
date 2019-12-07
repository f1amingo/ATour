package com.sanwei.sanwei4a.entity.parameter

import com.sanwei.sanwei4a.entity.po.Account

data class AccountParam(var smsId:Int?,var account: Account, val valiCode:String?): BaseParameter()