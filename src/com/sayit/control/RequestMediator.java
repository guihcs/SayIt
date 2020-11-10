package com.sayit.control;

import com.sayit.network.Request;
import com.sayit.network.MessageProtocol;
import com.sayit.network.RequestTransmitter;
import com.sayit.network.discovery.DiscoveryServer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class RequestMediator implements Requestable {

    private AtomicBoolean isRunning;
    private DiscoveryServer discoveryServer;
    private RequestTransmitter requestTransmitter;
    private Thread receiverThread;
    private Thread senderThread;
    private final BlockingQueue<Request> blockingQueue = new LinkedBlockingQueue<>();
    private final List<RequestCallback> requestCallbacks = new LinkedList<>();

    public RequestMediator() {
        try {
            discoveryServer = new DiscoveryServer();
            requestTransmitter = new RequestTransmitter(6000);
            isRunning = new AtomicBoolean(true);
            senderThread = new Thread(this::sendRequest);
            receiverThread = new Thread(this::receiveRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        senderThread.start();
        receiverThread.start();
        discoveryServer.startListening();
    }


    private void receiveRequest() {

        while (isRunning.get()) {
            try {
                Request request = requestTransmitter.receiveRequest();

                for (RequestCallback requestCallback : requestCallbacks) {
                    requestCallback.requestCallback(request);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRequest() {
        while (isRunning.get()) {
            try {
                requestTransmitter.sendRequest(blockingQueue.take());
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void stopApplication() {
        isRunning.set(false);
        discoveryServer.stopDiscover();
    }

    public void stopDiscover() {
        discoveryServer.stopDiscover();
    }


    @Override
    public void sendMessage(Request request) {
        request.setProtocol(MessageProtocol.MESSAGE);
        putRequest(request);
    }

    private void putRequest(Request request) {
        try {
            blockingQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendContactDiscoveryResponse(Request request) {
        request.setProtocol(MessageProtocol.DISCOVERY_RESPONSE);
        putRequest(request);
    }

    @Override
    public void multicastContactNameDiscovery(String name) {
        try {
            discoveryServer.multicastData(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendContactAddRequest(Request request) {
        request.setProtocol(MessageProtocol.CONTACT_ADD_RESPONSE);
        putRequest(request);
    }

    @Override
    public void stopServices() {
        stopApplication();
    }


    public void addListener(RequestCallback requestCallback) {
        requestCallbacks.add(requestCallback);
    }

}

