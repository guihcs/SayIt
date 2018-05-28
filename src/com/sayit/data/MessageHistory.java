package com.sayit.data;

import java.util.Date;
import java.util.List;

public class MessageHistory {

    private Contact contact;
    private List<Message> messageList;
    private String lastMessage;
    private String lastMessageDate;

    public MessageHistory(Contact contact) {
        //TODO Djan MessageHistory
        //iniciar a messageList com um arraylist
        this.contact = contact;
    }

    /**
     * Adiciona uma mensagem à lista de mensagens.
     *
     * @param message
     */
    public void addMessage(Message message) {
        //TODO Djan addMessage
        //adicionar a mensagem em last message
        //adicionar a data em lastmessage date
        messageList.add(message);
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

    public Date getLastMessageDate() {
        return null;
    }
}
