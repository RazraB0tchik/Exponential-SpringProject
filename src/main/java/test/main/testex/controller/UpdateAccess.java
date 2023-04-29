package test.main.testex.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import test.main.testex.dto.UpdateAccessDTO;
import test.main.testex.filter.FilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.main.testex.repositories.UserRepository;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/update")
public class UpdateAccess {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FilterProvider filterProvider;

    @PostMapping(value ="/getAccess")
    public ResponseEntity updateAccess(@RequestBody UpdateAccessDTO updateAccessDTO) throws JsonProcessingException {
        if(filterProvider.checkRefreshToken(updateAccessDTO.getUsername())) {
            String token = filterProvider.createToken(updateAccessDTO.getUsername(), updateAccessDTO.getRole());
            HashMap<Object, Object> newAccess = new HashMap<>();
            newAccess.put("tokenUpdate", token);
            newAccess.put("username", updateAccessDTO.getUsername());
            newAccess.put("role", updateAccessDTO.getRole());
            return ResponseEntity.ok(newAccess);
        }
        else {
            return ResponseEntity.status(405).body("Invalid refresh token");
        }
    }
}
