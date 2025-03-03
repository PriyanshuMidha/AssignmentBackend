package com.example.AsisgnmentMongo.Services;

import com.example.AsisgnmentMongo.DTO.InstructorDto;
import com.example.AsisgnmentMongo.Entities.Course;
import com.example.AsisgnmentMongo.Entities.CourseStatus;
import com.example.AsisgnmentMongo.Entities.Instructor;
import com.example.AsisgnmentMongo.Entities.Student;
import com.example.AsisgnmentMongo.Repositories.InstructorRepository;
import com.example.AsisgnmentMongo.Repositories.CourseRepository;
import com.example.AsisgnmentMongo.Repositories.StudentRepository;
import com.example.AsisgnmentMongo.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Register an Instructor
    public ApiResponse<Instructor> registerInstructor(InstructorDto instructorDto) {

        if (instructorRepository.existsById(instructorDto.getInstructorId())) {
            throw new RuntimeException("Instructor already exists.");
        }

        Instructor instructor = new Instructor();
        instructor.setInstructorId(instructorDto.getInstructorId());
        instructor.setInstructorName(instructorDto.getInstructorName());
        instructor.setDob(instructorDto.getDob());
        instructor.setCourseId(instructorDto.getCourseId());

        // Save the instructor
        instructorRepository.save(instructor);

        //return  instructor;
        log.info("Successfully added Instructor to the Database");
        return new ApiResponse<>(HttpStatus.CREATED, "Instructor added successfully", instructor);
    }

    // Update Instructor details
    public ApiResponse<Instructor>  updateInstructorDetails(String instructorId, InstructorDto updatedInstructorDto) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        if (!instructorOptional.isPresent()) {
            throw new RuntimeException("Instructor not found.");
        }

        Instructor instructor = instructorOptional.get();
        instructor.setInstructorName(updatedInstructorDto.getInstructorName());
        instructor.setDob(updatedInstructorDto.getDob());
        instructor.setCourseId(updatedInstructorDto.getCourseId());

        log.info("Successfully added Instructor to the Database");
        return new ApiResponse<>(HttpStatus.CREATED, "Instructor added successfully", instructor);
    }

    // Delete Instructor by ID
    public ApiResponse<Boolean> deleteInstructor(String instructorId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        Instructor instructor=new Instructor();
        Optional<Course> courseOptional = courseRepository.findById(instructor.getCourseId());


        if (!instructorOptional.isPresent()) {
            log.info("Instructor with id " + instructorId + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Instructor id doesn't exist . Delete failed!", false);
        }
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            // Remove the instructorId from the course
            course.setInstructorId("");
            courseRepository.save(course); // Save course with null instructorId
        }

        instructorRepository.deleteById(instructorId);
        log.info("Successfully deleted Instructor");
        return new ApiResponse<>(HttpStatus.ACCEPTED, "Instructor deleted successfully", true);
    }

    // Get Instructor details
    public ApiResponse<Instructor>  getInstructorDetails(String instructorId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        if(!instructorOptional.isPresent()){
            log.info("Instructor with id " + instructorId + " not found!");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Instructor with the id " + instructorId + " not found!", null);
        }
        log.info("Instructor found successfully");
        return new ApiResponse<>(HttpStatus.FOUND, "Instructor found successfully",instructorRepository.findById(instructorId).get() );
    }

    // Assign a course to the instructor
    public ApiResponse<Boolean>  assignCourseToInstructor(String instructorId, String courseId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (!instructorOptional.isPresent()) {
            log.info("Instructor with id " + instructorId + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Instructor id doesn't exist . Delete failed!", false);
        }
        if (!courseOptional.isPresent()) {
            log.info("Course with id " + courseId + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Course id doesn't exist . Delete failed!", false);
        }

        Instructor instructor = instructorOptional.get();
        // Check if the instructor is already assigned to a course
        if (instructor.getCourseId() != null && !instructor.getCourseId().isEmpty()) {
            log.info("Instructor with id " + courseId + "already assigned");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Instructor is already assigned to a course ", false);
        }

        Course course = courseOptional.get();
        course.setInstructorId(instructorId);
        courseRepository.save(course);

        instructor.setCourseId(courseId);
        instructorRepository.save(instructor);
        log.info("Instructor with id " + courseId + " assigned");
        return new ApiResponse<>(HttpStatus.FOUND, "Instructor is assigned to a course ", true);
    }

    // Unassign a course from the instructor
    public ApiResponse<Boolean> unassignCourseFromInstructor(String instructorId, String courseId) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (!instructorOptional.isPresent()) {
            log.info("Instructor with id " + instructorId + "doesn't exist");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Instructor id doesn't exist . Delete failed!", false);
        }

        Instructor instructor = instructorOptional.get();
        Course course = courseOptional.get();

        // Check if the instructor is assigned to the course
        if (instructor.getCourseId() == null || !instructor.getCourseId().equals(courseId)) {
            log.info("Instructor with id  not  assigned");
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "Instructor is not assigned to a course ", false);
        }
        course.setInstructorId("");
        courseRepository.save(course);

        instructor.setCourseId(null);  // Unassign the course
        instructorRepository.save(instructor);
        log.info("Course unassigned from instructor successfully");
        return new ApiResponse<>(HttpStatus.FOUND, "Instructor is unassigned from a course ", true);
    }
    public ApiResponse<Boolean> updateCourseStatusForStudent(String instructorId, String courseId, String studentId, CourseStatus status) {
//        if (!isInstructorAuthorized(instructorId, courseId)) {
//            throw new RuntimeException("Instructor is not authorized to update course status.");
//        }

        Optional<Student> optionalStudent = studentRepository.findById(studentId);
//        if (!optionalStudent.isPresent()) {
//            throw new RuntimeException("Student not found.");
//        }
        Student student = optionalStudent.get();
//
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (!optionalCourse.isPresent()) {
            throw new RuntimeException("Course not found.");
        }
        Course course = optionalCourse.get();

//        if (!student.getEnrolledCourseIds().contains(courseId)) {
//            throw new RuntimeException("Student is not enrolled in this course.");
//        }

        student.updateCourseStatus(courseId, status);
        studentRepository.save(student);


        log.info("Course status updated successfully for student " + studentId + " in course " + courseId);
        return new ApiResponse<>(HttpStatus.FOUND, "Course status updated successfully for student", true);
    }

}
