package com.example.RoadSideApiGenericWay.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Problem extends BaseModel {
    private String email;
    private String image;
    private String description;
}
