package functions;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class BroadcastSend {
    
    public InetAddress getAddressBroadcast() throws SocketException{
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()){
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback()){
                continue;
            }
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast == null) {
                    continue;
                } else {
                    return broadcast;
                }
            }
        }
        return null;
    }
    
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
    
    /* Pega o endereço de broadcast */
    /* Para verificar pelo terminal usar: netcat -ul portMap */
    /* Para enviar pelo terminal : echo -n "Broadcast 192.168.100.12" | nc -4u -w1 localhost 6666 */
    public void sendMessage() throws SocketException, IOException{
        int port = 6666;
        InetAddress addressBroadcast = getAddressBroadcast();
        DatagramSocket socket = new DatagramSocket();
        String message = "sd:" + Integer.toString(port);
        byte []messageBytes = message.getBytes();
        DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, addressBroadcast, port);
        socket.send(packet);
    }
}
