package test.main.testex.controller;

import test.main.testex.dto.RegistrationDTO;
import test.main.testex.entity.User;
import test.main.testex.filter.FilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.main.testex.repositories.UserRepository;
import test.main.testex.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/reg")
public class RegistrateController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    FilterProvider filterProvider;

    @PostMapping(value = "/registrationUser")
    public ResponseEntity registration(@RequestBody RegistrationDTO registrationDTO){
            User user = userRepository.findUserByUserNameOrEmail(registrationDTO.getUsername(), registrationDTO.getEmail());
            if(user!=null){
                return ResponseEntity.badRequest().body("User with email "+registrationDTO.getEmail()+ " or username "+ registrationDTO.getUsername()+" exist.");
            }
            userService.saveNewUser(registrationDTO.getUsername(), registrationDTO.getPassword(), registrationDTO.getEmail());
            return ResponseEntity.ok("Success!");
    }
}
