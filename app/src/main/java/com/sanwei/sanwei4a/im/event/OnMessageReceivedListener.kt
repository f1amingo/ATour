package com.sanwei.sanwei4a.im.event

import com.sanwei.sanwei4a.im.protocal.MainContent.Message

interface OnMessageReceivedListener {
    fun onGroupMessageReceived(message: Message, fromId: Int, groupId:Int)

    fun onP2PMessageReceived(message: Message, fromId:Int)

    fun onSystemMessageReceived(message: Message)
}