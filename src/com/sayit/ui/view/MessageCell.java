package com.sayit.ui.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MessageCell extends ListCell<String> {

    public static final String VIEW_LAYOUT_LOCATION = "/com/sayit/resources/layout/view/view_message_cell.fxml";

    private BorderPane container;
    private Parent rootNode;
    private MessageViewController messageController;


    public MessageCell() {
        container = new BorderPane();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(VIEW_LAYOUT_LOCATION));

        try {
            rootNode = fxmlLoader.load();
            messageController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        container.setRight(rootNode);
    }


    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(empty) setGraphic(null);
        else {
            messageController.setMain(item);
            setGraphic(container);
        }
    }
}
