package org.progmatic.messenger.modell;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="TOPICS_TBL")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String topicName;

    @OneToMany
    private List<Message> messageList;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public Topic() {
    }

    public Topic(int id, String topicName, List<Message> messageList) {
        this.id = id;
        this.topicName = topicName;
        this.messageList = messageList;
    }
}
