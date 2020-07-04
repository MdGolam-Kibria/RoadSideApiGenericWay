package com.example.RoadSideApiGenericWay.serviceImplement;

import com.example.RoadSideApiGenericWay.model.User;
import com.example.RoadSideApiGenericWay.repository.UserRepository;
import com.example.RoadSideApiGenericWay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmailAndIsActiveTrue(email);
    }
}
