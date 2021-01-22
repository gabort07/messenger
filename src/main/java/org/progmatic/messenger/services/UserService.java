package org.progmatic.messenger.services;

import org.progmatic.messenger.modell.MyUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

public class UserService implements UserDetailsService {
    List<MyUser> usersList;

    public UserService(){
        this.usersList = new ArrayList<>();
    }

    public List<MyUser> getUsersMap() {
        return usersList;
    }

    public boolean isUserNameTaken(String name){
        return searchUserByName(name) != null;
    }
    public MyUser searchUserByName(String name){
        Optional<MyUser> user = usersList.stream()
                .filter(user1 -> user1.getUsername().equals(name)).findAny();
        return user.orElse(null);

    }

    public boolean isEmailTaken(String email){
        return searchUserByEmail(email) != null;
    }
    public MyUser searchUserByEmail(String email){
        Optional<MyUser> user = usersList.stream()
                .filter(user1 -> user1.getEmail().equals(email)).findAny();
        return user.orElse(null);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return searchUserByName(username);
    }
}
