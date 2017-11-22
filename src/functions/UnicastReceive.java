/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.*;

/**
 *
 * @author xandao
 */
public class UnicastReceive implements Runnable {

    List<Friends> listOfFriends;
    
    public UnicastReceive(List<Friends> listOfFriends) {
        this.listOfFriends = listOfFriends;
    }

    public void receiveMessage() throws SocketException, IOException {
        int port = 1234;
        int sizeBuffer = 2048;
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[sizeBuffer];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            System.out.println("Waiting packet unicasting");
            socket.receive(packet);
            String text = new String(buffer, 0, packet.getLength());
            if (text.contains("AD")) {
                text = text.replace("AD", "");
                System.out.println("Recebi a confirmação" + text);
                Friends f = new Friends(packet.getAddress().toString(), Integer.parseInt(text));
                OperationsFriends opFriends = new OperationsFriends();
                opFriends.addFriend(listOfFriends, f);
                opFriends.printFriend(listOfFriends);
                System.out.println("add:" + packet.getAddress().toString());
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
