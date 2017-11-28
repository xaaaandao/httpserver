package functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class BroadcastReceive implements Runnable {

    List<String> listOfFriends;
    
    public BroadcastReceive(List<String> friends){
        listOfFriends = friends;
    }
    
    public void responseServer(String address, InetAddress addressSend, int port) throws IOException {
        System.out.println("Endereço para quem to enviando: "+ address);
        String message = "AD";
        byte[] confirmMessage = message.getBytes();
        DatagramSocket confirm = new DatagramSocket();
        SocketAddress socket = new InetSocketAddress(addressSend, port);
        DatagramPacket packet = new DatagramPacket(confirmMessage, confirmMessage.length, socket);
        confirm.send(packet);
        confirm.close();
        new UnicastReceive(listOfFriends).addFriend(address);
        new UnicastReceive(listOfFriends).printFriend();
        System.out.println("Respondi por unicast com a seguinte mensagem:" + message);
    }

    public void receiveMessage() throws SocketException, IOException {
        int port = 6666;
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            System.out.println("Waiting packet broadcast");
            socket.receive(packet);
            String text = new String(buffer, 0, packet.getLength());
            if (text.contains("SD")) {
                /* Envia por unicast */
                System.out.println("Recebi por broadcast: " + text);
                text = text.replace("SD", "");
                responseServer(packet.getAddress().toString(), packet.getAddress(), Integer.parseInt(text));
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
