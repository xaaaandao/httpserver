
package httpserver;

import java.io.*;
import java.net.*;
import functions.*;
import java.util.*;
import java.util.logging.*;


/**
 * Descrição: responsável processar a requisição do cliente verificando qual 
 * método HTTP o path que foram solicitado pelo cliente.
 * Autor: Alexandre Yuji Kajihara
 */

public class ProcessRequest implements Runnable {

    private Socket s;
    InputStream input;
    OutputStream output;
    List<Friends> listOfFriends;
    
    /**
     * O ProcessRequest(List<Friends> list, Socket socket) é apenas um construtor.
     * @param list lista com os endereços e as porta dos servidores HTTP dos outros servidores.
     * @param socket socket da conexão aceita pelo servidor.
     */
    public ProcessRequest(List<Friends> list, Socket socket) {
        this.listOfFriends = list;
        this.s = socket;
    }

    /**
     * O ProcessRequest() é apenas um construtor.
      */
    public ProcessRequest() {
    }
    /**
     * O método methodHTTP(String headerHttpMethod, String path, BufferedReader buffer) verifica qual método veio durante a requisição
     * feita pelo cliente, e chama um método de acordo com o método que veio durante a requisição.
     * @param headerHttpMethod String vinda da requisição do cliente com o método solicitado.
     * @param path String vinda da requisição do cliente com o path solicitado.
     * @param buffer BufferedReader buffer que veio durante a requisição do cliente.
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @return retorna void, ou seja, nada.
     */
    public void methodHTTP(String headerHttpMethod, String path, BufferedReader buffer) throws IOException, InterruptedException {
    	switch(headerHttpMethod){
            case "OPTIONS":
        	break;
            case "GET":
                new MethodGet(this.s).processGet(path, buffer, this.listOfFriends);
        	break;
            case "HEAD":
            	break;
            case "POST":
            	break;
            case "DELETE":
        	break;
            case "TRANCE":
        	break;
            case "CONNECT":
        	break;
            default:
        	System.out.println("Método não existe!");
        	break;
        }
    }
    
    /**
     * O método checkPath(String path) percorre o path da requisição enviada pelo cliente
     * caso encontre várias ocorrências do caractere '/' remove elas e retorna
     * a string se múltiplas ocorrências do caractere '/'.
     * @param path String vinda da requisição do cliente.
     * @return pathCheck String sem repetições do caractere '/'.
     */
    public String checkPath(String path){
        StringBuilder pathCheck = new StringBuilder(path);
        for(int i = 0; i < pathCheck.length(); i++){
            /* Se na posição atual e na seguinte tiver elimina o que tem na posição atual */
            if(path.charAt(i) == '/' && path.charAt(i + 1) == '/'){
                pathCheck.deleteCharAt(i);
            }
        } 
        return pathCheck.toString();
    }
    
    /**
     * O método fakeBuffer() é chamado quando a requisição enviada pelo 
     * cliente é nula, e quando não tem nenhuma requisição na lista de 
     * requisições, então retorna esse buffer falso criado.
     * @return buffer BufferedReader é um buffer falso criado.
     */
    public BufferedReader fakeBuffer(){
        String fakeBuffer = "Connection: keep-alive\r\n" +
            "Upgrade-Insecure-Requests: 1\r\n" +
            "If-Modified-Since: Sun, 01 Oct 2017 17:29:37 GMT";
        InputStream  is = new ByteArrayInputStream(fakeBuffer.getBytes());
        BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
        return buffer;
    }
    
    /**
     * O método prepareToRun(InputStream input) recebe como parâmetro a requisição enviada pelo cliente,
     * verifica no cabeçalho o método (GET, POST, etc) e o caminho (podendo ser um arquivo
     * HTML ou um diretório), além disso verifica se o buffer veio vazio ou não, caso 
     * não tenha verifica se a lista de requisições é maior que zero, ou seja, se já foi feita
     * uma requisição, retorna o último elemento dessa lista, caso contrário retorna a 
     * página de erro 404 com um buffer falso, já caso o buffer venha preenchido 
     * retorna a página solicitada caso exista.
     * @param input contém o cabeçalho enviado pelo cliente ao servidor.
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @return retorna void, ou seja, nada.
    */
    public void prepareToRun(InputStream input) throws IOException, InterruptedException {
        /* Armazena o cabeçalho em uma String */
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
        String request = buffer.readLine();
        
        /* Se não tiver nada no buffer */
        if(request == null){
            String path = "";
            FileHtml f = new FileHtml();
            
            /* Caso já tenha feito alguma requisição */
            if(f.getFilesRequiredSize() > 0){
                path = f.getLastFilesRequired();
                buffer = f.getLastBuffer();
                
            /* Caso não tenha feito nenhuma requisição*/
            } else {
                path = "/error404.html";
                buffer = fakeBuffer();
            }
            
            /* Elimina o caractere '/' repetido */
            if(!path.equalsIgnoreCase("/")){
                path = checkPath(path);
            }
            
            /* Passa o método HTTP, caminho e o buffer */
            methodHTTP("GET", path, buffer);
            
        /* Caso tenha algo no buffer */
        } else if(request != null){
            String ss[] = request.split(" ");
            
            //System.out.println("->"+ss[1]);
            /* Elimina o caractere '/' repetido */
            if(!ss[1].equalsIgnoreCase("/")){
                ss[1] = checkPath(ss[1]);
            }
            
            /* Passa o método HTTP, caminho e o buffer */
            methodHTTP(ss[0], ss[1], buffer);
        }
    }
    
     /**
     * O método run() recebe a requisição que foi enviado pelo cliente 
     * e chama o método prepareToRun() que irá processar o que lhe foi enviado.
     * @return retorna void, ou seja, nada.
     */
    public void run() {
        FileHtml f = new FileHtml();
        try {
            
            /* Recebe o que foi enviado pelo cliente */
            input = s.getInputStream();
            output = s.getOutputStream();
            
            /* Vai atualizando o HTML com as informações do Admin */
            f.generateInfoAdmin();
            
            /* Processar o que foi enviado pelo cliente */
            prepareToRun(input);
            
            /* Encerra as conexões */
            //input.close();
            //output.close();
            //s.close();            
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProcessRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}   