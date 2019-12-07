package com.sanwei.sanwei4a.entity.po;

import java.util.Date;

/**
 * 数据库消息实体
 */
public class Message {
    private Integer messageId;

    private String messageFrom;

    private String messageTo;

    private String messageFp;
    /* 该消息是否已读*/
    private Boolean messageRead;

    private Date messageTime;

    private Integer messageTypeu;
    /* 消息内容*/
    private byte[] messageContent;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    public String getMessageFp() {
        return messageFp;
    }

    public void setMessageFp(String messageFp) {
        this.messageFp = messageFp;
    }

    public Boolean getMessageRead() {
        return messageRead;
    }

    public void setMessageRead(Boolean messageRead) {
        this.messageRead = messageRead;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public Integer getMessageTypeu() {
        return messageTypeu;
    }

    public void setMessageTypeu(Integer messageTypeu) {
        this.messageTypeu = messageTypeu;
    }

    public byte[] getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(byte[] messageContent) {
        this.messageContent = messageContent;
    }
}
