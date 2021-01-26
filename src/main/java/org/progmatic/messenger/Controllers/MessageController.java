package org.progmatic.messenger.Controllers;

import org.progmatic.messenger.modell.Message;
import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.services.MessageService;
import org.progmatic.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MessageController {

    private MessageService messageService;
    private UserDetailsService userDetailsService;

    @Autowired
    public MessageController(MessageService messageService, UserDetailsService service) {
        this.messageService = messageService;
        this.userDetailsService = service;
    }

    @GetMapping({"/allmessages"})
    public String messages(Model model) {
        model.addAttribute("message", "Üzenet szövege");
        model.addAttribute("writtenBy", "Írta");
        model.addAttribute("time", "Időpont");
        model.addAttribute("messages", messageService.getAllMessages());

        return "allmessages";
    }

    @GetMapping("/")
    public String main() {
        return "main";
    }


    @GetMapping("/addmessage")
    public String addMessage(@ModelAttribute("message") Message msg, Model model) {
        UserService userService = (UserService) userDetailsService;
        model.addAttribute("users", userService.lisAllUser());
        return "addmessage";
    }

    @PostMapping("/message/create")
    public String createMessage(@ModelAttribute("message") @Valid Message msg,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addmessage";
        }
        MyUser user = (MyUser) userDetailsService.loadUserByUsername(((UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUsername());
        msg.setSender(user);
        messageService.addMessage(msg);
        return "redirect:/allmessages";
    }

    @GetMapping("/onemessage/{id}")
    public String showMessage(
            @PathVariable("id") int messagesID, Model model)    {
        model.addAttribute("message", messageService.findMessageById(messagesID));
        return "onemessage";
    }

    @GetMapping("/deletemessage/{id}")
    public String deleteMessage(@PathVariable("id") int messageID){
        messageService.deleteMessage(messageID);
        return "redirect:/allmessages";
    }


}