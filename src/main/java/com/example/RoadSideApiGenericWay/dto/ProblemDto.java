package com.example.RoadSideApiGenericWay.dto;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.NotEmpty;

@Data
public class ProblemDto {
    private Long id;
    @NotEmpty(message = "email is mandatory")
    private String email;
    @NotEmpty(message = "image is mandatory")
    private String image;
    @NotEmpty(message = "description is mandatory")
    private String description;
}
