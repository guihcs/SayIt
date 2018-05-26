package com.sayit.network;


import java.io.IOException;
import java.net.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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


    private final String MCAST_ADDR = "239.239.239.239";
    private final int MCAST_DEST_PORT = 7777;
    private final int SERVER_SOCKET_DEST_PORT = 5000;
    private final int DT_SOCKET_DEST_PORT = 5001;
    private final int BUFFER_SIZE = 1024;

    /**
     * Instancia a interface Map com um LinkedHashMap.
     * Instancia a interface List com um LinkedList.
     * Cria um sevidor que escutará a porta 5000.
     *
     **/


    public NetworkAdapter() {

        connectionMap = new LinkedHashMap<>();
        connectionList = new LinkedList<>();

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
        if(!tmpReceiver.equals(null)){
            currentReceiver = tmpReceiver;
            return true;
        }
        return false;

    }

    /**
     * Busca o próximo transmissor na lista e o define como trasmissor atual.
     * A função só retorna verdadeiro caso o transmissor esteja online e possua mensagens
     * na stream.
     * Caso o transmissor esteja desconectado, fecha a conexão e retorna
     * false.
     *
     * @return true se a conexão está ativa ou
     * false se a conexão está inativa ou offline.
     * 
     */
    public boolean nextTransmitter() {

        for (Connection co : connectionList) {
            try {

                if (co.isOnline() && co.getDataInputStream().available() != 0) {
                    currentTransmitter = co;
                    return true;
                }else if (co.isOnline()){

                    ;
                }else {
                    co.closeConnection();
                    connectionList.remove(co);
                    connectionMap.remove(co.getpIPAddress());
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
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
            multicastSocket.send(packet);

        } catch (UnknownHostException e) {
            e.printStackTrace();

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

        byte[] BUFFER = new byte[BUFFER_SIZE];

        DatagramPacket datagramReceivePacket =  new DatagramPacket(BUFFER, BUFFER_SIZE);

        try {

            multicastSocket.receive(datagramReceivePacket);

            return new String(datagramReceivePacket.getData());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

        } catch (UnknownHostException e) {
            e.printStackTrace();
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
     * Recebe um array de bytes do trasmissor atual.
     *
     * @return array de bytes.
     *
     */
    public byte[] receiveBytes() {
        try {
            return currentTransmitter.isOnline() ? currentTransmitter.getDataInputStream().readAllBytes() : null;

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
     */
    public void closeAdapter() {
        try {
            serverSocket.close();
            multicastSocket.close();
            currentReceiver.closeConnection();
            currentTransmitter.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
