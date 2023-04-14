package test.main.testex.service;

import test.main.testex.entity.RefreshToken;
import test.main.testex.entity.User;
import test.main.testex.entity.UserDetailElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import test.main.testex.refreshToken.RefreshCreator;
import test.main.testex.repositories.RefreshRepository;
import test.main.testex.repositories.UserRepository;

import java.util.Collection;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RefreshRepository refreshRepository;

    @Autowired
    RefreshCreator refreshCreator;

    private Collection<GrantedAuthority> authorities;

    public UserService(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("inside service");
        User user = userRepository.findUserByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("user with name " + user +" not found");
        }
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        System.out.println(user.getActive());
        System.out.println("asd");
        UserDetailElement userDetailElement = new UserDetailElement(authorities, user.getPasswordUser(), user.getUserName(), user.getActive());
        System.out.println(userDetailElement.getPassword()+" pas in user detail");
        return userDetailElement;
    }

    public void saveNewUser(String username, String password, String email){
        String hashPasswrd = new BCryptPasswordEncoder(10).encode(password);
        User newUser = new User(username, hashPasswrd, "USER", true, email);
        RefreshToken refreshToken = refreshCreator.createRef();
        newUser.setRefreshToken(refreshToken);
        userRepository.save(newUser);
    }

}
