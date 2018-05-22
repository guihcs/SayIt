package com.sayit.control;

import com.sayit.data.Contact;
import com.sayit.data.ContactDao;
import com.sayit.data.MessageType;
import com.sayit.network.MessageProtocol;
import com.sayit.network.NetworkAdapter;
import javafx.scene.image.Image;

import java.nio.file.Path;
import java.util.List;

public class RequestMediator implements Requestable {

    private Path historyFolderPath;
    private Path messagesFolderPath;
    private Path contactsFolderPath;
    private ChatApplication chatApplication;
    private NetworkAdapter networkAdapter;
    private volatile boolean isRunning;
    private SenderRunnable senderRunnable;
    private ReceiverRunnable receiverRunnable;

    /**
     * Metodo principal
     *
     * @param args argumentos do sistema.
     */
    public static void main(String[] args) {
        //Application.launch(ChatApplication.class, args);
        ContactDao contactDao = new ContactDao();


        ChatApplication app = ChatApplication.launchApplication(args, new RequestMediator(), contactDao);
        contactDao.setUserProfile(new Contact("Antonio", new Image("http://i.imgur.com/jAkOMcB.png"), "192.168.0.1"));
        app.openStartScene();
        //TODO Segundo main
    }

    /**
     * Envia um protocolo de mensagem.
     *
     * @param messageProtocol protocolo da mensagem.
     */
    public void sendProtocol(MessageProtocol messageProtocol) {
        //TODO Segundo sendProtocol
    }

    /**
     * Returna se a aplicação está rodando.
     *
     * @return
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Libera os recursos da aplicação e para as threads em background.
     */
    public void stopApplication() {
        //TODO Segundo stopApplication
    }

    /**
     * Envia uma mensagem para o receptor atual.
     *
     * @param address     endereço do receptor.
     * @param content     conteudo da mensagem.
     * @param messageType tipo da mensagem.
     */
    @Override
    public void sendMessage(String address, byte[] content, MessageType messageType) {
        //TODO Segundo sendMessage
    }

    /**
     * Envia uma mensagem de texto ao receptor atual.
     *
     * @param address endereço do receptor.
     * @param message mensagem.
     */
    @Override
    public void sendMessage(String address, String message) {
        //TODO Segundo sendMessage
    }

    /**
     * Envia uma requisição de contato a todos os usuários.
     *
     * @param name nome a ser buscado.
     */
    @Override
    public void requestContact(String name) {
        //TODO Segundo requestContact
    }

    /**
     * Carrega a lista de mensagens do contato específico.
     *
     * @param id identificador do usuário.
     */
    @Override
    public void loadMessageList(int id) {
    }

    /**
     * Para todos os serviços e fecha a aplicação.
     */
    @Override
    public void stopServices() {
        //TODO Segundo stopServices
    }

    /**
     * Abre a interface para criar o perfil.
     */
    private void openCreateProfile() {
        //TODO Segundo openCreateProfile
    }

    /**
     * Abre a interface principal do chat.
     */
    private void openHome() {
        //TODO Segundo openHome
    }

    /**
     * Carrega o perfil do usuário a partir da memória.
     *
     * @return
     */
    private String loadProfile() {
        return null;
    }

    /**
     * Carrega a lista de contatos do contato específico.
     *
     * @param id
     * @return
     */
    private List<String> loadContactList(int id) {
        return null;
    }

    /**
     * Inicia a thread receptora.
     */
    private void startReceiverThread() {
        //TODO segundo startReceiverThread
    }

    /**
     * Inicia a thread transmissora.
     */
    private void startSenderThread() {
        //TODO Segundo startSenderThread
    }

    /**
     * Inicia a thread do servidor.
     */
    private void startServerThread() {
        //TODO Segundo startServerThread
    }


}
