package com.example.Asisgnmentpostgre.Service.impl;


import com.example.Asisgnmentpostgre.DTO.CourseDto;
import com.example.Asisgnmentpostgre.Entity.Course;
import com.example.Asisgnmentpostgre.Exception.EmptyFieldException;
import com.example.Asisgnmentpostgre.Repositories.CourseRepository;
import com.example.Asisgnmentpostgre.Response.ApiResponse;
import com.example.Asisgnmentpostgre.Service.CourseServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseService implements CourseServiceInterface {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public ApiResponse<CourseDto> insertOrUpdate(CourseDto courseDTO) {
        Course course = new Course();
        if (!courseDTO.getName().isEmpty()) {
            course.setName(courseDTO.getName());
        } else throw new EmptyFieldException("Course Name not Specified");
        course.setFee(courseDTO.getFee());
        saveCourse(course);
        log.info("Successfully added Course to the Database");
        return new ApiResponse<>(HttpStatus.CREATED, "Successfully created Course", courseDTO);
    }

    @CacheEvict(cacheNames = "courseCache", key = "#course.id")
    @CachePut(cacheNames = "courseCache", key = "#course.id")
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    @CacheEvict(cacheNames = "courseCache", key = "#id")
    public ApiResponse<Boolean> deleteCourse(Long id) {
        if (id != null && courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            log.info("Successfully deleted course");
            return new ApiResponse<>(HttpStatus.ACCEPTED, "Course deleted successfully", true);
        } else {
            log.info("Course with id " + id + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course id doesn't exist . Delete failed!", false);
        }
    }

    @Override
    @Cacheable(cacheNames = "courseCache", key = "#id")
    public ApiResponse<CourseDto> getCourseDetails(Long id) {
        if (id != null && courseRepository.existsById(id)) {
            Course course = courseRepository.findById(id).get();
            CourseDto courseDTO = new CourseDto();
            BeanUtils.copyProperties(course, courseDTO);
            log.info("Course found successfully");
            return new ApiResponse<>(HttpStatus.FOUND, "Course found successfully", courseDTO);
        } else {
            log.info("Course with id " + id + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course with the id " + id + " not found!", null);

        }
    }
}

