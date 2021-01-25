package org.progmatic.messenger.services;

import org.progmatic.messenger.modell.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    List<Message> messageList;

    public MessageService() {
        this.messageList = new ArrayList<>();
        messageList.add(new Message("Pisti", "Zoli", "Szia!"));
        messageList.add(new Message("Zoli", "Pist", "Szia!"));
        messageList.add(new Message("Pisti", "Zoli", "Mi a dörgés?"));
        messageList.add(new  Message("Zoli", "Pisti", "Semmi! Ott?"));
        messageList.add(new Message("Pisti", "Zoli", "Semmi!"));
        messageList.add(new Message("Zoli", "Pisti", "Ok! Csá!"));
        messageList.add(new Message("Pisti", "Zoli", "Ok! Csá!"));

    }

    public List<Message> getAllMessages() {
        return messageList;
    }

    public Message findMessageById(int messageId) {
        Optional<Message> opt = messageList.stream()
                .filter(message -> message.getId() == messageId)
                .findFirst();
        return opt.orElse(null);
    }

    public void addMessage(Message msg){
        messageList.add(msg);
    }

    public void deleteMessage(int messageID) {
        messageList.removeIf(actual -> actual.getId() == messageID);
    }
}



