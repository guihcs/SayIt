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
    private DatagramSocket datagramSocket;
    private Map<InetAddress, Connection> connectionMap;
    private List<Connection> connectionList;
    private Connection currentTransmitter;
    private Connection currentReceiver;

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
            ServerSocket serverSocket = new ServerSocket(5000);

        } catch (IOException e) {
            e.printStackTrace();

        }

        try {
            multicastSocket = new MulticastSocket(5001);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            datagramSocket = new DatagramSocket();

        } catch (SocketException e) {
            e.printStackTrace();
        }


    }

    /**
     * Converte um IP de texto em um objeto InetAddress.
     *
     * @param internetProtocol IP a ser convertido.
     * @return Um objeto InetAddress com o mesmo IP.
     */
    public static InetAddress parseAddress(String internetProtocol) {

        try {
            return InetAddress.getByName(internetProtocol);

        } catch (UnknownHostException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * Converte um InetAddress em sua representação String.
     *
     * @param address Objeto para conversão.
     * @return Representação string do objeto.
     */

    public static String getStringAddress(InetAddress address) {
        return address.getHostAddress();
    }

    /**
     * Busca um receptor no mapa de conexões e o coloca como receptor atual.
     *
     * @param address Endereço da conexão.
     */
    public void setCurrentReceiver(InetAddress address) {
        currentReceiver = connectionMap.get(address);
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
     */
    public boolean nextTransmitter() {

        for (Connection co : connectionList) {
            try {
                if (co.isOnline() && !co.getDataInputStream().readAllBytes().equals(null)) {
                    currentTransmitter = co;
                    return true;
                } else {
                    co.closeConnection();
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


        multicastSocket.close();
        //TODO Iarly multicastString
    }

    /**
     *
     * Recebe uma string do grupo multicast.
     *
     * @return string do grupo multicast. null caso nenhuma.
     *
     */
    public String receiveMulticast() {


        multicastSocket.close();
        //TODO Iarly receiveMulticast
        return null;
    }

    /**
     *
     * Inicia uma nova conexão.
     *
     * @param address Endereço do socket.
     *
     */
    public void connect(InetAddress address) {



        //TODO Iarly connect
    }

    /**
     * Busca uma conexão na lista e fecha a conexão. Usado também para
     * fechar as conexões inativas.
     *
     * @param address Endereço da conexão.
     */
    public void closeConnection(InetAddress address) {

        connectionMap.get(address).closeConnection();

    }

    /**
     * Aceita uma nova conexão TCP e adiciona nas listas.
     */
    public void acceptTCPConnection() {


        //TODO Iarly acceptTCPConnection
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
     * Envia um protocolo de mensagem para o receptor atual.
     * Esse protocolo descreve os tipos de dados a serem enviados.
     * É o primeiro tipo de informação a ser enviada para comunicação.
     *
     * @param messageProtocol Protocolo de mensagem.
     */
    public void sendProtocol(MessageProtocol messageProtocol) {


        //TODO Iarly sendProtocol
    }

    /**
     * Recebe um protocolo de mensagem informando os tipos de dados a receber.
     *
     * @return Um protocolo de mensagem.
     */
    public MessageProtocol receiveProtocol() {

        //TODO Iarly receiveProtocol
        return null;
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
     * Recebe um array de bytes do trasmissor atual.
     *
     * @return array de bytes.
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
     * Recebe um valor boleano do receptor atual.
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


}
