package com.sayit.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestTransmitter {

    private final ServerSocket serverSocket;
    private final int connectionPort;

    public RequestTransmitter(int port) throws IOException {
        this.connectionPort = port;
        serverSocket = new ServerSocket(connectionPort);
    }

    public void sendRequest(Request request) throws IOException {

        Socket socket = new Socket(request.getAddress(), connectionPort);

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(request.getProtocol().getValue());
        outputStream.write(request.getData().length);
        outputStream.write(request.getData());
        socket.getOutputStream().flush();
        socket.close();
    }

    public Request receiveRequest() throws IOException {

        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();

        MessageProtocol messageProtocol = MessageProtocol.castFrom(inputStream.read());
        int dataLength = inputStream.read();
        byte[] data = inputStream.readNBytes(dataLength);

        return new Request(messageProtocol, data, socket.getInetAddress().getHostAddress());
    }


    public void close() throws IOException {
        serverSocket.close();
    }

}
