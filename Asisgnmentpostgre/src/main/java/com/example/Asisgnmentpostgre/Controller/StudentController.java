package com.example.Asisgnmentpostgre.Controller;

import com.example.Asisgnmentpostgre.DTO.StudentDto;
import com.example.Asisgnmentpostgre.Entity.Student;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Repositories.CourseRepository;
import com.example.Asisgnmentpostgre.Response.ApiResponse;
import com.example.Asisgnmentpostgre.Service.impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/createStudent")
    public ResponseEntity<ApiResponse<Student>> createStudent(@RequestBody StudentDto studentDTO) {
        return ResponseEntity.ok(studentService.insertOrUpdate(studentDTO));
    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @GetMapping("/getStudent/{id}")
    public ResponseEntity<ApiResponse<StudentDto>> getStudentDetails(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentDetails(id));
    }

    @DeleteMapping("/deleteCourse/{id}/{courseId}")
    public ResponseEntity<ApiResponse<StudentDto>> deleteCourses(@PathVariable Long courseId, @PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteCourseById(courseId, id));
    }

    @PostMapping("/enrollCourse/{id}/{courseId}")
    public ResponseEntity<ApiResponse<StudentDto>> enrollCourse(@PathVariable Long id, @PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.enrollCourse(id, courseId));
    }

    @PostMapping("/setStatus/{studentId}/{courseId}/{status}")
    public ResponseEntity<ApiResponse<StudentDto>> setStatus(@PathVariable Long studentId, @PathVariable Long courseId, @PathVariable Status status) {
        return ResponseEntity.ok(studentService.setStatus(studentId, courseId, status));
    }
}
