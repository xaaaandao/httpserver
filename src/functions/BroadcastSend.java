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
    
    /* Emitindo um pacote multicast */
    /* Precisar por numa thread para sempre ficar enviar ou enviado uma vez ok? */
    /* Para verificar pelo terminal usar: netcat -ul portMap */
    /* Para enviar pelo terminal : echo -n "Broadcast 192.168.100.12" | nc -4u -w1 localhost 6666 */
    public void sendMessage(int portHttp) throws SocketException, IOException{
        int port = 6666;
        InetAddress addressBroadcast = getAddressBroadcast();
        DatagramSocket socket = new DatagramSocket();
        String message = "SD1234 " + Integer.toString(portHttp);
        byte []messageBytes = message.getBytes();
        DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, addressBroadcast, port);
        socket.send(packet);
        System.out.println("Enviando mensagem broadcast:" + message);
    }
}
