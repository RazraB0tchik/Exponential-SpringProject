package test.main.testex.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import test.main.testex.dto.AuthentificationDTO;
import test.main.testex.entity.User;
import test.main.testex.filter.FilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.main.testex.refreshToken.RefreshCreator;
import test.main.testex.repositories.UserRepository;
import test.main.testex.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth/api")
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final FilterProvider filterProvider;

    private final UserService userService;

    public AuthController(AuthenticationManager auth, FilterProvider provider, UserService service) {
        this.authenticationManager=auth;
        this.filterProvider=provider;
        this.userService = service;
    }

        @Autowired
        UserRepository userRepository;

    @Autowired
    RefreshCreator refreshCreator;

    @PostMapping( "/login")
    public ResponseEntity login(@RequestBody AuthentificationDTO authentificationDTO){
        try{
            String username = authentificationDTO.getUsername();
            User user = userRepository.findUserByUserName(username);
            if (user==null){
                throw new UsernameNotFoundException("user with name "+username+" not found");
            }
            if(!(new BCryptPasswordEncoder(10).matches(authentificationDTO.getPassword(), user.getPasswordUser()))){
                throw new BadCredentialsException("Passwords mismatch");
            }
            refreshCreator.updateRef(user);
            String token = filterProvider.createToken(username, user.getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("tokenLogin", token);
            response.put("username", username);
            response.put("role", user.getRole());

            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password - " + e.getMessage());
        }


    }

}
