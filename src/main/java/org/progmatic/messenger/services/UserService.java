package org.progmatic.messenger.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.progmatic.messenger.DTO.UserDTO;
import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.modell.QMyUser;
import org.progmatic.messenger.modell.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {


    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserService self;

    JPAQueryFactory queryFactory;

    public UserService() {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    public Collection<MyUser> lisAllUser() {
        return queryFactory.selectFrom(QMyUser.myUser).fetch();
    }

    public boolean isUserNameTaken(String name) {
        try {
            self.searchUserByName(name);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public MyUser searchUserByName(String name) {
        return queryFactory.selectFrom(QMyUser.myUser).where(QMyUser.myUser.userName.eq(name)).fetchOne();
    }

    public boolean isEmailTaken(String email) {
        return self.searchUserByEmail(email) != null;
    }

    public MyUser searchUserByEmail(String email) {
        return queryFactory.selectFrom(QMyUser.myUser).where(QMyUser.myUser.email.eq(email)).fetchOne();
    }

    @Transactional
    public void addUser(MyUser user) {
        Role userRole = entityManager.find(Role.class, 2);
        entityManager.persist(user);
        userRole.getUsersInThisRole().add(user);
        user.setRegistrationTime(LocalDateTime.now());
    }

    @Transactional
    public void addUserRest(UserDTO userDTO) {
        MyUser user = new MyUser();
        entityManager.persist(user);
        Role userRole = entityManager.find(Role.class, 2);
        user.setUserName(userDTO.getUserName());
        user.setPassword(user.getPassword());
        user.getUserRoles().add(userRole);
        user.setEmail(userDTO.getEmail());
        user.setBirthDay(LocalDate.parse(userDTO.getBirthDay()));
        user.setRegistrationTime(LocalDateTime.now());
        userRole.getUsersInThisRole().add(user);
    }

    @Transactional
    public void deleteUser(int userID) {
        entityManager.remove(self.findUserByID(userID));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return searchUserByName(username);
    }

    @Transactional
    public MyUser findUserByID(int id) {
        return entityManager.find(MyUser.class, id);
    }

    public List<UserDTO> makeUserDTOList() {
        List<UserDTO> list = new ArrayList<>();
        for (MyUser actual : self.lisAllUser()) {
            UserDTO dto = new UserDTO();
            dto.setUserName(actual.getUserName());
            dto.setPassword(actual.getPassword());
            dto.setBirthDay(actual.getBirthDay().toString());
            dto.setEmail(actual.getEmail());
            list.add(dto);
        }
        return list;
    }
}
