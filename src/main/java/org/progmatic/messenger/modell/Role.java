package org.progmatic.messenger.modell;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


    @ManyToMany
    private Collection<MyUser> usersInThisRole;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
