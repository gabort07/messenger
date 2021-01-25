package org.progmatic.messenger.Controllers;

import org.progmatic.messenger.modell.Message;
import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    private MessageService mService;
    private UserDetailsService userDetailsService;

    @Autowired
    public MessageController(MessageService messageService, UserDetailsService service) {
        this.mService = messageService;
        this.userDetailsService = service;
    }

    @GetMapping(value = {"/allmessages"})
    public String messages(Model model) {
        model.addAttribute("message", "Üzenet szövege");
        model.addAttribute("writtenBy", "Írta");
        model.addAttribute("time", "Időpont");
        model.addAttribute("messages", mService.getAllMessages());

        return "allmessages";
    }

    @GetMapping("/")
    public String main() {
        return "main";
    }


    @GetMapping("/addmessage")
    public String addMessage(@ModelAttribute("message") Message msg) {
        return "addmessage";
    }

    @PostMapping("/message/create")
    public String createMessage(@ModelAttribute("message") @Valid Message msg,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addmessage";
        }
        UserDetails user = userDetailsService.loadUserByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        msg.setSender(user.getUsername());
        mService.addMessage(msg);
        return "redirect:/messages";
    }

    @GetMapping("/onemessage/{id}")
    public String showMessage(
            @PathVariable("id") int messagesID, Model model)    {
        model.addAttribute("message", mService.findMessageById(messagesID));
        return "onemessage";
    }

    @GetMapping("/deletemessage/{id}")
    public String deleteMessage(@PathVariable("id") int messageID){
        mService.deleteMessage(messageID);
        return "/allmessages";
    }

    @GetMapping("/registration")
    public String registerUsers() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("User") MyUser user) {
        InMemoryUserDetailsManager userManager =
                (InMemoryUserDetailsManager) userDetailsService;
        userManager.createUser(user);
        return "redirect:/loginpage";
    }

    @GetMapping("/loginpage")
    public String loginPage() {
        return "loginpage";
    }

}