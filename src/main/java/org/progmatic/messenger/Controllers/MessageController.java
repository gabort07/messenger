package org.progmatic.messenger.Controllers;

import org.progmatic.messenger.modell.Message;
import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.modell.Topic;
import org.progmatic.messenger.services.MessageService;
import org.progmatic.messenger.services.TopicService;
import org.progmatic.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.List;

@Controller
public class MessageController {

    private MessageService messageService;
    private UserDetailsService userDetailsService;
    private TopicService topicService;

    @Autowired
    public MessageController(MessageService messageService, UserDetailsService service,
                             TopicService topicService) {
        this.messageService = messageService;
        this.userDetailsService = service;
        this.topicService = topicService;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/logout")
    public String logout() {
        return "loginpage";
    }

    @GetMapping("/addmessage")
    public String addMessage(@ModelAttribute("message") Message msg,
                             Model model) {
        UserService userService = (UserService) userDetailsService;
        MyUser actualUser = (MyUser) userDetailsService.loadUserByUsername(((UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUsername());
        List<MyUser> othersList = userService.lisAllUser();
        othersList.removeIf(myUser -> myUser.getId() == actualUser.getId());
        model.addAttribute("actualUser", actualUser);
        model.addAttribute("users", othersList);
        model.addAttribute("topics", topicService.listAllTopics());
        return "addmessage";
    }

    @PostMapping("/addmessage/newtopic")
    public String newTopic(@ModelAttribute("topic") @Valid Topic topic,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/addmessage";
        }
        topicService.addNewTopic(topic);
        return "redirect:/addmessage";
    }


    @PostMapping("/addmessage/create")
    public String createMessage(@ModelAttribute("message") @Valid Message msg,
                                @ModelAttribute("actualUser") @Valid MyUser actualUsr,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/addmessage";
        }
        msg.setSender(actualUsr);
        messageService.addMessage(msg);
        return "redirect:/allmessages";
    }

    @GetMapping("/onemessage/{id}")
    public String showMessage(
            @PathVariable("id") int messagesID, Model model) {
        model.addAttribute("message", messageService.findMessageById(messagesID));
        return "onemessage";
    }

    @GetMapping("/onemessage/{id}/{text}")
    public String modifyMessageText(
            @PathVariable("id") int messagesID,
            @PathVariable("text") String text,
            Model model) throws InterruptedException {
        model.addAttribute("message", messageService.modifyMessage(messagesID, text));
        return "onemessage";
    }

    @GetMapping("/onemessage/{id}/{sleep}/{text}")
    public String modifyMessageTextWithSleep(
            @PathVariable("id") int messagesID,
            @PathVariable("sleep") long sleep,
            @PathVariable("text") String text,
            Model model) throws InterruptedException {
        model.addAttribute("message", messageService.modifyMessage(messagesID, sleep, text));
        return "onemessage";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deletemessage/{id}")
    public String deleteMessage(@PathVariable("id") int messageID) {
        messageService.deleteMessage(messageID);
        return "redirect:/allmessages";
    }

    @GetMapping("/allmessages/{topicID}")
    public String listMessasgesInTopic(@PathVariable("topicID") int topicID, Model model) {
        model.addAttribute("messagesInTopic", messageService.getMessagesFromTopic(topicID));
        return "messagesintopic";
    }




}