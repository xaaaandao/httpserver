package functions;

import java.util.*;

/**
 * Descrição: essa classe é utilizada para em que outra classe seja instanciada
 * no formato de uma lista, é importante na hora de tentar encontrar um recurso
 * que não esteja presente locamente, em outros servidores. Autor: Alexandre
 * Yuji Kajihara
 */
public class Friends {

    String ipAddress;
    int portHttp;

    /**
     * O Friends() é apenas um construtor.
     */
    public Friends() {
    }

    /**
     * O Friends(String ipAddress, int portHttp) é apenas um construtor.
     */
    public Friends(String ipAddress, int portHttp) {
        this.ipAddress = ipAddress;
        this.portHttp = portHttp;
    }

    /**
     * O método getIpAddress() retorna o endereço do servidor.
     *
     * @return ipAddress String com o nome dá página.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * O método setIpAddress(String ipAddress) apenas atribui o endereço de um
     * servidor.
     *
     * @param ipAddress String com o endereço que será atribuído.
     * @param void, ou seja, não retorna nada.
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * O método getIpAddress() retorna o número da porta do servidor.
     *
     * @return ipAddress Inteiro com o número da porta que está se rodando em um
     * servidor.
     */
    public int getPortHttp() {
        return portHttp;
    }

    /**
     * O método setPortHttp(int portHttp) apenas atribui a porta de um servidor.
     *
     * @param portHttp Inteiro com a porta que está rodando o servidor HTTP.
     * @param void, ou seja, não retorna nada.
     */
    public void setPortHttp(int portHttp) {
        this.portHttp = portHttp;
    }

    /**
     * O método addFriend(List<Friends> listOfFriends, Friends f) adiciona
     * basicamente um amigo em uma lista, mas desde que não já esteja presente.
     *
     * @param listOfFriends Lista com os servidores onde é procurado o recurso
     * onde não é encontrado localmente.
     * @param f objeto do tipo Friends que será adicionado na lista.
     * @param void, ou seja, não retorna nada.
     */
    public void addFriend(List<Friends> listOfFriends, Friends f) {
        for (Friends friends : listOfFriends) {
            if (friends.getIpAddress().equalsIgnoreCase(f.getIpAddress())) {
                return;
            }
        }
        listOfFriends.add(f);
    }

    /**
     * O método printList(List<Friends> listOfFriends imprime basicamente um
     * amigo em uma lista.
     *
     * @param listOfFriends Lista com os servidores onde é impresso o recurso
     * onde não é encontrado localmente.
     * @param void, ou seja, não retorna nada.
     */
    public void printList(List<Friends> listOfFriends) {
        if (listOfFriends.size() == 0) {
            System.out.println("Lista vazia!");
            return;
        }
        for (Friends friends : listOfFriends) {
            System.out.println("Friends IP: " + friends.getIpAddress());
            System.out.println("Friends port HTTP: " + friends.getPortHttp());
        }
    }

    /**
     * O método getFriend(List<Friends> listOfFriends, Friends f) onde será
     * procurado basicamente um amigo em uma lista, retornando a posição desse
     * elemento na lista.
     *
     * @param listOfFriends Lista com os servidores onde é procurado o recurso
     * onde não é encontrado localmente.
     * @param listOfFriends objeto do tipo Friends que será adicionado na lista.
     * @param f objeto do tipo Friends que será adicionado na lista.
     * @return i, com a posição do elemento na posição da lista.
     */
    public int getFriend(List<Friends> listOfFriends, Friends f) {
        for (int i = 0; i < listOfFriends.size(); i++) {
            if (listOfFriends.get(i).getIpAddress().equalsIgnoreCase(f.getIpAddress())) {
                return i;
            }
        }
        return -1;
    }

}
