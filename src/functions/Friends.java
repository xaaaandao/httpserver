/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import java.util.*;

/**
 *
 * @author xandao
 */
public class Friends {
    String ipAddress;
    int portHttp;

    public Friends() {
    }

    public Friends(String ipAddress, int portHttp) {
        this.ipAddress = ipAddress;
        this.portHttp = portHttp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPortHttp() {
        return portHttp;
    }

    public void setPortHttp(int portHttp) {
        this.portHttp = portHttp;
    }
    
    public void addFriend(List<Friends> listOfFriends, Friends f){
        for(Friends friends : listOfFriends){
            if(friends.getIpAddress().equalsIgnoreCase(f.getIpAddress())){
                return;
            }
        }
        listOfFriends.add(f);
    }
    
    public void printList(List<Friends> listOfFriends){
        for(Friends friends : listOfFriends){
            System.out.println("Friends IP: " + friends.getIpAddress());
            System.out.println("Friends port HTTP: " + friends.getPortHttp());
        }
    }
    
}
