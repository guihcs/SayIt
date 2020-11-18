package com.sayit.data.contact;

import com.sayit.data.message.Message;
import com.sayit.data.message.MessageHistory;

import java.util.List;
import java.util.function.Consumer;

public interface ContactManager {
    Contact getUserProfile();

    void setUserProfile(Contact c);

    void addContact(Contact contact);

    void addFindContact(Contact contact);

    Contact getContact(long parseAddress);

    void addMessage(long parseAddress, Message message);

    void addUserProfileChangedListener(Consumer<Contact> contactConsumer);

    void addFindContactChangedListener(Consumer<Contact> contactConsumer);

    List<MessageHistory> getHistoryList();

    List<Contact> getContactList();

    List<Message> getMessageList(long id);

    void endFindContacts();

    void startFindContacts();
}
