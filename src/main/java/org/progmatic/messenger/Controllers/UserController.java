package org.progmatic.messenger.Controllers;

import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service){
        this.userService= service;
    }

    @GetMapping("/registration")
    public String registerUsers(@ModelAttribute("User") MyUser user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("User") MyUser user, BindingResult bindingResult) {
        if(userService.isUserNameTaken(user.getUserName())){
            bindingResult.addError(new FieldError("User","userName", "User name taken"));
        return "registration";
        }
        userService.addUser(user);
        return "redirect:/loginpage";
    }

    @GetMapping("/loginpage")
    public String loginPage() {
        return "loginpage";
    }


}
