package com.example.Asisgnmentpostgre.Service.impl;

import com.example.Asisgnmentpostgre.DTO.InstructorDto;
import com.example.Asisgnmentpostgre.Entity.Course;
import com.example.Asisgnmentpostgre.Entity.Instructor;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Exception.EmptyFieldException;
import com.example.Asisgnmentpostgre.Repositories.CourseRepository;
import com.example.Asisgnmentpostgre.Repositories.InstructorRepository;
import com.example.Asisgnmentpostgre.Repositories.StudentCourseRepository;
import com.example.Asisgnmentpostgre.Repositories.StudentRepository;
import com.example.Asisgnmentpostgre.Response.ApiResponse;
import com.example.Asisgnmentpostgre.Service.InstructorServiceInterface;
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
public class InstructorService implements InstructorServiceInterface {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private InstructorDto convertToDto(Instructor instructor) {
        InstructorDto instructorDTO = new InstructorDto();
        BeanUtils.copyProperties(instructor, instructorDTO);
        if (instructor.getCourse() != null) {
            instructorDTO.setCourseId(instructor.getCourse().getId());
        }
        return instructorDTO;
    }

//    @Override
//    public ApiResponse<InstructorDto> insertOrUpdate(InstructorDto instructorDTO) {
//        Instructor instructor = new Instructor();
//
//        if (!instructorDTO.getName().isEmpty()) {
//            instructor.setName(instructorDTO.getName());
//        } else throw new EmptyFieldException("Instructor Name not Specified");
//
//        instructor.setDateOfBirth(instructorDTO.getDateOfBirth());
//        Long courseId = instructorDTO.getCourseId();
//        instructor.setCourse(courseRepository.findById(courseId).get());
//        saveInstructor(instructor);
//
//        log.info("Successfully added Instructor to the Database");
//        return new ApiResponse<>(HttpStatus.CREATED, "Instructor added successfully", instructorDTO);
//    }
//
//    @CacheEvict(cacheNames = "instructorCache", key = "#instructor.id")
//    @CachePut(cacheNames = "instructorCache", key = "#instructor.id")
//    private void saveInstructor(Instructor instructor) {
//        instructorRepository.save(instructor);
//    }

    @Override
    public ApiResponse<InstructorDto> insertOrUpdate(InstructorDto instructorDTO) {
        Instructor instructor = new Instructor();

        if (!instructorDTO.getName().isEmpty()) {
            instructor.setName(instructorDTO.getName());
        } else throw new EmptyFieldException("Instructor Name not Specified");

        instructor.setDateOfBirth(instructorDTO.getDateOfBirth());
        Long courseId = instructorDTO.getCourseId();
        instructor.setCourse(courseRepository.findById(courseId).get());
        saveInstructor(instructor);

        log.info("Successfully added Instructor to the Database");
        return new ApiResponse<>(HttpStatus.CREATED, "Instructor added successfully", instructorDTO);
    }

    @CacheEvict(cacheNames = "instructorCache", key = "#instructor.id")
    @CachePut(cacheNames = "instructorCache", key = "#instructor.id")
    private void saveInstructor(Instructor instructor) {
        instructorRepository.save(instructor);
    }

    @Override
    @CacheEvict(cacheNames = "instructorCache", key = "#id")
    public ApiResponse<Boolean> deleteInstructor(Long id) {
        if (instructorRepository.existsById(id)) {
            instructorRepository.deleteById(id);
            log.info("Instructor deleted Successfully");
            return new ApiResponse<>(HttpStatus.ACCEPTED, "Instructor deleted successfully", true);
        } else {
            log.info("Instructor with id " + id + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Instructor id doesn't exist . Delete failed!", false);
        }
    }

    @Override
    @Cacheable(cacheNames = "instructorCache", key = "#id")
    public ApiResponse<InstructorDto> getInstructorDetails(Long id) {
        if (instructorRepository.existsById(id)) {
            Instructor instructor = instructorRepository.findById(id).get();
            log.info("Instructor found successfully");
            InstructorDto instructorDTO = convertToDto(instructor);
            ;
            return new ApiResponse<>(HttpStatus.FOUND, "Instructor found successfully", instructorDTO);
        } else {
            log.info("Instructor with id " + id + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Instructor with the id " + id + " not found!", null);
        }
    }

    @Override
    public ApiResponse<Boolean> withdrawCourse(Long id) {

        if (id != null && instructorRepository.existsById(id)) {
            Instructor instructor = instructorRepository.findById(id).get();
            log.info("Instructor with ID exists: " + id);

            Course course = instructor.getCourse();
            if (course != null) {
                courseRepository.deleteById(course.getId());
                log.info("Course withdrew successfully");
                return new ApiResponse<>(HttpStatus.
                        ACCEPTED, "Course withdrew successfully", true);
            } else {
                log.info("Course not found in Database");
                throw new EmptyFieldException("Course not found");
            }
        } else {
            log.info("Instructor with id " + id + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Instructor with the id " + id + " not found!", false);
        }
    }

    @Override
    public ApiResponse<Status> getStudentStatus(Long id) {
        if (id != null && studentRepository.existsById(id)) {
            Status status = studentCourseRepository.findByStudent(studentRepository.findById(id).get()).getStatus();
            log.info("Student Status found!");
            return new ApiResponse<>(HttpStatus.FOUND, "Student Status found", status);
        } else {
            log.info("Student id is null or not found in the Database");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Student with the id " + id + " not found!", null);
        }
    }
}
