package com.example.AsisgnmentMongo.Services;

import com.example.AsisgnmentMongo.DTO.StudentDto;
import com.example.AsisgnmentMongo.Entities.Course;
import com.example.AsisgnmentMongo.Entities.Student;
import com.example.AsisgnmentMongo.Entities.CourseStatus;
import com.example.AsisgnmentMongo.Repositories.CourseRepository;
import com.example.AsisgnmentMongo.Repositories.StudentRepository;
import com.example.AsisgnmentMongo.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public ApiResponse<Student> registerStudent(StudentDto studentDto) {
        if (studentRepository.existsById(studentDto.getStudentId())) {
            throw new RuntimeException("Instructor already exists.");
        }
        Student student = new Student();
        student.setStudentId(studentDto.getStudentId());
        student.setStudentName(studentDto.getStudentName());
        student.setDob(studentDto.getDob());
        student.setCourseStatuses(studentDto.getCourseStatuses());
        student.setEnrolledCourseIds(studentDto.getEnrolledCourseIds());
        studentRepository.save(student);
        log.info("Successfully added Studnt to the Database");
        return new ApiResponse<>(HttpStatus.CREATED, "Studnet added successfully", student);
    }

    public ApiResponse<Student>editStudentDetails(String studentId, StudentDto updatedStudentDto) {
        Optional<Student> existingStudentOpt = studentRepository.findById(studentId);
        if (!existingStudentOpt.isPresent()) {
            throw new RuntimeException("Student  not found.");
        }
        Student student=studentRepository.findById(studentId).get();
        student.setStudentId(updatedStudentDto.getStudentId());
        student.setStudentName(updatedStudentDto.getStudentName());
        student.setDob(updatedStudentDto.getDob());
        student.setCourseStatuses(updatedStudentDto.getCourseStatuses());
        student.setEnrolledCourseIds(updatedStudentDto.getEnrolledCourseIds());
        studentRepository.save(student);
        log.info("Successfully updated Student to the Database");
        return new ApiResponse<>(HttpStatus.CREATED, "Student added successfully", student);
    }

    public ApiResponse<Boolean> deleteStudentDetails(String studentId) {
        Optional<Student> existingStudentOpt = studentRepository.findById(studentId);
        Student student=new Student();
        for (String courseId : student.getEnrolledCourseIds()) {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                course.getEnrolledStudentIds().remove(studentId); // Remove student from course's enrolled list
                courseRepository.save(course); // Save updated course
            }
        }
        if (!existingStudentOpt.isPresent()) {
            log.info("Student with id " + studentId + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Student id doesn't exist . Delete failed!", false);
        }
        studentRepository.deleteById(studentId);
        log.info("Successfully deleted Student");
        return new ApiResponse<>(HttpStatus.ACCEPTED, "Student deleted successfully", true);
    }

    public ApiResponse<Student>  getStudentDetails(String studentId) {
        Optional<Student> existingStudentOpt = studentRepository.findById(studentId);
        if(!existingStudentOpt.isPresent())
        {
            log.info("Student with id " + studentId + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Student with the id " + studentId + " not found!", null);
        }
        log.info("Student found successfully");
        return new ApiResponse<>(HttpStatus.FOUND, "Student found successfully",studentRepository.findById(studentId).get() );
    }

    public ApiResponse<Boolean> enrollInCourse(String studentId, String courseId) {
        // Check if the course exists
        if (!courseRepository.existsById(courseId)) {
            log.info("Course with id " + courseId + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course id doesn't exist . Delete failed!", false);
        }

        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (!studentOpt.isPresent()) {
            log.info("Student with id " + studentId + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Student with the id " + studentId + " not found!", null);
        }

        Student student = studentOpt.get();
        if (student.getEnrolledCourseIds().contains(courseId)) {
            log.info("Student with id " + courseId + "already assigned");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Student is already assigned to a course ", false);
        }

        student.getEnrolledCourseIds().add(courseId);
        student.getCourseStatuses().add(CourseStatus.TO_DO);
        studentRepository.save(student);

        Course course = courseOpt.get();
        course.getEnrolledStudentIds().add(studentId);
        courseRepository.save(course);
        log.info("Student with id " + courseId + " assigned");
        return new ApiResponse<>(HttpStatus.FOUND, "Student is assigned to a course ", true);
    }

    public ApiResponse<Boolean> updateCourseStatus(String studentId, String courseId, CourseStatus status) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (!studentOpt.isPresent()) {
            log.info("Student with id " + studentId + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Student with the id " + studentId + " not found!", null);
        }
        Student student = studentOpt.get();
        int courseIndex = student.getEnrolledCourseIds().indexOf(courseId);
        if (courseIndex == -1) {
            log.info("Course with id " + courseId + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course id doesn't exist ", false);
        }
        student.getCourseStatuses().set(courseIndex, status);
        studentRepository.save(student);
        log.info("Course status updated successfully for student " + studentId + " in course " + courseId);
        return new ApiResponse<>(HttpStatus.FOUND, "Course status updated successfully for student", true);
    }

//    public void updateCourseStatus(String courseId, CourseStatus status) {
//        Student student=new Student();
//        for (int i = 0; i < student.getEnrolledCourseIds().size(); i++) {
//            if (student.getEnrolledCourseIds().get(i).equals(courseId)) {
//                student.getCourseStatuses().set(i, status);
//                return;
//            }
//        }
//        student.getEnrolledCourseIds().add(courseId);
//        student.getCourseStatuses().add(status);
//    }

    public ApiResponse<Boolean> withdrawFromCourse(String studentId, String courseId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (!studentOpt.isPresent()) {
            log.info("Student with id " + studentId + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Student with the id " + studentId + " not found!", null);
        }
        Student student = studentOpt.get();
        int courseIndex = student.getEnrolledCourseIds().indexOf(courseId);
        if (courseIndex == -1) {
            log.info("Course with id  was not found");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course with id  was not found", false);
        }
        student.getEnrolledCourseIds().remove(courseIndex);
        student.getCourseStatuses().remove(courseIndex);
        studentRepository.save(student);

        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.getEnrolledStudentIds().remove(studentId); // Remove student from course's enrolled list
            courseRepository.save(course); // Save updated course
        }

        log.info("Student withdrawn from course successfully");
        return new ApiResponse<>(HttpStatus.FOUND, "Student withdrawn from course successfully", true);
    }
}
