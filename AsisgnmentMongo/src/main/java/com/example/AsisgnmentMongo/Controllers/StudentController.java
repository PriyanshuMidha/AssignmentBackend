package com.example.AsisgnmentMongo.Controllers;

import com.example.AsisgnmentMongo.DTO.StudentDto;
import com.example.AsisgnmentMongo.Entities.CourseStatus;
import com.example.AsisgnmentMongo.Entities.Student;
import com.example.AsisgnmentMongo.Services.StudentService;
import com.example.AsisgnmentMongo.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> registerStudent(@RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.registerStudent(studentDto));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<ApiResponse<Student>> editStudent(@PathVariable String studentId, @RequestBody StudentDto updatedStudentDto) {
        return ResponseEntity.ok(studentService.editStudentDetails(studentId, updatedStudentDto));
    }

    @DeleteMapping("/{studentId}")
    public  ResponseEntity<ApiResponse<Boolean>>  deleteStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.deleteStudentDetails(studentId));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<ApiResponse<Student>>  getStudentDetails(@PathVariable String studentId) {
        return ResponseEntity.ok(studentService.getStudentDetails(studentId));
    }

    @PostMapping("/enroll/{studentId}/{courseId}")
    public ResponseEntity<ApiResponse<Boolean>>   enrollInCourse(@PathVariable String studentId, @PathVariable String courseId) {
        return ResponseEntity.ok(studentService.enrollInCourse(studentId, courseId));
    }

    @PostMapping("/withdraw/{studentId}/{courseId}")
    public ResponseEntity<ApiResponse<Boolean>>   withdrawFromCourse(@PathVariable String studentId, @PathVariable String courseId) {
        return ResponseEntity.ok(studentService.withdrawFromCourse(studentId, courseId));
    }

    @PostMapping("/updateCourseStatus/{studentId}/{courseId}/{status}")
    public ResponseEntity<ApiResponse<Boolean>>   updateCourseStatus(@PathVariable String studentId, @PathVariable String courseId, @PathVariable CourseStatus status) {
        return ResponseEntity.ok(studentService.updateCourseStatus(studentId, courseId, status));
    }
}
