package com.example.Asisgnmentpostgre.Service;

import com.example.Asisgnmentpostgre.DTO.StudentDto;
import com.example.Asisgnmentpostgre.Entity.Student;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Response.ApiResponse;

public interface StudentServiceInterface {
    ApiResponse<Student> insertOrUpdate(StudentDto studentDTO);

    ApiResponse<Boolean> deleteStudent(Long id);

    ApiResponse<StudentDto> getStudentDetails(Long id);

    ApiResponse<StudentDto> deleteCourseById(Long courseId, Long studentId);

    ApiResponse<StudentDto> enrollCourse(Long id, Long courseId);

    ApiResponse<StudentDto> setStatus(Long studentId, Long courseId, Status status);
}
