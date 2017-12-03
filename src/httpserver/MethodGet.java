package httpserver;

import functions.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.*;
import java.time.*;
import java.util.*;

/**
 * Descrição: responsável pelo método GET,verifica se o path que o usuário
 * solicitou é um virtual path, diretório ou uma página HTML, caso seja um dos
 * dois primeiros casos verificamos se existem a autenticação, além do que nessa
 * classe montamos os cabeçalhos de resposta ao cliente e enviamos a resposta ao
 * cliente. Autor: Alexandre Yuji Kajihara
 */
public class MethodGet {

    boolean isDirectory;
    boolean virtualPath;
    Socket s;

    /**
     * O método MethodGet() é apenas um construtor.
     */
    public MethodGet() {

    }

    /**
     * O método MethodGet() é apenas um construtor.
     *
     * @param s socket da conexão aceita pelo servidor.
     */
    public MethodGet(Socket s) {
        this.s = s;
    }

    /**
     * O método getContentType() recebe a extensão do arquivo e verifica qual
     * tipo de valor deve ser colocado no campo Content-Type no cabeçalho.
     *
     * @param extension String com a extensão que vai ser verificado qual valor
     * será colocado no Content-Type.
     * @return contentType String com o valor que vai ser colocado no campo
     * Content-Type do cabeçalho.
     */
    public String getContentType(String extension) {
        extension = extension.toLowerCase();
        String contentType = null;
        switch (extension) {
            case "aac":
                contentType = "audio/aac;charset=UTF-8\r\n";
                break;
            case "abw":
                contentType = "application/x-abiword;charset=UTF-8\r\n";
                break;
            case "arc":
                contentType = "application/octet-stream;charset=UTF-8\r\n";
                break;
            case "avi":
                contentType = "video/x-msvideo;charset=UTF-8\r\n";
                break;
            case "azw":
                contentType = "application/vnd.amazon.ebook;charset=UTF-8\r\n";
                break;
            case "bin":
                contentType = "application/octet-stream;charset=UTF-8\r\n";
                break;
            case "bz":
                contentType = "application/x-bzip;charset=UTF-8\r\n";
                break;
            case "bz2":
                contentType = "application/x-bzip2;charset=UTF-8\r\n";
                break;
            case "csh":
                contentType = "application/x-csh;charset=UTF-8\r\n";
                break;
            case "css":
                contentType = "text/css;charset=UTF-8\r\n";
                break;
            case "csv":
                contentType = "text/csv;charset=UTF-8\r\n";
                break;
            case "doc":
                contentType = "application/msword;charset=UTF-8\r\n";
                break;
            case "eot":
                contentType = "application/vnd.ms-fontobject;charset=UTF-8\r\n";
                break;
            case "epub":
                contentType = "application/epub+zip;charset=UTF-8\r\n";
                break;
            case "gif":
                contentType = "image/gif;charset=UTF-8\r\n";
                break;
            case "htm":
                contentType = "text/html;charset=UTF-8\r\n";
                break;
            case "html":
                contentType = "text/html;charset=UTF-8\r\n";
                break;
            case "ico":
                contentType = "image/x-icon;charset=UTF-8\r\n";
                break;
            case "ics":
                contentType = "text/calendar;charset=UTF-8\r\n";
                break;
            case "jar":
                contentType = "application/java-archive;charset=UTF-8\r\n";
                break;
            case "jpeg":
                contentType = "image/jpeg;charset=UTF-8\r\n";
                break;
            case "jpg":
                contentType = "image/jpeg;charset=UTF-8\r\n";
                break;
            case "js":
                contentType = "application/javascript;charset=UTF-8\r\n";
                break;
            case "json":
                contentType = "application/json;charset=UTF-8\r\n";
                break;
            case "mid":
                contentType = "audio/midi;charset=UTF-8\r\n";
                break;
            case "midi":
                contentType = "audio/midi;charset=UTF-8\r\n";
                break;
            case "mpeg":
                contentType = "video/mpeg;charset=UTF-8\r\n";
                break;
            case "mpkg":
                contentType = "application/vnd.apple.installer+xml;charset=UTF-8\r\n";
                break;
            case "odp":
                contentType = "application/vnd.oasis.opendocument.presentation;charset=UTF-8\r\n";
                break;
            case "ods":
                contentType = "application/vnd.oasis.opendocument.spreadsheet;charset=UTF-8\r\n";
                break;
            case "odt":
                contentType = "application/vnd.oasis.opendocument.text;charset=UTF-8\r\n";
                break;
            case "oga":
                contentType = "audio/ogg;charset=UTF-8\r\n";
                break;
            case "ogv":
                contentType = "video/ogg;charset=UTF-8\r\n";
                break;
            case "ogx":
                contentType = "application/ogg;charset=UTF-8\r\n";
                break;
            case "otf":
                contentType = "font/otf;charset=UTF-8\r\n";
                break;
            case "png":
                contentType = "image/png;charset=UTF-8\r\n";
                break;
            case "pdf":
                contentType = "application/pdf;charset=UTF-8\r\n";
                break;
            case "ppt":
                contentType = "application/vnd.ms-powerpoint;charset=UTF-8\r\n";
                break;
            case "rar":
                contentType = "application/x-rar-compressed;charset=UTF-8\r\n";
                break;
            case "rtf":
                contentType = "application/rtf;charset=UTF-8\r\n";
                break;
            case "sh":
                contentType = "application/x-sh;charset=UTF-8\r\n";
                break;
            case "svg":
                contentType = "image/svg+xml;charset=UTF-8\r\n";
                break;
            case "swf":
                contentType = "application/x-shockwave-flash;charset=UTF-8\r\n";
                break;
            case "tar":
                contentType = "application/x-tar;charset=UTF-8\r\n";
                break;
            case "tif":
                contentType = "image/tiff;charset=UTF-8\r\n";
                break;
            case "tiff":
                contentType = "image/tiff;charset=UTF-8\r\n";
                break;
            case "ts":
                contentType = "application/typescript;charset=UTF-8\r\n";
                break;
            case "ttf":
                contentType = "font/ttf;charset=UTF-8\r\n";
                break;
            case "vsd":
                contentType = "application/vnd.visio;charset=UTF-8\r\n";
                break;
            case "wav":
                contentType = "audio/x-wav;charset=UTF-8\r\n";
                break;
            case "weba":
                contentType = "audio/webm;charset=UTF-8\r\n";
                break;
            case "webm":
                contentType = "video/webm;charset=UTF-8\r\n";
                break;
            case "webp":
                contentType = "image/webp;charset=UTF-8\r\n";
                break;
            case "woff":
                contentType = "font/woff;charset=UTF-8\r\n";
                break;
            case "woff2":
                contentType = "font/woff2;charset=UTF-8\r\n";
                break;
            case "xhtml":
                contentType = "application/x-abiword;charset=UTF-8\r\n";
                break;
            case "xls":
                contentType = "application/vnd.ms-excel;charset=UTF-8\r\n"
                        + "Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8\r\n";
                break;
            case "xlsx":
                contentType = "application/vnd.ms-excel;charset=UTF-8\r\n"
                        + "Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8\r\n";
                break;
            case "xml":
                contentType = "application/xml;charset=UTF-8\r\n";
                break;
            case "xul":
                contentType = "application/vnd.mozilla.xul+xml;charset=UTF-8\r\n";
                break;
            case "zip":
                contentType = "application/zip;charset=UTF-8\r\n";
                break;
            case "3gp":
                contentType = "video/3gpp;charset=UTF-8\r\n"
                        + "Content-Type: audio/3gpp;charset=UTF-8\r\n";
                break;
            case "3g2":
                contentType = "video/3gpp2;charset=UTF-8\r\n"
                        + "Content-Type: audio/3gpp2;charset=UTF-8\r\n";
                break;
            case "7z":
                contentType = "application/x-7z-compressed;charset=UTF-8\r\n";
                break;
            default:
                break;
        }
        return contentType;
    }

