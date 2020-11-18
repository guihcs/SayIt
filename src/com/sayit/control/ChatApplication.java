package com.sayit.control;

import com.sayit.data.contact.Contact;
import com.sayit.data.contact.ContactDao;
import com.sayit.data.contact.ContactManager;
import com.sayit.data.message.Message;
import com.sayit.di.Injector;
import com.sayit.network.request.Request;
import com.sayit.network.discovery.DiscoveryData;
import com.sayit.ui.navigator.Navigator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Date;

public class ChatApplication extends Application {

    private ProtocolManager mediator;
    private ContactManager contactDao;
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Injector.registerProvider(Stage.class, primaryStage);
        Navigator.loadFrom("routes/routes.json");

        mediator = new RequestMediator();
        contactDao = new ContactDao();
        Injector.registerProvider(ContactManager.class, contactDao);
        Injector.registerProvider(ProtocolManager.class, mediator);

        mediator.addRequestListener(this::processRequest);
        mediator.addDiscoveryListener(this::contactDiscovery);
        mediator.start();
        Navigator.of(primaryStage).pushNamed("/startScene");
    }


    public void requestAddContact(Request request) {
        Contact contact = new Contact().fromRequest();
        Navigator.of(stage).pushNamedModal(
                "/contactRequest",
                400,
                300,
                contact,
                r -> {
                    if ((Boolean) r) {
                        mediator.sendContactAddResponse(contact.toRequest());
                    }
                }
        );
    }

    public void contactDiscovery(DiscoveryData discoveryData) {
        mediator.sendContactDiscoveryResponse(contactDao.getUserProfile().toRequest());
    }

    public void addContactResult(Request request) {
        Contact contact = new Contact().fromRequest();
        contactDao.addContact(contact);
    }

    public void addContactToFindResults(Request request) {
        Contact contact = new Contact().fromRequest();
        contactDao.addFindContact(contact);
    }

    public void addMessage(Request request) {
        Message message = new Message().fromRequest();
        message.setReceiverProfile(contactDao.getContact(ContactDao.parseAddress(message.getSenderAddress())));
        message.setMessageDate(new Date());
        contactDao.addMessage(ContactDao.parseAddress(message.getSenderAddress()), message);
    }


    private void processRequest(Request request) {
        switch (request.getProtocol()) {
            case MESSAGE:
                addMessage(request);
                break;
            case DISCOVERY_RESPONSE:
                addContactToFindResults(request);
                break;
            case CONTACT_ADD_REQUEST:
                requestAddContact(request);
                break;
            case CONTACT_ADD_RESPONSE:
                addContactResult(request);
                break;
            case SEND_ARCHIVE:
                break;
        }
    }

    @Override
    public void stop() {
        try {
            super.stop();
            mediator.stopServices();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
