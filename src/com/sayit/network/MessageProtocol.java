package com.sayit.network;

public enum MessageProtocol {

    MESSAGE(1), CONTACT_INFO(2), ADD_REQUEST(3), ADD_RESPONSE(4), SEND_ARCHIVE(5);
    private int value;

    MessageProtocol(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
