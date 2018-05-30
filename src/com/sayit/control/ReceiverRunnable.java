package com.sayit.control;

import com.sayit.network.MessageProtocol;
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

                MessageProtocol messageProtocol = MessageProtocol.castFrom(networkAdapter.receiveInt());
                digestProtocol(messageProtocol);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void digestProtocol(MessageProtocol protocol) {
        switch (protocol) {
            case CONTACT_INFO:
                //contact info received
                String contactName = networkAdapter.receiveString();
                String address = networkAdapter.getStringAddress();
                int height = networkAdapter.receiveInt();
                int width = networkAdapter.receiveInt();
                int contentSize = networkAdapter.receiveInt();
                byte[] imgBytes = networkAdapter.receiveBytes(contentSize);

                context.getChatApplication().addContactRequest(contactName, address, imgBytes, width, height);
                networkAdapter.closeConnection(address);
                break;
        }
    }


}


