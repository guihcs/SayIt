package com.sayit.control;

import com.sayit.network.Request;
import com.sayit.network.MessageProtocol;
import com.sayit.network.RequestTransmitter;
import com.sayit.network.discovery.DiscoveryData;
import com.sayit.network.discovery.DiscoveryServer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class RequestMediator {

    private AtomicBoolean isRunning;
    private DiscoveryServer discoveryServer;
    private RequestTransmitter requestTransmitter;
    private Thread receiverThread;
    private Thread senderThread;
    private Thread multicastThread;
    private final BlockingQueue<Request> blockingQueue = new LinkedBlockingQueue<>();
    private final List<RequestCallback> requestCallbacks = new LinkedList<>();
    private final List<Consumer<DiscoveryData>> discoveryDataCallbacks = new LinkedList<>();

    public RequestMediator() {
        try {
            discoveryServer = new DiscoveryServer();
            requestTransmitter = new RequestTransmitter(6000);
            isRunning = new AtomicBoolean(true);
            senderThread = new Thread(this::sendRequest);
            receiverThread = new Thread(this::receiveRequest);
            multicastThread = new Thread(this::startListening);

            discoveryServer.addListener(this::receiveContactPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void start() {
        senderThread.start();
        receiverThread.start();
        multicastThread.start();
    }

    private void receiveContactPacket(DiscoveryData data){
        for (Consumer<DiscoveryData> discoveryDataCallback : discoveryDataCallbacks) {
            discoveryDataCallback.accept(data);
        }
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



    public void stopDiscover() {

        System.out.println("discover stopped.");
        discoveryServer.stopDiscover();
    }

    private void startListening(){
        discoveryServer.startListening();
    }

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

    public void sendContactDiscoveryResponse(Request request) {
        request.setProtocol(MessageProtocol.DISCOVERY_RESPONSE);
        putRequest(request);
    }


    public void multicastContactNameDiscovery(String name) {
        try {
            discoveryServer.multicastData(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendContactAddRequest(Request request){
        request.setProtocol(MessageProtocol.CONTACT_ADD_REQUEST);
        putRequest(request);
    }

    public void sendContactAddResponse(Request request) {
        request.setProtocol(MessageProtocol.CONTACT_ADD_RESPONSE);
        putRequest(request);
    }


    public void stopServices() {

        try {
            isRunning.set(false);
            discoveryServer.close();
            requestTransmitter.close();
            receiverThread.interrupt();
            senderThread.interrupt();
            multicastThread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void addRequestListener(RequestCallback requestCallback) {
        requestCallbacks.add(requestCallback);
    }

    public void addDiscoveryListener(Consumer<DiscoveryData> discoveryDataConsumer){
        discoveryDataCallbacks.add(discoveryDataConsumer);
    }
}

