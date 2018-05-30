package com.sayit.control;

import com.sayit.data.MessageType;
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
                protocolInt = networkAdapter.receiveInt();
                System.out.println(protocolInt);
                MessageProtocol messageProtocol = MessageProtocol.castFrom(protocolInt);
                System.out.println("Info received");
                switch (messageProtocol){

                    case MESSAGE:
                        int typeInt = networkAdapter.receiveInt();
                        MessageType messageType = MessageType.castFrom(typeInt);

                        switch (messageType){

                            case TEXT:
                                String message = networkAdapter.receiveString();
                                String ipAdress = networkAdapter.getStringAddress();

                                context.getChatApplication().addMessage(ipAdress, message);
                                break;


                            default:
                                String name = networkAdapter.receiveString();
                                Integer sizeInt = networkAdapter.receiveInt();
                                byte[] arrayByte = networkAdapter.receiveBytes(sizeInt);

                                context.getChatApplication().addMessage(sizeInt.toString(),arrayByte, name);
                                break;
                        }
                        break;

                    case CONTACT_INFO:
                        System.out.println("received info");
                        System.out.println(networkAdapter.receiveInt());

                        break;

                    case ADD_REQUEST:

                        String nameRequest = networkAdapter.receiveString();
                        String adress = networkAdapter.getStringAddress();
                        byte[] bytes = networkAdapter.receiveBytes(0);

                        context.getChatApplication().addContact(nameRequest, bytes, adress);
                        break;

                    case ADD_RESPONSE:
                        //fixme resolver metodo de envio para GUI
                        boolean response = networkAdapter.receiveBoolean();

                        break;
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


