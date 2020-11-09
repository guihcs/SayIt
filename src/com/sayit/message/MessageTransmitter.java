package com.sayit.message;

import com.sayit.control.RequestEvent;
import com.sayit.data.Contact;
import com.sayit.data.Message;
import com.sayit.data.MessageType;
import com.sayit.data.image.ImageBuilder;
import com.sayit.network.NetworkAdapter;
import com.sayit.network.RequestTransmitter;

public class MessageTransmitter {

    private NetworkAdapter networkAdapter;
    private RequestTransmitter requestTransmitter;

    public MessageTransmitter(NetworkAdapter networkAdapter) {
        this.networkAdapter = networkAdapter;
    }

    public void sendMessage(RequestEvent event) {
        switch (event.getMessageType()) {
            case TEXT:
                networkAdapter.setCurrentReceiver(event.getReceiverAddress());
                networkAdapter.sendData(event.getMessageProtocol().getValue());
                networkAdapter.sendData(event.getMessageType().getValue());
                networkAdapter.sendData(event.getTextMessage());
                networkAdapter.flushData();
                break;
        }
    }


    public void sendContactInfo(RequestEvent event) {
        networkAdapter.connect(event.getReceiverAddress());
        networkAdapter.setCurrentReceiver(event.getReceiverAddress());

        networkAdapter.sendData(event.getMessageProtocol().getValue());

        networkAdapter.sendData(event.getTextMessage());
//        networkAdapter.sendData(event.getImageHeight());
//        networkAdapter.sendData(event.getImageWidth());
//        networkAdapter.sendData(event.getContentSize());
//        networkAdapter.sendData(event.getByteContent());
        networkAdapter.flushData();
    }


    public Contact receiveResponse() {
        String contactName;
        byte[] imgBytes;
        String address;
        int height;
        int contentSize;
        int width;
        contactName = networkAdapter.receiveString();
        address = networkAdapter.getStringAddress();
        height = networkAdapter.receiveInt();
        width = networkAdapter.receiveInt();
        contentSize = networkAdapter.receiveInt();
        imgBytes = networkAdapter.receiveBytes(contentSize);
        return new Contact(contactName, ImageBuilder.buildImage(imgBytes, width, height), address);


    }

    public Contact receiveAddRequest() {
        String contactName;
        byte[] imgBytes;
        String address;
        int height;
        int contentSize;
        int width;
        contactName = networkAdapter.receiveString();
        address = networkAdapter.getStringAddress();
        height = networkAdapter.receiveInt();
        width = networkAdapter.receiveInt();
        contentSize = networkAdapter.receiveInt();
        imgBytes = networkAdapter.receiveBytes(contentSize);

        networkAdapter.closeConnection(address);
        return new Contact(contactName, ImageBuilder.buildImage(imgBytes, width, height), address);

    }

    public Contact receiveContactInfo() {
        String contactName = networkAdapter.receiveString();
        String address = networkAdapter.getStringAddress();
        int height = networkAdapter.receiveInt();
        int width = networkAdapter.receiveInt();
        int contentSize = networkAdapter.receiveInt();
        byte[] imgBytes = networkAdapter.receiveBytes(contentSize);

        networkAdapter.closeConnection(address);
        return new Contact(contactName, ImageBuilder.buildImage(imgBytes, width, height), address);
    }


    public Message receiveMessage(){
        MessageType messageType = MessageType.castFrom(networkAdapter.receiveInt());

        switch (messageType) {
            case TEXT:
                String textMessage = networkAdapter.receiveString();
                String senderAddress = networkAdapter.getStringAddress();

                Message message = new Message();
                message.setTextContent(textMessage);
                message.setSenderAddress(senderAddress);
                return message;
        }


        return null;
    }


}
