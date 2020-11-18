package com.sayit.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageHistory {

    private final Contact contact;
    private final List<Message> messageList;
    private String lastMessage;
    private String lastDateText;
    private Date lastDateTime;

    public MessageHistory(Contact contact) {
        messageList = new ArrayList<>();
        this.contact = contact;
    }

    public void addMessage(Message message) {

        messageList.add(message);
        lastMessage = message.getTextContent();
        lastDateText = message.getFormattedTime();
        lastDateTime = message.getMessageDate();
    }


    public Contact getContact() {
        return contact;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastDateText() {

        return lastDateText;
    }

    public Date getLastDateTime() {
        return lastDateTime;
    }
}
