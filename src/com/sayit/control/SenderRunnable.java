package com.sayit.control;

import com.sayit.network.MessageProtocol;
import com.sayit.network.NetworkAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SenderRunnable implements Runnable {
    private RequestMediator context;
    private NetworkAdapter networkAdapter;
    private LinkedList<RequestEvent> eventList;


    public SenderRunnable(){
        context = new RequestMediator();
        networkAdapter = context.getNetworkAdapter();
        eventList = new LinkedList<>();
    }
    /**
     * Inicia a função de transmissão.
     */
    @Override
    public void run() {
        //TODO Segundo run
        while(context.isRunning()){
            if(eventList.size() > 0){
                RequestEvent event = eventList.getFirst();
                EventType eventType = event.getEventType();

                switch (eventType){

                    case SEND_MESSAGE:
                        boolean isAvaliable = networkAdapter.setCurrentReceiver(event.getIdentifier());

                        if(isAvaliable){
                            int protocol = MessageProtocol.MESSAGE.getValue();
                            MessageProtocol messageProtocol = MessageProtocol.castFrom(protocol);

                            switch(messageProtocol){

                                case MESSAGE:
                                    networkAdapter.sendData(protocol);
                                    int type = event.getMessageType().getValue();
                                    networkAdapter.sendData(type);
                                    networkAdapter.sendData(event.getMessage());
                                    eventList.removeFirst();
                                    break;

                                case ADD_RESPONSE:
                                    //fixme resolver o envio de solicitaçao.
                                    break;

                                case ADD_REQUEST:
                                    networkAdapter.sendData(event.getMessage());
                                    networkAdapter.sendData(event.getId());
                                    networkAdapter.sendData(event.getContent());
                                    eventList.removeFirst();
                                    break;

                                case CONTACT_INFO:
                                    networkAdapter.sendData(event.getMessage());
                                    networkAdapter.sendData(event.getId());
                                    networkAdapter.sendData(event.getContent());
                                    eventList.removeFirst();
                                    break;

                                default:
                                    networkAdapter.sendData(event.getMessage());
                                    networkAdapter.sendData(event.getId());
                                    networkAdapter.sendData(event.getContent());
                                    eventList.removeFirst();
                                    break;
                            }
                        }
                        break;

                    case REQUEST_CONTACT:
                        break;

                    case LOAD_MESSAGE_LIST:
                        break;

                }
            }

        }
    }

    public void addEvent(RequestEvent event){
        eventList.add(event);
    }
}
