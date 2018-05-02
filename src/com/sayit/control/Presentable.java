package com.sayit.control;

import com.sayit.data.Contact;
import com.sayit.data.Message;

import java.util.List;

public interface Presentable {

    Contact getContactInfo(int id);

    List<Message> getMessageList(int id);

    void openAddScene();

    void openEditProfileScene();

    void requestContactList(String name);

    void sendMessage(String message);
}
