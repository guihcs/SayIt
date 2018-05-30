package com.sayit.control;

import com.sayit.network.NetworkAdapter;

import java.util.LinkedList;

public class SenderRunnable implements Runnable {
    private RequestMediator context;
    private NetworkAdapter networkAdapter;
    private final LinkedList<RequestEvent> eventList = new LinkedList<>();


    public SenderRunnable(RequestMediator requestMediator){
        context = requestMediator;
        networkAdapter = context.getNetworkAdapter();
    }
    /**
     * Inicia a função de transmissão.
     */
    @Override
    public void run() {
        while(context.isRunning()){
            if(eventList.size() > 0){

                digestEvent(eventList.getFirst());
                eventList.removeFirst();
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addEvent(RequestEvent event){
        eventList.add(event);

    }

    private void digestEvent(RequestEvent event) {
        networkAdapter.setCurrentReceiver(event.getReceiverAddress());
        switch (event.getEventType()) {
            case SEND_MESSAGE:
                digestMessageEvent(event);
                break;
        }

        networkAdapter.flushData();
    }


    private void digestMessageEvent(RequestEvent event) {
        networkAdapter.sendData(event.getMessageProtocol().getValue());
        switch (event.getMessageProtocol()) {
            case CONTACT_INFO:
                //send contact info
                networkAdapter.sendData(event.getTextMessage());
                networkAdapter.sendData(event.getImageHeight());
                networkAdapter.sendData(event.getImageWidth());
                networkAdapter.sendData(event.getContentSize());
                networkAdapter.sendData(event.getByteContent());
                break;
        }
    }
}
