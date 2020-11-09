package com.sayit.ui.control.view;

import com.sayit.control.ChatApplication;
import com.sayit.data.MessageHistory;
import com.sayit.ui.control.FXMLManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class HistoryCell extends ListCell<MessageHistory> {

    private final Parent root;
    private final ContactViewController contactController;


    public HistoryCell() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ChatApplication.CONTACT_VIEW));

        root = (Parent) FXMLManager.loadFromLoader(fxmlLoader);
        contactController = fxmlLoader.getController();
        getStylesheets().add(ChatApplication.getStyleSheet(ChatApplication.CONTACT_STYLE));

    }


    @Override
    protected void updateItem(MessageHistory item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) {
            contactController.setName(item.getContact().getName());
            contactController.setRoundedImage(item.getContact().getPhoto());
            contactController.setDescription(item.getLastMessage());
            contactController.setTime(item.getLastDateText());
            setGraphic(root);
        } else {
            setGraphic(null);
        }
    }
}
