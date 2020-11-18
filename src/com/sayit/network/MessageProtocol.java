package com.sayit.network;


import java.util.Map;

public enum MessageProtocol {

    MESSAGE(1), DISCOVERY_RESPONSE(2), CONTACT_ADD_REQUEST(3), CONTACT_ADD_RESPONSE(4), SEND_ARCHIVE(5);

    private static final Map<Integer, MessageProtocol> CAST_MAP =
            Map.of(1, MESSAGE
                    , 2, DISCOVERY_RESPONSE
                    , 3, CONTACT_ADD_REQUEST
                    , 4, CONTACT_ADD_RESPONSE
                    , 5, SEND_ARCHIVE);
    private final int value;


    MessageProtocol(int val) {
        value = val;
    }

    public static MessageProtocol castFrom(int i) {
        return CAST_MAP.get(i);
    }

    public int getValue() {
        return value;
    }


}
