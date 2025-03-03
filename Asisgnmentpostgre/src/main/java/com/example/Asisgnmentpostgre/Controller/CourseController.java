package com.example.Asisgnmentpostgre.Controller;


import com.example.Asisgnmentpostgre.DTO.CourseDto;
import com.example.Asisgnmentpostgre.Repositories.CourseRepository;
import com.example.Asisgnmentpostgre.Response.ApiResponse;

import com.example.Asisgnmentpostgre.Service.impl.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/createCourse")
    public ResponseEntity<ApiResponse<CourseDto>> createCourse(@RequestBody CourseDto courseDTO) {
        return ResponseEntity.ok(courseService.insertOrUpdate(courseDTO));
    }

    @DeleteMapping("/deleteInstructor/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.deleteCourse(id));
    }

    @GetMapping("/getCourse/{id}")
    public ResponseEntity<ApiResponse<CourseDto>> getCourseDetails(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseDetails(id));
    }
}
