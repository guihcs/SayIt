package com.sayit.control;

import com.sayit.data.*;
import com.sayit.ui.control.frame.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    private volatile static ChatApplication instance;

    private Stage primaryStage;
    private Contact currentContact;
    private ContactDao contactDao;
    private Requestable requestable;
    private ChatHomeController chatHome;
    private FindContactController findContactController;
    private ProfileEditController profileEditController;

    private boolean isWaitingForContact;
    private final LinkedList<Contact> requestList = new LinkedList<>();
    private volatile boolean processingRequests = false;

    public ChatApplication() {
        ChatApplication.instance = this;
    }

    /**
     * Inicializa a aplicação e recebe uma instância da classe.
     *
     * @param args        Argumentos do sistema.
     * @param requestable Instância da classe main.
     * @param contactDao  Instância do DAO de contatos.
     * @return Uma instância da aplicação.
     */
    public synchronized static ChatApplication launchApplication(String[] args, Requestable requestable, ContactDao contactDao) {
        if(ChatApplication.instance == null) {
            new Thread(() -> Application.launch(ChatApplication.class, args)).start();
            while (ChatApplication.instance == null) Thread.onSpinWait();
            ChatApplication.instance.setContactDao(contactDao);
            ChatApplication.instance.setRequestable(requestable);
        }

        return ChatApplication.instance;
    }

    /**
     * Get a fxml loader.
     *
     * @param path Node address
     * @return FxmlLoader Object
     */
    public static FXMLLoader getLoader(String path) {
        return new FXMLLoader(ChatApplication.class.getResource(path));
    }

    /**
     * Load a node from a loader to reduce try/catch verbosity.
     *
     * @param loader Loader Object
     * @return inflated Node
     */
    public static Node loadFromLoader(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getStyleSheet(String path) {
        return ChatApplication.class.getResource(path).toExternalForm();
    }


    private byte[] readImageBytes(Image image) {
        ByteBuffer pixelBuffer = ByteBuffer.allocate((int) (image.getWidth() * image.getHeight()) * 4);
        PixelReader pixelReader = image.getPixelReader();

        for (int h = 0; h < image.getHeight(); h++) {
            for (int w = 0; w < image.getWidth(); w++) {
                pixelBuffer.putInt(pixelReader.getArgb(w, h));
            }
        }

        return pixelBuffer.array();
    }

    private Image writeImageBytes(byte[] buffer, int width, int height) {
        WritableImage writableImage = new WritableImage(width, height);
        ByteBuffer pixelBuffer = ByteBuffer.wrap(buffer);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                pixelWriter.setArgb(w, h, pixelBuffer.getInt());
            }
        }

        return writableImage;
    }

    /**
     * Abre uma janela modal especificando o tamanho.
     *
     * @param root Node Layout
     * @param width window width
     * @param height window height
     */
    private Stage createModal(Node root, double width, double height) {
        Stage stage = new Stage();
        Scene scene = new Scene((Parent) root, width, height);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        return stage;
    }

    /**
     * Centraliza a janela.
     */
    private void setWindowPosition() {
        var screenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(screenBounds.getMaxX() * 0.5 - primaryStage.getWidth() * 0.5);
        primaryStage.setY(screenBounds.getMaxY() * 0.5 - primaryStage.getHeight() * 0.5);
    }


    /**
     * Metodo chamado ao iniciar a aplicação.
     *
     * @param primaryStage Main Window
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle(HOME_TITLE);
    }

    /**
     * Adiciona uma mensagem de texto ao contato especificado.
     *
     * @param sid      o identificador do contato.
     * @param message a mensagem a ser adicionada
     */
    public void addMessage(String sid, String message) {
        long id = ContactDao.parseAddress(sid);
        Contact contact = contactDao.getContact(id);
        Message newMessage = new Message(contact, false, message, MessageType.TEXT);
        newMessage.setMessageDate(new Date());
        contactDao.addMessage(id, newMessage);
        Platform.runLater(() -> {
            if(currentContact != null && currentContact.getId() == id) {
                chatHome.setMessageList(contactDao.getMessageList(id));
            }
            chatHome.setHistoryList(contactDao.getHistoryList());
        });

    }

    /**
     * Adiciona uma mensagem ao contato específico.
     *
     * @param sid          identificador do contato.
     * @param content     conteúdo da mensagem.
     * @param fileName tipo da mensagem.
     */
    public void addMessage(String sid, byte[] content, String fileName) {

        //fixme archives will be implemented next
        long id = ContactDao.parseAddress(sid);
        Contact contact = contactDao.getContact(id);
        //fixme get file type from name
        //contactDao.addMessage(id, new Message(contact, false, content, messageType));
    }


    /**
     * Adiciona um contato na lista de requisições de contato.
     * @param name
     * @param address
     * @param image
     * @param width
     * @param height
     */
    public void addContact(String name, String address, byte[] image, int width, int height) {
        Contact requesterContact = new Contact(name, writeImageBytes(image, width, height), address);
        requestList.add(requesterContact);

        if(!processingRequests) {
            Platform.runLater(() -> {
                processingRequests = true;
                while (processingRequests && requestList.size() > 0) {
                    openContactRequest(requestList.pollFirst());
                }
                processingRequests = false;
            });
        }
    }

    /**
     * Adiciona um contato na lista de contatos.
     *
     * @param name
     * @param address
     * @param image
     * @param width
     * @param height
     */
    public void addContactResult(String name, String address, byte[] image, int width, int height) {

        Contact requesterContact = new Contact(name, writeImageBytes(image, width, height), address);
        contactDao.addContact(requesterContact);

    }



    /**
     * Abre a tela de confirmação de contato.
     *
     * @param contact contato a ser confirmado.
     */
    public void openContactRequest(Contact contact) {

        var loader = getLoader(ADD_RESPONSE_LAYOUT);
        Parent node = (Parent) loadFromLoader(loader);
        node.getStylesheets().add(getStyleSheet(ADD_RESPONSE_STYLE));


        AddResponseController addController = loader.getController();

        addController.setContact(contact);

        Stage requestWindow = createModal(node, 300, 400);
        requestWindow.setTitle(REQUEST_TITLE);
        addController.setConfirmCallback(contact1 -> {
            requestable.sendContactResult(contact.getIpAddress(), getUserName(), getUserImageBytes(), getImageWidth(), getImageHeight());
            contactDao.addContact(contact1);
            requestWindow.close();
        });
        addController.setCancelCallback(e -> requestWindow.close());


        requestWindow.showAndWait();
    }

    /**
     * Abre a tela inicial da aplicação, contendo a tela de edição de perfil.
     */
    public void openStartScene() {
        FXMLLoader loader = getLoader(START_FRAME);
        Parent parent = (Parent) loadFromLoader(loader);
        parent.getStylesheets().add(getStyleSheet(START_STYLE));
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

    /**
     * Abre a tela de chat.
     */
    public void openHomeScene() {
        var loader = getLoader(HOME_LAYOUT);
        Parent parent = (Parent) loadFromLoader(loader);
        chatHome = loader.getController();
        chatHome.setPresentable(this);
        chatHome.setParentWindow(primaryStage);
        var res = getClass().getResource(HOME_STYLE);

        parent.getStylesheets().add(res.toExternalForm());

        chatHome.setUserProfile(contactDao.getUserProfile());
        chatHome.setHistoryList(contactDao.getHistoryList());

        Platform.runLater(() -> {
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
            setWindowPosition();
        });

    }


    /**
     * Retorna verdadeiro se estiver requisitando contatos.
     *
     * @return Caso esteja esperando por contatos.
     */
    public boolean isWaitingForContact() {
        return isWaitingForContact;
    }

    /**
     * Adiciona uma requisição de contato para a lista.
     *
     * @param name Contact name
     * @param image Contact Icon
     */
    public void addContactRequest(String name, String address, byte[] image, int width, int height) {
        if(isWaitingForContact) {
            Image contactImage = writeImageBytes(image, width, height);
            Contact requestContact = new Contact(name, contactImage, address);
            findContactController.addContact(requestContact);
        }
    }

    /**
     * Verifica se o usuário atual foi requisitado.
     *
     * @param name Nome requisitado.
     * @return verdadeiro caso seja requisitado.
     */
    public boolean checkUserRequest(String name) {
        return contactDao.getUserProfile().getName().toUpperCase().contains(name.toUpperCase());
    }

    private void setContactDao(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    private void setRequestable(Requestable requestable) {
        this.requestable = requestable;
    }

    /**
     * Retorna o nome do usuário atual.
     *
     * @return Retorna o nome do usuário atual.
     */
    public String getUserName() {
        return contactDao.getUserProfile().getName();
    }

    /**
     * Retorna a imagem do usuário em bytes.
     *
     * @return byte[]
     */
    public byte[] getUserImageBytes() {
        return readImageBytes(contactDao.getUserProfile().getPhoto());
    }

    /**
     * Retorna largura da foto do usuário.
     *
     * @return largura da image
     */
    public int getImageWidth() {
        return (int) contactDao.getUserProfile().getPhoto().getWidth();
    }

    /**
     * Retorna a altura da imagem.
     *
     * @return altura da imagem.
     */
    public int getImageHeight() {
        return (int) contactDao.getUserProfile().getPhoto().getHeight();
    }


    /**
     * Retorna um contato específico.
     *
     * @param id identificador do contato.
     * @return A contact object
     */
    @Override
    public Contact getContactInfo(long id) {
        currentContact = contactDao.getContact(id);
        return currentContact;
    }

    /**
     * Retorna o perfil do usuário atual.
     *
     * @return the current user contact
     */
    @Override
    public Contact getUserProfile() {
        return contactDao.getUserProfile();
    }

    /**
     * Retorna a lista de mensagens de um contato específico. E configura o contato
     * como receptor atual.
     *
     * @param id User id for search
     * @return list of messages from contact
     */
    @Override
    public List<Message> requestMessageList(long id) {
        //fixme addload from database function
        var messageList = contactDao.getMessageList(id);
        if(messageList == null) requestable.loadMessageList(id);
        return messageList;
    }

    /**
     * Retorna a lista de históricos.
     *
     * @return history list.
     */
    @Override
    public List<MessageHistory> getHistoryList() {
        return contactDao.getHistoryList();
    }

    /**
     * Retorna a lista de contatos;
     *
     * @return contact list.
     */
    @Override
    public List<Contact> getContactList() {
        return contactDao.getContactList();
    }


    /**
     * Abre a tela de adição de contatos.
     */
    @Override
    public void openAddScene() {

        FXMLLoader loader = getLoader(FIND_CONTACT_LAYOUT);
        Parent addView = (Parent)loadFromLoader(loader);
        addView.getStylesheets().add(getStyleSheet(FIND_CONTACT_STYLE));
        findContactController = loader.getController();

        var window = createModal(addView, 400, 300);
        window.setTitle(ADD_TITLE);

        findContactController.setCloseCallback(() -> {
            isWaitingForContact = false;
            window.close();
        });

        findContactController.setSearchCallback(searchInput -> {

            requestable.requestContact(searchInput);
        });

        findContactController.setContactResult(contact -> {
            requestable.contactAdd(contact.getIpAddress(), getUserName(), getUserImageBytes(), getImageWidth(), getImageHeight());
            window.close();
            isWaitingForContact = false;
        });

        findContactController.requestSearchFocus();

        isWaitingForContact = true;
        window.showAndWait();
    }

    /**
     * Abre a tela de edição de perfil.
     */
    @Override
    public void openEditProfileScene() {

        FXMLLoader loader = getLoader(EDIT_PROFILE_LAYOUT);
        Parent editLayout = (Parent)loadFromLoader(loader);
        editLayout.getStylesheets().add(getStyleSheet(EDIT_CONTACT_STYLE));
        ProfileEditController editController = loader.getController();

        var window = createModal(editLayout, 400, 300);
        window.setTitle(EDIT_TITLE);

        editController.setOwnerWindow(window);
        if(getUserProfile() != null) editController.setContact(getUserProfile());
        editController.setConcludeCallback(contact -> {
            contactDao.setUserProfile(contact);
            chatHome.setUserProfile(contact);
            window.close();
        });
        editController.setBackCallback(window::close);

        window.showAndWait();
    }


    /**
     * Requisita a lista de contatos.
     *
     * @param name contact name for add.
     */
    @Override
    public void requestContactList(String name) {
        requestable.requestContact(name);
    }

    /**
     * Envia uma mensagem para o contato atual.
     *
     * @param message
     */
    @Override
    public void sendMessage(String message) {
        Message sendMessage = new Message(currentContact, true, message, MessageType.TEXT);
        sendMessage.setMessageDate(new Date());
        requestable.sendMessage(currentContact.getIpAddress(), message);
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
}
