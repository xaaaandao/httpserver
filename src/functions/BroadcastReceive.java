package functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class BroadcastReceive implements Runnable {

    List <Friends> listOfFriends;
    int myPortHttp;
    int portUnicast;
    
    public BroadcastReceive(List <Friends> listOfFriends, int portHttp, int portUnicast){
        this.listOfFriends = listOfFriends;
        this.myPortHttp = portHttp;
        this.portUnicast = portUnicast;
    }
    
    public void receiveMessage() throws SocketException, IOException {
        int port = 6666;
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) { 
            System.out.println("Waiting packet multicast");
            socket.receive(packet);
            String text = new String(buffer, 0, packet.getLength());
            if (text.contains("SD")) {
                System.out.println("Recebi pacote em multicast, a mensagem: " + text);
                text = text.replace("SD", "");
                String []ports = text.split(" ");
                UnicastSend us = new UnicastSend();
                this.portUnicast = Integer.parseInt(ports[0]);
                us.sendMessage(listOfFriends, packet.getAddress(), Integer.parseInt(ports[0]), Integer.parseInt(ports[1]), myPortHttp);
            }
            packet.setLength(buffer.length);
        }
    }

    @Override
    public void run() {
        try {
            receiveMessage();
        } catch (IOException ex) {
            Logger.getLogger(BroadcastReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
