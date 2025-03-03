package com.example.Asisgnmentpostgre.Controller;

import com.example.Asisgnmentpostgre.DTO.InstructorDto;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Repositories.CourseRepository;
import com.example.Asisgnmentpostgre.Response.ApiResponse;
import com.example.Asisgnmentpostgre.Service.impl.InstructorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/createInstructor")
    public ResponseEntity<ApiResponse<InstructorDto>> createInstructor(@RequestBody InstructorDto instructorDto) {
        return ResponseEntity.ok(instructorService.insertOrUpdate(instructorDto));
    }

    @DeleteMapping("/deleteInstructor/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteInstructor(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.deleteInstructor(id));
    }

    @GetMapping("/getInstructor/{id}")
    public ResponseEntity<ApiResponse<InstructorDto>> getInstructorDetails(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.getInstructorDetails(id));
    }

    @DeleteMapping("/withdrawCourse/{id}")
    public ResponseEntity<ApiResponse<Boolean>> withdrawCourse(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.withdrawCourse(id));
    }


    @GetMapping("/getStudentStatus/{id}")
    public ResponseEntity<ApiResponse<Status>> getStudentStatus(@PathVariable Long id) {
        return ResponseEntity.ok(instructorService.getStudentStatus(id));
    }
}
