package com.example.AsisgnmentMongo.Repositories;

import com.example.AsisgnmentMongo.Entities.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student,String> {
}
