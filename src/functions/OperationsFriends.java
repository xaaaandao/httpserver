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
public class OperationsFriends {

    public boolean existFriend(List<Friends> listOfFriends, Friends friends) {
        for (Friends f : listOfFriends) {
            if (f.getAddress().equalsIgnoreCase(friends.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public void addFriend(List<Friends> listOfFriends, Friends f) {
        if (!existFriend(listOfFriends, f)) {
            listOfFriends.add(f);
        }
    }

    public void printFriend(List<Friends> listOfFriends) {
        for (Friends f : listOfFriends) {
            System.out.println("Friend address: " + f.getAddress());
            System.out.println("Friend port http: " + f.getPortHttp());
        }
        System.out.println("===========================");
    }
}
