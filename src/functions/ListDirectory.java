package functions;

import httpserver.MethodGet;
import java.io.*;
import java.util.*;

/**
 * Descrição: armazena o nome, o tamanho e a última modificação de cada arquivo
 * ou diretório em uma lista do diretório que foi solicitado pelo cliente, e gera um
 * arquivo HTML que exiba o conteúdo desse diretório.
 * Autor: Alexandre Yuji Kajihara
 */

public class ListDirectory {

    /**
     * O método getParentDirectory() dado um diretório verifica qual é seu diretório pai.
     * @param directory String com o nome do diretório que será procurado seu diretório pai.
     * @return parentDirectory, String com o diretório pai.
     */
    public String getParentDirectory(String directory){
        String parentDirectory = directory;
        parentDirectory = parentDirectory.replace("/html", "");
        parentDirectory = parentDirectory.substring(0, parentDirectory.lastIndexOf("/"));
        if(parentDirectory.length() == 0){
            return "/";
        }
        return parentDirectory;
    }
    
    /**
     * O método generateDirectoryHtml() produz o arquivo HTML que contém todos
     * os conteúdos presentes naquele diretório.
     * @param directory String com o nome do diretório e o seu conteúdo.
     * @param listFiles Lista com os arquivos presentes naquele diretório.
     * @return true ou false, booleano true caso consiga gerar um HTML e false caso não consiga.
     * @throws java.io.IOException
     */
    public boolean generateDirectoryHtml(String directory, List<Arquivo> listFiles) throws IOException {
        try (BufferedWriter f = new BufferedWriter(new FileWriter("/html/directory.html"))) {
            /* Escrevemos no arquivo */
            f.write(new FileHtml().headerDirectoryHtml(directory));
            f.write(new FileHtml().filesHtml(directory, listFiles));
            f.write(new FileHtml().footerDirectoryHtml());
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean generateDirectorySortName(String directory, List<Arquivo> listFiles) throws IOException {
        try (BufferedWriter f = new BufferedWriter(new FileWriter("/html/directorySortName.html"))) {
            new FileHtml().sortNameFile(listFiles);
            /* Escrevemos no arquivo */
            f.write(new FileHtml().headerDirectoryHtml(directory));
            f.write(new FileHtml().filesHtml(directory, listFiles));
            f.write(new FileHtml().footerDirectoryHtml());
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean generateDirectorySortLastModified(String directory, List<Arquivo> listFiles) throws IOException {
        try (BufferedWriter f = new BufferedWriter(new FileWriter("/html/directorySortLastModified.html"))) {
            new FileHtml().sortLastModifiedFile(listFiles);
            /* Escrevemos no arquivo */
            f.write(new FileHtml().headerDirectoryHtml(directory));
            f.write(new FileHtml().filesHtml(directory, listFiles));
            f.write(new FileHtml().footerDirectoryHtml());
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean generateDirectorySortSize(String directory, List<Arquivo> listFiles) throws IOException {
        try (BufferedWriter f = new BufferedWriter(new FileWriter("/html/directorySortSize.html"))) {
            new FileHtml().sortSizeFile(listFiles);
            /* Escrevemos no arquivo */
            f.write(new FileHtml().headerDirectoryHtml(directory));
            f.write(new FileHtml().filesHtml(directory, listFiles));
            f.write(new FileHtml().footerDirectoryHtml());
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * O método filesDirectory() armazena todos os arquivos e diretórios em uma lista
     * com o nome, o tamanho e a última modificação.
     * @param directory String com o nome do diretório que será carregado o seu conteúdo na lista.
     * @return true ou false, booleano true caso consiga gerar um HTML que mostre o conteúdo dos
     * diretórios e false caso não consiga.
     * @throws java.io.IOException
     */
    public boolean filesDirectory(String directory) throws IOException {
        List<Arquivo> listFiles = new ArrayList<>();
        File folder = new File(directory);
        File[] allOfFiles = folder.listFiles();

        /* Armazenando os arquivos e diretórios presente naquele diretório em uma lista */
        for (int i = 0; i < allOfFiles.length; i++) {
            Arquivo a = new Arquivo();
            String lastModified = directory + "/";
            a.setName(allOfFiles[i].getName());
            a.setSize(String.valueOf(allOfFiles[i].length()));
            lastModified = lastModified + allOfFiles[i].getName();
            a.setLastModified(new MethodGet().headerLastModified(new File(lastModified)));
            listFiles.add(a);
        }

        /* Verificamos se o arquivo com o conteúdo dos diretórios foi criado */
        if(generateDirectoryHtml(directory, listFiles)){
            generateDirectorySortName(directory, listFiles);
            generateDirectorySortLastModified(directory, listFiles);
            generateDirectorySortSize(directory, listFiles);
            return true;
        }
        return false;
    }
}
