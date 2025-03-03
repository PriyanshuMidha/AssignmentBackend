package com.example.AsisgnmentMongo.Services;

import com.example.AsisgnmentMongo.DTO.InstructorDto;
import com.example.AsisgnmentMongo.Entities.Course;
import com.example.AsisgnmentMongo.Entities.Instructor;
import com.example.AsisgnmentMongo.Entities.Student;
import com.example.AsisgnmentMongo.Repositories.CourseRepository;
import com.example.AsisgnmentMongo.Repositories.InstructorRepository;
import com.example.AsisgnmentMongo.Repositories.StudentRepository;
import com.example.AsisgnmentMongo.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrganizationService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;


    public ApiResponse<Long> getStudentCount() {
        return new ApiResponse<>(HttpStatus.FOUND, "Student Count found", studentRepository.count());
    }

    public ApiResponse<Integer> getCourseStudentCount(String courseId) {
        List<Student> students = studentRepository.findAll();
        int count = 0;
        for (Student student : students) {
            if (student.getEnrolledCourseIds().contains(courseId)) {
                count++;
            }
        }
        log.info("Student Count found");

        return new ApiResponse<>(HttpStatus.FOUND, "Student Count found", count);
    }


    public  ApiResponse<List<Instructor>> getCourseInstructors(String courseId) {
        return new ApiResponse<>(HttpStatus.FOUND, "Instructors found", instructorRepository.findAll());
    }

    public ApiResponse<Long> getInstructorsCount() {
        log.info("Instructors Count found");
        return new ApiResponse<>(HttpStatus.FOUND, "Instructors Count found", instructorRepository.count());
    }

    // Get all details by Course ID (students, course, and instructors)
//    public Object getAllDetailsByCourseId(String courseId) {
//        Optional<Course> courseOptional = courseRepository.findById(courseId);
//        if (courseOptional.isPresent()) {
//            Course course = courseOptional.get();
//            List<Student> enrolledStudents = studentRepository.findAll();
//            enrolledStudents.removeIf(student -> !student.getEnrolledCourseIds().contains(courseId));
//
//            List<Instructor> instructors = instructorRepository.findAll();
//            instructors.removeIf(instructor -> !instructor.getCourseId().equals(courseId));
//
//            // Creating a response with all the details
//            return new Object() {
//                public Course course = course;
//                public List<Student> students = enrolledStudents;
//                public List<Instructor> instructors = instructors;
//            };
//        } else {
//            return null; // Or you can throw an exception if you prefer
//        }
//    }
//
//    // Get all students by a particular course status
//    public List<Student> getStudentsByCourseStatus(CourseStatus status) {
//        List<Student> students = studentRepository.findAll();
//        students.removeIf(student -> {
//            boolean hasStatus = false;
//            for (int i = 0; i < student.getEnrolledCourseIds().size(); i++) {
//                if (student.getCourseStatuses().get(i).equals(status)) {
//                    hasStatus = true;
//                    break;
//                }
//            }
//            return !hasStatus;
//        });
//        return students;
//    }
}
