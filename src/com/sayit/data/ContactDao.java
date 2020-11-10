package com.sayit.data;

import java.util.*;

public class ContactDao {

    private Contact userProfile;
    private final Map<Long, Contact> contactMap;
    private final Map<Long, MessageHistory> messageMap;
    private List<ContactChanged> userProfileChangedListeners;

    public ContactDao() {
        contactMap = new HashMap<>();
        messageMap = new HashMap<>();
        userProfileChangedListeners = new ArrayList<>();
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
        userProfileChangedListeners.forEach(c -> c.changed(userProfile));
        this.userProfile = userProfile;
    }

    public void addUserProfileChangedListener(ContactChanged contactChanged){
        userProfileChangedListeners.add(contactChanged);
    }

}
