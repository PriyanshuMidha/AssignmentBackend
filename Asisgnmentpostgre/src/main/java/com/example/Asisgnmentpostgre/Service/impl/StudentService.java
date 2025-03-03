package com.example.Asisgnmentpostgre.Service.impl;

import com.example.Asisgnmentpostgre.DTO.StudentDto;
import com.example.Asisgnmentpostgre.Entity.Course;
import com.example.Asisgnmentpostgre.Status;
import com.example.Asisgnmentpostgre.Entity.Student;
import com.example.Asisgnmentpostgre.Entity.StudentCourseEnrollment;
import com.example.Asisgnmentpostgre.Exception.ActionAlreadyPerformedException;
import com.example.Asisgnmentpostgre.Exception.EmptyFieldException;
import com.example.Asisgnmentpostgre.Exception.TypeMismatchException;
import com.example.Asisgnmentpostgre.Repositories.CourseRepository;
import com.example.Asisgnmentpostgre.Repositories.StudentCourseRepository;
import com.example.Asisgnmentpostgre.Repositories.StudentRepository;
import com.example.Asisgnmentpostgre.Response.ApiResponse;
import com.example.Asisgnmentpostgre.Service.StudentServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.Asisgnmentpostgre.Status.*;

@Slf4j
@Service
public class StudentService implements StudentServiceInterface {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    private StudentDto convertToDto(Student student) {
        StudentDto studentDTO = new StudentDto();
        BeanUtils.copyProperties(student, studentDTO);
        List<Long> courseIds = new ArrayList<>();
        if (student.getCourseEnrollments() != null) {
            for (StudentCourseEnrollment enrollment : student.getCourseEnrollments()) {
                courseIds.add(enrollment.getCourse().getId());
            }
        }
        studentDTO.setCourseIds(courseIds);
        return studentDTO;
    }

    @Override
    public ApiResponse<Student> insertOrUpdate(StudentDto studentDTO) {
        Student student = new Student();
        if (!studentDTO.getName().isEmpty()) {
            student.setName(studentDTO.getName());
        } else {
            throw new EmptyFieldException("Student Name not Specified");
        }
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        List<StudentCourseEnrollment> studentCourseEnrollments = new ArrayList<>();
        for (Long courseId : studentDTO.getCourseIds()) {
            StudentCourseEnrollment studentCourseEnrollment = new StudentCourseEnrollment();
            studentCourseEnrollment.setStudent(student);
            studentCourseEnrollment.setCourse(courseRepository.findById(courseId).get());
            studentCourseEnrollment.setStatus(TO_DO);
            studentCourseEnrollments.add(studentCourseEnrollment);
        }
        student.setCourseEnrollments(studentCourseEnrollments);
        studentRepository.save(student);
        log.info("Successfully added/updated the student with id: " + student.getId());
        return new ApiResponse<>(HttpStatus.CREATED, "Student added successfully", student);
    }

   // @CacheEvict(cacheNames = "studentCache", key = "#student.id")
//    @CachePut(cacheNames = "studentCache", key = "#student.id")
//    private void saveStudent(Student student) {
//        studentRepository.save(student);
//    }


