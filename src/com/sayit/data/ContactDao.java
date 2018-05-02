package com.sayit.data;

import java.util.HashMap;
import java.util.List;

public class ContactDao {

    private Contact userProfile;
    private List<Contact> contactList;
    private HashMap<Integer, List<Message>> messageMap;

    public void addContact(Contact contact) {

    }

    public void addMessage(int id, Message message) {
    }

    public Contact getContact(int id) {
        return null;
    }

    public List<Message> getMessageList(int id) {
        return null;
    }

    public void editContact(int id, String newName) {

    }

    public String getSaveMessagesData(int id) {
        return null;
    }

    public String getSaveContactsData() {
        return null;
    }

    public String getSaveHistoryData() {
        return null;
    }
}
