package com.sayit.ui.control.view;

import com.sayit.control.ChatApplication;
import com.sayit.data.Message;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MessageCell extends ListCell<Message> {

    private VBox container;
    private Parent rootNode;
    private MessageViewController messageController;


    public MessageCell() {
        container = new VBox();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ChatApplication.MESSAGE_VIEW));

        try {
            rootNode = fxmlLoader.load();
            rootNode.getStylesheets().add(ChatApplication.getStyleSheet(ChatApplication.MESSAGE_STYLE));
            messageController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setStyle("-fx-background-color: transparent");
    }


    @Override
    protected void updateItem(Message item, boolean empty) {
        //TODO Guilherme updateItem message
        super.updateItem(item, empty);
        if(empty) setGraphic(null);
        else {
            messageController.setMain(item.getTextContent());
            container.getChildren().add(rootNode);
            if(item.isSendByMe()) {
                container.setAlignment(Pos.CENTER_RIGHT);
                rootNode.setId("user-message");
            }
            setGraphic(container);
        }
    }
}
