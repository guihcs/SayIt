package com.sayit.control;

import com.sayit.data.Contact;
import com.sayit.data.ContactDao;
import com.sayit.data.Message;
import com.sayit.di.Autowired;
import com.sayit.network.Request;
import javafx.application.Platform;

import java.util.Date;
import java.util.LinkedList;

public class ChatManager {

    private final LinkedList<Contact> requestList = new LinkedList<>();
    private volatile boolean processingRequests = false;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private ChatApplication chatApplication;
    @Autowired
    private RequestMediator requestMediator;

    public ChatManager(){
        requestMediator.addListener(this::processRequest);
    }

    public void addContact(Contact contact) {
        requestList.add(contact);

        if(!processingRequests) {
            Platform.runLater(() -> {
                processingRequests = true;
                while (processingRequests && requestList.size() > 0) {
//                    chatApplication.openContactRequest(requestList.pollFirst());
                }
                processingRequests = false;
            });
        }
    }

    public void addContactResult(Contact contact) {
        contactDao.addContact(contact);
    }

    public void addContactRequest(Contact contact) {

//        chatApplication.getFindContactController().addContact(contact);
    }

    public void addMessage(Message message) {
        message.setReceiverProfile(contactDao.getContact(ContactDao.parseAddress(message.getSenderAddress())));
        message.setMessageDate(new Date());
        contactDao.addMessage(ContactDao.parseAddress(message.getSenderAddress()), message);
    }


    private void processRequest(Request request){
        switch (request.getProtocol()){
            case MESSAGE:
                Message message = new Message().fromRequest(request);
                addMessage(message);
                break;
            case DISCOVERY_RESPONSE:
                Contact contact = new Contact().fromRequest(request);
                addContactRequest(contact);
                break;
            case CONTACT_ADD_REQUEST:
                contact = new Contact().fromRequest(request);
                addContact(contact);
                break;
            case CONTACT_ADD_RESPONSE:
                contact = new Contact().fromRequest(request);
                addContactResult(contact);
                break;
            case SEND_ARCHIVE:
                break;
        }
    }

    public void stopContactRequest(){
        requestMediator.stopDiscover();
    }
}