    /**
     * O método headerDate() apenas retorna a String com a data e o horário
     * atual.
     *
     * @return String com a data e o horário atual.
     */
    public String headerDate() {
        return java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
    }

    /**
     * O método headerLastModified() recebe o arquivo que irá sendo retornado
     * pelo cliente e verificamos quando ele é modificado pela última vez.
     *
     * @param f Arquivo com conteúdo HTML onde extraímos a sua última
     * modificação.
     * @return sdf.format String com a última modificação do arquivo.
     */
    public String headerLastModified(File f) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date(f.lastModified()));
    }

    public String checkCookie(BufferedReader buffer) throws IOException {
        if (buffer == null) {
            return "acessos=1";
        }
        String cookie = null;
        String request = buffer.readLine();
        if(request == null){
            return "acessos=1";
        }
        while (!(request = buffer.readLine()).isEmpty()) {
            if (request.contains("Cookie: ")) {
                if (request.equalsIgnoreCase("Cookie: ")) {
                    return "acessos=1";
                }
                int lastChar = request.indexOf(";");
                if (lastChar == -1) {
                    cookie = request.substring(request.indexOf("=") + 1, request.length());
                } else {
                    cookie = request.substring(request.indexOf("=") + 1, lastChar);
                }
                int numberOfAcess = Integer.parseInt(cookie);
                numberOfAcess++;
                cookie = "acessos=" + Integer.toString(numberOfAcess);
                return cookie;
            }
        }
        return "acessos=1";
    }

    /**
     * O método responseHeader() recebe o arquivo que irá ser retornado para o
     * cliente e o nome do arquivo, o arquivo verificamos quando ele é
     * modificado pela última vez e o tamanho dele e o segundo parâmetro que é o
     * nome do arquivo utilizamos para pegar a sua extensão, com todas essas
     * informações montamos o cabeçalho.
     *
     * @param f Arquivo com conteúdo HTML onde extraímos o tamanho e sua última
     * modificação.
     * @param nameFile String com o nome do arquivo onde será extraído sua
     * extensão.
     * @throws java.io.IOException
     * @return header String com o cabeçalho montado.
     */
    public String responseHeader(File f, String nameFile, BufferedReader buffer) throws IOException {
        /* Pega a extensão do nome do arquivo */
        String extension = nameFile.substring(nameFile.lastIndexOf(".") + 1, nameFile.length());

        /* Montamos o cabeçalho */
        String header = "HTTP/1.1 200 OK\r\n"
                + "Date: " + headerDate() + "\r\n"
                + "Last-Modified: " + headerLastModified(f) + "\r\n"
                + "Content-Length: " + f.length() + "\r\n"
                + "Set-Cookie: " + checkCookie(buffer) + "\r\n"
                + "Content-Type: " + getContentType(extension) + "\r\n\r\n";
        return header;
    }

    /**
     * O método responseBody() recebe em String o conteúdo do cabeçalho e o
     * arquivo que será anexado a esse cabeçalho, basicamente lê o arquivo e
     * acrescentar no final da String que veio, após isso envia para o cliente
     * que o solicitou.
     *
     * @param content String com o texto do cabeçalho que irá ser feito a
     * resposta ao cliente.
     * @param f Arquivo com conteúdo HTML que será anexado ao cabeçalho e será
     * enviado na resposta ao cliente.
     * @throws java.io.IOException
     */
    public void responseBody(String content, File f) throws IOException {
        OutputStream out = s.getOutputStream();

        /* Texto contendo o cabeçalho */
        content = content + "\r\n\r\n";
        FileInputStream fileHtml = new FileInputStream(f);
        int value;

        /* Lendo o arquivo e acrescentando o mesmo no final do cabeçalho*/
        while ((value = fileHtml.read()) != -1) {
            content += (char) value;
        }
        fileHtml.close();
        content = content + "\r\n\r\n";
        byte[] bytesText = content.getBytes("ISO-8859-1");

        /* Enviando para o cliente */
        out.write(bytesText);
        out.flush();
        out.close();
    }

    /**
     * O método isPathInvisible() verifica se o path que foi solicitado na
     * requisição ele deve ser retornado ou não ao cliente, exemplo não queremos
     * que o nosso usuário tenha acesso as páginas de erro, ou as páginas que
     * foram geradas para exibir os diretórios.
     *
     * @param path String com o path se ele pode ou não ser retornado ao
     * cliente.
     * @return true ou false boolean true caso possa ser retornado, false caso
     * contrário.
     */
    public boolean isPathInvisible(String path) {
        List<String> listFiles = new ArrayList<>();

        /* Path que o usuário não pode acessar */
        listFiles.add("/html/error404.html");
        listFiles.add("/html/error401.html");
        listFiles.add("/html/directory.html");

        /* Verifica se um dos path é igual ao path solicitado pelo cliente */
        for (String s : listFiles) {
            if (s.contentEquals(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * O método isVirtualPath() verifica se o path que foi requisitado pelo
     * cliente é um virtual path ou não, para tal façanha verifica um arquivo de
     * configuração presente no diretório /html caso no conteúdo dele contenha
     * esse virtual path retorna true ou false caso não exista.
     *
     * @param path String com o virtual path vindo da requisição do cliente.
     * @throws java.io.IOException
     * @return true ou false boolean true caso exista o virtual path, false caso
     * contrário.
     */
    public boolean isVirtualPath(String path) throws IOException {
        /* Abre o arquivo de configuração*/
        BufferedReader br = new BufferedReader(new FileReader("/html/config.xml"));
        List<String> virtualPath = new ArrayList<String>();
        String line;

        /* Percorre o conteúdo verificando se nas linhas contém o virtual path */
        while ((line = br.readLine()) != null) {
            if (line.contains("<virtualpath>") && line.contains("</virtualpath>")) {
                virtualPath.add(line.substring(13, (line.length() - 14)));
            }
        }

        /* Verifica se o path é igual ao virtual path que tem no arquivo de configuração */
        path = path.replace("/", "");
        for (String s : virtualPath) {
            if ((s.equalsIgnoreCase(path)) && !(new File("/html/" + path).isDirectory())) {
                return true;
            }
        }

        return false;
    }

    /**
     * O método isDirOrIsFile() verifica se o path que foi requisitado pelo
     * cliente é um virtual path ou um diretório ou uma página HTML, além do que
     * verifica a veracidade, ou seja, se o mesmo existe, caso não exista
     * retorna a página com o erro 404, senão retorno a página ou o recurso
     * solicitado.
     *
     * @param path String com a página, virtual path ou diretório vinda da
     * requisição do cliente.
     * @param buffer BufferedReader com o método, o path e outros itens do
     * cabeçalho vindos da requisição do cliente.
     * @throws java.io.IOException
     * @return newPath String retorna o path correto e com a localização exata
     * dá que foi solicitada.
     */
    public String isDirOrIsFile(String path, BufferedReader buffer) throws IOException {
        String newPath = null;
        String nameDirectory = "null";

        String query = null;

        //System.out.println("->"+path);
        if (path.contains("/?") == true) {
            int startQuery = path.indexOf("?");
            query = path.substring(startQuery + 1, path.length());
            path = path.substring(0, startQuery);
        }

        /* Verifica se existe o virtual path */
        if (isVirtualPath(path)) {
            newPath = "/html" + path + ".html";
            /* Caso exista adicionamos o path em uma lista */
            new FileHtml().setFilesRequired(newPath, nameDirectory, buffer);
            /* Geramos o arquivo desse virtual path */
            new FileHtml().generateVirtualHtml(newPath);
            virtualPath = true;
            return newPath;
        }

        /* Verificamos se o path que veio é o / se for está referindo ao diretório /html */
        if (path.equalsIgnoreCase("/")) {
            newPath = "/html";
        } else {
            /* Caso não seja verificamos se o path não é um arquivo */
            newPath = "/html" + path;
            File existsFile = new File(newPath);

            /* Se não existir esse arquivo retorna-se a página com erro 404 */
            if ((!existsFile.canRead() && !existsFile.isFile() && !existsFile.isDirectory()) || isPathInvisible(newPath)) {
                newPath = "/html/error404.html";
            }
        }

        /* Se não for um arquivo e nenhum virtual path verificamos se é um diretório */
        if (new File(newPath).isDirectory()) {
            if (new ListDirectory().filesDirectory(newPath)) {
                nameDirectory = newPath;
                //newPath = newPath + "/directory.html";
                newPath = newPath + returnQuery(query);

                isDirectory = true;
            }
        }

        /* Depois de achado adicionamos na lista de arquivos que forem requiridos */
        if (newPath.contains("infoAdmin.html") == false) {
            //System.out.println("**"+newPath);
            newPath = new ProcessRequest().checkPath(newPath);
            //System.out.println("**dps"+newPath);
            if (newPath.contains("directorySort")) {
                nameDirectory = new FileHtml().getDirectory(newPath);
            }
            new FileHtml().setFilesRequired(newPath, nameDirectory, buffer);
        }
        return newPath;
    }

    public String returnQuery(String query) {
        if (query == null) {
            return "/directory.html";
        }
        switch (query) {
            case "n=o":
                return "/directorySortName.html";
            case "n=r":
                return "/directorySortReverseName.html";
            case "l=o":
                return "/directorySortLastModified.html";
            case "l=r":
                return "/directorySortReverseLastModified.html";
            case "s=o":
                return "/directorySortSize.html";
            case "s=r":
                return "/directorySortReverseSize.html";
            default:
                break;
        }
        return null;
    }

    public void checkOtherServer(List<Friends> listOfFriends, String path) throws UnsupportedEncodingException, IOException {
        for (Friends f : listOfFriends) {
            String ip = f.getIpAddress().replace("/", "");
            Socket socket = new Socket(ip, f.getPortHttp());
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            String request = "GET " + path + " HTTP/1.1\r\n"
                    + "From Server: True";
            request = request + "\r\n\r\n";
            byte[] bytesText = request.getBytes("ISO-8859-1");
            out.write(bytesText);
            System.out.println("to aqui porra");
            int value = in.read();
            System.out.println("value:" + value);
            //System.out.println("nada" + getStringFromInputStream(in));
            String text = getStringFromInputStream(in);
            System.out.println("text:"+text);
            String result = "H" + text;
            System.out.println("final"+result);

            //Se a requisição vim algo null
            if(request == null || socket == null){
                socket.close();
                Friends fr = new Friends();
                int index = fr.getFriend(listOfFriends, f);
                if(index > -1){
                    listOfFriends.remove(index);
                    System.out.println("removeu");
                }
            //Se vier algo manda para o cliente
            } else {
                OutputStream outA = s.getOutputStream();
                outA.write(result.getBytes());
            }
            
        }
    }

    public String getStringFromInputStream(InputStream is) throws IOException {
        String text = null;
        try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
            text = scanner.useDelimiter("\\A").next();
        }

        //assertThat(text, equalTo(originalString));
        return text;
    }

    /**
     * O método processGet() verifica se o path que foi requisitado pelo cliente
     * é um virtual path ou é um diretório, caso seja verifica se o buffer da
     * requisição do cliente possui ou não a autenticação, caso não seja nenhum
     * desses dois casos retorna a página solicitada.
     *
     * @param path String com a página, virtual path ou diretório vinda da
     * requisição do cliente.
     * @param buffer BufferedReader com o método, o path e outros itens do
     * cabeçalho vindos da requisição do cliente.
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public void processGet(String path, BufferedReader buffer, List<Friends> listOfFriends) throws IOException, InterruptedException {
        //new Friends().printList(listOfFriends);

        isDirectory = false;
        virtualPath = false;

        /* Verifica se o path que foi passado, é diretório, página ou virtual path
        Se for um diretório, página ou virtual path verifica se ele existe mesmo */
        String newPath = isDirOrIsFile(path, buffer);
        boolean errorAuthentication = false;

        /* Caso seja um diretório ou um virtual path verifica se no buffer
        tem o cabeçalho de autenticação*/
        if (isDirectory || virtualPath) {
            /* Verifica se veio o cabeçalho com a autenticação 
            Caso não venha e o usuário não digite o usuário e a senha correta 
            retorna a página 401 */
            if (!new BasicAuthentication(this.s).hasAuthentication(buffer)) {
                new BasicAuthentication(this.s).responseAuthentication(buffer);
                errorAuthentication = true;
            }
        }

        /* Caso não seja nenhum dos dois é uma página */
        if (errorAuthentication == false) {
            if (newPath.contains("error404")) {
                checkOtherServer(listOfFriends, path);
            } else {
                File fileHtml = new File(newPath);
                String text = responseHeader(fileHtml, newPath, buffer);
                responseBody(text, fileHtml);
            }
        }

    }
}
