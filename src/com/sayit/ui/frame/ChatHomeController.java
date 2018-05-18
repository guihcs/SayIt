package com.sayit.ui.frame;

import com.sayit.ui.view.ContactCell;
import com.sayit.ui.view.MessageCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ChatHomeController {

    @FXML
    private ListView<String> messageListView;
    @FXML
    private ListView<String> historyListView;

    public void initialize() {
        ObservableList<String> observableList = FXCollections.observableArrayList("alealehandroalalaaeahsiuchaiuschaiushciausiaushciaushciaushciauhciua", "b");
        ObservableList<String> histObs = FXCollections.observableArrayList();
        for (int i = 0; i < 200; i++) {
            observableList.add("lmas");
            histObs.add("jaisn");
        }
        messageListView.setItems(observableList);
        messageListView.setCellFactory(param -> new MessageCell());

        historyListView.setItems(histObs);
        historyListView.setCellFactory(param -> new ContactCell());
    }
}
