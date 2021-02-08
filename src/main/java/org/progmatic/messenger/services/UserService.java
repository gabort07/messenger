package org.progmatic.messenger.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.progmatic.messenger.DTO.UserDTO;
import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.modell.QMyUser;
import org.progmatic.messenger.modell.QRole;
import org.progmatic.messenger.modell.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserService self;


    public Collection<MyUser> lisAllUser() {
         JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMyUser.myUser).fetch();
    }

    public boolean isUserNameTaken(String name) {
       return self.searchUserByName(name) !=null;
    }

    @Transactional
    public MyUser searchUserByName(String name) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMyUser.myUser).where(QMyUser.myUser.userName.eq(name)).fetchOne();
    }

    public boolean isEmailTaken(String email) {
        return self.searchUserByEmail(email) != null;
    }

    public MyUser searchUserByEmail(String email) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMyUser.myUser).where(QMyUser.myUser.email.eq(email)).fetchOne();
    }

    @Transactional
    public void addUser(UserDTO userDTO) {
        self.addUser(self.makeUserFromJson(userDTO));
    }

    @Transactional
    public void addUser(MyUser user) {
        Role userRole = self.returnRoleUserRole();
        userRole.getUsersInThisRole().add(user);
        user.getUserRoles().add(userRole);
        user.setRegistrationTime(LocalDateTime.now());
        entityManager.persist(user);
    }

    public MyUser makeUserFromJson(UserDTO dto){
        MyUser user = new MyUser();
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        if(dto.getBirthDay() != null) {
            user.setBirthDay(LocalDate.parse(dto.getBirthDay()));
        }
        user.setRegistrationTime(LocalDateTime.now());
        return user;
    }

    @Transactional
    public Role returnRoleUserRole(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QRole.role)
                .where(QRole.role.name.eq("ROLE_USER")).fetchOne();
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
