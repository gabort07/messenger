package org.progmatic.messenger.Controllers;

import org.progmatic.messenger.DTO.UserDTO;
import org.progmatic.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/rest")
public class RestUserController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public RestUserController(UserDetailsService service){
        this.userDetailsService= service;
    }

    @PostMapping("/registration")
    public String registerUsers(@RequestBody UserDTO userDTO,
                                BindingResult bindingResult){
        UserService userService = (UserService) userDetailsService;
        if(userService.isUserNameTaken(userDTO.getUserName())){
            return "userName taken";
        }
        userService.addUserRest(userDTO);
        return "registration success "+userDTO.getUserName();
    }

    @GetMapping("/usersList")
    public @ResponseBody
    Collection<UserDTO> allMessages(){
        UserService userService = (UserService) userDetailsService;
        return userService.makeUserDTOList();
    }

    @GetMapping("/loginpage")
    public String loginPage() {
        return "loginpage";
    }

}
