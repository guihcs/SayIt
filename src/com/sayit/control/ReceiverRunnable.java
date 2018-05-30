package com.sayit.control;

import com.sayit.network.NetworkAdapter;

public class ReceiverRunnable implements Runnable {

    private RequestMediator context;
    //para facilitar uso
    private NetworkAdapter networkAdapter;
    private int protocolInt;


    public ReceiverRunnable(RequestMediator requestMediator){
        this.context = requestMediator;
        this.networkAdapter = context.getNetworkAdapter();
    }

    /**
     * Inicia as funções de recepção de mensagem.
     */
    @Override
    public void run() {
        //TODO Segundo run
        while(context.isRunning()){
            if(networkAdapter.nextTransmitter()){
                System.out.println("receiving int");
                System.out.println(networkAdapter.receiveInt());
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


