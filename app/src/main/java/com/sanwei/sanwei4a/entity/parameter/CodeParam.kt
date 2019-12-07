package com.sanwei.sanwei4a.entity.parameter

import com.sanwei.sanwei4a.entity.parameter.BaseParameter

class CodeParam : BaseParameter() {
    var userPhone: String? = null
    var type: Int = 0

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
}