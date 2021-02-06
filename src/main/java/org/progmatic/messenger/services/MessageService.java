package org.progmatic.messenger.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.progmatic.messenger.DTO.CreateMessageDTO;
import org.progmatic.messenger.DTO.MessageDTOWiev;
import org.progmatic.messenger.modell.Message;
import org.progmatic.messenger.modell.QMessage;
import org.progmatic.messenger.modell.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserService userService;
    @Autowired
    MessageService self;

    public List<Message> getAllMessages() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMessage.message1).fetch();
    }

    public List<MessageDTOWiev> makeDTOMessageList() {
        List<MessageDTOWiev> list = new ArrayList<>();
        for (Message actual : self.getAllMessages()) {
            list.add(self.makeDTOWievFromMessage(actual));
        }
        return list;
    }

    @Transactional
    public Message findMessageById(int messageId) {
        return entityManager.find(Message.class, messageId);
    }

    @Transactional
    public void addMessage(Message msg) {
        entityManager.persist(msg);
        msg.setTime(LocalDateTime.now());
    }

    @Transactional
    public MessageDTOWiev findMessage(int id) {
        Message message = self.findMessageById(id);
        return self.makeDTOWievFromMessage(message);

    }

    public MessageDTOWiev makeDTOWievFromMessage(Message message) {
        MessageDTOWiev messageDTOWiev = new MessageDTOWiev();
        messageDTOWiev.setSenderName(message.getSender().getUserName());
        messageDTOWiev.setReceiverName(message.getReceiver().getUserName());
        messageDTOWiev.setTime(message.getTime());
        messageDTOWiev.setTopicName(message.getTopic().getTopicName());
        messageDTOWiev.setText(message.getMessage());
        return messageDTOWiev;
    }

    @Transactional
    public CreateMessageDTO createRestMessage(CreateMessageDTO restMessage) {
        Message newMessage = new Message();
        newMessage.setMessage(restMessage.getText());
        newMessage.setSender(userService.findUserByID(restMessage.getSenderID()));
        newMessage.setReceiver(userService.findUserByID(restMessage.getReceiverID()));
        newMessage.setTopic(self.searchTopicByID(restMessage.getTopicID()));
        self.addMessage(newMessage);

        return restMessage;

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
        Thread.sleep(sleep * 1000);
        actual.setMessage(actual.getMessage().concat(text));
        return actual;
    }

    @Transactional
    public void deleteMessage(int messageID) {
        entityManager.remove(self.findMessageById(messageID));
    }

    @Transactional
    public List<Message> getMessagesFromTopic(int topicID) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMessage.message1).where(QMessage.message1.topic.id.eq(topicID)).fetch();
    }

    @Transactional
    public List<Message> searchIn(String text, Boolean topics, Boolean users, Boolean messages) {
        List<Message> list = new ArrayList<>();
        if (topics) {
            list.addAll(self.searchInTopics(text));
        }
        if (users) {
            list.addAll(self.searchInUsers(text));
        }
        if (messages) {
            list.addAll(self.searchInMessages(text));
        }
        return list;
    }

    @Transactional
    public List<Message> searchInTopics(String text) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMessage.message1).where(QMessage.message1.topic.topicName.contains(text)).fetch();
    }

    @Transactional
    public List<Message> searchInUsers(String text) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMessage.message1).where(QMessage.message1.sender.userName.contains(text)).fetch();
    }

    @Transactional
    public List<Message> searchInMessages(String text) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        return queryFactory.selectFrom(QMessage.message1).where(QMessage.message1.message.contains(text)).fetch();
    }

    @Transactional
    public Topic searchTopicByID(int id) {
        return entityManager.find(Topic.class, id);
    }


}



