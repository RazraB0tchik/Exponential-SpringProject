package test.main.testex.controller;


import test.main.testex.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.main.testex.repositories.UserRepository;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/controller1")
public class StandartUserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/getUsers")
    public ResponseEntity getUsers(){
        System.out.println(userRepository.findAllByRole("USER"));
        List<User> allUsers = userRepository.findAllByRole("USER");
        HashMap<Object, Object> usersMap = new HashMap<>();
        for (User user: allUsers) {
            usersMap.put(user.getUserName(), user.getEmail());
        }
        System.out.println(usersMap);
        return ResponseEntity.ok(usersMap);
    }

    @GetMapping(value = "/getText")
    public void getText(){
        System.out.println("hello");
    }
}
