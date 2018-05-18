package com.sayit.ui.control.view;

import com.sayit.control.ChatApplication;
import com.sayit.data.Contact;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ContactCell extends ListCell<Contact> {


    private Parent root;
    private ContactViewController contactController;


    public ContactCell() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ChatApplication.CONTACT_VIEW));

        try {
            root = fxmlLoader.load();
            contactController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) setGraphic(root);
    }
}
