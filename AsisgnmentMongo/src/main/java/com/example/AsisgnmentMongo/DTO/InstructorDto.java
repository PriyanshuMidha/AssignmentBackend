package com.example.AsisgnmentMongo.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class InstructorDto {

    private String instructorId;
    private String instructorName;
    private Date dob;
    private String courseId;


}
