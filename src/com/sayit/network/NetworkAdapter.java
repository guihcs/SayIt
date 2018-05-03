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


    public void setCurrentReceiver(InetAddress address) {
    }

    public boolean nextTransmitter() {
        return false;
    }

    public void multicastString(String text) {
    }

    public void connect(InetAddress address) {

    }

    public void closeConnection(InetAddress address) {

    }

    public void acceptTCPConnection() {
    }

    public void sendData(int data) {
    }

    public void sendData(String data) {
    }

    public void sendData(byte[] data) {
    }

    public void sendData(boolean data) {
    }

    public void sendProtocol(MessageProtocol messageProtocol) {

    }

    public MessageProtocol receiveProtocol() {
        return null;
    }

    public int receiveInt() {
        return 0;
    }

    public String receiveString() {
        return null;
    }

    public byte[] receiveBytes() {
        return null;
    }

    public boolean receiveBoolean() {
        return false;
    }


}
