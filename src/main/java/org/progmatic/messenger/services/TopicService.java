package org.progmatic.messenger.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.progmatic.messenger.modell.QTopic;
import org.progmatic.messenger.modell.Topic;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    @PersistenceContext
    EntityManager entityManager;


    public List<Topic> listAllTopics(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        List<Topic> list =  queryFactory.selectFrom(QTopic.topic).fetch();
        if(list == null){
            return new ArrayList<>();
        }
        return list;
    }

    @Transactional
    public void addNewTopic(Topic topic){
        entityManager.persist(topic);
    }


}
