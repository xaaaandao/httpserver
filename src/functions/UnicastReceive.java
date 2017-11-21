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

    List<String> listOfFriends;
    
    public UnicastReceive(List<String> friends){
        listOfFriends = friends;
    }
    
    public boolean existFriend(String address){
        for(String add : listOfFriends){
            if(add.equalsIgnoreCase(address)){
                return true;
            }
        }
        return false;
    }
    
    public void addFriend(String address){
        if(!existFriend(address)){
            listOfFriends.add(address);
        }
    }

    public void printFriend(){
        for(String add : listOfFriends){
            System.out.println("Friend: "+add);
        }
        System.out.println("===========================");
    }
    
    public void receiveMessage() throws SocketException, IOException {
        int port = 1234;
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            System.out.println("Waiting packet");
            socket.receive(packet);
            String text = new String(buffer, 0, packet.getLength());
            if (text.equalsIgnoreCase("AD")) {
                System.out.println("Recebi a confirmação"+text);
                addFriend(packet.getAddress().toString());
                printFriend();
                System.out.println("add:"+packet.getAddress().toString());
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
