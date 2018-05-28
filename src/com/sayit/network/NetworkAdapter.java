package com.sayit.network;


import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NetworkAdapter {

    private ServerSocket serverSocket;
    private MulticastSocket multicastSocket;
    private Map<String, Connection> connectionMap;
    private List<Connection> connectionList;
    private Connection currentTransmitter;
    private Connection currentReceiver;
    private InetAddress multicastGrup;
    private DatagramPacket currentPackage;
    private int currentTransmitterControl;

    private final String MCAST_ADDR = "239.239.239.239";
    private final int MCAST_DEST_PORT = 7777;
    private final int SERVER_SOCKET_DEST_PORT = 5000;
    private final int DT_SOCKET_DEST_PORT = 5001;
    private final int BUFFER_SIZE = 1024;

    /**
     *
     * Instancia a interface Map com um LinkedHashMap.
     * Instancia a interface List com um LinkedList.
     * Cria um sevidor que escutará a porta 5000.
     *
     **/
    public NetworkAdapter() {

        connectionMap = new LinkedHashMap<>();
        connectionList = new ArrayList<>();
        currentTransmitterControl = 0;

        try {

            serverSocket = new ServerSocket(SERVER_SOCKET_DEST_PORT);

            multicastSocket = new MulticastSocket(MCAST_DEST_PORT);

            multicastGrup = InetAddress.getByName(MCAST_ADDR);

            multicastSocket.joinGroup(multicastGrup);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }


    /**
     * Retorna a representação string do IP do Transmissor atual.
     * @return IP do transmissor.
     */
    public String getStringAddress() {

        return currentTransmitter.getpIPAddress();

    }

    /**
     * Busca um receptor no mapa de conexões e o coloca como receptor atual.
     *
     * @param address Endereço da conexão.
     */
    public boolean setCurrentReceiver(String address) {

        Connection tmpReceiver = connectionMap.get(address);
        if(tmpReceiver != null) {

            currentReceiver = tmpReceiver;
            return true;
        }
        return false;

    }

    /**
     *
     * Busca o próximo transmissor na lista e o define como trasmissor atual.
     * A função só retorna verdadeiro caso o transmissor esteja online e possua mensagens
     * na stream.
     * Caso o transmissor esteja desconectado, fecha a conexão e retorna
     * false.
     * Fechar e remover todas as conexão offline
     *
     * @return true se a conexão está ativa ou
     * false se a conexão está inativa ou offline.
     *
     */
    public boolean nextTransmitter() {

        Connection connection = connectionList.get(currentTransmitterControl);

        if(connection.isOnline()){
            try {
                if(connection.getDataInputStream().available() !=0){

                    currentTransmitter = connection;
                    currentTransmitterControl++;

                    if(connectionList.size() == currentTransmitterControl){
                        currentTransmitterControl = 0;
                    }

                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();

            }
        }else {

            connection.closeConnection();
            connectionList.remove(connection);
            connectionMap.remove(connection.getpIPAddress());

        }

        return false;
    }

    /**
     * Envia o texto para todos no grupo de multicasting.
     *
     * @param text Nome a ser buscado na rede.
     */
    public void multicastString(String text) {


        byte[] multicastMessage = text.getBytes();


        try {

            DatagramPacket packet = new DatagramPacket(multicastMessage, multicastMessage.length, multicastGrup, MCAST_DEST_PORT);
            currentPackage = packet;
            multicastSocket.send(packet);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * Recebe uma string do grupo multicast.
     *
     * @return string do grupo multicast. null caso nenhuma.
     *
     */
    public String receiveMulticast() {
        //TODO Iarly receiveMulticast
        byte[] BUFFER = new byte[BUFFER_SIZE];

        DatagramPacket datagramReceivePacket =  new DatagramPacket(BUFFER, BUFFER_SIZE);

        try {

            multicastSocket.receive(datagramReceivePacket);
            //fixme corrigir envio utf8 nos packets (acentos no texto)
            //fixme setar pacote atual
            return new String(datagramReceivePacket.getData());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retorna o endereço ip de quem enviou o datagram packet atual.
     *
     * @return string contendo endereço ip do pacote.
     */
    public String getPackageAddress() {
        return currentPackage.getAddress().toString();
    }


    /**
     *
     * Inicia uma nova conexão TCP através do IP especificado
     * e a adciona na lista de conecções.
     *
     * @param ipAddress Endereço ip do socket.
     *
     */
    public void connect(String ipAddress) {

        try {

            Socket client = new Socket(InetAddress.getByName(ipAddress),SERVER_SOCKET_DEST_PORT);
            Connection connection = new Connection(client);
            connectionList.add(connection);
            connectionMap.put(connection.getpIPAddress(),connection);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Busca uma conexão na lista e fecha a conexão. Usado também para
     * fechar as conexões inativas.
     *
     * @param address Endereço da conexão.
     */
    public void closeConnection(String address) {

        connectionMap.get(address).closeConnection();

    }

    /**
     * Aceita uma nova conecção à partir do server socket.
     * Encapsula o socket em uma classe connection e adiciona nas listas.
     */
    public void acceptTCPConnection() {

        try {
            Connection connection = new Connection(serverSocket.accept());
            connectionList.add(connection);
            connectionMap.put(connection.getpIPAddress(),connection);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Envia informação para o receptor atual.
     *
     * @param data envia um inteiro.
     *
     */
    public void sendData(int data) {

        try {
            currentReceiver.getDataOutputStream().write(data);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    /**
     * Envia informação para o receptor atual.
     *
     * @param data envia uma String.
     */
    public void sendData(String data) {
        try {
            currentReceiver.getDataOutputStream().writeUTF(data);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Envia informação para o receptor atual.
     *
     * @param data envia um array de bytes.
     */
    public void sendData(byte[] data) {
        try {
            currentReceiver.getDataOutputStream().write(data);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Envia informação para o receptor atual.
     *
     * @param data envia um valor booleano.
     */
    public void sendData(boolean data) {

        try {
            currentReceiver.getDataOutputStream().writeBoolean(data);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Recebe um valor inteiro do transmissor atual.
     *
     * @return um valor inteiro.
     */
    public int receiveInt() {
        try {
            return currentTransmitter.isOnline() ? currentTransmitter.getDataInputStream().readInt() : 0;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Recebe um texto do transmissor atual.
     *
     * @return uma string.
     */
    public String receiveString() {

        try {
            return currentTransmitter.isOnline() ? currentTransmitter.getDataInputStream().readUTF() : null;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * Recebe um array de bytes de tamanho específico do trasmissor atual.
     * O parametro expecifíca a quantidade de bytes a ser lido do stream.
     *
     * @return array de bytes.
     * @param size quantidade de informação a ser recebida.
     */
    public byte[] receiveBytes(int size) {
        //TODO Iarly receiveBytes
        //fixme ajustar metodo.
        byte[] returnBytes = new byte[size];
        byte[] currentBytes = null;
        int len = size;
        try {
            if(currentTransmitter.isOnline())

                while(currentTransmitter.getDataInputStream().read(returnBytes) > 0){

                }

            return returnBytes;
        } catch (IOException e) {
            e.printStackTrace();

        }

        return null;

    }

    /**
     * Recebe um valor boleano do transmissor atual.
     *
     * @return um valor boleano.
     */
    public boolean receiveBoolean() {

        try {
            return (currentTransmitter.isOnline() && currentTransmitter.getDataInputStream().readBoolean());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Encerra o adaptador liberando todos os recursos
     * e fechando todas as conexões.
     *
     */
    public void closeAdapter() {

        try {

            serverSocket.close();
            multicastSocket.close();

            if(currentReceiver != null)
                currentReceiver.closeConnection();
            if(currentTransmitter != null)
                currentTransmitter.closeConnection();

        } catch (IOException e) {
            e.printStackTrace();

        }


    }


}
