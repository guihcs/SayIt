package main;

import com.sayit.network.discovery.DiscoveryServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class DiscoveryServerTest {

    @Test
    public void t1() throws IOException {
        DiscoveryServer discoveryServer = new DiscoveryServer();

        discoveryServer.multicastData("hue");

        discoveryServer.addListener(e -> System.out.println(e.getSenderData()));
        discoveryServer.startListening();
    }


    @Test
    public void t2() throws Exception{
        String msg = "sayitdiscovery:123|cleitorasta";
        InetAddress group = InetAddress.getByName("239.239.239.239");
        MulticastSocket s = new MulticastSocket(7777);
        s.joinGroup(group);
        DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
                group, 7777);
        s.send(hi);
    }




}
