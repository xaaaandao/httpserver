package functions;

import java.io.*;
import java.net.*;
import java.util.*;

public class BroadcastReceive {

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
            String text = new String(buffer, 0, packet.getLength());
            if (text.contains("Broadcast ")){
                /* Se a mensagem que eu recebi for a acima, mando meu endereço ao solicitante */
                /* Pego o endereço que veio do solicitante, que veio junto com a mensagem */
                /* Como o ouvinte vai enviar? Se utilizar TCP, UDP ambos são bloqueante */
                String []packetReceived = text.split(" ");
            }
            packet.setLength(buffer.length);
        }
    }

}
