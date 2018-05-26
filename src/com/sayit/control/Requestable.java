package com.sayit.control;

import com.sayit.data.MessageType;

public interface Requestable {

    void sendMessage(String address, byte[] content, String fileName);

    void sendMessage(String address, String message);

    void requestContact(String name);

    void contactAdd(String ip, String userName, byte[] imageBytes);

    void loadMessageList(int id);

    void stopServices();
}
