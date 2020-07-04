package com.example.RoadSideApiGenericWay.service.securityService;

import com.example.RoadSideApiGenericWay.model.User;
import com.example.RoadSideApiGenericWay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUser(s);
        //UserPrinciple userPrinciple = UserPrinciple.create(user);
        UserPrinciple userPrinciple = UserPrinciple.create(user);
        if (userPrinciple == null) {
            throw new UsernameNotFoundException("User Email Not Found");
        }
        return userPrinciple;
    }
}
