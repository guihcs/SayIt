package com.sayit.control;


import com.sayit.network.Request;
import com.sayit.network.RequestCallback;
import com.sayit.network.discovery.DiscoveryData;

import java.util.function.Consumer;

public interface ProtocolManager {

    void stopServices();

    void sendContactDiscoveryResponse(Request toRequest);

    void sendContactAddResponse(Request toRequest);

    void start();

    void addDiscoveryListener(Consumer<DiscoveryData> discoveryDataConsumer);

    void addRequestListener(RequestCallback requestCallback);

    void stopDiscover();

    void multicastContactNameDiscovery(String text);

    void sendMessage(Request toRequest);

    void sendContactAddRequest(Request toRequest);
}
