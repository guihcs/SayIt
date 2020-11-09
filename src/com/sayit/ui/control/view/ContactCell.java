package com.sayit.ui.control.view;

import com.sayit.control.ChatApplication;
import com.sayit.data.Contact;
import com.sayit.ui.control.FXMLManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class ContactCell extends ListCell<Contact> {


    private final Parent root;
    private final ContactViewController contactController;


    public ContactCell() {

        FXMLLoader fxmlLoader = FXMLManager.getLoader(ChatApplication.CONTACT_VIEW);

        root = (Parent) FXMLManager.loadFromLoader(fxmlLoader);
        contactController = fxmlLoader.getController();

        getStylesheets().add(ChatApplication.getStyleSheet(ChatApplication.CONTACT_STYLE));
    }


    @Override
    protected void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) {
            contactController.setName(item.getName());
            contactController.setRoundedImage(item.getPhoto());
            contactController.setDescription("");
            contactController.setTime("");
            setGraphic(root);
        } else setGraphic(null);
    }
}
