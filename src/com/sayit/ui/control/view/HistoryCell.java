package com.sayit.ui.control.view;

import com.sayit.control.ChatApplication;
import com.sayit.data.MessageHistory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class HistoryCell extends ListCell<MessageHistory> {

    private Parent root;
    private ContactViewController contactController;


    public HistoryCell() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ChatApplication.CONTACT_VIEW));

        try {
            root = fxmlLoader.load();
            contactController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void updateItem(MessageHistory item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) setGraphic(root);
    }
}
