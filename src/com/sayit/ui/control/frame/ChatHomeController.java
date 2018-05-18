package com.sayit.ui.control.frame;

import com.sayit.control.Presentable;
import com.sayit.data.Message;
import com.sayit.data.MessageHistory;
import com.sayit.ui.control.view.HistoryCell;
import com.sayit.ui.control.view.MessageCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ChatHomeController {

    private Presentable presentable;


    @FXML
    private ListView<Message> messageListView;
    @FXML
    private ListView<MessageHistory> historyListView;

    private ObservableList<Message> messageObservableList;
    private ObservableList<MessageHistory> historyObservableList;

    public void initialize() {

        messageObservableList = FXCollections.observableArrayList();
        historyObservableList = FXCollections.observableArrayList();

        messageListView.setItems(messageObservableList);
        historyListView.setItems(historyObservableList);

        messageListView.setCellFactory(e -> {
            MessageCell messageCell = new MessageCell();
            return messageCell;
        });
        historyListView.setCellFactory(e -> {
            HistoryCell historyCell = new HistoryCell();
            return historyCell;
        });

    }

    public void setPresentable(Presentable presentable) {
        this.presentable = presentable;
    }
}
