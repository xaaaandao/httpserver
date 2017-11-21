package httpserver;

import functions.*;
import java.net.*;
import java.util.*;

/**
 * Descrição: pega o horário atual que o servidor começou a executar,
 * apaga o arquivo que lista os diretórios caso já exista e fica aguardando conexões.
 * Autor: Alexandre Yuji Kajihara
 */

public class SocketServer extends Thread {
    public static void main(String argv[]) throws Exception {
        
        /* Pega o horário atual que o servidor começou a executar */
        FileHtml f = new FileHtml();
        f.getTimeAndDate();
        
        /* Broadcast */
        List<String> listOfFriends = new ArrayList<>();
        new BroadcastSend().sendMessage();
        new Thread(new BroadcastReceive()).start();
        new Thread(new UnicastReceive(listOfFriends)).start();
        
        System.out.println("Server is Running  ");
 
        /* Apaga o arquivo directory.html e admin.html, caso alguém tente acessar de primeira não terá */
        Process p = Runtime.getRuntime().exec(new String[]{"bash","-c", "rm /html/directory.html && rm /html/admin.html && && rm /html/infoAdmin.html"});
        
        /* Porta 5555 do servidor */
        ServerSocket mysocket = new ServerSocket(5555);
        
        /* Esperando as conexões */
        while (true) {
            
            /* Aceita conexões do cliente */
            Socket socket = mysocket.accept();
            
            /* Roda a thread */
            new Thread(new ProcessRequest(socket)).start();
        }
    }
}
