package com.sayit.data;


import com.sayit.network.Rebuildable;
import com.sayit.network.Request;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Rebuildable<Message> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private Contact receiverProfile;
    private String senderAddress;
    private String textContent;
    private boolean sendByMe;
    private byte[] content;
    private Date messageDate;
    private MessageType type;


    public Message() {
    }

    public Message(Contact receiverProfile, boolean sendByMe, String content, MessageType type) {
        this.receiverProfile = receiverProfile;
        this.sendByMe = sendByMe;
        this.textContent = content;
        this.type = type;
    }

    public Message(Contact receiverProfile, boolean sendByMe, byte[] content, MessageType type) {
        this.receiverProfile = receiverProfile;
        this.sendByMe = sendByMe;
        this.content = content;
        this.type = type;
    }

    public void setReceiverProfile(Contact receiverProfile) {
        this.receiverProfile = receiverProfile;
    }

    public boolean isSendByMe() {
        return sendByMe;
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

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    @Override
    public String toString() {
        return "Message{" + "receiverProfile=" + receiverProfile + ", sendByMe=" + sendByMe + ", content=" + textContent + ", messageDate=" + messageDate + ", type=" + type + '}';
    }

    public String getFormattedTime() {

        String time = "none";
        if (messageDate != null) time = dateFormat.format(messageDate.getTime());

        return time;

    }


    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }


    @Override
    public Message fromRequest(Request request) {
        return this;
    }

    @Override
    public Request toRequest() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteStream);

        try {
            dataOutputStream.writeInt(MessageType.TEXT.getValue());
            dataOutputStream.writeUTF(getTextContent());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Request(byteStream.toByteArray(),
                getSenderAddress());
    }
}