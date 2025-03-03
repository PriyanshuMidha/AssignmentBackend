package com.example.Asisgnmentpostgre.Repositories;

import com.example.Asisgnmentpostgre.Entity.Course;
import com.example.Asisgnmentpostgre.Entity.Instructor;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Instructor findInstructorByCourse(Course course);

    @Query("SELECT i FROM Instructor i WHERE i.course.id = :courseId")
    List<Instructor> findAllByCourse(@Param("courseId") Long courseId);
}
