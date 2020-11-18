package com.sayit.network.request;

public class Request {

    private MessageProtocol protocol;
    private byte[] data;
    private String address;

    public Request(byte[] data, String address) {
        this.data = data;
        this.address = address;
    }

    public Request(MessageProtocol messageProtocol, byte[] data, String address) {
        this.data = data;
        this.address = address;
    }

    public MessageProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(MessageProtocol protocol) {
        this.protocol = protocol;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Request{" +
                "protocol=" + protocol +
                ", address='" + address + '\'' +
                '}';
    }
}
