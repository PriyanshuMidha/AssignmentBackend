package com.example.Asisgnmentpostgre.DTO;
import com.example.Asisgnmentpostgre.Entity.Course;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Entity.Student;

import lombok.Data;

@Data
public class StudentCourseEnrollmentDTO {
    private Student student;
    private Course course;
    private Status status;
}

