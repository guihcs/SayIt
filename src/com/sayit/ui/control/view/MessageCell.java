package com.sayit.ui.control.view;

import com.sayit.control.ChatApplication;
import com.sayit.data.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MessageCell extends ListCell<Message> {

    private BorderPane container;
    private Parent rootNode;
    private MessageViewController messageController;


    public MessageCell() {
        container = new BorderPane();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ChatApplication.MESSAGE_VIEW));

        try {
            rootNode = fxmlLoader.load();
            messageController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        container.setRight(rootNode);
    }


    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if(empty) setGraphic(null);
    }
}
