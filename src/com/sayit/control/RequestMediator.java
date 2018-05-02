package com.sayit.control;

import com.sayit.data.MessageType;
import com.sayit.network.MessageProtocol;
import com.sayit.network.NetworkAdapter;

import java.net.InetAddress;
import java.nio.file.Path;
import java.util.List;

public class RequestMediator implements Requestable {

    private Path historyFolderPath;
    private Path messagesFolderPath;
    private Path contactsFolderPath;
    private ChatApplication chatApplication;
    private NetworkAdapter networkAdapter;
    private volatile boolean isRunning;
    private SenderRunnable senderRunnable;
    private ReceiverRunnable receiverRunnable;

    public static void main(String[] args) {

    }

    public void sendProtocol(MessageProtocol messageProtocol) {

    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stopApplication() {

    }

    @Override
    public void sendMessage(InetAddress address, byte[] content, MessageType messageType) {

    }

    @Override
    public void requestContact(String name) {

    }

    @Override
    public void loadMessageList(int id) {

    }

    @Override
    public void stopServices() {

    }

    private void openCreateProfile() {

    }

    private void openHome() {

    }

    private String loadProfile() {
        return null;
    }

    private List<String> loadContactList(int id) {
        return null;
    }

    private void startReceiverThread() {

    }

    private void startSenderThread() {

    }

    private void startServerThread() {
    }




}
