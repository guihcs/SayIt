package com.sayit.ui.control.frame;

import com.sayit.data.Contact;
import com.sayit.ui.control.ContactManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class AddResponseController {

    private Contact contact;

    @FXML
    private Circle imageView;
    @FXML
    private Label nameLabel;

    private ContactManager cancelCallback;
    private ContactManager confirmCallback;


    //        addController.setContact(contact);

//        Stage requestWindow = createModal(node, 300, 400);
//        requestWindow.setTitle(REQUEST_TITLE);
//        addController.setConfirmCallback(contact1 -> {
//            requestable.sendContactDiscoveryResponse(contact.toRequest());
////            contactDao.requestAddContact(contact1);
//            requestWindow.close();
//        });
//        addController.setCancelCallback(e -> requestWindow.close());
//


    public void accept() {
        if(confirmCallback != null) confirmCallback.contactResult(contact);
    }

    public void cancel() {
        if(cancelCallback != null) cancelCallback.contactResult(contact);
    }

    public void setCancelCallback(ContactManager cancelCallback) {
        this.cancelCallback = cancelCallback;
    }

    public void setConfirmCallback(ContactManager confirmCallback) {
        this.confirmCallback = confirmCallback;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        nameLabel.setText(contact.getName());
        imageView.setFill(new ImagePattern(contact.getPhoto()));
    }
}
