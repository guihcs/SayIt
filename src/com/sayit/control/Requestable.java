package com.sayit.control;

import com.sayit.data.MessageType;

public interface Requestable {

    void sendMessage(String address, byte[] content, MessageType messageType);

    void sendMessage(String address, String message);

    void requestContact(String name);

    void loadMessageList(int id);

    void stopServices();
}
