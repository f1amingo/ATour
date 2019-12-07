package com.sanwei.sanwei4a.im.event

import per.johnson.server.event.MessageQoSEvent
import per.johnson.server.protocal.ProtocalOuterClass
import java.util.ArrayList

class MessageQoSEventImpl:MessageQoSEvent{
    override fun messagesBeReceived(fp: String?) {
        //啥事儿不干
    }

    override fun messagesLost(msgs: ArrayList<ProtocalOuterClass.Protocal>?) {
        //msgs 为丢失的消息，应该考虑重新发送
    }
}