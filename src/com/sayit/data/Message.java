package com.sayit.data;

import java.awt.TrayIcon.MessageType;
import java.util.Date;

public class Message {

    private Contact receiverProfile;
    private boolean sendByMe;
    private byte[] content;
    private Date messageDate;
    private MessageType type;

    public Message(Contact receiverProfile, boolean sendByMe, byte[] content, MessageType type) {
        this.receiverProfile = receiverProfile;
        this.sendByMe = sendByMe;
        this.content = content;
        this.type = type;
    }

    public Contact getReceiverProfile() {
        return receiverProfile;
    }

    public void setReceiverProfile(Contact receiverProfile) {
        this.receiverProfile = receiverProfile;
    }

    public boolean isSendByMe() {
        return sendByMe;
    }

    public void setSendByMe(boolean sendByMe) {
        this.sendByMe = sendByMe;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     * Retorna uma representação String do objeto.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Message{" + "receiverProfile=" + receiverProfile + ", sendByMe=" + sendByMe + ", content=" + content + ", messageDate=" + messageDate + ", type=" + type + '}';
    }

}