package com.example.Asisgnmentpostgre.Repositories;

import com.example.Asisgnmentpostgre.DTO.StudentDto;
import com.example.Asisgnmentpostgre.Entity.Course;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Entity.Student;
import com.example.Asisgnmentpostgre.Entity.StudentCourseEnrollment;
import com.example.Asisgnmentpostgre.Response.ApiResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseEnrollment, Long> {
    StudentCourseEnrollment findByStudent(Student student);

    StudentCourseEnrollment findByStudentAndCourse(Student student, Course course);

    List<StudentCourseEnrollment> findAllByCourse(Course course);

    List<StudentCourseEnrollment> findAllByStatus(Status status);


    @Query("SELECT COUNT(sc) FROM StudentCourseEnrollment sc WHERE sc.course.id = :courseId")
    Integer findStudentCountByCourse(Long courseId);

//    @Cacheable(cacheNames = "studentCache", key = "#id")
//    ApiResponse<StudentDto> getStudentDetails(Long id);
//
//    ApiResponse<StudentDto> deleteCourseById(Long courseId, Long studentId);
//
//    ApiResponse<StudentDto> enrollCourse(Long id, Long courseId);
//
//    ApiResponse<StudentDto> setStatus(Long studentId, Long courseId, Status status);
}
