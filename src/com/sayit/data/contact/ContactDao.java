package com.sayit.data.contact;

import com.sayit.data.message.Message;
import com.sayit.data.message.MessageHistory;
import com.sayit.data.message.MessageType;

import java.util.*;
import java.util.function.Consumer;

public class ContactDao implements ContactManager {

    private final Map<Long, Contact> contactMap;
    private final Map<Long, MessageHistory> messageMap;
    private final List<Consumer<Contact>> userProfileChangedListeners;
    private final List<Consumer<Contact>> findContactChangedListener;
    private Contact userProfile;
    private List<Contact> findContacts;

    public ContactDao() {
        contactMap = new HashMap<>();
        messageMap = new HashMap<>();
        userProfileChangedListeners = new ArrayList<>();
        findContactChangedListener = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String id = i + "." + i;
            contactMap.put(parseAddress(id), new Contact("" + i, null, id));
        }

        for (int i = 0; i < 10; i++) {

            String id = i + "." + i;

            messageMap.put(parseAddress(id), new MessageHistory(contactMap.get(parseAddress(id))));

            for (int j = 0; j < 10; j++) {

                int value = (int) (Math.random() * 10);
                String randomID = value + "." + value;
                Message message = new Message(contactMap.get(parseAddress(randomID)), Math.random() > 0.5, "uahscuasc" + j, MessageType.TEXT);
                message.setMessageDate(new Date());
                messageMap.get(parseAddress(id))
                        .addMessage(message);
            }
        }
    }

    public static long parseAddress(String address) {
        String[] splitAddress = address.split("\\.");
        StringBuilder resultAddress = new StringBuilder();
        for (String ipToken : splitAddress) {
            resultAddress.append(ipToken);
        }
        return Long.parseLong(resultAddress.toString());
    }


    public void addContact(Contact contact) {
        contactMap.put(contact.getId(), contact);
        messageMap.put(contact.getId(), new MessageHistory(contact));
    }


    public void addMessage(long id, Message message) {
        if (!messageMap.containsKey(id)) {
            messageMap.put(id, new MessageHistory(getContact(id)));
        }
        messageMap.get(id).addMessage(message);
    }


    public Contact getContact(long id) {
        return contactMap.get(id);
    }


    public List<Message> getMessageList(long id) {
        return messageMap.get(id).getMessageList();
    }


    public List<MessageHistory> getHistoryList() {
        Collection<MessageHistory> list = messageMap.values();
        var historyList = new ArrayList<>(list);
        historyList.sort(Comparator.comparing(MessageHistory::getLastDateTime).reversed());
        return historyList;
    }

    public Contact getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Contact userProfile) {
        userProfileChangedListeners.forEach(c -> c.accept(userProfile));
        this.userProfile = userProfile;
    }

    public List<Contact> getContactList() {
        return new ArrayList<>(contactMap.values());
    }

    public void addUserProfileChangedListener(Consumer<Contact> contactChanged) {
        userProfileChangedListeners.add(contactChanged);
    }

    public void addFindContactChangedListener(Consumer<Contact> contactChanged) {
        findContactChangedListener.add(contactChanged);
    }


    public void startFindContacts() {
        findContacts = new ArrayList<>();
    }

    public void addFindContact(Contact c) {
        findContacts.add(c);
        for (Consumer<Contact> contactConsumer : findContactChangedListener) {
            contactConsumer.accept(c);
        }
    }

    public void endFindContacts() {
        findContacts = null;
    }


}
