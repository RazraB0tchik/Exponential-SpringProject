package test.main.testex.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import test.main.testex.filter.FilterJWT;
import test.main.testex.filter.FilterProvider;
import test.main.testex.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserService userService;

    private final FilterProvider filterProvider;

    public SecurityConfig(FilterProvider provider) {
        this.filterProvider = provider;
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

        http
                .httpBasic().and().csrf().disable() //http Security конфигурация проверят ауентификацию пользователя для каждого запроса (если запрос был отправлен аутентифицированным пользователем то збс)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers("/auth/api/login", "/reg/registrationUser", "/update/getAccess/**").permitAll()
                            .anyRequest().authenticated();
                })
                .cors()
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new FilterJWT(filterProvider), UsernamePasswordAuthenticationFilter.class);//добавили фильтр в конфигурацию

        return http.build();
    }
}
