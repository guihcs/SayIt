package com.sayit.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactDao {

    private Contact userProfile;
    private List<Contact> contactList;
    private Map<Integer, List<Message>> messageMap;

    public ContactDao() {
        contactList = new ArrayList<>();
        messageMap = new HashMap<>();
    }

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

    public List<String> getMessagesData(int id) {
        return null;
    }

    public List<String> getContactsData() {
        return null;
    }

    public List<String> getHistoryData() {
        return null;
    }
}
