package com.example.AsisgnmentMongo.Controllers;

import com.example.AsisgnmentMongo.DTO.InstructorDto;
import com.example.AsisgnmentMongo.Entities.CourseStatus;
import com.example.AsisgnmentMongo.Entities.Instructor;
import com.example.AsisgnmentMongo.Services.InstructorService;
import com.example.AsisgnmentMongo.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<Instructor>> registerInstructor(@RequestBody InstructorDto instructorDto) {
        return ResponseEntity.ok(instructorService.registerInstructor(instructorDto));
    }

    @PutMapping("/{instructorId}")
    public ResponseEntity<ApiResponse<Instructor>>  updateInstructorDetails(@PathVariable String instructorId, @RequestBody InstructorDto updatedInstructorDto) {
        return ResponseEntity.ok(instructorService.updateInstructorDetails(instructorId, updatedInstructorDto));
    }

    @DeleteMapping("/{instructorId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteInstructor(@PathVariable String instructorId) {
        return ResponseEntity.ok(instructorService.deleteInstructor(instructorId));
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<ApiResponse<Instructor>>  getInstructorDetails(@PathVariable String instructorId) {
        return ResponseEntity.ok(instructorService.getInstructorDetails(instructorId));
    }

    @PostMapping("/{instructorId}/assign")
    public ResponseEntity<ApiResponse<Boolean>> assignCourseToInstructor(@PathVariable String instructorId, @RequestParam String courseId) {
        return ResponseEntity.ok(instructorService.assignCourseToInstructor(instructorId, courseId));
    }

    @DeleteMapping("/{instructorId}/unassign")
    public ResponseEntity<ApiResponse<Boolean>> unassignCourseFromInstructor(@PathVariable String instructorId, @RequestParam String courseId) {
        return ResponseEntity.ok(instructorService.unassignCourseFromInstructor(instructorId, courseId));
    }

    @PutMapping("/{instructorId}/courses/{courseId}/status")
    public ResponseEntity<ApiResponse<Boolean>> updateCourseStatusForStudent(@PathVariable String instructorId,
                                               @PathVariable String courseId,
                                               @RequestParam String studentId,
                                               @RequestParam CourseStatus status) {
        return ResponseEntity.ok(instructorService.updateCourseStatusForStudent(instructorId, courseId, studentId, status));
    }
}
