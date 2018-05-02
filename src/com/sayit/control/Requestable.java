package com.sayit.control;

import com.sayit.data.MessageType;

import java.net.InetAddress;

public interface Requestable {

    void sendMessage(InetAddress address, byte[] content, MessageType messageType);

    void requestContact(String name);

    void loadMessageList(int id);

    void stopServices();
}
