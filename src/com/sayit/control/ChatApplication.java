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

    private volatile static ChatApplication instance;

    private Stage primaryStage;
    private Contact currentContact;
    private ContactDao contactDao;
    private Requestable requestCallback;
    private ChatHomeScene chatHome;
    private ContactAddScene contactAddScene;
    private ProfileEditScene profileEditScene;

    private ChatApplication() {
        ChatApplication.instance = this;
    }

    public synchronized static ChatApplication launchApplication(String[] args, Requestable requestable, ContactDao contactDao) {
        if(ChatApplication.instance == null) {

            new Thread(() -> Application.launch(ChatApplication.class, args));
            while (ChatApplication.instance == null) Thread.onSpinWait();
        }
        return ChatApplication.instance;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();
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
