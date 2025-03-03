package com.example.AsisgnmentMongo.DTO;


import lombok.Data;

import java.util.List;
@Data
public class CourseDto {


    private String courseId;
    private String courseName;
    private double courseFee;
    private String instructorId;
    private List<String> enrolledStudentIds;

}
