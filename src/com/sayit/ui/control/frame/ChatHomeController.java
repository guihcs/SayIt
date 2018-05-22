package com.sayit.ui.control.frame;

import com.sayit.control.ChatApplication;
import com.sayit.control.Presentable;
import com.sayit.data.Message;
import com.sayit.data.MessageHistory;
import com.sayit.ui.control.view.HistoryCell;
import com.sayit.ui.control.view.MessageCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.IOException;

public class ChatHomeController {

    private Presentable presentable;

    @FXML
    private TextField messageField;
    @FXML
    private Pane findPane;
    @FXML
    private ListView<Message> messageListView;
    @FXML
    private ListView<MessageHistory> historyListView;

    private Window parentWindow;
    private Pane findRoot;

    private FindContactController findContactController;
    //TODO Guilherme set observable lists
    private ObservableList<Message> messageObservableList;
    private ObservableList<MessageHistory> historyObservableList;

    public void initialize() {

        messageObservableList = FXCollections.observableArrayList();
        historyObservableList = FXCollections.observableArrayList();

        messageListView.setItems(messageObservableList);
        historyListView.setItems(historyObservableList);

        messageListView.setCellFactory(e -> {
            //TODO Guilherme Set click callback message
            MessageCell messageCell = new MessageCell();
            return messageCell;
        });
        historyListView.setCellFactory(e -> {
            //TODO Guilherme click callback history load message list
            HistoryCell historyCell = new HistoryCell();
            //historyCell.setOnMouseClicked(event -> System.out.println("hue"));
            return historyCell;
        });

        historyObservableList.add(new MessageHistory(null));

        FXMLLoader loader = ChatApplication.getLoader(ChatApplication.FIND_CONTACT_LAYOUT);
        try {
            findRoot = loader.load();
            findContactController = loader.getController();
            findContactController.setCloseCallback(this::closeFindContact);
            findPane.getChildren().add(findRoot);


            findPane.heightProperty().addListener(e -> {

                findRoot.setPrefHeight(findPane.getHeight());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public void setPresentable(Presentable presentable) {
        this.presentable = presentable;
    }

    public void showFindContact() {
        findContactController.setContactList(presentable.getContactList());

        findPane.setManaged(true);
        findPane.setVisible(true);
        findRoot.setPrefWidth(findPane.getWidth());
        //findRoot.setLayoutX(50);
        //fixme add slide animation
    }

    public void closeFindContact() {
        findPane.setManaged(false);
        findPane.setVisible(false);
    }

    public void showAddContact() {
        presentable.openAddScene();
    }

    public void showEditProfile() {
        presentable.openEditProfileScene();
    }


    public void sendMessage() {
        if(!messageField.getText().isEmpty()) {
            //TODO Guilherme sendMessage
            messageField.setText("");
        }
    }

    private void getArchive() {

    }

    public void sendArchive() {
        //TODO Guilherme sendArchive
        FileChooser fileChooser = new FileChooser();
        fileChooser.showOpenDialog(parentWindow);
    }


    public void setParentWindow(Window parentWindow) {
        this.parentWindow = parentWindow;
    }
}
