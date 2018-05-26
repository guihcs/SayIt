package com.sayit.control;

import com.sayit.data.ContactDao;
import com.sayit.data.MessageType;
import com.sayit.network.MessageProtocol;
import com.sayit.network.NetworkAdapter;

import java.nio.file.Path;
import java.util.List;

public class RequestMediator implements Requestable {

    private ChatApplication chatApplication;
    private NetworkAdapter networkAdapter;
    private volatile boolean isRunning;
    private SenderRunnable senderRunnable;
    private ReceiverRunnable receiverRunnable;

    public RequestMediator(){
        this.isRunning = true;
        this.networkAdapter = new NetworkAdapter();
        senderRunnable = new SenderRunnable(this);
        receiverRunnable = new ReceiverRunnable(this);


    }

    /**
     * Metodo principal
     *
     * @param args argumentos do sistema.
     */
    public static void main(String[] args) {
        //Application.launch(ChatApplication.class, args);
        ContactDao contactDao = new ContactDao();
        RequestMediator mediator = new RequestMediator();

        ChatApplication app = ChatApplication.launchApplication(args, new RequestMediator(), contactDao);
        
        //TODO Segundo main

        mediator.setChatApplication(app);
        app.openStartScene();

        var t = new Thread(() -> {
            while (mediator.isRunning){
                System.out.println(mediator.networkAdapter.receiveMulticast());
            }
        });
        t.setDaemon(true);
        t.start();
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
        isRunning = false;
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

        RequestEvent event = new RequestEvent();
        event.setIdentifier(address);
        event.setContent(content);
        event.setMessageType(messageType);
        event.setEventType(EventType.SEND_MESSAGE);

        senderRunnable.addEvent(event);

    }

    /**
     * Envia uma mensagem de texto ao receptor atual.
     *
     * @param address endereço do receptor.
     * @param message mensagem.
     */
    @Override
    public void sendMessage(String address, String message) {

        RequestEvent event = new RequestEvent();
        event.setMessage(message);
        event.setIdentifier(address);
        event.setMessageType(MessageType.TEXT);
        event.setEventType(EventType.SEND_MESSAGE);

        senderRunnable.addEvent(event);
    }

    /**
     * Envia uma requisição de contato a todos os usuários.
     *
     * @param name nome a ser buscado.
     */
    @Override
    public void requestContact(String name) {
        //TODO Segundo requestContact
        System.out.println("mediator: request contact");
        networkAdapter.multicastString(name);
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
        stopApplication();
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

    public NetworkAdapter getNetworkAdapter(){
        return networkAdapter;
    }

    public void setChatApplication(ChatApplication chatApplication){
        this.chatApplication = chatApplication;

    }

    public ChatApplication getChatApplication(){
        return chatApplication;
    }


}

