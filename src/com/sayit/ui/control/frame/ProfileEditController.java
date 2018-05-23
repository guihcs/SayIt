package com.sayit.ui.control.frame;

import com.sayit.data.Contact;
import com.sayit.ui.control.ContactManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class ProfileEditController {

    private Runnable backCallback;
    private ContactManager concludeCallback;
    private Window ownerWindow;

    private Contact contact;
    @FXML
    private Circle roundImage;
    @FXML
    private TextField nameField;

    public void initialize() {
        contact = new Contact("Antonio", new Image("http://i.imgur.com/jAkOMcB.png"), "192.168.0.1");
    }

    public void close() {
        if(backCallback != null) backCallback.run();
    }

    public void confirm() {
        if(concludeCallback != null) {
            concludeCallback.contactResult(contact);
        }
        //TODO Guilherme confirm (create profile)
    }

    public void getImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.showOpenDialog(ownerWindow);
        //TODO Guilherme getImage
    }

    public void setBackCallback(Runnable backCallback) {
        this.backCallback = backCallback;
    }

    public void setConcludeCallback(ContactManager concludeCallback) {
        this.concludeCallback = concludeCallback;
    }

    public void setOwnerWindow(Window ownerWindow) {
        this.ownerWindow = ownerWindow;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
