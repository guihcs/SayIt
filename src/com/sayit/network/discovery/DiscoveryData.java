package com.sayit.network.discovery;

public class DiscoveryData {

    private final String senderAddress;
    private final String senderData;

    public DiscoveryData(String senderAddress, String senderData) {
        this.senderAddress = senderAddress;
        this.senderData = senderData;
    }


    public String getSenderAddress() {
        return senderAddress;
    }

    public String getSenderData() {
        return senderData;
    }
}
