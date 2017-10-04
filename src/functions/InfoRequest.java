package functions;

import java.io.*;

/**
 * Descrição: essa classe é utilizada para em que outra classe seja instanciada no 
 * formato de uma lista, é importante no momento em que vem a requisição null
 * já que verificamos qual foi a última página e o último buffer, e também na
 * geração da página HTML do virtual path do admin, em que mostramos as últimas
 * requisições e o nome do diretório.
 * Autor: Alexandre Yuji Kajihara
 */

public class InfoRequest {
    String page;
    String nameDirectory;
    BufferedReader buffer;

    /**
     * O método InfoRequest() é apenas um construtor.
     */
    public InfoRequest() {
    }

    /**
     * O método getPage() retorna o nome dá pagina.
     * @return page String com o nome dá página.
     */
    public String getPage() {
        return page;
    }

    /**
     * O método setPage() apenas atribui o nome de uma página.
     * @param page String com o nome dá página que será atribuído.
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * O método getNamDirectory() retorna o nome do diretório.
     * @return nameDirectory String com o nome do diretório.
     */
    public String getNameDirectory() {
        return nameDirectory;
    }

    /**
     * O método setNameDirectory() apenas atribui o nome de um diretório.
     * @param nameDirectory String com o nome do diretório que será estabelecido.
     */
    public void setNameDirectory(String nameDirectory) {
        this.nameDirectory = nameDirectory;
    }

    /**
     * O método getBuffer() retorna um buffer que contém um buffer que foi enviado
     * pelo cliente durante a requisição.
     * @return buffer BufferedReader com o nome do diretório.
     */
    public BufferedReader getBuffer() {
        return buffer;
    }

    /**
     * O método setBuffer() apenas atribui um buffer que foi enviado pelo cliente.
     * @param buffer BufferedReader como buffer que foi enviado pelo cliente.
     */
    public void setBuffer(BufferedReader buffer) {
        this.buffer = buffer;
    }
    
    
}
