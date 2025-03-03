package com.example.Asisgnmentpostgre.Controller;

import com.example.Asisgnmentpostgre.DTO.AllDetailsByCourseId;
import com.example.Asisgnmentpostgre.DTO.InstructorDto;
import com.example.Asisgnmentpostgre.DTO.StudentCourseEnrollmentDTO;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Response.ApiResponse;
import com.example.Asisgnmentpostgre.Service.impl.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/studentCount")
    public ResponseEntity<ApiResponse<Long>> getStudentCount() {
        return ResponseEntity.ok(organizationService.getStudentCount());
    }

    @GetMapping("/courseStudentCount/{id}")
    public ResponseEntity<ApiResponse<Integer>> getCourseStudentCount(@PathVariable Long id) {
        return ResponseEntity.ok(organizationService.getCourseStudentCount(id));
    }

    @GetMapping("/courseInstructors/{id}")
    public ResponseEntity<ApiResponse<List<InstructorDto>>> getCourseInstructors(@PathVariable Long id) {
        return ResponseEntity.ok(organizationService.getCourseInstructors(id));
    }

    @GetMapping("/instructorsCount")
    public ResponseEntity<ApiResponse<Long>> getInstructorsCount() {
        return ResponseEntity.ok(organizationService.getInstructorsCount());
    }

    @GetMapping("/getAllDetailsByCourseId/{id}")
    public ResponseEntity<ApiResponse<AllDetailsByCourseId>> getAllDetailsByCourseId(@PathVariable Long id) {
        return ResponseEntity.ok(organizationService.getAllDetailsByCourseId(id));
    }

    @GetMapping("/getAllStudentsByStatus/{status}")
    public ResponseEntity<ApiResponse<List<StudentCourseEnrollmentDTO>>> getAllStudentsByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(organizationService.getAllStudentsByStatus(status));
    }
}

