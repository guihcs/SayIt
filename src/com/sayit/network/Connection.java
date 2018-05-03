package com.sayit.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Connection {

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ConnectionStatus connectionStatus;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public boolean isOnline() {
        return false;
    }

    public void closeConnection() {

    }


}
