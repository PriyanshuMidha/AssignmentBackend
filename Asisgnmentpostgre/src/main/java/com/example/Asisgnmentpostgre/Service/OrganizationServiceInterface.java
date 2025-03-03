package com.example.Asisgnmentpostgre.Service;

import com.example.Asisgnmentpostgre.DTO.AllDetailsByCourseId;
import com.example.Asisgnmentpostgre.DTO.InstructorDto;
import com.example.Asisgnmentpostgre.DTO.StudentCourseEnrollmentDTO;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Response.ApiResponse;

import java.util.List;

public interface OrganizationServiceInterface {
    ApiResponse<Long> getStudentCount();

    ApiResponse<Integer> getCourseStudentCount(Long id);

    ApiResponse<List<InstructorDto>> getCourseInstructors(Long id);

    ApiResponse<Long> getInstructorsCount();

    ApiResponse<AllDetailsByCourseId> getAllDetailsByCourseId(Long id);

    ApiResponse<List<StudentCourseEnrollmentDTO>> getAllStudentsByStatus(Status status);
}
