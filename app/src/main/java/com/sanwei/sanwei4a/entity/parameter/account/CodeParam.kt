package com.sanwei.sanwei4a.entity.parameter.account

import com.sanwei.sanwei4a.entity.parameter.BaseParameter

data class CodeParam(var phone:String, var type:Int) : BaseParameter()

interface CodeType {
    companion object {
        /** 注册 */
        val REGISTER = 1
        /** 登陆 */
        val LOGIN = 2
        /** 找回密码 */
        val FIND_PASSWD = 3
        /** 安全验证 */
        val SAFE = 4
    }
}