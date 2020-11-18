package com.sayit.ui.control.view;

import com.sayit.data.contact.Contact;
import com.sayit.ui.navigator.Navigator;
import com.sayit.ui.navigator.RenderedNode;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class ContactCell extends ListCell<Contact> {


    private final Parent root;
    private final ContactViewController contactController;


    public ContactCell() {
        RenderedNode renderedNode = Navigator.getRenderedNode("/contactView");
        root = renderedNode.getParent();
        contactController = (ContactViewController) renderedNode.getController();
    }


    @Override
    protected void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            contactController.setName(item.getName());
            contactController.setRoundedImage(item.getPhoto());
            contactController.setDescription("");
            contactController.setTime("");
            setGraphic(root);
        } else setGraphic(null);
    }
}
