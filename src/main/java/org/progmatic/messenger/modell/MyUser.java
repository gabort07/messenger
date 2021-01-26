package org.progmatic.messenger.modell;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="USERS_TBL")
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull

    private String userName;
    @NotNull

    private String email;
    @NotNull

    private String password;
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    private LocalDateTime registrationTime;
    @OneToMany
    private List<Message> sentMessages;
    @OneToMany
    private List<Message> receivedMessages;

    public MyUser(@NotNull String userName, @NotNull String email,
                  @NotNull String password, LocalDate birthDay) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.birthDay = birthDay;
        this.registrationTime = LocalDateTime.now();
    }

    public MyUser(int id, @NotNull String userName,
                  @NotNull String email, @NotNull String password,
                  LocalDate birthDay) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.birthDay = birthDay;
        this.registrationTime = LocalDateTime.now();
    }

    public MyUser() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(LocalDateTime registrationTime) {
        this.registrationTime = registrationTime;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
