package test.main.testex.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import test.main.testex.exceptions.JwtAuthenticationException;
import test.main.testex.exceptions.TokenRequiredException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, TokenRequiredException, JwtAuthenticationException {
        try {
            String token = filterProvider.resolve(request); //вытащил токен из заголовка
            if ((token != null)  && (filterProvider.validate(token))&& (filterProvider.checkRef(token))) {
                Authentication authentication = filterProvider.authenticationToken(token);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication); //если все хорошо то мы   System.out.println("adasdasda");заносим в контекст данне о пользователе
                }
            }

            filterChain.doFilter(request, response);
        }
        catch (JwtAuthenticationException e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(converObjectToJson(e.getMessage()));
        }
        catch (RuntimeException e){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
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
