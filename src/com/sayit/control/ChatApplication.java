package com.sayit.control;

import com.sayit.data.*;
import com.sayit.ui.frame.ChatHomeController;
import com.sayit.ui.frame.ContactAddController;
import com.sayit.ui.frame.ProfileEditController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ChatApplication extends Application implements Presentable {

    private volatile static ChatApplication instance;

    private Stage primaryStage;
    private Contact currentContact;
    private ContactDao contactDao;
    private Requestable requestCallback;
    private ChatHomeController chatHome;
    private ContactAddController contactAddController;
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
     * Metodo chamado ao iniciar a aplicação.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        //TODO Guilherme start
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../resources/layout/window/layout_chat_home.fxml"));
            primaryStage.setScene(new Scene(parent));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.show();
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
     * Abrir tela de edição de perfil.
     *
     * @param contact
     */
    public void openEditProfile(Contact contact) {
        //TODO Guilherme openEditProfile
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
     * Retorna a lista de mensagens de um contato específico.
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
     * Abre a tela de adição de contatos.
     */
    @Override
    public void openAddScene() {
        //TODO Guilherme openAddScene
    }

    /**
     * Abre a tela de edição de perfil.
     */
    @Override
    public void openEditProfileScene() {
        //TODO Guilherme openEditProfileScene
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
