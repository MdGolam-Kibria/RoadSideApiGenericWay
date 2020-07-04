package com.example.RoadSideApiGenericWay.repository;

import com.example.RoadSideApiGenericWay.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    int countByName(String name);
    Role findByName(String name);
}
