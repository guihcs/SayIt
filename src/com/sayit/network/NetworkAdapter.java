package com.sayit.network;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
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
     * Converte um IP de texto em um objeto InetAddress.
     *
     * @param text Texto a ser convertido.
     * @return Um objeto InetAddress com o mesmo ip.
     */
    public static InetAddress parseAddress(String text) {
        //TODO Iarly parseAddress
        return null;
    }

    /**
     * Converte um InetAddress em sua representação String.
     *
     * @param address Objeto para conversão.
     * @return Representação string do objeto.
     */
    public static String getStringAddress(InetAddress address) {
        //TODO Iarly getStringAddress
        return null;
    }

    /**
     * Busca um receptor no mapa de conexões e o coloca como receptor atual.
     *
     * @param address Endereço da conexão.
     */
    public void setCurrentReceiver(InetAddress address) {
        //TODO Iarly setCurrentReceiver
    }

    /**
     * Busca o próximo transmissor na lista e o define como trasmissor atual.
     * A função só retorna verdadeiro caso o transmissor esteja online e possua mensagens
     * na stream.
     * Caso o transmissor esteja desconectado, fecha a conexão e retorna
     * false.
     * @return true se a conexão está ativa ou
     * false se a conexão está inativa ou offline.
     *
     */
    public boolean nextTransmitter() {
        //TODO Iarly nextTransmitter
        return false;
    }

    /**
     * Envia o texto para todos no grupo de multicasting.
     * @param text Nome a ser buscado na rede.
     */
    public void multicastString(String text) {
        //TODO Iarly multicastString
    }

    /**
     * Recebe uma string do grupo multicast.
     * @return string do grupo multicast. null caso nenhuma.
     */
    public String receiveMulticast() {
        //TODO Iarly receiveMulticast
        return null;
    }

    /**
     * Inicia uma nova conexão.
     *
     * @param address Endereço do socket.
     */
    public void connect(InetAddress address) {
        //TODO Iarly connect
    }

    /**
     * Busca uma conexão na lista e fecha a conexão. Usado também para
     * fechar as conexões inativas.
     * @param address Endereço da conexão.
     */
    public void closeConnection(InetAddress address) {
        //TODO Iarly closeConnection
    }

    /**
     * Aceita uma nova conexão TCP e adiciona nas listas.
     */
    public void acceptTCPConnection() {
        //TODO Iarly acceptTCPConnection
    }

    /**
     * Envia informação para o receptor atual.
     * @param data envia um inteiro.
     */
    public void sendData(int data) {
        //TODO Iarly sendData int
    }

    /**
     * Envia informação para o receptor atual.
     * @param data envia uma String.
     */
    public void sendData(String data) {
        //TODO Iarly sendData string
    }

    /**
     * Envia informação para o receptor atual.
     * @param data envia um array de bytes.
     */
    public void sendData(byte[] data) {
        //TODO Iarly sendData byte[]
    }

    /**
     * Envia informação para o receptor atual.
     * @param data envia um valor booleano.
     */
    public void sendData(boolean data) {
        //TODO Iarly sendData boolean
    }

    /**
     * Envia um protocolo de mensagem para o receptor atual.
     * Esse protocolo descreve os tipos de dados a serem enviados.
     * É o primeiro tipo de informação a ser enviada para comunicação.
     * @param messageProtocol Protocolo de mensagem.
     */
    public void sendProtocol(MessageProtocol messageProtocol) {
        //TODO Iarly sendProtocol
    }

    /**
     * Recebe um protocolo de mensagem informando os tipos de dados a receber.
     * @return Um protocolo de mensagem.
     */
    public MessageProtocol receiveProtocol() {
        //TODO Iarly receiveProtocol
        return null;
    }

    /**
     * Recebe um valor inteiro do transmissor atual.
     * @return um valor inteiro.
     */
    public int receiveInt() {
        //TODO Iarly receiveInt
        return 0;
    }

    /**
     * Recebe um texto do transmissor atual.
     * @return uma string.
     */
    public String receiveString() {
        //TODO Iarly receiveString
        return null;
    }

    /**
     * Recebe um array de bytes do trasmissor atual.
     * @return array de bytes.
     */
    public byte[] receiveBytes() {
        //TODO Iarly receiveBytes
        return null;
    }

    /**
     * Receve um valor boleano do receptor atual.
     * @return um valor boleano.
     */
    public boolean receiveBoolean() {
        //TODO Iarly receiveBoolean
        return false;
    }


}
