package org.progmatic.messenger.modell;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name="MESSAGES_TBL")
public class Message {

    @Version
    private long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @ManyToOne
    private MyUser sender;

    @NotNull
    @ManyToOne
    private MyUser receiver;

    @Column(name="sentDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;

    @NotBlank
    @NotEmpty
    @Size(min = 0, max = 300)
    private String message;

    @ManyToOne

    private Topic topic;

    @OneToMany
    private Collection<Message> commentsList;


    public Message() {}

    public Message(MyUser sender, MyUser receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.id = (int) (Math.random() * 1000) + 1;
        this.time = LocalDateTime.now();

    }

    public Message(int id, MyUser sender, MyUser receiver, String message, Collection<Message> commentsList) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.id = id;
        this.commentsList = commentsList;
        this.time = LocalDateTime.now();
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }


    public MyUser getReceiver() {
        return receiver;
    }

    public int getId() {
        return id;
    }

    public MyUser getSender() {
        return sender;
    }

    public LocalDateTime getTime() {
//        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return time;
    }

    public String getMessage() {
        return message;
    }

    public void setReceiver(MyUser receiver) {
        this.receiver = receiver;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(MyUser sender) {
        this.sender = sender;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Collection<Message> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(Collection<Message> commentsList) {
        this.commentsList = commentsList;
    }
}


