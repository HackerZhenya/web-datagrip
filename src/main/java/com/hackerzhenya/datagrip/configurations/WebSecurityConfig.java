package com.hackerzhenya.datagrip.configurations;

import com.hackerzhenya.datagrip.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //Доступ только для не зарегистрированных пользователей
                .antMatchers("/sign-up").not().fullyAuthenticated()
            //Все остальные страницы требуют аутентификации
            .anyRequest().authenticated()
            .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/sign-in")
                .defaultSuccessUrl("/connections")
                .permitAll()
            .and()
                .logout().permitAll()
                .logoutUrl("/sign-out")
                .logoutSuccessUrl("/sign-in");
    }

    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(bCryptPasswordEncoder());
    }
}
