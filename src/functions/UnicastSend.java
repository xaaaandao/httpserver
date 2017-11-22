package functions;

import java.io.*;
import java.net.*;
import java.util.*;

public class UnicastSend {
    
    public void sendMessage(List <Friends> listOfFriends, InetAddress address, int portUnicast, int portHttp, int myPortHttp) throws SocketException, IOException{
        System.out.println("Endereço para quem eu to enviando multicast: " + address.toString());
        String message = "AD" + Integer.toString(myPortHttp); 
        byte[] confirmMessage = message.getBytes();
        DatagramSocket dSocket = new DatagramSocket();
        SocketAddress sAddress = new InetSocketAddress(address, portUnicast);
        DatagramPacket packet = new DatagramPacket(confirmMessage, confirmMessage.length, sAddress);
        dSocket.send(packet);
        dSocket.close();
        Friends f = new Friends(packet.getAddress().toString(), portHttp);
        OperationsFriends opFriends = new OperationsFriends();
        opFriends.addFriend(listOfFriends, f);
        opFriends.printFriend(listOfFriends);
        System.out.println("Respondi com: " + message);
    }
    
}
