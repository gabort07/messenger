package org.progmatic.messenger.Controllers;

import org.progmatic.messenger.DTO.UserDTO;
import org.progmatic.messenger.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestUserController {

    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUsers(@RequestBody UserDTO userDTO,
                                BindingResult bindingResult){
        if(userService.isUserNameTaken(userDTO.getUserName())){
            return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT);
        }
        userService.addUser(userDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/regproba")
    public String proba(){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("pityuka");
        userDTO.setEmail("pityu@p.hu");
        userDTO.setPassword("pityuka");
        userService.addUser(userDTO);
        return "OK";
    }

    @GetMapping("/userslist")
    public @ResponseBody
    List<UserDTO> allMessages(){
        return userService.makeUserDTOList();
    }

    @GetMapping("/loginpage")
    public String loginPage() {
        return "loginpage";
    }

}
