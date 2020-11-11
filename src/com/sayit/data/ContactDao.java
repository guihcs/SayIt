package com.sayit.data;

import java.util.*;
import java.util.function.Consumer;

public class ContactDao {

    private Contact userProfile;
    private final Map<Long, Contact> contactMap;
    private final Map<Long, MessageHistory> messageMap;
    private final List<Consumer<Contact>> userProfileChangedListeners;
    private final List<Consumer<Contact>> findContactChangedListener;
    private List<Contact> findContacts;

    public ContactDao() {
        contactMap = new HashMap<>();
        messageMap = new HashMap<>();
        userProfileChangedListeners = new ArrayList<>();
        findContactChangedListener = new ArrayList<>();
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
        if(!messageMap.containsKey(id)) {
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

    public List<Contact> getContactList() {
        return new ArrayList<>(contactMap.values());
    }

    public void setUserProfile(Contact userProfile) {
        userProfileChangedListeners.forEach(c -> c.accept(userProfile));
        this.userProfile = userProfile;
    }

    public void addUserProfileChangedListener(Consumer<Contact> contactChanged){
        userProfileChangedListeners.add(contactChanged);
    }

    public void addFindContactChangedListener(Consumer<Contact> contactChanged){
        findContactChangedListener.add(contactChanged);
    }


    public void startFindContacts(){
        findContacts = new ArrayList<>();
    }

    public void addFindContact(Contact c){
        findContacts.add(c);
        for (Consumer<Contact> contactConsumer : findContactChangedListener) {
            contactConsumer.accept(c);
        }
    }

    public void endFindContacts(){
        findContacts = null;
    }



}
