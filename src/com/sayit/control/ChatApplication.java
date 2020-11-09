package com.sayit.control;

import com.sayit.data.*;
import com.sayit.ui.control.FXMLManager;
import com.sayit.ui.control.frame.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ChatApplication extends Application implements Presentable {
    //Window Constants
    public static final String HOME_TITLE = "Say It";
    public static final String ADD_TITLE = "Adicionar Contato";
    public static final String EDIT_TITLE = "Editar Perfil";
    public static final String REQUEST_TITLE = "Solicitação";
    //Layouts
    public static final String HOME_LAYOUT = "/com/sayit/resources/layout/window/layout_chat_home.fxml";
    public static final String START_LAYOUT = "/com/sayit/resources/layout/window/layout_start.fxml";
    public static final String START_FRAME = "/com/sayit/resources/layout/window/layout_frame_start.fxml";
    public static final String FIND_CONTACT_LAYOUT = "/com/sayit/resources/layout/window/layout_find_contact.fxml";
    public static final String EDIT_PROFILE_LAYOUT = "/com/sayit/resources/layout/window/layout_edit_profile.fxml";
    public static final String CONTACT_VIEW = "/com/sayit/resources/layout/view/view_contact_cell.fxml";
    public static final String MESSAGE_VIEW = "/com/sayit/resources/layout/view/view_message_cell.fxml";
    public static final String ADD_RESPONSE_LAYOUT = "/com/sayit/resources/layout/window/layout_add_response.fxml";
    //Styles
    public static final String HOME_STYLE = "/com/sayit/resources/stylesheet/style_chat_home.css";
    public static final String FIND_CONTACT_STYLE = "/com/sayit/resources/stylesheet/style_find_contact.css";
    public static final String EDIT_CONTACT_STYLE = "/com/sayit/resources/stylesheet/style_edit_profile.css";
    public static final String CONTACT_STYLE = "/com/sayit/resources/stylesheet/style_contact.css";
    public static final String MESSAGE_STYLE = "/com/sayit/resources/stylesheet/style_message.css";
    public static final String ADD_RESPONSE_STYLE = "/com/sayit/resources/stylesheet/style_add_response.css";
    public static final String START_STYLE = "/com/sayit/resources/stylesheet/style_start.css";
    //Constants
    public static final int MAX_NAME_LENGTH = 20;
    private Stage primaryStage;
    private Contact currentContact;
    private ContactDao contactDao;
    private Requestable requestable;
    private ChatHomeController chatHome;
    private ChatManager chatManager;
    private FindContactController findContactController;


    public static void main(String[] args) {
        launch(args);
    }

    public static String getStyleSheet(String path) {
        return ChatApplication.class.getResource(path).toExternalForm();
    }

    private Stage createModal(Node root, double width, double height) {
        Stage stage = new Stage();
        Scene scene = new Scene((Parent) root, width, height);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        return stage;
    }

    private void setWindowPosition() {
        var screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(screenBounds.getMaxX() * 0.5 - primaryStage.getWidth() * 0.5);
        primaryStage.setY(screenBounds.getMaxY() * 0.5 - primaryStage.getHeight() * 0.5);
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle(HOME_TITLE);

        RequestMediator mediator = new RequestMediator();
        ContactDao contactDao = new ContactDao();

        setContactDao(contactDao);
        setRequestable(mediator);

        mediator.start();
        openStartScene();
    }

    public void openContactRequest(Contact contact) {

        var loader = FXMLManager.getLoader(ADD_RESPONSE_LAYOUT);
        Parent node = (Parent) FXMLManager.loadFromLoader(loader);
        Objects.requireNonNull(node).getStylesheets().add(getStyleSheet(ADD_RESPONSE_STYLE));

        AddResponseController addController = loader.getController();

        addController.setContact(contact);

        Stage requestWindow = createModal(node, 300, 400);
        requestWindow.setTitle(REQUEST_TITLE);
        addController.setConfirmCallback(contact1 -> {
            requestable.sendContactDiscoveryResponse(contact.toRequest());
            contactDao.addContact(contact1);
            requestWindow.close();
        });
        addController.setCancelCallback(e -> requestWindow.close());

        requestWindow.showAndWait();
    }

    public void openStartScene() {
        FXMLLoader loader = FXMLManager.getLoader(START_FRAME);
        Parent parent = (Parent) FXMLManager.loadFromLoader(loader);
        Objects.requireNonNull(parent).getStylesheets().add(getStyleSheet(START_STYLE));
        StartFrameController startController = loader.getController();

        startController.setConcludeCallback(contact -> {
            contactDao.setUserProfile(contact);
            openHomeScene();
        });

        Platform.runLater(() -> {
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
            setWindowPosition();
        });
    }

    public void openHomeScene() {
        var loader = FXMLManager.getLoader(HOME_LAYOUT);
        Parent parent = (Parent) FXMLManager.loadFromLoader(loader);
        chatHome = loader.getController();
        chatHome.setPresentable(this);
        chatHome.setParentWindow(primaryStage);
        var res = getClass().getResource(HOME_STYLE);

        Objects.requireNonNull(parent).getStylesheets().add(res.toExternalForm());

        chatHome.setUserProfile(contactDao.getUserProfile());
        chatHome.setHistoryList(contactDao.getHistoryList());

        Platform.runLater(() -> {
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
            setWindowPosition();
        });

    }

    private void setContactDao(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    private void setRequestable(Requestable requestable) {
        this.requestable = requestable;
    }

    @Override
    public Contact getContactInfo(long id) {
        currentContact = contactDao.getContact(id);
        return currentContact;
    }


    @Override
    public Contact getUserProfile() {
        return contactDao.getUserProfile();
    }


    @Override
    public List<Message> requestMessageList(long id) {
        return contactDao.getMessageList(id);
    }


    @Override
    public List<MessageHistory> getHistoryList() {
        return contactDao.getHistoryList();
    }


    @Override
    public List<Contact> getContactList() {
        return contactDao.getContactList();
    }


    @Override
    public void openAddScene() {

        FXMLLoader loader = FXMLManager.getLoader(FIND_CONTACT_LAYOUT);
        Parent addView = (Parent)FXMLManager.loadFromLoader(loader);
        Objects.requireNonNull(addView).getStylesheets().add(getStyleSheet(FIND_CONTACT_STYLE));
        FindContactController findContactController = loader.getController();

        var window = createModal(addView, 400, 300);
        window.setTitle(ADD_TITLE);

        findContactController.setCloseCallback(() -> {
            chatManager.stopContactRequest();
            window.close();
        });

        findContactController.setSearchCallback(searchInput -> requestable.multicastContactNameDiscovery(searchInput));

        findContactController.setContactResult(contact -> {
            requestable.sendContactAddRequest(contact.toRequest());
            window.close();
            chatManager.stopContactRequest();
        });

        findContactController.requestSearchFocus();
        chatManager.stopContactRequest();
        window.showAndWait();
    }

    @Override
    public void openEditProfileScene() {

        FXMLLoader loader = FXMLManager.getLoader(EDIT_PROFILE_LAYOUT);
        Parent editLayout = (Parent)FXMLManager.loadFromLoader(loader);
        Objects.requireNonNull(editLayout).getStylesheets().add(getStyleSheet(EDIT_CONTACT_STYLE));
        ProfileEditController editController = loader.getController();
        var window = createModal(editLayout, 400, 300);
        window.setTitle(EDIT_TITLE);


        editController.setOwnerWindow(window);
        if(getUserProfile() != null) {
            editController.setContact(getUserProfile());
        }
        editController.setConcludeCallback(contact -> {
            contactDao.setUserProfile(contact);
            chatHome.setUserProfile(contact);
            window.close();
        });
        editController.setBackCallback(window::close);

        window.showAndWait();
    }



    @Override
    public void requestContactList(String name) {
        requestable.multicastContactNameDiscovery(name);
    }


    @Override
    public void sendMessage(String message) {
        Message sendMessage = new Message(currentContact, true, message, MessageType.TEXT);
        sendMessage.setMessageDate(new Date());
        requestable.sendMessage(sendMessage.toRequest());
        contactDao.addMessage(currentContact.getId(), sendMessage);
        chatHome.setMessageList(contactDao.getMessageList(currentContact.getId()));
        chatHome.setHistoryList(contactDao.getHistoryList());
    }

    @Override
    public void stop() {
        try {
            super.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestable.stopServices();
    }


    public FindContactController getFindContactController() {
        return findContactController;
    }
}
