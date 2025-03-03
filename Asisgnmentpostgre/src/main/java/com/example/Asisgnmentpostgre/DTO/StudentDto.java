package com.example.Asisgnmentpostgre.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StudentDto {
    private String name;
    private Date dateOfBirth;
    private List<Long> courseIds;
}
