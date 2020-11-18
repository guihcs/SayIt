package com.sayit.ui.control.view;

import com.sayit.data.message.MessageHistory;
import com.sayit.ui.navigator.Navigator;
import com.sayit.ui.navigator.RenderedNode;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class HistoryCell extends ListCell<MessageHistory> {

    private final Parent root;
    private final ContactViewController contactController;


    public HistoryCell() {

        RenderedNode renderedNode = Navigator.getRenderedNode("/contactView");
        root = renderedNode.getParent();
        contactController = (ContactViewController) renderedNode.getController();

    }


    @Override
    protected void updateItem(MessageHistory item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
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
