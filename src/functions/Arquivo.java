package functions;

/**
 * Descrição: essa classe é utilizada para em que outra classe seja instanciada no 
 * formato de uma lista, é importante no momento em que vamos listar o conteúdo
 * do diretório, em que temos guardar informações importante como o nome, tamanho e 
 * a última modificação.
 * Autor: Alexandre Yuji Kajihara
 */

public class Arquivo {
    private String name;
    private String lastModified;
    private String size;

    /**
     * O método InfoRequest() é apenas um construtor.
     */
    public Arquivo() {
    }

    /**
     * O método getPage() retorna o nome de um arquivo.
     * @return name String com o nome do arquivo.
     */
    public String getName() {
        return name;
    }

    /**
     * O método getPage() apenas atribui o nome de um arquivo.
     * @param name String com o nome do arquivo.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * O método getPage() retorna a última modificação do arquivo.
     * @return lastModified String com o nome dá última modificação.
     */
    public String getLastModified() {
        return lastModified;
    }

    /**
     * O método setLastModified() apenas atribui a última modificação que ocorreu.
     * @param lastModified String com a última modificação que ocorreu do arquivo.
     */
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * O método getSize() retorna o tamanho da arquivo.
     * @return size String com o tamanho do arquivo.
     */
    public String getSize() {
        return size;
    }

    /**
     * O método setSize() apenas atribui o tamanho do arquivo.
     * @param size String com o tamanho do arquivo.
     */
    public void setSize(String size) {
        this.size = size;
    }
    
}
