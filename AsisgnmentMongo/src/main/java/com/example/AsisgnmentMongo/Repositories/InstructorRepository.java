package com.example.AsisgnmentMongo.Repositories;

import com.example.AsisgnmentMongo.Entities.Instructor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InstructorRepository extends MongoRepository<Instructor,String> {}
