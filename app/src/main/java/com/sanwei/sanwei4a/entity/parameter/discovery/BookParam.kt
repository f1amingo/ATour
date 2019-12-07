package com.sanwei.sanwei4a.entity.parameter.discovery

import com.sanwei.sanwei4a.entity.parameter.BaseParameter
import com.sanwei.sanwei4a.entity.po.BusinessBook

class BookParam : BaseParameter(){
    var booId:Int=0
    var isbn:String?=null
    var bBook: BusinessBook?=null
}