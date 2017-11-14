package functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BroadcastReceive implements Runnable{

    /* Sempre verificar a interface */
    public String getMyIP() throws UnknownHostException, SocketException {
        String interfaceName = "wlp2s0";
        String ip = null;
        NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
        Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses();
        InetAddress currentAddress;
        currentAddress = inetAddress.nextElement();
        while (inetAddress.hasMoreElements()) {
            currentAddress = inetAddress.nextElement();
            if (currentAddress instanceof Inet4Address && !currentAddress.isLoopbackAddress()) {
                ip = currentAddress.toString();
                break;
            }
        }
        return ip;
    }

    public void receiveMessage() throws SocketException, IOException {
        int port = 6666;
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

       while (true) {
            System.out.println("Waiting packet");
            socket.receive(packet);
            System.out.println("add"+packet.getAddress());
            System.out.println("port"+packet.getPort());
            String text = new String(buffer, 0, packet.getLength());
            if (text.equalsIgnoreCase("oi")){
                /* Envia por unicast */

            }
            System.out.println(text);
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
