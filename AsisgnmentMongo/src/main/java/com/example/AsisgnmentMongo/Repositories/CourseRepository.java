package com.example.AsisgnmentMongo.Repositories;

import com.example.AsisgnmentMongo.Entities.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course,String> {
}
