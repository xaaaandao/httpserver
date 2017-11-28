package functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class BroadcastReceive implements Runnable {

    List<Friends> listOfFriends;
    int portHttp;
    
    public BroadcastReceive(List<Friends> friends, int port){
        listOfFriends = friends;
        portHttp = port;
    }
    
    public void responseServer(InetAddress address, int port, int portFriend) throws IOException {
        System.out.println("Endereço para quem to enviando: "+ address.toString());
        String message = "AD" + Integer.toString(portHttp);
        byte[] confirmMessage = message.getBytes();
        DatagramSocket confirm = new DatagramSocket();
        SocketAddress socket = new InetSocketAddress(address, port);
        DatagramPacket packet = new DatagramPacket(confirmMessage, confirmMessage.length, socket);
        confirm.send(packet);
        confirm.close();
        Friends f = new Friends(address.toString(), portFriend);
        new Friends().addFriend(listOfFriends, f);
        if(listOfFriends.size() == 0){
            System.out.println("lista vazia!");
        }
        new Friends().printList(listOfFriends);
        //new UnicastReceive(listOfFriends).addFriend(address);
        //new UnicastReceive(listOfFriends).printFriend();
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
                String[] ports = text.split(" ");
                responseServer(packet.getAddress(), Integer.parseInt(ports[0]), Integer.parseInt(ports[1]));
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
