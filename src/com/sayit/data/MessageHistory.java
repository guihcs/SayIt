package com.sayit.data;

import java.util.ArrayList;
import java.util.List;

public class MessageHistory {

    private Contact contact;
    private List<Message> messageList;
    private String lastMessage;
    private String lastMessageDate;

    public MessageHistory(Contact contact) {
        messageList = new ArrayList<>();
        this.contact = contact;
    }

    /**
     * Adiciona uma mensagem à lista de mensagens.
     *
     * @param message
     */
    public void addMessage(Message message) {

        messageList.add(message);
        lastMessage = message.getTextContent();
        lastMessageDate = message.getFormattedTime();
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

    public String getLastMessageDate() {

        return lastMessageDate;
    }


}
