package com.example.Asisgnmentpostgre.DTO;

import com.example.Asisgnmentpostgre.Entity.Course;
import com.example.Asisgnmentpostgre.Entity.Instructor;
import com.example.Asisgnmentpostgre.Entity.StudentCourseEnrollment;
import lombok.Data;

import java.util.List;

@Data
public class AllDetailsByCourseId {
    private List<StudentCourseEnrollment> students;
    private Course course;
    private Instructor instructor;
}

