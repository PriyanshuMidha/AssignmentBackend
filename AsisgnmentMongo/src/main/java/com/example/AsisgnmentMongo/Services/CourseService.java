package com.example.AsisgnmentMongo.Services;

import com.example.AsisgnmentMongo.DTO.CourseDto;
import com.example.AsisgnmentMongo.Entities.Course;
import com.example.AsisgnmentMongo.Entities.Instructor;
import com.example.AsisgnmentMongo.Entities.Student;
import com.example.AsisgnmentMongo.Repositories.CourseRepository;
import com.example.AsisgnmentMongo.Repositories.StudentRepository;
import com.example.AsisgnmentMongo.Repositories.InstructorRepository;
import com.example.AsisgnmentMongo.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    public ApiResponse<Course> addCourse(CourseDto courseDto) {
        Instructor instructor=new Instructor();
        Course course = new Course();
        course.setCourseId(courseDto.getCourseId());
        course.setCourseName(courseDto.getCourseName());
        course.setCourseFee(courseDto.getCourseFee());

        // if(instructorRepository.findById(instructor.getCourseId())==courseDto.getCourseId())
        course.setInstructorId(courseDto.getInstructorId());

        List<String> enrolledStudentIds = new ArrayList<>();
        List<Student> allStudents = studentRepository.findAll();

        for (Student student : allStudents) {
            if (student.getEnrolledCourseIds().contains(course.getCourseId())) {
                //  student.getEnrolledCourseIds().add(course.getCourseId());
                enrolledStudentIds.add(student.getStudentId());
                //studentRepository.save(student);
            }
        }

        course.setEnrolledStudentIds(enrolledStudentIds);
        courseRepository.save(course);

        log.info("Successfully added Course to the Database");
        return new ApiResponse<>(HttpStatus.CREATED, "Successfully created Course", course);
    }

    public ApiResponse<Course>  updateCourseDetails(String courseId, CourseDto updatedCourseDto) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            log.info("Course with id " + courseId + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course id doesn't exist . Delete failed!", null);

        }

        Course course = courseOptional.get();

        if (updatedCourseDto.getInstructorId() != null && !updatedCourseDto.getInstructorId().equals(course.getInstructorId())) {
            Optional<Instructor> instructorOptional = instructorRepository.findById(updatedCourseDto.getInstructorId());
            if (instructorOptional.isPresent()) {
                Instructor instructor = instructorOptional.get();
                course.setCourseName(instructor.getInstructorName() + " - " + updatedCourseDto.getCourseName());
            }
        }

        course.setCourseId(updatedCourseDto.getCourseId());
        course.setCourseName(updatedCourseDto.getCourseName());
        course.setCourseFee(updatedCourseDto.getCourseFee());
        course.setInstructorId(updatedCourseDto.getInstructorId());
        course.setEnrolledStudentIds(updatedCourseDto.getEnrolledStudentIds());

        courseRepository.save(course);
        log.info("Successfully updated Course to the Database");
        return new ApiResponse<>(HttpStatus.CREATED, "Successfully updated Course", course);
    }

    public ApiResponse<Boolean> deleteCourse(String courseId) {
        Optional<Course> existingCourseOpt = courseRepository.findById(courseId);
        if (!existingCourseOpt.isPresent()) {
            log.info("Course with id " + courseId + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course id doesn't exist . Delete failed!", false);
        }
        courseRepository.deleteById(courseId);

//        if (courseRepository.existsById(courseId)) {
//            return "Course deletion failed";
//        }

        log.info("Successfully deleted course");
        return new ApiResponse<>(HttpStatus.ACCEPTED, "Course deleted successfully", true);
    }

    public ApiResponse<Course> getCourseDetails(String courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(!courseOptional.isPresent())
        {
            log.info("Course with id " + courseId + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course with the id " + courseId + " not found!", null);
        }
        log.info("Course found successfully");
        return new ApiResponse<>(HttpStatus.FOUND, "Course found successfully",courseRepository.findById(courseId).get() );
    }
}
