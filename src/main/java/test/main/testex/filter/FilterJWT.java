package test.main.testex.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import test.main.testex.exceptions.AccessTokenException;
import test.main.testex.exceptions.RefreshTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import test.main.testex.repositories.UserRepository;
//import repositories.UserRepository;

import java.io.IOException;

public class FilterJWT extends OncePerRequestFilter { //это базовый класс реализации интерфейса javax.servlet.Filter, ифльтр созданный через этот абстрактый класс получает поддержку spring

    @Autowired
    UserRepository userRepository;

    private FilterProvider filterProvider;


    public FilterJWT(FilterProvider filterProviderNew) {
        this.filterProvider = filterProviderNew;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = String.valueOf(request.getRequestURL());

        if (path.equals("http://localhost:8000/auth/api/login")) {
            return true;
        } else if (path.equals("http://localhost:8000/reg/registrationUser")) {
            return true;
        } else if (path.equals("http://localhost:8000/update/getAccess")) {
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = filterProvider.resolve(request); //вытащил токен из заголовка
            if (token != null && filterProvider.checkAccess(token)) {
                Authentication authentication = filterProvider.authenticationToken(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication); //если все хорошо то мы   System.out.println("adasdasda");заносим в контекст данне о пользователе
                }
            }

            filterChain.doFilter(request, response);
        }
        catch (AccessTokenException e){
            response.setStatus(401);
            response.getWriter().write(converObjectToJson(e.getMessage()));
        }


    }

    public String converObjectToJson(Object object) throws JsonProcessingException {
        if (object==null){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper(); //класс, выполняющий сериализацию
        return mapper.writeValueAsString(object);
    }
}
