package com.example.AsisgnmentMongo.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "instructors")
public class Instructor  implements Serializable {

    @Id
    private String instructorId;
    private String instructorName;
    private Date dob;
    private String courseId;

//    public Instructor(String instructorId, String instructorName, Date dob, String courseId) {
//        this.instructorId = instructorId;
//        this.instructorName = instructorName;
//        this.dob = dob;
//        this.courseId = courseId;
//    }

}
