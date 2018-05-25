package com.sayit.ui.control.frame;

import com.sayit.control.ChatApplication;
import com.sayit.control.Presentable;
import com.sayit.data.Contact;
import com.sayit.data.Message;
import com.sayit.data.MessageHistory;
import com.sayit.ui.control.view.HistoryCell;
import com.sayit.ui.control.view.MessageCell;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class ChatHomeController {



    @FXML
    private TextArea messageField;
    @FXML
    private Pane findPane;
    @FXML
    private ListView<Message> messageListView;
    @FXML
    private ListView<MessageHistory> historyListView;

    @FXML
    private Label userNameLabel;
    @FXML
    private Label contactNameLabel;
    @FXML
    private Label contactStatusLabel;

    @FXML
    private Circle userImage;
    @FXML
    private Circle contactImage;

    private Window parentWindow;
    private Pane findRoot;

    private Presentable presentable;
    private FindContactController findContactController;
    private ObservableList<Message> messageObservableList;
    private ObservableList<MessageHistory> historyObservableList;

    //Open contact animation
    private TranslateTransition translateTransition;
    private Duration transitionDuration;

    //Resize text property
    private Text messageText = new Text();

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
            var historyInfo = historyCell.getItem();
            setReceiverProfile(historyInfo.getContact());
            setMessageList(presentable.requestMessageList(historyInfo.getContact().getId()));
            return historyCell;
        });

        FXMLLoader loader = ChatApplication.getLoader(ChatApplication.FIND_CONTACT_LAYOUT);
        try {
            findRoot = loader.load();
            findContactController = loader.getController();
            findContactController.setCloseCallback(this::closeFindContact);
            findPane.getChildren().add(findRoot);


            findPane.heightProperty().addListener(e -> findRoot.setPrefHeight(findPane.getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        messageField.setOnKeyReleased(e -> resizeTextArea());

        configSlideAnimation();
    }


    private void configSlideAnimation() {
        transitionDuration = Duration.millis(300);
        translateTransition = new TranslateTransition(transitionDuration, findRoot);


        translateTransition.setInterpolator(Interpolator.EASE_OUT);
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

        translateTransition.setFromX(findPane.getWidth());
        translateTransition.setToX(0);
        translateTransition.setOnFinished(null);
        translateTransition.playFromStart();
    }

    public void closeFindContact() {

        translateTransition.setFromX(0);
        translateTransition.setToX(findPane.getWidth());
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.setOnFinished(e -> {
            findPane.setManaged(false);
            findPane.setVisible(false);
        });
        translateTransition.playFromStart();


    }


    public void resizeTextArea() {
        //fixme upgrade height calculation
        messageText.setText(messageField.getText());
        messageText.setWrappingWidth(messageField.getWidth());
        messageText.setFont(messageField.getFont());

        if(messageText.getLayoutBounds().getHeight() >= (messageText.getFont().getSize() + messageText.getLineSpacing()) * 5) {
            messageText.setText("\n\n\n\n");
        }

        messageField.setPrefHeight(messageText.getLayoutBounds().getHeight() + 10);

    }

    public void showAddContact() {
        presentable.openAddScene();
    }

    public void showEditProfile() {
        presentable.openEditProfileScene();
    }


    public void sendMessage() {
        if(!messageField.getText().isEmpty()) {
            presentable.sendMessage(messageField.getText());
            messageField.setText("");
        }
    }

    private void getArchive() {

    }

    public void sendArchive() {
        //fixme sendArchive
        FileChooser fileChooser = new FileChooser();
        fileChooser.showOpenDialog(parentWindow);
    }


    public void setParentWindow(Window parentWindow) {
        this.parentWindow = parentWindow;
    }

    public void setUserProfile(Contact userProfile) {
        userImage.setFill(new ImagePattern(userProfile.getPhoto()));
        userNameLabel.setText(userProfile.getName());
    }

    public void setReceiverProfile(Contact receiverProfile) {
        contactImage.setFill(new ImagePattern(receiverProfile.getPhoto()));
        contactNameLabel.setText(receiverProfile.getName());
        //fixme add a status to contact
        contactStatusLabel.setText("");
    }

    public void setHistoryList(List<MessageHistory> messageHistories) {
        historyObservableList.clear();
        if(messageHistories.size() > 0) historyObservableList.addAll(messageHistories);
    }

    public void setMessageList(List<Message> messageList) {
        messageObservableList.clear();
        if(messageList.size() > 0) messageObservableList.addAll(messageList);
    }
}
