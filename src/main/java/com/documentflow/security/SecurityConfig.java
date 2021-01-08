package com.documentflow.security;

import com.documentflow.services.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) //enable security single methods
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Setter(onMethod_ = {@Autowired})
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    //map views names to URLs with no need for an explicit controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/api/**").authenticated()
                .antMatchers("old/docs/in/**").hasAnyRole("DOC_IN_READ")
                .antMatchers("old/docs/out/**").hasAnyRole("DOC_OUT_READ")
                .antMatchers("old/tasks/**").hasRole("TASKS_READ")
                .antMatchers("old/sys/**").hasRole("SYS_READ")
                .antMatchers("old/profile/**").authenticated()
// контроль за роутингами на стороне фронта, после перехода на /
// кроме login, logout, perform_login, api/**
// тут необходимо прописывать пермишены для получения данных по апи после переделки апи
// получать заглушку при отсутсвии прав на рест с той или иной страницы
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", false)
                .loginProcessingUrl("/perform_login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login?logout")
                .permitAll();

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}