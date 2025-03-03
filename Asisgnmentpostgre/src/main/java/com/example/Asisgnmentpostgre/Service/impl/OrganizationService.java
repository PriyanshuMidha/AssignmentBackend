package com.example.Asisgnmentpostgre.Service.impl;

import com.example.Asisgnmentpostgre.DTO.AllDetailsByCourseId;
import com.example.Asisgnmentpostgre.DTO.InstructorDto;
import com.example.Asisgnmentpostgre.DTO.StudentCourseEnrollmentDTO;
import com.example.Asisgnmentpostgre.Entity.Course;
import com.example.Asisgnmentpostgre.Entity.Instructor;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Entity.StudentCourseEnrollment;
import com.example.Asisgnmentpostgre.Exception.TypeMismatchException;
import com.example.Asisgnmentpostgre.Repositories.CourseRepository;
import com.example.Asisgnmentpostgre.Repositories.InstructorRepository;
import com.example.Asisgnmentpostgre.Repositories.StudentCourseRepository;
import com.example.Asisgnmentpostgre.Repositories.StudentRepository;
import com.example.Asisgnmentpostgre.Response.ApiResponse;
import com.example.Asisgnmentpostgre.Service.OrganizationServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.Asisgnmentpostgre.Status.*;

@Slf4j
@Service
public class OrganizationService implements OrganizationServiceInterface {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Override
    public ApiResponse<Long> getStudentCount() {
        log.info("Student Count found");
        return new ApiResponse<>(HttpStatus.FOUND, "Student Count found", studentRepository.count());
    }

    @Override
    public ApiResponse<Integer> getCourseStudentCount(Long id) {
        if (id != null && courseRepository.existsById(id)) {
            Integer studentCount = studentCourseRepository.findStudentCountByCourse(id);
            log.info("Student Count found");

            return new ApiResponse<>(HttpStatus.FOUND, "Student Count found", studentCount);
        } else {
            log.info("Course id null or not found in Database");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course id null or not found in Database", null);
        }
    }

    @Override
    public ApiResponse<List<InstructorDto>> getCourseInstructors(Long id) {
        if (id != null && courseRepository.existsById(id)) {
            List<Instructor> instructors = instructorRepository.findAllByCourse(id);
            log.info("Instructors found");
            List<InstructorDto> instructorDTOs = instructors.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return new ApiResponse<>(HttpStatus.FOUND, "Instructors found", instructorDTOs);
        } else {
            log.info("Course id null or not found in Database");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course id null or not found in Database", null);
        }
    }

    private InstructorDto convertToDTO(Instructor instructor) {
        InstructorDto instructorDTO = new InstructorDto();
        instructorDTO.setName(instructor.getName());
        instructorDTO.setDateOfBirth(instructor.getDateOfBirth());
        if (instructor.getCourse() != null) {
            instructorDTO.setCourseId(instructor.getCourse().getId());
        }
        return instructorDTO;
    }

    @Override
    public ApiResponse<Long> getInstructorsCount() {
        log.info("Instructors Count found");
        return new ApiResponse<>(HttpStatus.FOUND, "Instructors Count found", instructorRepository.count());
    }

    @Override
    public ApiResponse<AllDetailsByCourseId> getAllDetailsByCourseId(Long id) {
        AllDetailsByCourseId allDetailsByCourseId = new AllDetailsByCourseId();

        if (id != null && courseRepository.existsById(id)) {
            Course course = courseRepository.findById(id).get();
            allDetailsByCourseId.setStudents(studentCourseRepository.findAllByCourse(course));
            allDetailsByCourseId.setCourse(course);
            allDetailsByCourseId.setInstructor(instructorRepository.findInstructorByCourse(course));
            log.info("Fetched all the details ");
            return new ApiResponse<>(HttpStatus.FOUND, "Fetched All the Details", allDetailsByCourseId);
        } else {
            log.info("Course id null or not found in Database");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course id null or not found in Database", null);
        }
    }

    @Override
    public ApiResponse<List<StudentCourseEnrollmentDTO>> getAllStudentsByStatus(Status status) {
        if (status.equals(TO_DO) || status.equals(IN_PROGRESS) || status.equals(COMPLETED)) {
            log.info("Students with status " + status + " found");
            List<StudentCourseEnrollmentDTO> studentCourseEnrollmentDTOs = studentCourseRepository.findAllByStatus(status)
                    .stream().map(this::convertToDTO)
                    .collect(Collectors.toList());
            return new ApiResponse<>(HttpStatus.FOUND, "Students with status " + status + " found", studentCourseEnrollmentDTOs);
        } else {
            log.info("Invalid Status");
            throw new TypeMismatchException("Invalid Status Type");
        }
    }

    private StudentCourseEnrollmentDTO convertToDTO(StudentCourseEnrollment studentCourseEnrollment) {
        StudentCourseEnrollmentDTO studentCourseEnrollmentDTO = new StudentCourseEnrollmentDTO();
        BeanUtils.copyProperties(studentCourseEnrollmentDTO, studentCourseEnrollment);
        return studentCourseEnrollmentDTO;
    }
}


