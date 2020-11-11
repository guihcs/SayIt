package com.sayit.control;

import com.sayit.network.Request;

public interface Requestable {

    void sendMessage(Request message);

    void sendContactDiscoveryResponse(Request contact);

    void multicastContactNameDiscovery(String name);

    void sendContactAddResponse(Request contact);

    void stopServices();
}
