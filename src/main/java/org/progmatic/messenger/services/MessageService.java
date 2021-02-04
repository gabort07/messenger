package org.progmatic.messenger.services;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.progmatic.messenger.modell.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.swing.text.StyledEditorKit;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.transaction.annotation.Isolation.*;

@Service
public class MessageService {
    List<Message> messageList;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MessageService self;

    public MessageService() {
        this.messageList = new ArrayList<>();

    }

    public List<Message> getAllMessages() {
        List<Message> list = entityManager.createQuery(
                "SELECT m FROM Message m")
                .getResultList();
        if(list == null){
            return new ArrayList<>();
        }
        return list;
    }

    @Transactional
    public Message findMessageById(int messageId) {
        return entityManager.find(Message.class, messageId, LockModeType.PESSIMISTIC_WRITE);
    }

    @Transactional
    public Message modifyMessage(int messageId, String text) {
        Message actual = self.findMessageById(messageId);
        actual.setMessage(actual.getMessage().concat(text));
        return actual;
    }

    @Transactional
    public Message modifyMessage(int messageId, long sleep, String text) throws InterruptedException {
        Message actual = self.findMessageById(messageId);
        Thread.sleep(sleep*1000);
        actual.setMessage(actual.getMessage().concat(text));
        return actual;
    }

    @Transactional
    public void addMessage(Message msg){
        entityManager.persist(msg);
       msg.setTime(LocalDateTime.now());
    }

    @Transactional
    public void deleteMessage(int messageID) {
        entityManager.remove(self.findMessageById(messageID));
    }

    @Transactional
    public List<Message> getMessagesFromTopic(int topicID){
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMessage.message1).where(QMessage.message1.topic.id.eq(topicID)).fetch();
    }

    @Transactional
    public List<Message> searchIn(String text, Boolean topics, Boolean users, Boolean messages){
        List<Message> list = new ArrayList<>();
        if (topics){
            list.addAll(self.searchInTopics(text));
        }
        if(users){
            list.addAll(self.searchInUsers(text));
        }
        if(messages){
            list.addAll(self.searchInMessages(text));
        }
        return list;
    }

    @Transactional
    public List<Message> searchInTopics(String text){
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMessage.message1).where(QMessage.message1.topic.topicName.contains(text)).fetch();
    }

    @Transactional
    public List<Message> searchInUsers(String text){
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMessage.message1).where(QMessage.message1.sender.userName.contains(text)).fetch();
    }

    @Transactional
    public List<Message> searchInMessages(String text){
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMessage.message1).where(QMessage.message1.message.contains(text)).fetch();
    }


}



