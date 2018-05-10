package com.sayit.control;

import com.sayit.data.Contact;
import com.sayit.data.Message;
import com.sayit.data.MessageHistory;

import java.util.List;

public interface Presentable {

    Contact getContactInfo(int id);

    List<Message> requestMessageList(int id);

    List<MessageHistory> getHistoryList();

    void openAddScene();

    void openEditProfileScene();

    void requestContactList(String name);

    void sendMessage(String message);
}
