package com.example.AsisgnmentMongo.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "courses")
public class Course  implements Serializable {

    @Id
    private String courseId;
    private String courseName;
    private double courseFee;
    private String instructorId;
    private List<String> enrolledStudentIds;

}
