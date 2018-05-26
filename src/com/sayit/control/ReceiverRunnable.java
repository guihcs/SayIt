package com.sayit.control;

import com.sayit.data.MessageType;
import com.sayit.network.MessageProtocol;
import com.sayit.network.NetworkAdapter;

public class ReceiverRunnable implements Runnable {

    private RequestMediator context;
    //para facilitar uso
    private NetworkAdapter networkAdapter;
    private int protocolInt;


    public ReceiverRunnable(){
        this.context = new RequestMediator();
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
                MessageProtocol messageProtocol = MessageProtocol.castFrom(protocolInt);

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
                                //fixme resolver tamanho do getByte()
                                String name = networkAdapter.receiveString();
//                                int sizeInt = networkAdapter.receiveInt();
//                                byte[] arrayByte = networkAdapter.receiveBytes();
//
//                                context.getChatApplication().addMessage(arrayByte, name);
                                break;
                        }
                        break;

                    case CONTACT_INFO:
                        //fixme resolver tamanho do getByte()
                        String name = networkAdapter.receiveString();
//                        int sizeInt = networkAdapter.receiveInt();
//                        byte[] arrayByte = networkAdapter.receiveBytes();

                        break;

                    case ADD_REQUEST:

                        String nameRequest = networkAdapter.receiveString();
                        String adress = networkAdapter.getStringAddress();
                        byte[] bytes = networkAdapter.receiveBytes();

                        context.getChatApplication().addContactRequest(nameRequest, bytes, adress);
                        break;

                    case ADD_RESPONSE:
                        //fixme resolver metodo de envio para GUI
                        boolean response = networkAdapter.receiveBoolean();
                        break;
                }
            }
        }
    }
    }


