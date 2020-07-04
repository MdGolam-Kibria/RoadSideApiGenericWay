package com.example.RoadSideApiGenericWay.repository;

import com.example.RoadSideApiGenericWay.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    //Problem findByIdAndIsActiveTrue(Long id);
    Problem findByEmailAndIsActiveTrue(String email);
    List<Problem> findAllByIsActiveTrue();
}
