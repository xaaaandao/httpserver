package functions;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.*;

/**
 * Descrição: fica ouvindo por unicasting as mensagens vindas por ele, por meio
 * de uma thread.
 * Autor: Alexandre Yuji Kajihara
 */
public class UnicastReceive implements Runnable {

    List<Friends> listOfFriends;

    /**
     * O UnicastReceive(List<Friends> friends) é apenas um construtor.
     *
     * @param friends lista de servidores amigos onde procure o recurso caso não
     * encontre localmente.
     */
    public UnicastReceive(List<Friends> friends) {
        listOfFriends = friends;
    }

    /**
     * O receiveMessage() fica agurdando a mensagem vinda por unicast, e
     * adiciona o servidor que recebeu a mensagem na lista.
     *
     * @return retorna void, ou seja, nada.
     */
    public void receiveMessage() throws SocketException, IOException {
        int port = 1234;
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            System.out.println("Waiting packet a unicast");
            socket.receive(packet);
            String text = new String(buffer, 0, packet.getLength());
            if (text.contains("AD")) {
                System.out.println("Recebi a confirmação por unicast " + text);
                text = text.replace("AD", "");
                text = text.replace("\n", "");
                Friends f = new Friends(packet.getAddress().toString(), Integer.parseInt(text));
                new Friends().addFriend(listOfFriends, f);
                new Friends().printList(listOfFriends);
                System.out.println("Endereço de quem eu recebi:" + packet.getAddress().toString());
            }
            packet.setLength(buffer.length);
        }
    }

    /**
     * O run() que é uma thread que fica ouvindo as mensagens vindas de unicast.
     *
     * @return retorna void, ou seja, nada.
     */
    @Override
    public void run() {
        try {
            receiveMessage();
        } catch (IOException ex) {
            Logger.getLogger(BroadcastReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
