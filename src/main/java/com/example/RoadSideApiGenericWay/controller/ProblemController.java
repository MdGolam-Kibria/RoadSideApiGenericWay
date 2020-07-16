package com.example.RoadSideApiGenericWay.controller;

import com.example.RoadSideApiGenericWay.annotation.ApiController;
import com.example.RoadSideApiGenericWay.annotation.ValidateData;
import com.example.RoadSideApiGenericWay.dto.ProblemDto;
import com.example.RoadSideApiGenericWay.service.ProblemService;
import com.example.RoadSideApiGenericWay.util.UrlConstraint;
import com.example.RoadSideApiGenericWay.view.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@ApiController
@RequestMapping(UrlConstraint.ProblemManagement.ROOT)
public class ProblemController {
    private final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }
    @PostMapping(UrlConstraint.ProblemManagement.CREATE)
    @ValidateData
    public Response createProblem(@Valid @RequestBody ProblemDto problemDto, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response){
//        if (bindingResult.hasErrors()){
//            return ResponseBuilder.getFailureResponce(bindingResult,"Bean Binding Error");
//        }
        return problemService.save(problemDto);
    }

    @PutMapping(UrlConstraint.ProblemManagement.UPDATE)
    @ValidateData
    public Response update(@PathVariable("email") String email, @Valid @RequestBody ProblemDto problemDto, BindingResult result) {
//        if (result.hasErrors()) {
//            return ResponseBuilder.getFailureResponce(result, "Bean Binding error");
//        }
        return problemService.update(email, problemDto);
    }
@GetMapping("/getAll")
    public Response getAll(HttpServletRequest request){
        return problemService.getAll();
    }

}
