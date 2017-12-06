package functions;

import java.io.*;
import java.net.*;

/**
 * Descrição: essa classe é onde verificamos se no buffer que foi enviado pelo
 * cliente tem um campo no Authorization e se o valor desse campo é o que é
 * esperado, caso haja um erro na autenticação montamos um cabeçalho com a
 * página de erro na autenticação. Autor: Alexandre Yuji Kajihara
 */
public class BasicAuthentication {

    Socket s;

    /**
     * O BasicAuthentication(Socket s) é apenas um construtor.
     *
     * @param s Socket que espera as conexões do cliente.
     */
    public BasicAuthentication(Socket s) {
        this.s = s;
    }

    /**
     * O método hasAuthentication(BufferedReader buffer) verifica se na
     * requisição do cliente contém o campo Authorization se tiver verifica se o
     * conteúdo desse campo em base 64 é igual ao esperado, se sim retorna true
     * caso contrário retorna false.
     *
     * @param buffer BufferedReader buffer enviado pelo cliente durante a
     * requisição.
     * @return true ou falso, boolean true caso tenha essa autorização e falso
     * caso não tenha essa autorização.
     * @throws IOException
     */
    public boolean hasAuthentication(BufferedReader buffer) throws IOException {
        String request = buffer.readLine();

        /* Percorre o buffer enviado pelo cliente */
        while (!(request = buffer.readLine()).isEmpty()) {

            /* Verificamos se tem o campo Authorization */
            if (request.contains("Authorization: Basic ")) {

                /* Se sim verifica o valor desse campo */
                String ss[] = request.split(" ");
                if (ss[2].equals(new String("YWRtaW46YWRtaW4="))) {
                    return true;
                }

            }

        }
        return false;
    }

    /**
     * O método responseAuthentication(BufferedReader buffer) monta um cabeçalho
     * de resposta ao cliente de acesso não autorizado com uma página HTML com o
     * erro 401 de acesso não autorizado, caso não venha o valor (ou seja, o
     * login e a senha incorreto) esperado retorna essa página.
     *
     * @param buffer BufferedReader buffer enviado pelo cliente durante a
     * requisição.
     * @throws IOException
     * @return void, ou seja, nada.
     */
    public void responseAuthentication(BufferedReader buffer) throws IOException {
        OutputStream out = s.getOutputStream();
        /* Monta o cabeçalho */
        String content = "HTTP/1.1 401 Unauthorized\r\n"
                + "WWW-Authenticate: Basic userid:password realm=\"Access required\"\r\n";
        content = content + "\r\n\r\n";

        /* Lê o arquivo HTML com o erro 401 */
        FileInputStream fileHtml = new FileInputStream("/html/error401.html");
        new FileHtml().setFilesRequired("/html/error401.html", "null", buffer);
        int value;
        while ((value = fileHtml.read()) != -1) {
            content += (char) value;
        }
        fileHtml.close();
        content = content + "\r\n\r\n";

        /* Depois é enviado ao cliente */
        byte[] bytesText = content.getBytes("ISO-8859-1");
        out.write(bytesText);
        out.flush();
    }
}
