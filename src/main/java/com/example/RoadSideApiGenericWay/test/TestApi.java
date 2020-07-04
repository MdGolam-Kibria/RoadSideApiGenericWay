package com.example.RoadSideApiGenericWay.test;

import com.example.RoadSideApiGenericWay.model.Role;
import com.example.RoadSideApiGenericWay.model.User;
import com.example.RoadSideApiGenericWay.repository.RoleRepository;
import com.example.RoadSideApiGenericWay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class TestApi {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${login.useremail}")
    private String useremail;
    @Value("${login.password}")
    private String password;

    @Autowired
    public TestApi(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        String roleName = "ROLE_USER";
        int roleExistCount = roleRepository.countByName(roleName);
        Role role = null;
        if (roleExistCount == 1) {
            role = roleRepository.findByName(roleName);
        } else {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        User user = userRepository.findByEmailAndIsActiveTrue("golamkibria.java@gmail.com");
        if (user == null) {
            user = new User();
            user.setEmail(useremail);
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setRoles(Arrays.asList(role));
        user = userRepository.save(user);
    }
}
