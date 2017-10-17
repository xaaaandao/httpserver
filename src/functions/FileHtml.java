package functions;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Descrição: essa classe é onde geramos mesmo os arquivos HTML dependendo do
 * path vindo do cliente, aqui geramos os arquivos do virtual path (que no nosso
 * caso é admin) e no arquivo que geramos e mostramos no conteúdo do diretório.
 * Autor: Alexandre Yuji Kajihara
 */

public class FileHtml {
    
    static String startDateHour;
    static List<InfoRequest> filesRequired;
    static long start;
    
    /**
     * O método headerDirectoryHtml() abre as TAG que foram abertas para que pudessemos
     * listar o conteúdo do diretório solicitado pelo cliente.
     * @param directory String com o nome do diretório solicitado pelo cliente.
     * @return String com o conteúdo do arquivo HTML, abrindo as TAG que conterão os diretórios listados.
     */
    public String headerDirectoryHtml(String directory) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "table{ width: 50%; }\n" +
                "td, td{ text-align: center; padding: 8px; }\n" +
                "</style>\n" +
                "<title> Index of " + directory + "</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1> Index of " + directory + "</h1>\n" +
                "<table>\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<th> Name </th>\n" +
                "<th> Last modified </th>\n" +
                "<th> Size </th>\n" +
                "</tr>\n" +
                "<hr>\n";
    }

    /**
     * O método footerDirectoryHtml() fecha as TAG que foram abertas no
     * método headerDirectoryHtml() que compõe o arquivo HTML que 
     * irá mostrar os arquivos e diretórios presentes no diretório solicitado.
     * @return String com o conteúdo do arquivo HTML, fechando as TAG que foram aberta.
     */
    public String footerDirectoryHtml() {
        return "</tbody>\n" + "</table>\n" + "</body>\n" + "</html>\n" + "</>\n";
    }
    
    /**
     * O método filesHtml() é invocado quando o usuário solicita um diretório,
     * então esse método gera um parte do HTML que exibe os arquivos presente
     * naquele diretório. 
     * @param directory String com o nome do diretório solicitado pelo cliente.
     * @param listFiles Lista com o nome de todos os arquivos presente naquele diretório.
     * @return content String com o conteúdo do arquivo HTML, mostrando tudo que está presente no diretório.
     */
    public String filesHtml(String directory, List<Arquivo> listFiles) {
        String content = new String();
        /* For each: você quer iterar, mas sem uma ordem específica */
        /* For: quando você sabe o tamanho */
        /* While: quando você não sabe o tamanho */
        /* Se o diretório for barra não tem diretório pai caso contrário irá aparecer na página */
        if(!directory.equalsIgnoreCase("/html")){
            String parentDirectory = new ListDirectory().getParentDirectory(directory);
            content = "<tr>\n" + "<td> <a href='" + parentDirectory + "'\"> parent directory </a> </td>";
            content = content + "<td> - </td>";
            content = content + "<td> - </td>\n</tr>\n";
        }
        
        /* Imprime os arquivos e diretórios no arquivo HTML presentes naquele determinado diretório */
        for(Arquivo a : listFiles) {
            if(!a.getName().equalsIgnoreCase("directory.html") && !a.getName().contains("error40") && !a.getName().equalsIgnoreCase("config.xandao") && !a.getName().equalsIgnoreCase("admin.html")){
                String redirect = directory + '/' + a.getName();
                if(redirect.contains("/html")){
                    redirect = redirect.replace("/html", "");
                }
                content = content + "<tr>\n" + "<td> <a href='" + redirect + "'>" + a.getName() + "</a> </td>\n";
                content = content + "<td> <a>" + a.getLastModified() + "</a> </td>\n";
                content = content + "<td> <a>" + a.getSize() + "</a> </td>\n" + "</tr>\n";
            }
        }
        return content;
    }
    
    /**
     * O método getTimeAndDate() pega a horário e a data atual que o servidor começou 
     * a executar, além disso instância a lista de arquivos requiridos, lista que 
     * contém todos os arquivos requisitados pelos clientes e também inicializa
     * o tempo que é útil para calcular os minutos e segundos que o servidor
     * que está sendo executado.
     */        
    public void getTimeAndDate(){
        /* start útil para pegar os minutos e segundos que o servidor está executando */
        start = System.nanoTime();
        startDateHour = new SimpleDateFormat("yyyy/MM/d HH:mm:ss", Locale.ENGLISH).format(new Date());
        
        /* Instanciando a lista de arquivos requiridos */
        filesRequired = new ArrayList<InfoRequest>();
    }
    
    /**
     * O método executionTime() pega quantos milisegundos o servidor está ligado,
     * e transforma esses valores em minutos e segundos e retorna no formato de uma 
     * String.
     * @return String com os minutos e os segundos que o servidor está ligado.
     */
    public String executionTime(){
        /* Retornando minutos e segundos que o servidor está ligado */
        long end = System.nanoTime();
        long time = ((end - start) / 1000000);
        return String.format("%d minutos %d segundos", TimeUnit.MILLISECONDS.toMinutes(time), TimeUnit.MILLISECONDS.toSeconds(time) -    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }
    
    public void generateInfoAdmin(){
        String content = "<p> O servidor está no ar desde: " + startDateHour + " (" + executionTime() +" ligado).</p>\n" +
                            "<p> Número de requisições atendidas: " + Integer.toString(filesRequired.size()) +"</p>" +
                            "<p> Últimas requisições:  </p> \n" +
                        "<ul>\n";
        
        for(int i = filesRequired.size() - 1; i >= 0; i--){
            String location = filesRequired.get(i).getPage();
            location = location.replace("/html", "");
            if(filesRequired.get(i).getNameDirectory().equalsIgnoreCase("null")){
                content = content + "<li> <a href=\"" + location +"\">" + filesRequired.get(i).getPage() + "</a> </li>\n";
            } else {
                content = content + "<li> <a href=\"" + location +"\">" + filesRequired.get(i).getPage()+ "</a>  <ul> <li>" + filesRequired.get(i).getNameDirectory() + "</li> </ul> </li>\n";
            }
        }
        
       content = content + "</ul>";
        
        try (BufferedWriter f = new BufferedWriter(new FileWriter("/html/infoAdmin.html"))) {
            /* Preenchendo o arquivo HTML do virtual path */
            f.write(content);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * O método generateadminHtml() gera um arquivo HTML que contém as seguintes 
     * informações a hora e data que ele está ligado, a quantidade minutos e segundos
     * em que ele está ligado, as últimas requisições solicitadas pelo cliente, 
     * quando for solicitado um diretório mostra também o diretório que foi solicitado.
     * @return content String com o conteúdo presente no arquivo HTML.
     */
    public String generateAdminHtml(){
        String content = "<!DOCTYPE html>\n" +
                            "<html>\n" +
                                "<head>\n" +
                                    "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js\"></script>\n" +
                                    "<script>\n" +
                                        "setInterval(function(){\n" +
                                            "$('#conteudo').load('infoAdmin.html');\n" +
                                        "}, 100);\n" +
                                    "</script>\n" +
                                    "<title> Information Web Server </title>\n" +
                                "</head>\n" +
                                "<body>\n" +
                                    "<h1 align=\"center\"> Information Web Server </h1>\n" +
                                    "<hr> </hr>\n" +
                                    "<div id=\"conteudo\"></div>  \n" +
                                "</body>\n" +
                            "</html>" + 
                        "</>";
        
        /* Imprime as últimas solicitações */
        /*for(int i = filesRequired.size() - 1; i >= 0; i--){
            String location = filesRequired.get(i).getPage();
            location = location.replace("/html", "");
            if(filesRequired.get(i).getNameDirectory().equalsIgnoreCase("null")){
                content = content + "<li> <a href=\"" + location +"\">" + filesRequired.get(i).getPage() + "</a> </li>\n";
            } else {
                content = content + "<li> <a href=\"" + location +"\">" + filesRequired.get(i).getPage()+ "</a>  <ul> <li>" + filesRequired.get(i).getNameDirectory() + "</li> </ul> </li>\n";
            }
        }
        
       content = content + "</ul>";*/
       return content;
    }
    
    /**
     * O método generateVirtualHtml() gera um arquivo HTML que será retornado
     * ao cliente.
     * @param path String com o nome do arquivo idêntico nome do virtual path.
     */
    public void generateVirtualHtml(String path){
        try (BufferedWriter f = new BufferedWriter(new FileWriter(path))) {
            /* Preenchendo o arquivo HTML do virtual path */
            f.write(generateAdminHtml());
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * O método setFilesRequired() adiciona o nome da página, o nome do diretório
     * caso seja um diretório e o buffer que foi enviado pelo cliente, todos
     * esses campos são adicionados na lista que contém todos os arquivos que foram solicitados.
     * @param file String com o nome do arquivo que foi solicitado pelo cliente.
     * @param nameDirectory String com o nome o diretório caso a página que tenha sido solicitada foi um diretório.
     * @param buffer BufferedReader com o buffer que foi enviado pelo cliente.
     */
    public void setFilesRequired(String file, String nameDirectory, BufferedReader buffer) {
        /* Instanciando uma classe */
        InfoRequest i = new InfoRequest();
        i.setPage(file);
        i.setNameDirectory(nameDirectory);
        i.setBuffer(buffer);
        
        /* Adicionando em uma lista */
        filesRequired.add(i);
    }
    
    /**
     * O método getFilesRequiredSize() retorna o tamanho da lista que guarda
     * o nome da página, buffer que lhe foi enviado pelo cliente ou nome do diretório,
     * caso seja um diretório.
     * @return String com o nome da última página presente na lista.
     */
    public int getFilesRequiredSize(){
        return filesRequired.size();
    }
    
    /**
     * O método getLastFilesRequired() retorna o nome da última página que está 
     * presente na lista que guarda o nome da página, buffer que lhe foi enviado
     * pelo cliente ou nome do diretório, caso seja um diretório.
     * @return String com o nome da última página presente na lista.
     */
    public String getLastFilesRequired(){
        return filesRequired.get(filesRequired.size() - 1).getPage();
    }
    
    /**
     * O método getLastBuffer() retorna o último buffer que está presente na lista
     * que guarda o nome da página, buffer que lhe foi enviado pelo cliente ou 
     * nome do diretório, caso seja um diretório.
     * @return BufferedReader com o último buffer presente na lista.
     */
    public BufferedReader getLastBuffer(){
        return filesRequired.get(filesRequired.size() - 1).getBuffer();
    }
    
}
