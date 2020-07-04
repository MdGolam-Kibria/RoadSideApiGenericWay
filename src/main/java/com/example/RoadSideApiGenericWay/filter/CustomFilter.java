package com.example.RoadSideApiGenericWay.filter;

import com.example.RoadSideApiGenericWay.model.User;
import com.example.RoadSideApiGenericWay.service.UserService;
import com.example.RoadSideApiGenericWay.service.securityService.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Enumeration;

@Configuration
public class CustomFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    public CustomFilter(UserService userService, PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    private User getUser(HttpServletRequest request) {
        String authString = null;
        Enumeration<String> headersNameEnumeration = request.getHeaderNames();
        while (headersNameEnumeration.hasMoreElements()) {
            String headerKey = headersNameEnumeration.nextElement();
            if (headerKey.equalsIgnoreCase("Authorization")) {
                authString = request.getHeader(headerKey);
                break;
            }
        }
        if (authString != null && !authString.equals("")) {
            String[] authParts = authString.split("\\s+");
            String authInfo = authParts[1];
            byte[] bytes = DatatypeConverter.parseBase64Binary(authInfo);
            String decodeAuth = new String(bytes);
            String userEmailAndPassword[] = decodeAuth.split(":");
            if (userEmailAndPassword[0] != null && userEmailAndPassword[1] != null) {
                User user = userService.getUser(userEmailAndPassword[0]);
                if (passwordEncoder.matches(userEmailAndPassword[1], user.getPassword())) {
                    return user;
                }
                return null;
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            User user = getUser(httpServletRequest);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);//for pass user details
        }catch (Exception e){

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);//for next step
    }
}
