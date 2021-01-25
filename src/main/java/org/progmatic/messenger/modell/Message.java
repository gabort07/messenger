package org.progmatic.messenger.modell;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="MESSAGES_TBL")
public class Message {
    //    @NotEmpty
    @Column
    private String sender;

    @NotEmpty
    @Column
    private String reciver;

    @Column(name="sentDate")
    private LocalDateTime time;

    @Column
    @Size(min = 0, max = 300)
    private String message;

    @Id
    @GeneratedValue
    private int id;

    public Message() {
    }

    public Message(String sender, String reciver, String message) {
        this.sender = sender;
        this.reciver = reciver;
        this.message = message;
        this.id = (int) (Math.random() * 1000) + 1;
        this.time = LocalDateTime.now();
    }

    public Message(int id, String sender, String reciver, String message) {
        this.sender = sender;
        this.reciver = reciver;
        this.message = message;
        this.id = id;
        this.time = LocalDateTime.now();
    }

    public String getReciver() {
        return reciver;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getTime() {
//        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return time;
    }

    public String getMessage() {
        return message;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
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

    public void setSender(String sender) {
        this.sender = sender;
    }

}


