package org.progmatic.messenger.modell;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;


    @ManyToMany
    private Collection<MyUser> usersInThisRole;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<MyUser> getUsersInThisRole() {
        return usersInThisRole;
    }

    public void setUsersInThisRole(Collection<MyUser> usersInThisRole) {
        this.usersInThisRole = usersInThisRole;
    }
}
