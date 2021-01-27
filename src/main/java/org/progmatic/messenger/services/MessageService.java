package org.progmatic.messenger.services;

import org.progmatic.messenger.modell.Message;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    List<Message> messageList;

    @PersistenceContext
    EntityManager entityManager;

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

    public Message findMessageById(int messageId) {
        return entityManager.find(Message.class, messageId);
    }

    @Transactional
    public void addMessage(Message msg){
        entityManager.persist(msg);
       msg.setTime(LocalDateTime.now());
    }

    @Transactional
    public void deleteMessage(int messageID) {
        entityManager.remove(entityManager.find(Message.class, messageID));
    }
}



