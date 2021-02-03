package org.progmatic.messenger.services;

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
        List<Topic> list = entityManager.createQuery(
                "SELECT m FROM Topic m")
                .getResultList();
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
