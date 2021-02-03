package org.progmatic.messenger.services;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.progmatic.messenger.modell.Message;
import org.progmatic.messenger.modell.QMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

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
       return entityManager.createQuery("select m from Message m where m.topic.id = :topicID", Message.class)
                .setParameter("topicID", topicID)
                .getResultList();
    }


}