    @Override
    @CacheEvict(cacheNames = "studentCache", key = "#id")
    public ApiResponse<Boolean> deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            log.info("Student deleted Successfully");
            return new ApiResponse<>(HttpStatus.ACCEPTED, "Student deleted successfully", true);
        } else {
            log.info("Student with id " + id + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Student id doesn't exist . Delete failed!", false);
        }
    }

    @Cacheable(cacheNames = "studentCache", key = "#id")
    @Override
    public ApiResponse<StudentDto> getStudentDetails(Long id) {
        if (id != null && studentRepository.existsById(id)) {
            Student student = studentRepository.findById(id).get();
            StudentDto studentDTO = convertToDto(student);
            log.info("Student found successfully");
            return new ApiResponse<>(HttpStatus.FOUND, "Student found successfully", studentDTO);
        } else {
            log.info("Student with id " + id + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Student with the id " + id + " not found!", null);
        }
    }

    @Override
    public ApiResponse<StudentDto> deleteCourseById(Long courseId, Long studentId) {

        if (studentId != null && studentRepository.existsById(studentId)) {
            Student student = studentRepository.findById(studentId).get();
            log.info("Student with id " + studentId + " exists");

            if (courseId != null && courseRepository.existsById(courseId)) {
                Course course = courseRepository.findById(courseId).get();
                log.info("Course with id " + courseId + " exists");

                StudentCourseEnrollment studentCourseEnrollment = studentCourseRepository.findByStudentAndCourse(student, course);
                if (studentCourseEnrollment != null) {
                    studentCourseRepository.deleteById(studentCourseEnrollment.getId());
                    log.info("Course with id " + courseId + " has been deleted for student with id " + studentId);

                    StudentDto studentDTO = convertToDto(student);

                    return new ApiResponse<>(HttpStatus.ACCEPTED, "Course deleted Successfully", studentDTO);
                } else {
                    log.info("Student course enrollment not found");
                    throw new EmptyFieldException("No student-course enrollment found");
                }
            } else {
                log.info("courseId is null or not in the Database");
                throw new EmptyFieldException("CourseId is null or not found");
            }
        } else {
            log.info("studentId is null or not in the Database");
            throw new EmptyFieldException("StudentId is null or not found");
        }
    }

    @Override
    public ApiResponse<StudentDto> enrollCourse(Long id, Long courseId) {
        if (id != null && studentRepository.existsById(id)) {
            Student student = studentRepository.findById(id).get();
            log.info("Student with id " + id + " exists");

            StudentCourseEnrollment studentCourseEnrollment = new StudentCourseEnrollment();

            studentCourseEnrollment.setStudent(student);


            if (courseId != null && courseRepository.existsById(courseId)) {
                Course course = courseRepository.findById(courseId).get();
                log.info("found course with id " + courseId);

                if (studentCourseRepository.findByStudentAndCourse(student, course) == null) {
                    studentCourseEnrollment.setCourse(courseRepository.findById(courseId).get());
                    studentCourseRepository.save(studentCourseEnrollment);
                    log.info("Successfully enrolled course");
                } else {
                    log.info("Already enrolled");
                    throw new ActionAlreadyPerformedException("Already enrolled to course");
                }

                StudentDto studentDTO = convertToDto(student);

                return new ApiResponse<>(HttpStatus.ACCEPTED, "Successfully enrolled course", studentDTO);
            } else {
                log.info("courseId is null or not in the Database");
                throw new EmptyFieldException("CourseId is null or not found");
            }
        } else {
            log.info("studentId is null or not in the Database");
            throw new EmptyFieldException("StudentId is null or not found");
        }
    }

    @Override
    public ApiResponse<StudentDto> setStatus(Long studentId, Long courseId, Status status) {
        if (studentId != null && studentRepository.existsById(studentId)) {
            Student student = studentRepository.findById(studentId).get();
            Course course = courseRepository.findById(courseId).get();
            log.info("Student with id " + studentId + " found");

            if (status.equals(TO_DO) || status.equals(IN_PROGRESS) || status.equals(COMPLETED)) {
                StudentCourseEnrollment enrollment = studentCourseRepository.findByStudentAndCourse(student, course);

                if (enrollment != null) {
                    enrollment.setStatus(status);
                    studentCourseRepository.save(enrollment);
                    log.info("Student Course status updated successfully");
                    StudentDto studentDTO = convertToDto(student);
                    return new ApiResponse<>(HttpStatus.ACCEPTED, "Student Course status updated successfully", studentDTO);
                } else {
                    log.info("Student-Course relationship not found");
                    throw new TypeMismatchException("Student-Course relationship not found");
                }
            } else {
                log.info("Invalid Status");
                throw new TypeMismatchException("Invalid Status Type");
            }
        } else {
            log.info("studentId is null or not in the Database");
            throw new TypeMismatchException("StudentId is null or not found");
        }
    }
}