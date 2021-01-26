package org.progmatic.messenger.Controllers;

import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserDetailsService userDetailsService;

    @Autowired
    public UserController(UserDetailsService service){
        this.userDetailsService= service;
    }

    @GetMapping("/registration")
    public String registerUsers() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("User") MyUser user) {
        UserService userService = (UserService) userDetailsService;
        userService.addUser(user);
        return "redirect:/loginpage";
    }

    @GetMapping("/loginpage")
    public String loginPage() {
        return "loginpage";
    }


}
