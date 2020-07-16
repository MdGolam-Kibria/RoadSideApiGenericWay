package com.example.RoadSideApiGenericWay.service;

import com.example.RoadSideApiGenericWay.dto.ProblemDto;
import org.apache.coyote.Response;

public interface ProblemService {
    com.example.RoadSideApiGenericWay.view.Response save(ProblemDto problemDto);
    com.example.RoadSideApiGenericWay.view.Response update(String email, ProblemDto problemDto);
    Response get(Long id);
    Response delete(Long id);
    com.example.RoadSideApiGenericWay.view.Response getAll();
}
