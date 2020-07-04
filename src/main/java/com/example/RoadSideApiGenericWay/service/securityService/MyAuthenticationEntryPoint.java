package com.example.RoadSideApiGenericWay.service.securityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class MyAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyAuthenticationEntryPoint.class.getName());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic Realm=" + getRealmName());
        LOGGER.error("Message = " + authException.getMessage());
        response.setStatus(response.SC_UNAUTHORIZED);
        response.sendError(response.SC_UNAUTHORIZED, "This is UnAuthorized Request");
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("RoadSideHelper");
        super.afterPropertiesSet();
    }
}
