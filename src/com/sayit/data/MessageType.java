package com.sayit.data;

import java.util.Map;

public enum MessageType {

    TEXT(1), IMAGE(2), SOUND(3), ARCHIVE(4);

    private static final Map<Integer, MessageType> CAST_MAP =
            Map.of(1, TEXT
                    , 2, IMAGE
                    , 3, SOUND
                    , 4, ARCHIVE);
    private final int value;


    MessageType(int val) {
        value = val;
    }

    public static MessageType castFrom(int i) {
        return CAST_MAP.get(i);
    }

    public int getValue() {
        return value;
    }
}