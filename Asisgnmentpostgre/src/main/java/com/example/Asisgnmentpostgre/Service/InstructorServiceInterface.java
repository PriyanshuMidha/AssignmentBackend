package com.example.Asisgnmentpostgre.Service;

import com.example.Asisgnmentpostgre.DTO.InstructorDto;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Response.ApiResponse;

public interface InstructorServiceInterface {
    ApiResponse<InstructorDto> insertOrUpdate(InstructorDto instructorDTO);


    ApiResponse<Boolean> deleteInstructor(Long id);

    ApiResponse<InstructorDto> getInstructorDetails(Long id);

    ApiResponse<Boolean> withdrawCourse(Long id);

    ApiResponse<Status> getStudentStatus(Long id);
}
