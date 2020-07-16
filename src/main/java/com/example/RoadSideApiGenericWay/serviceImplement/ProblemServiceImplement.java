package com.example.RoadSideApiGenericWay.serviceImplement;

import com.example.RoadSideApiGenericWay.dto.ProblemDto;
import com.example.RoadSideApiGenericWay.model.Problem;
import com.example.RoadSideApiGenericWay.repository.ProblemRepository;
import com.example.RoadSideApiGenericWay.service.ProblemService;
import com.example.RoadSideApiGenericWay.view.ResponseBuilder;
import org.apache.coyote.Response;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("problemService")
public class ProblemServiceImplement implements ProblemService {
    private final String root = "Problem";
    private final ProblemRepository problemRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public ProblemServiceImplement(ProblemRepository problemRepository, ModelMapper modelMapper) {
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public com.example.RoadSideApiGenericWay.view.Response save(ProblemDto problemDto) {
        Problem problem = modelMapper.map(problemDto,Problem.class);
        problem = problemRepository.save(problem);
        if (problem!=null){
            return ResponseBuilder.getSuccessResponce(HttpStatus.CREATED,root+" Created Successfully",null);
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error occurs");
    }

    @Override
    public com.example.RoadSideApiGenericWay.view.Response update(String email, ProblemDto problemDto) {
        Problem problem  = problemRepository.findByEmailAndIsActiveTrue(email);
        if (problem!=null){
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(problemDto,problem);
            problem = problemRepository.save(problem);
            if (problem!=null){
                return ResponseBuilder.getSuccessResponce(HttpStatus.OK,root+" Updated Successfully",null);
            }
            return ResponseBuilder.getFailureResponce(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error occurs");
        }
        return ResponseBuilder.getFailureResponce(HttpStatus.NOT_FOUND,root+" Not Found");
    }

    @Override
    public Response get(Long id) {
        return null;
    }

    @Override
    public Response delete(Long id) {
        return null;
    }

    @Override
    public com.example.RoadSideApiGenericWay.view.Response getAll() {
        List<Problem>problems = problemRepository.findAllByIsActiveTrue();
        List<ProblemDto> productDtos = this.getProducts(problems);
        return ResponseBuilder.getSuccessResponce(HttpStatus.OK, root + " retrieved Successfully",productDtos);
    }
    private List<ProblemDto> getProducts(List<Problem> problems) {//DTO dara data dissi getAll method ke ei method dara
        List<ProblemDto> productDtos = new ArrayList<>();
        problems.forEach(product -> {
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            ProblemDto problemDto =  modelMapper.map(product,ProblemDto.class);
            productDtos.add(problemDto);
        });
        return productDtos;
    }
}
