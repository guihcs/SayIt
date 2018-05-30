package com.sayit.control;

import com.sayit.data.MessageType;
import com.sayit.network.NetworkAdapter;

import java.util.List;
import java.util.Scanner;

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
        //fixme carregar do banco de dados o contactDao

        RequestMediator mediator = new RequestMediator();

        NetworkAdapter networkAdapter = mediator.getNetworkAdapter();

        Scanner s = new Scanner(System.in);

        System.out.println("receive?");

        if(s.equals("s")) {

            networkAdapter.acceptTCPConnection();


        } else {
            networkAdapter.connect(s.nextLine());
        }

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
        //fixme salvar no banco antes de fechar.
        this.isRunning = false;
        networkAdapter.closeAdapter();

    }

    /**
     * Envia uma mensagem para o receptor atual.
     *
     * @param address     endereço do receptor.
     * @param content     conteudo da mensagem.
     * @param fileName  nome do arquivo.
     */
    @Override
    public void sendMessage(String address, byte[] content, String fileName) {
        //TODO Segundo sendMessage
        RequestEvent event = new RequestEvent();
        event.setIdentifier(address);
        event.setContent(content);
        event.setMessageType(MessageType.ARCHIVE);
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
    * Envia os dados do usuário para um contato específico.
    *
    * @param receiverIp ip do usuário.
    * @param name nome do usuário.
    * @param image conteúdo da imagem.
    */
    @Override
    public void sendUserInfo(String receiverIp, String name, byte[] image){
        //TODO Segundo sendUserInfo
    }
    
    /**
     * Envia uma requisição de contato a todos os usuários.
     *
     * @param name nome a ser buscado.
     */
    @Override
    public void requestContact(String name) {

        networkAdapter.multicastString(name);
    }

    /**
     * Envia uma solicitação de adição para um outro contato.
     * @param ip endereço ip do novo contato.
     * @param userName nome do usuário atual.
     * @param imageBytes bytes da imagem do usuário atual.
     */
    @Override
    public void contactAdd(String ip, String userName, byte[] imageBytes) {
        //TODO Segundo contactAdd
        RequestEvent event = new RequestEvent();
        event.setIdentifier(ip);
        event.setMessage(userName);
        event.setContent(imageBytes);
        event.setEventType(EventType.REQUEST_CONTACT);

        senderRunnable.addEvent(event);
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
        stopApplication();
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
        Thread receiverThread = new Thread(receiverRunnable);
        receiverThread.start();
    }

    /**
     * Inicia a thread transmissora.
     */
    private void startSenderThread() {
        Thread senderThread = new Thread(senderRunnable);
        senderThread.start();
    }

    /**
     * Inicia a thread do servidor.
     */
    private void startServerThread() {
        Thread serverThread = new Thread(() -> {
            while(isRunning){
                networkAdapter.acceptTCPConnection();
            }
        });
        //serverThread.setDaemon(true);
        serverThread.start();
    }

    private void startMulticastServer(){
        Thread multicastServer = new Thread(() -> {
            while(isRunning){
                String name = networkAdapter.receiveMulticast();

                String address = networkAdapter.getPackageAddress();
                System.out.println("sending info");
                networkAdapter.connect(address);
                networkAdapter.setCurrentReceiver(address);
                networkAdapter.sendData(12);
                networkAdapter.flushData();
                System.out.println("info sended");
            }
        });
        //multicastServer.setDaemon(true);
        multicastServer.start();
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

