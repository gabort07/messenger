package org.progmatic.messenger.modell;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
//    @NotEmpty
    private String sender;
    @NotEmpty
    private String reciver;
    private LocalDateTime time;
    @Size(min = 0, max = 300)
    private String message;
    private int id;

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

    public Message(String sender, String reciver, String message) {
        this.sender = sender;
        this.reciver = reciver;
        this.message = message;
        this.id = (int) (Math.random() * 1000) +1;
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

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getMessage() {
        return message;
    }
}
