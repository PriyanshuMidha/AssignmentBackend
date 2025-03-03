package com.example.Asisgnmentpostgre.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class InstructorDto {
    private String name;
    private Date dateOfBirth;
    private Long courseId;
}
