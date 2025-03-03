package com.example.AsisgnmentMongo.Controllers;

import com.example.AsisgnmentMongo.DTO.CourseDto;
import com.example.AsisgnmentMongo.Entities.Course;
import com.example.AsisgnmentMongo.Services.CourseService;
import com.example.AsisgnmentMongo.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> addCourse(@RequestBody CourseDto courseDto) {
        return ResponseEntity.ok(courseService.addCourse(courseDto));

    }

    @PutMapping("/{courseId}")
    public  ResponseEntity<ApiResponse<Course>>  updateCourse(@PathVariable String courseId, @RequestBody CourseDto updatedCourseDto) {
        return ResponseEntity.ok(courseService.updateCourseDetails(courseId, updatedCourseDto));
    }

    @DeleteMapping("/{courseId}")
    public  ResponseEntity<ApiResponse<Boolean>>deleteCourse(@PathVariable String courseId) {
        return ResponseEntity.ok(courseService.deleteCourse(courseId));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse<Course>>  getCourseDetails(@PathVariable String courseId) {
        return  ResponseEntity.ok(courseService.getCourseDetails(courseId));
    }
}
