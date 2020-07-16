package com.example.RoadSideApiGenericWay.config;

import com.example.RoadSideApiGenericWay.filter.CustomFilter;
import com.example.RoadSideApiGenericWay.service.securityService.MyAuthenticationEntryPoint;
import com.example.RoadSideApiGenericWay.service.securityService.CustomUserDetailsService;
import com.example.RoadSideApiGenericWay.util.UrlConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class.getName());
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    private final CustomFilter customFilter;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder, MyAuthenticationEntryPoint myAuthenticationEntryPoint, CustomFilter customFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
        this.customFilter = customFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String allPrefix = "/*";
        http.cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/login*")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("bcryptPassword")
                .loginProcessingUrl("/doLogIn")
                .defaultSuccessUrl("/doLogIn")
                .failureUrl("/login_error")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    if (authentication.isAuthenticated()){
                        LOGGER.info("login success "+ authentication.getName());
                        httpServletResponse.sendRedirect("/");
                    }
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendRedirect("/login_error"));
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {//if you want to to ignore certain URLs
        web.ignoring().antMatchers("/RoadSideHelper/");
    }
}
