package com.example.AsisgnmentMongo.Controllers;

//import com.example.AsisgnmentMongo.DTO.AllDetailByCourseId;
import com.example.AsisgnmentMongo.DTO.InstructorDto;
import com.example.AsisgnmentMongo.Entities.CourseStatus;
import com.example.AsisgnmentMongo.Entities.Instructor;
import com.example.AsisgnmentMongo.Services.OrganizationService;
import com.example.AsisgnmentMongo.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Organization")
public class Organizationontroller {


    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/studentCount")
    public ResponseEntity<ApiResponse<Long>> getStudentCount() {
        return ResponseEntity.ok( organizationService.getStudentCount());
    }

    @GetMapping("/courseStudentCount/{id}")
    public ResponseEntity<ApiResponse<Integer>> getCourseStudentCount(@PathVariable String id) {

        return ResponseEntity.ok( organizationService.getCourseStudentCount(id));
    }

    @GetMapping("/courseInstructors/{id}")
    public ResponseEntity<ApiResponse<List<Instructor>>> getCourseInstructors(@PathVariable String id) {
        return ResponseEntity.ok( organizationService.getCourseInstructors(id));
    }

    @GetMapping("/instructorsCount")
    public ResponseEntity<ApiResponse<Long>> getInstructorsCount() {
        return ResponseEntity.ok( organizationService.getInstructorsCount());
    }

//    @GetMapping("/getAllDetailsByCourseId/{id}")
//    public AllDetailByCourseId.AllDetailsByCourseId getAllDetailsByCourseId(@PathVariable String id) {
//        return organizationService.getAllDetailsByCourseId(id);
//    }

//    @GetMapping("/getAllStudentsByStatus/{status}")
//    public StudentCourseEnrollmentDTO getAllStudentsByStatus(@PathVariable CourseStatus status) {
//        return organizationService.getAllStudentsByStatus(status);
//    }
}


