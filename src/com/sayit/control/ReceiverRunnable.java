package com.sayit.control;

import com.sayit.network.MessageProtocol;
import com.sayit.network.NetworkAdapter;

public class ReceiverRunnable implements Runnable {

    private RequestMediator context;
    private NetworkAdapter networkAdapter;
    private MessageProtocol protocol;

    /**
     * Inicia as funções de recepção de mensagem.
     */
    @Override
    public void run() {
        //TODO Segundo run
        while(context.isRunning()){
            if(networkAdapter.nextTransmitter()){
                //protocol = networkAdapter.receiveInt();

                switch (protocol){

                    case MESSAGE:
                }
            }
        }
    }

    private void receiveMessage(){

        }
    }


