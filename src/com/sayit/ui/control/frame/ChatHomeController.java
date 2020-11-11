package com.sayit.ui.control.frame;

import com.sayit.control.RequestMediator;
import com.sayit.data.*;
import com.sayit.di.Autowired;
import com.sayit.ui.control.view.HistoryCell;
import com.sayit.ui.control.view.MessageCell;
import com.sayit.ui.navigator.Navigator;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ChatHomeController {

    @FXML
    private HBox inputContainer;
    @FXML
    private HBox tabButtonsContainer;

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

    private Pane findRoot;


    private ObservableList<Message> messageObservableList;
    private ObservableList<MessageHistory> historyObservableList;
    private Contact currentContact;
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private Stage stage;
    @Autowired
    private RequestMediator mediator;

    private TranslateTransition translateTransition;
    private final Text messageText = new Text();

    public void initialize() {

        messageObservableList = FXCollections.observableArrayList();
        historyObservableList = FXCollections.observableArrayList();

        messageListView.setItems(messageObservableList);
        historyListView.setItems(historyObservableList);

        messageListView.setCellFactory(e -> new MessageCell());

        historyListView.setCellFactory(e -> {
            HistoryCell historyCell = new HistoryCell();
            historyCell.setOnMouseClicked(ev -> {
                var historyInfo = historyCell.getItem();
                setReceiverProfile(historyInfo.getContact());

            });

            return historyCell;
        });

        messageField.setOnKeyReleased(e -> resizeTextArea());

        messageField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if(e.getCode() == KeyCode.ENTER) {
                if(e.isShiftDown()) {
                    messageField.insertText(messageField.getCaretPosition(), "\n");
                } else {
                    sendMessage();
                }
                e.consume();

            }
        });

        contactDao.addUserProfileChangedListener(c -> {
            if (c != null) setUserProfile(c);
        });

        setUserProfile(contactDao.getUserProfile());

        setStartupPage();
    }


    private void setStartupPage() {
        tabButtonsContainer.setVisible(false);
        contactImage.setVisible(false);
        inputContainer.setVisible(false);
        contactNameLabel.setText("");
        contactStatusLabel.setText("");

    }

    private void showChatComponents() {
        contactImage.setVisible(true);
        inputContainer.setVisible(true);
    }


    private void loadContactView(List<Contact> contactList) {

        Node node = Navigator.buildNamed(
                "/addContact",
                contactList,
                o -> {
                    if (o != null) {
                        setReceiverProfile((Contact) o);
                    }
                    closeSearchContact();
                }
        );

        findRoot = (Pane) node;
        findPane.getChildren().add(node);
        findPane.heightProperty().addListener(e -> findRoot.setPrefHeight(findPane.getHeight()));
        configSlideAnimation();
    }


    private void configSlideAnimation() {
        Duration transitionDuration = Duration.millis(300);
        translateTransition = new TranslateTransition(transitionDuration, findRoot);
        translateTransition.setInterpolator(Interpolator.EASE_OUT);
    }


    public void showSearchContact() {

        loadContactView(contactDao.getContactList());

        findPane.setManaged(true);
        findPane.setVisible(true);
        findRoot.setPrefWidth(findPane.getWidth());

        translateTransition.setFromX(findPane.getWidth());
        translateTransition.setToX(0);
        translateTransition.setOnFinished(null);
        translateTransition.playFromStart();
    }

    private void closeSearchContact() {
        translateTransition.setFromX(0);
        translateTransition.setToX(findPane.getWidth());
        translateTransition.setInterpolator(Interpolator.EASE_IN);
        translateTransition.setOnFinished(e -> {
            findPane.setManaged(false);
            findPane.setVisible(false);
        });
        translateTransition.playFromStart();
    }


    private void resizeTextArea() {
        messageText.setText(messageField.getText());
        messageText.setWrappingWidth(messageField.getWidth());
        messageText.setFont(messageField.getFont());

        if(messageText.getLayoutBounds().getHeight() >= (messageText.getFont().getSize() + messageText.getLineSpacing()) * 5) {
            messageText.setText("\n\n\n\n");
        }

        messageField.setPrefHeight(messageText.getLayoutBounds().getHeight() + 10);

    }

    public void showAddContact() {

        Navigator.of(stage)
                .pushNamedModal("/addContact",
                        400,
                        300,
                        res -> {
                            if (res != null){
                                mediator.sendContactAddRequest(((Contact)res).toRequest());
                            }
                        });
    }

    public void showEditProfile() {
        Navigator.of(stage).pushNamedModal("/editProfile",
                300,
                400,
                contactDao.getUserProfile(),
                c -> contactDao.setUserProfile((Contact)c));
    }


    public void sendMessage() {
        if(!messageField.getText().isEmpty()) {

            Message sendMessage = new Message(currentContact, true, messageField.getText(), MessageType.TEXT);
            sendMessage.setMessageDate(new Date());
            mediator.sendMessage(sendMessage.toRequest());
            contactDao.addMessage(currentContact.getId(), sendMessage);
            setMessageList(contactDao.getMessageList(currentContact.getId()));
            setHistoryList(contactDao.getHistoryList());

            messageField.setText("");
        }
    }




    public void setUserProfile(Contact userProfile) {
        userImage.setFill(new ImagePattern(userProfile.getPhoto()));
        userNameLabel.setText(userProfile.getName());
    }

    private void setReceiverProfile(Contact receiverProfile) {

        final var profileId = receiverProfile.getId();

        receiverProfile = contactDao.getContact(profileId);
        contactImage.setFill(new ImagePattern(receiverProfile.getPhoto()));
        contactNameLabel.setText(receiverProfile.getName());
        showChatComponents();

        Platform.runLater(() -> {

            for (int i = 0; i < historyObservableList.size(); i++) {
                if(profileId == historyObservableList.get(i).getContact().getId()) {
                    historyListView.getSelectionModel().select(i);
                    historyListView.scrollTo(i);
                    break;
                }
            }
        });

        List<Message> messageList = contactDao.getMessageList(profileId);
        messageList.sort(Comparator.comparing(Message::getMessageDate));
        setMessageList(messageList);
        messageListView.scrollTo(messageObservableList.size() - 1);
    }

    public void setHistoryList(List<MessageHistory> messageHistories) {
        historyObservableList.clear();
        if(messageHistories.size() > 0) historyObservableList.addAll(messageHistories);
    }

    public void setMessageList(List<Message> messageList) {
        messageObservableList.clear();
        if(messageList.size() > 0) messageObservableList.addAll(messageList);
    }

//    private void contactAdd(Message message){
//        Platform.runLater(() -> {
//            if(currentContact != null && currentContact.getId() == ContactDao.parseAddress(message.getSenderAddress())) {
//                List<Message> messageList = contactDao.getMessageList(ContactDao.parseAddress(message.getSenderAddress()));
//                messageList.sort(Comparator.comparing(Message::getMessageDate));
//                setMessageList(messageList);
//            }
//            setHistoryList(contactDao.getHistoryList());
//        });
//    }
}
