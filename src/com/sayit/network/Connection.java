package com.sayit.network;

import java.io.*;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ConnectionStatus connectionStatus;

    /**
     * Constroi uma nova conexão utilizando um socket.
     *
     * @param socket socket da conexão.
     */
    public Connection(Socket socket) {

        this.socket = socket;
        
        try {

            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constroi uma nova conexão com um estado prédefinido.
     *
     * @param socket
     * @param status
     */
    public Connection(Socket socket, ConnectionStatus status) {
        this.socket = socket;
        connectionStatus = status;
        try {

            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna a stream de entrada desta conexão.
     * @return
     */
    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    /**
     * Retorna a stream de saída dessa conexão.
     * @return
     */
    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    /**
     * Retorna o estado da conexão.
     * @return
     */
    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    /**
     * Retorna a atividade da conexão.
     * @return true caso a conexão esteja ativa. false caso esteja inativa.
     *
     */
    public boolean isOnline() {

        //TODO Iarly isOnline
        if(socket.isConnected()) {
            connectionStatus = ConnectionStatus.ACTIVE;
            return true;
        }
        connectionStatus = ConnectionStatus.CLOSED;
        return false;
    }

    /**
     * Fecha esta conexão.
     */
    public void closeConnection() {
        try {
            socket.close();
            connectionStatus = ConnectionStatus.CLOSED;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO Iarly closeConnection
    }


}
