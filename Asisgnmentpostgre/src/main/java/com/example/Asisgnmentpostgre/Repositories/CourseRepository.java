package com.example.Asisgnmentpostgre.Repositories;

import com.example.Asisgnmentpostgre.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}

