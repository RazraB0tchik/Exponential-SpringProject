package test.main.testex.filter;


import test.main.testex.entity.RefreshToken;
import test.main.testex.entity.User;
import test.main.testex.exceptions.JwtAuthenticationException;
import test.main.testex.exceptions.TokenRequiredException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import test.main.testex.repositories.UserRepository;
import test.main.testex.service.UserService;


import java.util.Base64;
import java.util.Date;

@Component
public class FilterProvider {

    @Value(value = "${jwt.token.secret}")
    private String secretKey;

    @Value(value = "${jwt.token.expired}")
    private long expire;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;



    @PostConstruct
    protected void init(){
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String role){
      Date now = new Date();
      Date validData = new Date(now.getTime()+expire);

      Claims claims = Jwts.claims().setSubject(username);
      claims.put("role", role);

      return Jwts.builder()
              .setClaims(claims)
              .setExpiration(validData)
              .setIssuedAt(now)
              .signWith(SignatureAlgorithm.HS256, secretKey)
              .compact();
  }

  public Authentication authenticationToken(String token){ //этот метод сработает только если есть у пользователя токен
      String username = getSubject(token);
      UserDetails userDetails = userService.loadUserByUsername(username);
      return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()); //обновляет UserPass.. тем самым полностью заполняя информацию о юзере
  }

  public String getSubject(String token){ //достает из токена имя пользователя
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolve(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if((bearerToken != null) && (bearerToken.startsWith("Bearer_"))){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
  }


  public boolean checkRef(String token) throws TokenRequiredException, AuthenticationException {

            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            User user = userRepository.findUserByUserName(claims.getBody().getSubject());
            RefreshToken refreshToken = user.getRefreshToken();


            if (refreshToken.getExpiredDate().before(new Date())) {
                throw new TokenRequiredException("invalid token refresh");
            } else {
                return true;
            }
}

  public boolean validate(String token) throws AuthenticationException {
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                if (claims.getBody().getExpiration().before(new Date())) {
                    return false;
                } else {
                    return true;
                }
            } catch (JwtException | IllegalArgumentException e) {
                 throw new JwtAuthenticationException("Invalid access token");
    }
  }
}
