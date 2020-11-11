package com.sayit.control;

import com.sayit.data.*;
import com.sayit.di.Injector;
import com.sayit.network.Request;
import com.sayit.network.discovery.DiscoveryData;
import com.sayit.ui.navigator.Navigator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Date;

public class ChatApplication extends Application {

    //Layouts
    public static final String CONTACT_VIEW = "/layout/view/view_contact_cell.fxml";
    public static final String MESSAGE_VIEW = "/layout/view/view_message_cell.fxml";
    //Styles
    public static final String CONTACT_STYLE = "/stylesheet/style_contact.css";
    public static final String MESSAGE_STYLE = "/stylesheet/style_message.css";

    //Constants
    public static final int MAX_NAME_LENGTH = 20;
    private RequestMediator mediator;
    private ContactDao contactDao;
    private Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    public static String getStyleSheet(String path) {
        return ChatApplication.class.getResource(path).toExternalForm();
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Injector.registerProvider(Stage.class, primaryStage);
        Navigator.loadFrom("routes/routes.json");

        mediator = new RequestMediator();
        contactDao = new ContactDao();
        Injector.registerProvider(ContactDao.class, contactDao);
        Injector.registerProvider(RequestMediator.class, mediator);

        mediator.addRequestListener(this::processRequest);
        mediator.addDiscoveryListener(this::contactDiscovery);
        mediator.start();
        Navigator.of(primaryStage).pushNamed("/startScene");
    }



    public void requestAddContact(Request request) {
        Contact contact = new Contact().fromRequest(request);
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
        Contact contact = new Contact().fromRequest(request);
        contactDao.addContact(contact);
    }

    public void addContactToFindResults(Request request) {
        Contact contact = new Contact().fromRequest(request);
        contactDao.addFindContact(contact);
    }

    public void addMessage(Request request) {
        Message message = new Message().fromRequest(request);
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
