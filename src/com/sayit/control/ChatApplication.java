package com.sayit.control;

import com.sayit.data.Contact;
import com.sayit.data.ContactDao;
import com.sayit.data.Message;
import com.sayit.data.MessageType;
import com.sayit.ui.ChatHomeScene;
import com.sayit.ui.ContactAddScene;
import com.sayit.ui.ProfileEditScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.util.List;

public class ChatApplication extends Application implements Presentable {

    private Stage primaryStage;
    private Contact currentContact;
    private ContactDao contactDao;
    private Requestable requestCallback;
    private ChatHomeScene chatHome;
    private ContactAddScene contactAddScene;
    private ProfileEditScene profileEditScene;

    public ChatApplication(Requestable requestable, ContactDao contactDao) {
        this.requestCallback = requestable;
        this.contactDao = contactDao;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void addMessage(int id, byte[] content, MessageType messageType) {

    }

    public void addContact(int id, byte[] img, String name, InetAddress address) {

    }

    public void openEditProfile(Contact contact) {

    }

    @Override
    public Contact getContactInfo(int id) {
        return null;
    }

    @Override
    public List<Message> getMessageList(int id) {
        return null;
    }

    @Override
    public void openAddScene() {

    }

    @Override
    public void openEditProfileScene() {

    }

    @Override
    public void requestContactList(String name) {

    }

    @Override
    public void sendMessage(String message) {

    }
}
