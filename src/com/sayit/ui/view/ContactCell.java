package com.sayit.ui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ContactCell extends ListCell<String> {

    public static final String VIEW_LAYOUT_LOCATION = "/com/sayit/resources/layout/view/view_contact_cell.fxml";

    private Parent root;
    private ContactViewController contactController;


    public ContactCell() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEW_LAYOUT_LOCATION));

        try {
            root = fxmlLoader.load();
            contactController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) setGraphic(root);
    }
}
