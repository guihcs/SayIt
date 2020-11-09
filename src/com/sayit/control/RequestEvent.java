package com.sayit.control;

import com.sayit.data.MessageType;
import com.sayit.network.MessageProtocol;
import javafx.scene.image.Image;

public class RequestEvent {

    private final EventType eventType;
    private final MessageProtocol messageProtocol;
    private MessageType messageType;

    private String receiverAddress;
    private String textMessage;

    private Image image;


    public RequestEvent(EventType eventType, MessageProtocol messageProtocol) {
        this.eventType = eventType;
        this.messageProtocol = messageProtocol;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public EventType getEventType() {
        return eventType;
    }

    public MessageProtocol getMessageProtocol() {
        return messageProtocol;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
