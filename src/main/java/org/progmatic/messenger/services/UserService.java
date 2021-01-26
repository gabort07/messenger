package org.progmatic.messenger.services;

import org.progmatic.messenger.modell.Message;
import org.progmatic.messenger.modell.MyUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDetailsService {
    List<MyUser> usersList;

    @PersistenceContext
    EntityManager entityManager;

    public UserService() {
        this.usersList = new ArrayList<>();
    }


    public List<MyUser> lisAllUser(){
        List<MyUser> list = entityManager.createQuery(
                "SELECT m FROM MyUser m")
                .getResultList();
        if(list == null){
            return new ArrayList<>();
        }
        return list;
    }
    public boolean isUserNameTaken(String name) {
        return searchUserByName(name) != null;
    }

    public MyUser searchUserByName(String name) {
        return entityManager.createQuery("select a from MyUser a where a.userName = :name", MyUser.class)
                .setParameter("name", name).getSingleResult();
    }

    public boolean isEmailTaken(String email) {
        return searchUserByEmail(email) != null;
    }

    public MyUser searchUserByEmail(String email) {
        return entityManager.find(MyUser.class,email);
    }

    @Transactional
    public void addUser(MyUser user) {
        entityManager.persist(user);
        user.setRegistrationTime(LocalDateTime.now());
    }

    public void deleteUser(int userID) {
        usersList.removeIf(user -> user.getId() == userID);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return searchUserByName(username);
    }
}
