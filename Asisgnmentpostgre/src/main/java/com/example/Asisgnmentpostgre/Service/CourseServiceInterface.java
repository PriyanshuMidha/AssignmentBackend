package com.example.Asisgnmentpostgre.Service;

import com.example.Asisgnmentpostgre.DTO.CourseDto;
import com.example.Asisgnmentpostgre.Response.ApiResponse;

public interface CourseServiceInterface {
    ApiResponse<CourseDto> insertOrUpdate(CourseDto courseDTO);

    ApiResponse<Boolean> deleteCourse(Long id);

    ApiResponse<CourseDto> getCourseDetails(Long id);
}
