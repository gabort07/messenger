package org.progmatic.messenger.Controllers;

import org.progmatic.messenger.DTO.CreateMessageDTO;
import org.progmatic.messenger.DTO.MessageDTOWiev;
import org.progmatic.messenger.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.web.csrf.CsrfToken;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestMessageController {


    private final MessageService messageService;

    @Autowired
    public RestMessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/csrf")
    public CsrfToken getCsrfOfSession(CsrfToken token) {
        return token;
    }

    @GetMapping("/allmessages")
    public @ResponseBody
    List<MessageDTOWiev> allMessages() {
        return messageService.makeDTOMessageList();
    }

    @GetMapping("/onemessage/{id}")
    public MessageDTOWiev showDTOMessage(@PathVariable("id") int id) {
        return messageService.findMessage(id);
    }

    @PostMapping(value = "/addmessage/create")
    public String createMessage(@RequestBody CreateMessageDTO createMessageDTO) {
         messageService.addMessage(createMessageDTO);
         return "OK";
    }
}
