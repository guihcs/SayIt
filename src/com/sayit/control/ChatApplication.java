package com.sayit.control;

import com.sayit.data.*;
import com.sayit.ui.control.frame.ChatHomeController;
import com.sayit.ui.control.frame.FindContactController;
import com.sayit.ui.control.frame.ProfileEditController;
import com.sayit.ui.control.frame.StartFrameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatApplication extends Application implements Presentable {

    public static final String HOME_LAYOUT = "/com/sayit/resources/layout/window/layout_chat_home.fxml";
    public static final String START_LAYOUT = "/com/sayit/resources/layout/window/layout_start.fxml";
    public static final String START_FRAME = "/com/sayit/resources/layout/window/layout_frame_start.fxml";
    public static final String FIND_CONTACT_LAYOUT = "/com/sayit/resources/layout/window/layout_find_contact.fxml";
    public static final String EDIT_PROFILE_LAYOUT = "/com/sayit/resources/layout/window/layout_edit_profile.fxml";
    public static final String CONTACT_VIEW = "/com/sayit/resources/layout/view/view_contact_cell.fxml";
    public static final String MESSAGE_VIEW = "/com/sayit/resources/layout/view/view_message_cell.fxml";


    private volatile static ChatApplication instance;

    private Stage primaryStage;
    private Contact currentContact;
    private ContactDao contactDao;
    private Requestable requestCallback;
    private ChatHomeController chatHome;
    private FindContactController findContactController;
    private ProfileEditController profileEditController;

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

        }
        return ChatApplication.instance;
    }

    /**
     * Get a fxml loader.
     *
     * @param path
     * @return
     */
    public static FXMLLoader getLoader(String path) {
        return new FXMLLoader(ChatApplication.class.getResource(path));
    }

    /**
     * Load a node from a loader to reduce try/catch verbosity.
     *
     * @param loader
     * @return
     */
    public static Node loadFromLoader(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Abre uma janela modal.
     *
     * @param root
     */
    private Stage createModal(Node root) {
        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene((Parent) root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        return stage;
    }

    /**
     * Abre uma janela modal especificando o tamanho.
     *
     * @param root
     * @param width
     * @param height
     */
    private Stage createModal(Node root, double width, double height) {
        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene((Parent) root, width, height);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        return stage;
    }



    /**
     * Metodo chamado ao iniciar a aplicação.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        //TODO Guilherme start
        this.primaryStage = primaryStage;

    }

    /**
     * Adiciona uma mensagem de texto ao contato especificado.
     *
     * @param id      o identificador do contato.
     * @param message a mensagem a ser adicionada
     */
    public void addMessage(int id, String message) {
        //TODO Guilherme addMessage text
    }

    /**
     * Adiciona uma mensagem ao contato específico.
     *
     * @param id          identificador do contato.
     * @param content     conteúdo da mensagem.
     * @param messageType tipo da mensagem.
     */
    public void addMessage(int id, byte[] content, MessageType messageType) {
        //TODO Guilherme addMessage bytes

    }

    /**
     * Adiciona um contato na lista de contatos.
     *
     * @param id      identificador do contato.
     * @param img     imagem do contato.
     * @param name    nome do contato.
     * @param address endereço do contato.
     */
    public void addContact(int id, byte[] img, String name, String address) {
        //TODO Guilherme addContact
    }

    /**
     * Abre a tela inicial da aplicação, contendo a tela de edição de perfil.
     *
     * @return O contato editado.
     */
    public Contact openStartScene() {
        //TODO Guilherme openEditProfile
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(START_FRAME));
            Parent parent = loader.load();
            StartFrameController startController = loader.getController();

            startController.setConcludeCallback(contact -> {
                openHomeScene();
            });

            Platform.runLater(() -> {
                primaryStage.setScene(new Scene(parent));
                primaryStage.show();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Abre a tela de chat.
     */
    public void openHomeScene() {
        //TODO Guilherme openHomeScene
        var loader = getLoader(HOME_LAYOUT);
        Parent parent = (Parent) loadFromLoader(loader);
        chatHome = loader.getController();
        chatHome.setPresentable(this);
        chatHome.setParentWindow(primaryStage);

        Platform.runLater(() -> {
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();
        });

    }


    /**
     * Retorna verdadeiro se estiver requisitando contatos.
     *
     * @return
     */
    public boolean isWaitingForContact() {
        //TODO Guilherme isWaitingForContact
        return false;
    }

    /**
     * Adiciona uma requisição de contato para a lista.
     *
     * @param name
     * @param image
     */
    public void addContactRequest(String name, byte[] image) {
        //TODO Guilherme addContactRequest
    }


    /**
     * Retorna um contato específico.
     *
     * @param id identificador do contato.
     * @return
     */
    @Override
    public Contact getContactInfo(int id) {
        //TODO Guilherme getContactInfo
        return null;
    }

    /**
     * Retorna a lista de mensagens de um contato específico. E configura o contato
     * como receptor atual.
     *
     * @param id
     * @return
     */
    @Override
    public List<Message> requestMessageList(int id) {
        //TODO Guilherme requestMessageList
        return null;
    }

    /**
     * Retorna a lista de históricos.
     *
     * @return
     */
    @Override
    public List<MessageHistory> getHistoryList() {
        //TODO Guilherme getHistoryList
        return null;
    }

    /**
     * Retorna a lista de contatos;
     *
     * @return
     */
    @Override
    public List<Contact> getContactList() {
        //TODO Guilherme getContactList
        List<Contact> contactList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            contactList.add(new Contact("Lucas", null, null, null));
        }
        return contactList;
    }


    /**
     * Abre a tela de adição de contatos.
     */
    @Override
    public void openAddScene() {

        FXMLLoader loader = getLoader(FIND_CONTACT_LAYOUT);
        Node addLayout = loadFromLoader(loader);
        FindContactController contactController = loader.getController();

        var window = createModal(addLayout, 400, 300);
        //fixme set button callbacks
        contactController.setCloseCallback(window::close);

        window.showAndWait();
    }

    /**
     * Abre a tela de edição de perfil.
     */
    @Override
    public void openEditProfileScene() {

        FXMLLoader loader = getLoader(EDIT_PROFILE_LAYOUT);
        Node addLayout = loadFromLoader(loader);
        ProfileEditController editController = loader.getController();
        var window = createModal(addLayout, 400, 300);

        editController.setOwnerWindow(window);
        //fixme set conclude callbacks
        editController.setBackCallback(window::close);

        window.showAndWait();
    }

    /**
     * Requisita a lista de contatos.
     *
     * @param name
     */
    @Override
    public void requestContactList(String name) {
        //TODO Guilherme requestContactList
    }

    /**
     * Envia uma mensagem para o contato atual.
     *
     * @param message
     */
    @Override
    public void sendMessage(String message) {
        //TODO Guilherme sendMessage
    }


}
