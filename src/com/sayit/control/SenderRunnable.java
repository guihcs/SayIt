package com.sayit.control;

import com.sayit.network.NetworkAdapter;

import java.util.LinkedList;

public class SenderRunnable implements Runnable {
    private RequestMediator context;
    private NetworkAdapter networkAdapter;
    private LinkedList<RequestEvent> eventList;


    public SenderRunnable(RequestMediator requestMediator){
        context = requestMediator;
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
}
