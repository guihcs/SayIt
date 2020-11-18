package com.sayit.network.discovery;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DiscoveryServer {

    private static final String MULTICAST_ADDRESS = "239.239.239.239";
    private static final int MULTICAST_PORT = 7777;
    private static final String DISCOVER_MESSAGE_PREFIX = "sayitdiscovery";
    private static final int BUFFER_SIZE = 1024;
    private final String packetID = String.valueOf((int) (Math.random() * 10000));

    private final MulticastSocket multicastSocket;
    private final InetAddress multicastGroup;
    private final AtomicBoolean isDiscovering = new AtomicBoolean(false);
    private final List<DiscoveryCallback> discoveryListeners = new LinkedList<>();

    public DiscoveryServer() throws IOException {

        multicastSocket = new MulticastSocket(MULTICAST_PORT);
        multicastGroup = InetAddress.getByName(MULTICAST_ADDRESS);
        multicastSocket.joinGroup(multicastGroup);
    }

    public void multicastData(String data) throws IOException {

        String finalMessage = DISCOVER_MESSAGE_PREFIX + ":" + packetID + "|" + data;

        DatagramPacket packet = new DatagramPacket(
                finalMessage.getBytes(),
                finalMessage.getBytes().length,
                multicastGroup,
                MULTICAST_PORT);

        multicastSocket.send(packet);
    }

    public void startListening() {
        isDiscovering.set(true);

        while (isDiscovering.get()) {
            try {
                receiveDiscoveryData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void receiveDiscoveryData() throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        DatagramPacket datagramReceivePacket =
                new DatagramPacket(buffer, BUFFER_SIZE);

        multicastSocket.receive(datagramReceivePacket);

        String receivedData = new String(datagramReceivePacket.getData());

        String[] split = receivedData.split("[:|]");

        if (split.length != 3
                || !split[0].equals(DISCOVER_MESSAGE_PREFIX)
                || split[1].equals(packetID)) return;

        DiscoveryData discoveryData =
                new DiscoveryData(
                        datagramReceivePacket.getAddress().getHostAddress(),
                        split[2]);

        for (DiscoveryCallback discoveryListener : discoveryListeners) {
            discoveryListener.discoveryReceived(discoveryData);
        }
    }


    public void stopDiscover() {
        isDiscovering.set(false);
    }

    public void addListener(DiscoveryCallback callback) {
        discoveryListeners.add(callback);
    }

    public void close() {
        stopDiscover();
        multicastSocket.close();
    }
}
