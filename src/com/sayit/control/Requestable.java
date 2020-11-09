package com.sayit.control;

import com.sayit.message.Request;

public interface Requestable {

    void sendMessage(Request message);

    void sendContactDiscoveryResponse(Request contact);

    void multicastContactNameDiscovery(String name);

    void sendContactAddRequest(Request contact);

    void stopServices();
}
