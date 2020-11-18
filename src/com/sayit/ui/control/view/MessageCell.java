package com.sayit.ui.control.view;

import com.sayit.data.Message;
import com.sayit.ui.navigator.Navigator;
import com.sayit.ui.navigator.RenderedNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;


public class MessageCell extends ListCell<Message> {

    private final VBox container;
    private final Parent rootNode;
    private final MessageViewController messageController;


    public MessageCell() {
        container = new VBox();
        RenderedNode renderedNode = Navigator.getRenderedNode("/messageView");
        rootNode = renderedNode.getParent();
        messageController = (MessageViewController) renderedNode.getController();

        setStyle("-fx-background-color: transparent");
        container.setPadding(new Insets(0, 60, 0, 60));
        container.getChildren().add(rootNode);
    }


    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) setGraphic(null);
        else {
            setGraphic(container);
            messageController.setMain(item.getTextContent());
            messageController.setTime(item.getFormattedTime());
            if (item.isSendByMe()) {
                rootNode.setId("user-message");
                container.setAlignment(Pos.CENTER_RIGHT);
            } else {
                rootNode.setId("");
                container.setAlignment(Pos.CENTER_LEFT);
            }
        }
    }
}
