package com.example.RoadSideApiGenericWay.repository;

import com.example.RoadSideApiGenericWay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndIsActiveTrue(String email);
}
