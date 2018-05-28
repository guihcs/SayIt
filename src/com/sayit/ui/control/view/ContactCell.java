package com.sayit.ui.control.view;

import com.sayit.control.ChatApplication;
import com.sayit.data.Contact;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class ContactCell extends ListCell<Contact> {


    private Parent root;
    private ContactViewController contactController;


    public ContactCell() {

        FXMLLoader fxmlLoader = ChatApplication.getLoader(ChatApplication.CONTACT_VIEW);

        root = (Parent) ChatApplication.loadFromLoader(fxmlLoader);
        contactController = fxmlLoader.getController();

        getStylesheets().add(ChatApplication.getStyleSheet(ChatApplication.CONTACT_STYLE));
    }


    @Override
    protected void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) {
            contactController.setName(item.getName());
            setGraphic(root);
        } else setGraphic(null);
    }
}
